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
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.function.Consumer;

public class ItemBoundIgniter extends Item implements IBindable, IActivatable, IMeshProvider
{
    public ItemBoundIgniter(String name)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + ".bound.igniter");
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (I18n.hasKey("tooltip.bloodarsenal.bound.igniter.desc"))
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.bound.igniter.desc"));

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
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!player.getHeldItem(hand).isEmpty() && getActivated(player.getHeldItem(hand)))
        {
            pos = pos.offset(facing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (!player.canPlayerEdit(pos, facing, itemstack))
            {
                return EnumActionResult.FAIL;
            }
            else
            {
                if (worldIn.isAirBlock(pos))
                {
                    worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                    worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
                }

                if (player instanceof EntityPlayerMP)
                    CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);

                NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(itemstack, worldIn, player, ConfigHandler.values.boundIgniterCost));
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (getActivated(stack) && target != null && !target.isDead && attacker instanceof EntityPlayer)
        {
            target.setFire(4);
            NetworkHelper.getSoulNetwork((EntityPlayer) attacker).syphonAndDamage((EntityPlayer) attacker, SoulTicket.item(stack, attacker.world, attacker, 2 * ConfigHandler.values.boundIgniterCost));
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("bound_igniter");
    }

    @Override
    public void gatherVariants(Consumer<String> variants)
    {
        variants.accept("active=true");
        variants.accept("active=false");
    }
}
