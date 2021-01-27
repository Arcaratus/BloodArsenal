package arcaratus.bloodarsenal.common.core;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

/**
 * Code partially adapted from Botania
 * https://github.com/Vazkii/Botania
 */
public interface IProxy
{
    default void registerHandlers() {}

    default boolean isTheClientPlayer(LivingEntity entity)
    {
        return false;
    }

    default PlayerEntity getClientPlayer()
    {
        return null;
    }

    default long getWorldElapsedTicks()
    {
        return ServerLifecycleHooks.getCurrentServer().getWorld(World.OVERWORLD).getGameTime();
    }
}
