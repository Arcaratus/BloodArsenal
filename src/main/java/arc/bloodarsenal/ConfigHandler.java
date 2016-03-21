package arc.bloodarsenal;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler
{
    private static Configuration config;

    public static void init(File file)
    {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig()
    {
        config.save();
    }
}
