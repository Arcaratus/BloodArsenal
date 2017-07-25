package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.ItemStackWrapper;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import arc.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import arc.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static arc.bloodarsenal.registry.Constants.Item.SICKLE_EFFECTIVE_ON;

public class ItemBoundSickle extends ItemBoundTool
{
    private final int RANGE = 3;

    public ItemBoundSickle()
    {
        super("sickle", 5, SICKLE_EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state)
    {
        return state.getBlock() instanceof IPlantable || state.getBlock() instanceof IShearable;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        return state.getBlock() instanceof IPlantable ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, state);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemStack, BlockPos pos, EntityPlayer player)
    {
        World world = player.worldObj;
        IBlockState state = world.getBlockState(pos);

        boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) > 0;
        int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);

        if (!world.isRemote && canHarvestBlock(state))
        {
            Iterable<MutableBlockPos> positions = BlockPos.getAllInBoxMutable(pos.add(-RANGE, -RANGE, -RANGE), pos.add(RANGE, RANGE, RANGE));
            HashMultiset<ItemStackWrapper> drops = HashMultiset.create();

            for (MutableBlockPos blockPos : positions)
            {
                BlockStack blockStack = BlockStack.getStackFromPos(world, blockPos);

                if (blockStack.getBlock().isAir(blockStack.getState(), world, blockPos))
                    continue;

                if (!SICKLE_EFFECTIVE_ON.contains(blockStack.getBlock()))
                    continue;

                BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, blockPos, blockStack.getState(), player);
                if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
                    continue;

                if (blockStack.getBlock().getBlockHardness(blockStack.getState(), world, blockPos) != -1.0F)
                {
                    float strengthVsBlock = getStrVsBlock(itemStack, blockStack.getState());

                    if (strengthVsBlock > 1.1F && world.canMineBlockBody(player, blockPos))
                    {
                        if (silkTouch)
                        {
                            if (blockStack.getBlock() instanceof IShearable)
                            {
                                for (ItemStack stack : ((IShearable) blockStack.getBlock()).onSheared(itemStack, world, blockPos, fortuneLvl))
                                {
                                    drops.add(ItemStackWrapper.getHolder(stack));
                                }
                            }
                            else if (blockStack.getBlock().canSilkHarvest(world, blockPos, world.getBlockState(blockPos), player))
                            {
                                drops.add(new ItemStackWrapper(blockStack.getBlock()));
                            }
                            else
                            {
                                List<ItemStack> itemDrops = blockStack.getBlock().getDrops(world, blockPos, world.getBlockState(blockPos), fortuneLvl);

                                for (ItemStack stacks : itemDrops)
                                {
                                    drops.add(ItemStackWrapper.getHolder(stacks));
                                }
                            }
                        }
                        else
                        {
                            List<ItemStack> itemDrops = blockStack.getBlock().getDrops(world, blockPos, world.getBlockState(blockPos), fortuneLvl);

                            for (ItemStack stacks : itemDrops)
                            {
                                drops.add(ItemStackWrapper.getHolder(stacks));
                            }
                        }

                        world.setBlockToAir(blockPos);
                        BloodArsenalUtils.dropStacks(drops, world, blockPos);
                        drops.clear();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState block, BlockPos pos, EntityLivingBase entityLiving)
    {
        return true;
    }

    @Override
    protected void onBoundRelease(ItemStack itemStack, World world, EntityPlayer player, int charge)
    {
        if (world.isRemote)
            return;

        boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) > 0;
        int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
        int range = charge / 3;

        HashMultiset<ItemStackWrapper> drops = HashMultiset.create();
        BlockPos playerPos = player.getPosition();

        for (int i = -range; i <= range; i++)
        {
            for (int j = 0; j <= 2 * range; j++)
            {
                for (int k = -range; k <= range; k++)
                {
                    BlockPos blockPos = playerPos.add(i, j, k);
                    BlockStack blockStack = BlockStack.getStackFromPos(world, blockPos);

                    if (blockStack.getBlock().isAir(blockStack.getState(), world, blockPos))
                        continue;

                    if (!SICKLE_EFFECTIVE_ON.contains(blockStack.getBlock()))
                        continue;

                    BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, blockPos, blockStack.getState(), player);
                    if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
                        continue;

                    if (blockStack.getBlock().getBlockHardness(blockStack.getState(), world, blockPos) != -1.0F)
                    {
                        float strengthVsBlock = getStrVsBlock(itemStack, blockStack.getState());

                        if (strengthVsBlock > 1.1F && world.canMineBlockBody(player, blockPos))
                        {
                            if (silkTouch)
                            {
                                if (blockStack.getBlock() instanceof IShearable)
                                {
                                    for (ItemStack stack : ((IShearable) blockStack.getBlock()).onSheared(itemStack, world, blockPos, fortuneLvl))
                                    {
                                        drops.add(ItemStackWrapper.getHolder(stack));
                                    }
                                }
                                else if (blockStack.getBlock().canSilkHarvest(world, blockPos, world.getBlockState(blockPos), player))
                                {
                                    drops.add(new ItemStackWrapper(blockStack.getBlock()));
                                }
                            }
                            else
                            {
                                List<ItemStack> itemDrops = blockStack.getBlock().getDrops(world, blockPos, world.getBlockState(blockPos), fortuneLvl);

                                for (ItemStack stacks : itemDrops)
                                {
                                    drops.add(ItemStackWrapper.getHolder(stacks));
                                }
                            }

                            world.setBlockToAir(blockPos);
                        }
                    }
                }
            }
        }

        NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, (int) (charge * charge * charge / 2.7));
        world.createExplosion(player, playerPos.getX(), playerPos.getY(), playerPos.getZ(), 0.1F, false);
        BloodArsenalUtils.dropStacks(drops, world, playerPos.add(0, 1, 0));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!player.canPlayerEdit(pos.offset(facing), facing, itemStack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(itemStack, player, world, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up()))
            {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
                {
                    BloodArsenalUtils.tillBlock(itemStack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT)
                {
                    switch (iblockstate.getValue(BlockDirt.VARIANT))
                    {
                        case DIRT:
                            BloodArsenalUtils.tillBlock(itemStack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            BloodArsenalUtils.tillBlock(itemStack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getActivated(stack) ? 6 : 2, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3.0, 0));
        }
        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("ItemBoundSickle");
    }
}
