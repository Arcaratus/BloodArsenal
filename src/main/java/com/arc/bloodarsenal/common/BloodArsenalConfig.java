package com.arc.bloodarsenal.common;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class BloodArsenalConfig
{
    public static Configuration config;

	// Config Categories
	public static String potionId = "Potion ID";
	public static String ritualBlacklist = "Ritual Blacklist";
    public static String blockSettings = "Block Settings";
	public static String itemSettings = "Item Settings";
    public static String modSettings = "Mod Settings";
    public static String lpSettings = "LP Settings";
    public static String misc = "Miscellaneous";

	// Config Options
	// Potion ID's
    public static int vampiricAuraID;
	public static int bleedingID;
	public static int swimmingID;
    public static int soulBurnID;

    // Ritual Blacklist
    public static boolean ritualDisabledMidas;
    public static boolean ritualDisabledWither;
    public static boolean ritualDisabledEnchantment;
    public static boolean ritualDisabledMobOppression;
    public static boolean ritualDisabledFisherman;

    // Block Settings
    public static String[] blocksToBeDisabled;

	// Item Settings
    public static String[] itemsToBeDisabled;

    //Mod Settings
    public static boolean baublesIntegration;
    public static boolean tinkersIntegration;
    public static int bloodInfusedWoodID;
    public static int bloodInfusedIronID;

    //LP Settings
    public static int sigilSwimmingCost;
    public static int sigilDivinityCost;
    public static int enderSigilOpenCost;
    public static int enderSigilTeleportMultiplier;
    public static int lightningSigilMultiplier;
    public static int lpMaterializerCost;

	// Miscellaneous
    public static boolean versionCheckingAllowed;
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
            BloodArsenal.logger.error("There has been an error loading the configurations, go report this on the forum.");
        }
        finally
        {
            config.save();
        }
	}

    public static void syncConfig()
    {
	    config.addCustomCategoryComment(potionId, "Change potion ID's here if you have conflicts");
        config.addCustomCategoryComment(ritualBlacklist, "Blacklist rituals that you don't want/like");
	    config.addCustomCategoryComment(itemSettings, "Settings for items");
        config.addCustomCategoryComment(modSettings, "Toggle mod integration");
        config.addCustomCategoryComment(lpSettings, "Change the LP costs for things");
	    config.addCustomCategoryComment(misc, "Random stuffs");

        vampiricAuraID = config.get(potionId, "Vampiric Aura", 50).getInt(vampiricAuraID);
        bleedingID = config.get(potionId, "Bleeding", 51).getInt(bleedingID);
        swimmingID = config.get(potionId, "Swimming", 52).getInt(swimmingID);
        soulBurnID = config.get(potionId, "Soul Burn", 53).getInt(soulBurnID);

        ritualDisabledWither = config.get(ritualBlacklist, "Ritual of Withering", false).getBoolean(ritualDisabledWither);
        ritualDisabledMidas = config.get(ritualBlacklist, "Midas Touch", false).getBoolean(ritualDisabledMidas);
        ritualDisabledEnchantment = config.get(ritualBlacklist, "The Enchantress's Spell", false).getBoolean(ritualDisabledEnchantment);
        ritualDisabledMobOppression = config.get(ritualBlacklist, "Ritual of Mob Oppression", false).getBoolean(ritualDisabledMobOppression);
        ritualDisabledFisherman = config.get(ritualBlacklist, "Fisherman's Hymn", false).getBoolean(ritualDisabledFisherman);

        blocksToBeDisabled = config.getStringList("Disable blocks here", blockSettings, new String[]{""}, "Type in the unlocalized name of the block you want disabled here (separate them using ENTER)");
        itemsToBeDisabled = config.getStringList("Disable items here", itemSettings, new String[]{""}, "Type in the unlocalized name of the item you want disabled (separate them using ENTER)");

        baublesIntegration = config.get(modSettings, "Enable Baubles integration?", true).getBoolean(baublesIntegration);
        tinkersIntegration = config.get(modSettings, "Enable TConstruct integration?", true).getBoolean(tinkersIntegration);
        bloodInfusedWoodID = config.get(modSettings, "Material ID for Blood Infused Wood; Default: 250", 250).getInt(bloodInfusedWoodID);
        bloodInfusedIronID = config.get(modSettings, "Material ID for Blood Infused Iron; Defualt: 251", 251).getInt(bloodInfusedIronID);

        sigilSwimmingCost = config.get(lpSettings, "Sigil of Swimming cost; Default: 150", 150).getInt(sigilSwimmingCost);
        sigilDivinityCost = config.get(lpSettings, "Sigil of Divinity cost; Default: 1000000", 1000000).getInt(sigilDivinityCost);
        enderSigilOpenCost = config.get(lpSettings, "Ender Sigil cost (to open the Ender Chest); Default: 200", 200).getInt(enderSigilOpenCost);
        enderSigilTeleportMultiplier = config.get(lpSettings, "Ender Sigil cost (to teleport (multiplier)); Default: 250", 250).getInt(enderSigilTeleportMultiplier);
        lightningSigilMultiplier = config.get(lpSettings, "Lightning Sigil cost (multiplier); Default: 1000", 1000).getInt(lightningSigilMultiplier);
        lpMaterializerCost = config.get(lpSettings, "Converts this number to 100LE (from this (SN) to 100 (LE)); Default: 150", 150).getInt(lpMaterializerCost);

        versionCheckingAllowed = config.get(misc, "Is auto-magic version checking allowed?", true, "Allows for your version to be checked against the latest version").getBoolean();
        isRedGood = config.get(misc, "Is RED > PURPLE?", false, "Purple is always better than Red. But I won't tell you how to live your life. Even if it is incorrectly.").getBoolean(isRedGood);
        cakeIsLie = config.get(misc, "The cake is a lie", false, "The cake is a lie").getBoolean(cakeIsLie);

        config.save();
    }
}
