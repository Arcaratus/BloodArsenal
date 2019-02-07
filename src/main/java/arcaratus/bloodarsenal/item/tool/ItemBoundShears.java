package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.iface.IActivatable;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.*;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.function.Consumer;

public class ItemBoundShears extends ItemShears implements IBindable, IActivatable, IMeshProvider
{
    public static final Set<Block> EFFECTIVE_BLOCKS = Sets.newHashSet(Blocks.WEB, Blocks.TALLGRASS, Blocks.VINE, Blocks.TRIPWIRE, Blocks.WOOL);

    public ItemBoundShears(String name)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + ".bound.shears");
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (I18n.hasKey("tooltip.bloodarsenal.bound.shears.desc"))
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.bound.shears.desc"));

        tooltip.add(TextHelper.localize("tooltip.bloodmagic." + (getActivated(stack) ? "activated" : "deactivated")));

        if (!stack.hasTagCompound())
            return;

        Binding binding = getBinding(stack);
        if (binding != null)
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        NetworkHelper.syphonFromContainer(stack, SoulTicket.item(stack, worldIn, entityLiving, ConfigHandler.values.boundShearsCost));
        Block block = state.getBlock();
        if (block instanceof IShearable) return true;
        return state.getMaterial() != Material.LEAVES || !EFFECTIVE_BLOCKS.contains(block);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand)
    {
        if (entity.world.isRemote)
            return false;

        if (entity instanceof IShearable)
        {
            IShearable target = (IShearable) entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            if (target.isShearable(itemstack, entity.world, pos))
            {
                List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));

                Random rand = new Random();
                for (ItemStack stack : drops)
                {
                    EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }

                NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(itemstack, player.world, player, ConfigHandler.values.boundShearsCost));
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        if (player.world.isRemote || player.capabilities.isCreativeMode)
            return false;

        Block block = player.world.getBlockState(pos).getBlock();
        if (block instanceof IShearable)
        {
            IShearable target = (IShearable) block;
            if (target.isShearable(itemstack, player.world, pos))
            {
                List<ItemStack> drops = target.onSheared(itemstack, player.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack));
                Random rand = new Random();

                for (ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d  = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(player.world, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
                    entityitem.setDefaultPickupDelay();
                    player.world.spawnEntity(entityitem);
                }

                NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(itemstack, player.world, player, ConfigHandler.values.boundShearsCost));
                player.addStat(StatList.getBlockStats(block));
                player.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11); //TODO: Move to IShearable implementors in 1.12+
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking())
            setActivatedState(stack, !getActivated(stack));

        // TODO implement a charge effect
//        if (!player.isSneaking() && getActivated(stack))
//        {
//            BoundToolEvent.Charge event = new BoundToolEvent.Charge(player, stack);
//            if (MinecraftForge.EVENT_BUS.post(event))
//                return new ActionResult<>(EnumActionResult.FAIL, event.result);
//
//            player.setActiveHand(hand);
//            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
//        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("bound_shears");
    }

    @Override
    public void gatherVariants(Consumer<String> variants)
    {
        variants.accept("active=true");
        variants.accept("active=false");
    }
}
