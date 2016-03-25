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

    public static void init(File file)
    {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig()
    {
        String category;

        category = "Item/Block Blacklisting";
        config.addCustomCategoryComment(category, "Allows disabling of specific Blocks/Items.\nNote that using this may result in crashes. Use is not supported.");
        config.setCategoryRequiresMcRestart(category, true);
        itemBlacklist = Arrays.asList(config.getStringList("itemBlacklist", category, new String[] {}, "Items to not be registered. This requires their mapping name. Usually the same as the class name. Can be found in F3+H mode."));
        blockBlacklist = Arrays.asList(config.getStringList("blockBlacklist", category, new String[] {}, "Blocks to not be registered. This requires their mapping name. Usually the same as the class name. Can be found in F3+H mode."));

        config.save();
    }
}
