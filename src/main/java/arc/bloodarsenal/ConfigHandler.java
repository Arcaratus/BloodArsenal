package arc.bloodarsenal;

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ConfigHandler
{
    private static Configuration config;

    // Item/Block Disabling
    public static List<String> itemBlacklist;
    public static List<String> blockBlacklist;

    // Block configs
    public static float glassShardHealth;

    // Item configs
    public static int glassSacrificialDaggerLP;
    public static int glassSacrificialDaggerHealth;
    public static int glassDaggerOfSacrificeLPMultiplier;
    public static int bloodInfusedWoodenToolsRepairUpdate;
    public static int bloodInfusedWoodenToolsRepairCost;
    public static int bloodInfusedIronToolsRepairUpdate;
    public static int bloodInfusedIronToolsRepairCost;
    public static int sigilSwimmingCost;
    public static int sigilEnderOpenCost;
    public static int sigilEnderTeleportMultiplier;
    public static int sigilLightningMultiplier;
    public static int sigilDivinityCost;

    // Mod configs
    public static boolean baublesEnabled;

    public static int sacrificeAmuletConversion;
    public static int selfSacrificeAmuletConversion;

    // Miscellaneous
    public static boolean doGlassShardsDrop;
    public static double rayTraceRange;

    public static void init(File file)
    {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig()
    {
        String category;

        category = "Item/Block Blacklisting";
        config.addCustomCategoryComment(category, "Allows disabling of specific Blocks/Items.\nNote that using this may result in crashes. Use is not supported although feel free to use it anyways :)");
        config.setCategoryRequiresMcRestart(category, true);
        itemBlacklist = Arrays.asList(config.getStringList("itemBlacklist", category, new String[] {}, "Items to not be registered. This requires their mapping name. Usually the same as the class name. Can be found in F3 + H mode."));
        blockBlacklist = Arrays.asList(config.getStringList("blockBlacklist", category, new String[] {}, "Blocks to not be registered. This requires their mapping name. Usually the same as the class name. Can be found in F3 + H mode."));

        category = "Block Configs";
        config.addCustomCategoryComment(category, "Specific block configs");
        glassShardHealth = config.getFloat("glassShardHealth", category, 2, 0, Float.MAX_VALUE, "Set the amount that the Glass Shards block deals when an entity touches it");

        category = "Item Configs";
        config.addCustomCategoryComment(category, "Specific item configs");
        config.setCategoryRequiresWorldRestart(category, true);
        glassSacrificialDaggerLP = config.getInt("glassSacrificialDaggerLP", category, 500, 0, Integer.MAX_VALUE, "Set the amount that the Glass Sacrificial Dagger gives per use");
        glassSacrificialDaggerHealth = config.getInt("glassSacrificialDaggerHealth", category, 2, 1, Integer.MAX_VALUE, "Set the amount of health the Glass Sacrificial Dagger takes per use");
        glassDaggerOfSacrificeLPMultiplier = config.getInt("glassDaggerOfSacrificeLPMultiplier", category, 2, 1, 100, "Set the amount of LP that the Glass Dagger of Sacrifice grants (multiply by the standard Dagger of Sacrifice)");
        bloodInfusedWoodenToolsRepairUpdate = config.getInt("bloodInfusedWoodenToolsRepairUpdate", category, 80, 0, 1000, "Set the amount of ticks at which Blood Infused Wooden Tools repair at");
        bloodInfusedWoodenToolsRepairCost = config.getInt("bloodInfusedWoodenToolsRepairCost", category, 20, 0, 1000, "Set the LP cost of which Blood Infused Wooden Tools repair at");
        bloodInfusedIronToolsRepairUpdate = config.getInt("bloodInfusedIronToolsRepairUpdate", category, 40, 0, 1000, "Set the amount of ticks at which Blood Infused Iron Tools repair at");
        bloodInfusedIronToolsRepairCost = config.getInt("bloodInfusedIronToolsRepairCost", category, 50, 0, 1000, "Set the LP cost of which Blood Infused Iron Tools repair at");
        sigilSwimmingCost = config.getInt("sigilSwimmingCost", category, 100, 0, 10000, "Set the LP cost of the Sigil of Swimming");
        sigilEnderOpenCost = config.getInt("sigilEnderCost", category, 500, 0, 10000, "Set the LP cost of opening your Ender Chest with the Ender Sigil");
        sigilEnderTeleportMultiplier = config.getInt("sigilEnderTeleportMultiplier", category, 200, 0, 10000, "Set the multiplier of the cost of using the Ender Sigil to teleport");
        sigilLightningMultiplier = config.getInt("sigilLightningMultiplier", category, 800, 0, 10000, "Set the multiplier per lighting bolt for the Lightning Sigil");
        sigilDivinityCost = config.getInt("sigilDivinityCost", category, 100000, 0, Integer.MAX_VALUE, "Set the LP cost for the Sigil of Divinity");

        category = "Mod Configs";
        config.addCustomCategoryComment(category, "Mod interaction configs");
        config.setCategoryRequiresMcRestart(category, true);
        baublesEnabled = config.getBoolean("baublesEnabled", category, true, "Toggle Baubles interactions");

        sacrificeAmuletConversion = config.getInt("sacrificeAmuletConversion", category, 20, 0, 100, "Base conversion for the Sacrifice Amulet (damage dealt * sacrificeAmuletConversion)");
        selfSacrificeAmuletConversion = config.getInt("selfSacrificeAmuletConversion", category, 2, 0, 100, "Base conversion for the Self Sacrifice Amulet (damage dealt * selfSacrificeAmuletConversion)");

        category = "Miscellaneous";
        config.addCustomCategoryComment(category, "Miscellaneous configs of magical sorts");
        doGlassShardsDrop = config.getBoolean("doGlassShardsDrop", category, true, "Should be used when another mod adds in its own glass shards");
        rayTraceRange = config.getFloat("rayTraceRange", category, 64F, 0F, Float.MAX_VALUE, "The range for anything that uses raytracing (Ender Sigil, Lightning Sigil, etc.)");

        config.save();
    }

    public static Configuration getConfig()
    {
        return config;
    }
}
