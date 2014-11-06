package com.arc.bloodarsenal;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class BloodArsenalConfig
{
    public static Configuration config;

	// Config Categories
	public static String misc = "Miscellaneous";
	public static String potionid = "Potion ID";
	public static String ritualblacklist = "Ritual Blacklist";
	public static String toolsetting = "Tool Settings";

	// Config Options
	// Potion ID's
	public static int bleedingID;
	public static int swimmingID;
	public static int vampiricAuraID;

	// Tool Settings
	public static boolean diamondToolsAllowed;

	// Ritual Blacklist
	public static boolean ritualDisabledMidas;
	public static boolean ritualDisabledWither;

	// Miscellaneous
	public static boolean isRedGood;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		syncConfig();
	}

    public static void syncConfig()
    {
	    config.addCustomCategoryComment(potionid, "Change potion ID's here if you have conflicts");
	    config.addCustomCategoryComment(toolsetting, "Settings for various tools");
	    config.addCustomCategoryComment(ritualblacklist, "Blacklist rituals that you don't want/like.");
	    config.addCustomCategoryComment(misc, "Random things'n'stuff.");

        vampiricAuraID = config.get(potionid, "Vampiric Aura", 30).getInt(vampiricAuraID);
        bleedingID = config.get(potionid, "Bleeding", 31).getInt(bleedingID);
        swimmingID = config.get(potionid, "Swimming", 32).getInt(swimmingID);

        diamondToolsAllowed = config.get(toolsetting, "Are Infused Diamond tools allowed", true).getBoolean(diamondToolsAllowed);

        ritualDisabledWither = config.get(ritualblacklist, "Ritual of Withering", false).getBoolean(ritualDisabledWither);
        ritualDisabledMidas = config.get(ritualblacklist, "Midas Touch", false).getBoolean(ritualDisabledMidas);

        isRedGood = config.get(misc, "Is RED > PURPLE?", false, "Purple is always better than Red. But I won't tell you how to live your life. Even if it is incorrectly.").getBoolean(isRedGood);

        config.save();
    }
}
