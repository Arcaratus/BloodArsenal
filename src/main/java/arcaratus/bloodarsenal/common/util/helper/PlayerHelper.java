package arcaratus.bloodarsenal.common.util.helper;

import arcaratus.bloodarsenal.common.ConfigHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;

public class PlayerHelper
{
    public static PlayerEntity getPlayerFromId(UUID uuid)
    {
        return ServerLifecycleHooks.getCurrentServer() == null ? null : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(uuid);
    }

    public static PlayerEntity getPlayerFromUUID(UUID uuid)
    {
        return getPlayerFromId(uuid);
    }

    public static UUID getUUIDFromPlayer(PlayerEntity player)
    {
        return player.getGameProfile().getId();
    }

    public static String getUsernameFromUUID(UUID uuid)
    {
        return UsernameCache.getLastKnownUsername(uuid);
    }

    public static boolean isFakePlayer(PlayerEntity player)
    {
        return wayoftime.bloodmagic.util.helper.PlayerHelper.isFakePlayer(player);
    }

    /**
     * Modified raytrace method to first return an EntityRayTraceResult or else a BlockRayTraceResult
     */
    public static RayTraceResult rayTrace(World world, PlayerEntity player, RayTraceContext.FluidMode fluidMode)
    {
        if (world == null || player == null)
            return null;

        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d start = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double range = ConfigHandler.COMMON.rayTraceRange.get();
        Vector3d end = start.add((double) f6 * range, (double) f5 * range, (double) f7 * range);

        AxisAlignedBB aabb = player.getBoundingBox().expand(end).grow(1);
        RayTraceResult entityRayTrace = ProjectileHelper.rayTraceEntities(player, start, end, aabb, e -> e instanceof LivingEntity, range);

        return entityRayTrace != null ? entityRayTrace : world.rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }
}
