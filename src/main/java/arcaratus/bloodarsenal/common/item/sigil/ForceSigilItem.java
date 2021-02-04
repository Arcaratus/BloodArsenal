package arcaratus.bloodarsenal.common.item.sigil;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.util.Utils;
import arcaratus.bloodarsenal.common.util.helper.PlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wayoftime.bloodmagic.common.item.sigil.ISigil;

public class ForceSigilItem extends SigilBaseItem
{
    public ForceSigilItem(Properties properties)
    {
        super(properties, ConfigHandler.COMMON.forceSigilCost.get());
    }

//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
//    {
//        ItemStack stack = player.getHeldItem(hand);
//        if (stack.getItem() instanceof ISigil.Holding)
//            stack = ((Holding) stack.getItem()).getHeldItem(stack, player);
//        if (PlayerHelper.isFakePlayer(player) || getBinding(stack) == null)
//            return ActionResult.resultFail(stack);
//
//        return ActionResult.resultPass(stack);
//    }

    // Block right click
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ItemStack stack = context.getItem();
        if (stack.getItem() instanceof ISigil.Holding)
            stack = ((Holding) stack.getItem()).getHeldItem(stack, context.getPlayer());
        if (PlayerHelper.isFakePlayer(context.getPlayer()) || getBinding(stack) == null)
            return ActionResultType.FAIL;

        World world = context.getWorld();
        System.out.println("0");
        if (!world.isRemote())
        {
            BlockPos targetPos = context.getPos();
            BlockState targetState = world.getBlockState(targetPos);
            Block targetBlock = targetState.getBlock();
            Direction direction = context.getFace();
            System.out.println("1");
            if (targetBlock != Blocks.AIR)
            {
                System.out.println("2");
                if (Utils.canPush(targetState, world, targetPos, direction, false, direction.getOpposite()))
                {
                    System.out.println("3");
                    BlockPos destPos = targetPos.offset(direction);
                    BlockState destState = world.getBlockState(destPos);
                    if (destState.getBlock() == Blocks.AIR || !destState.getMaterial().blocksMovement() || destState.getMaterial().isReplaceable())
                    {
                        System.out.println("4");
                        TileEntity tileEntity = world.getTileEntity(targetPos);
                        CompoundNBT tileNBT = null;
                        if (tileEntity != null)
                        {
                            tileNBT = tileEntity.getTileData();
                        }
                        world.setBlockState(targetPos, Blocks.AIR.getDefaultState());
                        world.setBlockState(destPos, targetState, 3);
                        if (tileNBT != null)
                        {
                            world.setTileEntity(destPos, TileEntity.readTileEntity(targetState, tileNBT));
                        }

                        world.notifyNeighborsOfStateChange(destPos, targetBlock);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }

        return ActionResultType.PASS;
    }

    // Right click entity
    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        return ActionResultType.PASS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
    {
        return false;
    }

    @Override
    public void onUse(World worldIn, LivingEntity entityLiving, ItemStack stack, int count)
    {
    }
}
