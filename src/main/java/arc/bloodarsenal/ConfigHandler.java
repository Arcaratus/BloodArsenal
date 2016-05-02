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

    // Miscellaneous
    public static boolean doGlassShardsDrop;

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

        category = "Miscellaneous";
        config.addCustomCategoryComment(category, "Miscellaneous configs of magical sorts");
        doGlassShardsDrop = config.get(category, "doGlassShardsDrop", true, "Should be used when another mod adds in its own glass shards").getBoolean();

        config.save();
    }

    public static Configuration getConfig()
    {
        return config;
    }
}
