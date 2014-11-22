package com.arc.bloodarsenal;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class BloodArsenalConfig
{
    public static Configuration config;

	// Config Categories
	public static String potionid = "Potion ID";
	public static String ritualblacklist = "Ritual Blacklist";
	public static String toolsetting = "Tool Settings";
    public static String misc = "Miscellaneous";

	// Config Options
	// Potion ID's
	public static int bleedingID;
	public static int swimmingID;
	public static int vampiricAuraID;

	// Tool Settings
	public static boolean diamondToolsAllowed;
    public static boolean tinkerToolsAllowed;

	// Ritual Blacklist
	public static boolean ritualDisabledMidas;
	public static boolean ritualDisabledWither;

	// Miscellaneous
	public static boolean isRedGood;
    public static boolean cakeIsLie;
	
	public static void init(File file)
	{
		config = new Configuration(file);
        try
        {
            syncConfig();
        }
        catch (Exception e)
        {
            BloodArsenal.logger.error("There has been an error loading the configurations, go ask on the forums.");
        }
        finally
        {
            config.save();
        }
	}

    public static void syncConfig()
    {
	    config.addCustomCategoryComment(potionid, "Change potion ID's here if you have conflicts");
	    config.addCustomCategoryComment(toolsetting, "Settings for various tools");
	    config.addCustomCategoryComment(ritualblacklist, "Blacklist rituals that you don't want/like");
	    config.addCustomCategoryComment(misc, "Random stuffs");

        vampiricAuraID = config.get(potionid, "Vampiric Aura", 150).getInt(vampiricAuraID);
        bleedingID = config.get(potionid, "Bleeding", 151).getInt(bleedingID);
        swimmingID = config.get(potionid, "Swimming", 152).getInt(swimmingID);

        diamondToolsAllowed = config.get(toolsetting, "Are Infused Diamond tools allowed", true).getBoolean(diamondToolsAllowed);
        tinkerToolsAllowed = config.get(toolsetting, "Are Tinker's Construct tools allowed (only when TConstruct is found)", true).getBoolean(tinkerToolsAllowed);

        ritualDisabledWither = config.get(ritualblacklist, "Ritual of Withering", false).getBoolean(ritualDisabledWither);
        ritualDisabledMidas = config.get(ritualblacklist, "Midas Touch", false).getBoolean(ritualDisabledMidas);

        isRedGood = config.get(misc, "Is RED > PURPLE?", false, "Purple is always better than Red. But I won't tell you how to live your life. Even if it is incorrectly.").getBoolean(isRedGood);
        cakeIsLie = config.get(misc, "The cake is a lie", false, "The cake is a lie").getBoolean(cakeIsLie);

        config.save();
    }
}
