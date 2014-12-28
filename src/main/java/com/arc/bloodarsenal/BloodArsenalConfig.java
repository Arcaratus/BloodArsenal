package com.arc.bloodarsenal;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.List;

public class BloodArsenalConfig
{
    public static Configuration config;

	// Config Categories
	public static String potionId = "Potion ID";
	public static String ritualBlacklist = "Ritual Blacklist";
	public static String toolSetting = "Tool Settings";
    public static String modSettings = "Mod Settings";
    public static String lpSettings = "LP Settings";
    public static String misc = "Miscellaneous";

	// Config Options
	// Potion ID's
	public static int bleedingID;
	public static int swimmingID;
	public static int vampiricAuraID;

    // Ritual Blacklist
    public static boolean ritualDisabledMidas;
    public static boolean ritualDisabledWither;

	// Tool Settings
	public static boolean diamondToolsAllowed;

    //ModSettings
    public static boolean baublesIntegration;
    public static boolean tinkersIntegration;

    //LP Settings
    public static int sigilSwimmingCost;
    public static int sigilDivinityCost;

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
	    config.addCustomCategoryComment(potionId, "Change potion ID's here if you have conflicts");
        config.addCustomCategoryComment(ritualBlacklist, "Blacklist rituals that you don't want/like");
	    config.addCustomCategoryComment(toolSetting, "Settings for various tools");
        config.addCustomCategoryComment(modSettings, "Disable mod integration");
        config.addCustomCategoryComment(lpSettings, "Change the LP costs for things");
	    config.addCustomCategoryComment(misc, "Random stuffs");

        vampiricAuraID = config.get(potionId, "Vampiric Aura", 50).getInt(vampiricAuraID);
        bleedingID = config.get(potionId, "Bleeding", 51).getInt(bleedingID);
        swimmingID = config.get(potionId, "Swimming", 52).getInt(swimmingID);

        ritualDisabledWither = config.get(ritualBlacklist, "Ritual of Withering", false).getBoolean(ritualDisabledWither);
        ritualDisabledMidas = config.get(ritualBlacklist, "Midas Touch", false).getBoolean(ritualDisabledMidas);

        diamondToolsAllowed = config.get(toolSetting, "Are Infused Diamond tools allowed", true).getBoolean(diamondToolsAllowed);

        baublesIntegration = config.get(modSettings, "Disable Baubles integration?", false).getBoolean(baublesIntegration);
        tinkersIntegration = config.get(modSettings, "Disable TConstruct integration?", false).getBoolean(tinkersIntegration);

        sigilSwimmingCost = config.get(lpSettings, "Sigil of Swimming cost; Default: 150", 150).getInt(sigilSwimmingCost);
        sigilDivinityCost = config.get(lpSettings, "Sigil of Divinity cost; Default: 1000000", 1000000).getInt(sigilDivinityCost);

        isRedGood = config.get(misc, "Is RED > PURPLE?", false, "Purple is always better than Red. But I won't tell you how to live your life. Even if it is incorrectly.").getBoolean(isRedGood);
        cakeIsLie = config.get(misc, "The cake is a lie", false, "The cake is a lie").getBoolean(cakeIsLie);

        config.save();
    }

    public static void lpDouble(AltarRecipe altarRecipe)
    {
        List<AltarRecipe> recipeList = AltarRecipeRegistry.altarRecipes;
        int recipeCost = altarRecipe.getLiquidRequired();
        int newCost = recipeCost * 2;
    }
}
