package arc.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemSigilEnder extends ItemSigilBase
{
    public ItemSigilEnder()
    {
        super("ender", ConfigHandler.sigilEnderOpenCost);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (getCooldownRemainder(stack) > 0)
            reduceCooldown(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        stack = NBTHelper.checkNBT(stack);
        RayTraceResult mop = BloodArsenalUtils.rayTrace(world, player, false);

        if (getCooldownRemainder(stack) > 0)
            return super.onItemRightClick(stack, world, player, hand);

        if (player.isSneaking() && mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            BlockPos blockPos = mop.getBlockPos().offset(mop.sideHit);

            double distance = player.getPosition().getDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            if (NetworkHelper.syphonAndDamage(NetworkHelper.getSoulNetwork(player), player, (int) distance * ConfigHandler.sigilEnderTeleportMultiplier) || player.capabilities.isCreativeMode)
            {
                world.spawnParticle(EnumParticleTypes.PORTAL, player.posX + (world.rand.nextDouble() - 0.5D) * (double) player.width, player.posY + world.rand.nextDouble() * (double) player.height - 0.25D, player.posZ + (world.rand.nextDouble() - 0.5D) * (double) player.width,  (itemRand.nextDouble() - 0.5D) * 2.0D, -itemRand.nextDouble(), (itemRand.nextDouble() - 0.5D) * 2.0D, new int[0]);
                world.playSound(player, player.prevPosX, player.prevPosY, player.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
                player.fallDistance = 0;
                player.setPositionAndUpdate(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                resetCooldown(stack);
                player.swingArm(hand);
            }
        }
        else if (!player.isSneaking())
        {
            player.displayGUIChest(player.getInventoryEnderChest());

            if (!world.isRemote)
                NetworkHelper.syphonAndDamage(NetworkHelper.getSoulNetwork(player), player, getLpUsed());

            resetCooldown(stack);
        }

        return super.onItemRightClick(stack, world, player, hand);
    }

    public int getCooldownRemainder(ItemStack stack)
    {
        return NBTHelper.checkNBT(stack).getTagCompound().getInteger(Constants.NBT.TICKS_REMAINING);
    }

    public void reduceCooldown(ItemStack stack)
    {
        NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.TICKS_REMAINING, getCooldownRemainder(stack) - 1);
    }

    public void resetCooldown(ItemStack stack)
    {
        NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.TICKS_REMAINING, 10);
    }
}
