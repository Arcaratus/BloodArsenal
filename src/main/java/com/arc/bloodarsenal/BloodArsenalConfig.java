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
            syncConfig();
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
//        BloodArsenal.vampiricAuraID = config.get("Potion ID", "Vampiric Aura", 150).getInt();
//        BloodArsenal.bleedingID = config.get("Potion ID", "Bleeding", 151).getInt();
//        BloodArsenal.swimmingID = config.get("Potion ID", "Swimming", 122).getInt();

        BloodArsenal.diamondToolsAllowed = config.get("Tool Settings", "Are Infused Diamond tools allowed", true).getBoolean();

        BloodArsenal.ritualDisabledWither = config.get("Ritual Blacklist", "Ritual of Withering", false).getBoolean(false);
        BloodArsenal.ritualDisabledMidas = config.get("Ritual Blacklist", "Midas Touch", false).getBoolean(false);

        BloodArsenal.isRedGood = config.get("Misc", "Is RED > PURPLE?", false).getBoolean(false);

        config.save();
    }
}
