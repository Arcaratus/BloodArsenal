package arcaratus.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemSigilEnder extends ItemSigilBase
{
    public ItemSigilEnder()
    {
        super("ender", ConfigHandler.values.sigilEnderOpenCost);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (getDelay(stack) > 0)
            reduceDelay(stack);

        BlockPos cachedPos = BloodArsenalUtils.getPosFromStack(stack);
        if (cachedPos != BlockPos.ORIGIN && getDelay(stack) == 0 && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            world.spawnParticle(EnumParticleTypes.PORTAL, player.posX + (world.rand.nextDouble() - 0.5D) * (double) player.width, player.posY + world.rand.nextDouble() * (double) player.height - 0.25D, player.posZ + (world.rand.nextDouble() - 0.5D) * (double) player.width,  (itemRand.nextDouble() - 0.5D) * 2.0D, -itemRand.nextDouble(), (itemRand.nextDouble() - 0.5D) * 2.0D, new int[0]);
            world.playSound(player, player.prevPosX, player.prevPosY, player.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.HOSTILE, 1.0F, 1.0F);
            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            player.fallDistance = 0;
            player.setPositionAndUpdate(cachedPos.getX() + 0.5, cachedPos.getY() + 0.5, cachedPos.getZ() + 0.5);
            player.getCooldownTracker().setCooldown(this, ConfigHandler.values.sigilEnderTeleportationCooldown);
            reduceDelay(stack);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = NBTHelper.checkNBT(player.getHeldItem(hand));
        if (PlayerHelper.isFakePlayer(player))
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        RayTraceResult mop = BloodArsenalUtils.rayTrace(world, player, false);

        if (getDelay(stack) > 0)
            return super.onItemRightClick(world, player, hand);

        if (player.isSneaking() && mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            BlockPos blockPos = mop.getBlockPos().offset(mop.sideHit);

            double distance = player.getPosition().getDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            if (NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(stack, world, player, (int) (distance * ConfigHandler.values.sigilEnderTeleportMultiplier))).isSuccess() || player.capabilities.isCreativeMode)
            {
                BloodArsenalUtils.writePosToStack(stack, blockPos);
                resetDelay(stack);
                player.getCooldownTracker().setCooldown(this, 1000);
            }
        }
        else if (!player.isSneaking())
        {
            player.displayGUIChest(player.getInventoryEnderChest());

            if (!world.isRemote)
                NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(stack, world, player, getLpUsed()));
        }

        return super.onItemRightClick(world, player, hand);
    }

    public int getDelay(ItemStack stack)
    {
        return NBTHelper.checkNBT(stack).getTagCompound().getInteger(Constants.NBT.DELAY);
    }

    public void reduceDelay(ItemStack stack)
    {
        NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.DELAY, getDelay(stack) - 1);
    }

    public void resetDelay(ItemStack stack)
    {
        NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.DELAY, ConfigHandler.values.sigilEnderTeleportationDelay);
    }
}
