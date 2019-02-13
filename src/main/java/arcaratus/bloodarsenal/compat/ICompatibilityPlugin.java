package arcaratus.bloodarsenal.compat;

import net.minecraftforge.common.config.Configuration;

public interface ICompatibilityPlugin
{
    default void handleConfiguration(Configuration configuration) {}

    default void preInit() {}

    default void init() {}

    default void postInit() {}
}