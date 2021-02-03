package arcaratus.bloodarsenal.common.item.sigil;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.util.helper.PlayerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import wayoftime.bloodmagic.common.item.sigil.ISigil;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

public class EnderSigilItem extends SigilBaseItem
{
    public EnderSigilItem(Properties properties)
    {
        super(properties, ConfigHandler.COMMON.enderSigilCost.get());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ISigil.Holding)
            stack = ((Holding) stack.getItem()).getHeldItem(stack, player);
        if (PlayerHelper.isFakePlayer(player) || getBinding(stack) == null)
            return ActionResult.resultFail(stack);

        RayTraceResult mop = PlayerHelper.rayTrace(world, player, RayTraceContext.FluidMode.NONE);
        if (player.isSneaking() && mop != null)
        {
            Vector3d hitVec = mop.getHitVec();
            Vector3d dispVec = hitVec.subtract(player.getPositionVec());

            if (NetworkHelper.getSoulNetwork(getBinding(stack)).syphonAndDamage(player, SoulTicket.item(stack, world, player, (int) dispVec.length() * ConfigHandler.COMMON.enderSigilTeleportMultiplier.get())).isSuccess() || player.isCreative())
            {
                if (!world.isRemote)
                {
                    player.fallDistance = 0;
                    Vector3d correction = dispVec.normalize().mul(-0.5, 0, -0.5);
                    hitVec = hitVec.add(correction);
                    player.setPositionAndUpdate(hitVec.getX(), hitVec.getY(), hitVec.getZ());
                }

                world.addParticle(ParticleTypes.PORTAL, player.getPosX() + (world.rand.nextDouble() - 0.5D) * (double) player.getWidth(), player.getPosY() + world.rand.nextDouble() * (double) player.getHeight() - 0.25D, player.getPosZ() + (world.rand.nextDouble() - 0.5D) * (double) player.getWidth(),(random.nextDouble() - 0.5D) * 2.0D, - random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
                world.playSound(player, player.prevPosX, player.prevPosY, player.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
                world.playSound(player, hitVec.getX(), hitVec.getY(), hitVec.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
                player.getCooldownTracker().setCooldown(this, ConfigHandler.COMMON.enderSigilTeleportCooldown.get());
                return ActionResult.resultSuccess(stack);
            }
        }
        else if (NetworkHelper.getSoulNetwork(getBinding(stack)).syphonAndDamage(player, SoulTicket.item(stack, world, player, getLpUsed())).isSuccess())
        {
            if (!world.isRemote)
            {
                player.openContainer(new SimpleNamedContainerProvider((windowId, playerInv, p) -> ChestContainer.createGeneric9X3(windowId, playerInv, p.getInventoryEnderChest()), stack.getDisplayName()));
            }

            player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, 1, 1);
            return ActionResult.resultSuccess(stack);
        }

        return ActionResult.resultPass(stack);
    }
}
