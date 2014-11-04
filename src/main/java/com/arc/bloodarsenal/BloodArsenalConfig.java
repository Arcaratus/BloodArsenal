package com.arc.bloodarsenal;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class BloodArsenalConfig
{
    public static Configuration config;

	public static boolean exampleBoolean;

    public static void init(File configFile)
    {
        config = new Configuration(configFile);

        try
        {
            config.load();
	        syncConfig();

	        config.addCustomCategoryComment("Example Section", "This is an example section. Isn't it wonderful?");
        }
        catch (Exception e)
        {
	        BloodArsenal.logger.error("Configuration loading has failed. Please report this.");
			BloodArsenal.logger.error(e);
        }
        finally
        {
            config.save();
        }
    }

	public static void syncConfig()
	{
		// Options go here

		exampleBoolean = config.get("Example Section", "exampleBoolean", true, "This is an example.").getBoolean(exampleBoolean);

		config.save();
	}
}
