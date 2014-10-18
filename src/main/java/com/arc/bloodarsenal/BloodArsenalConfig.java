package com.arc.bloodarsenal;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class BloodArsenalConfig
{
    public static Configuration config;

    public static void init(File configFile)
    {
        config = new Configuration(configFile);

        try
        {
            config.load();
        }
        catch (Exception e)
        {

        }
        finally
        {
            config.save();
        }
    }
}
