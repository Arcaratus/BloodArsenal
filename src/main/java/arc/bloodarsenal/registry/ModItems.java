package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.item.ItemBloodArsenalBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
    public static Item glass_shard;

    public static void init()
    {
        glass_shard = registerItem(new ItemBloodArsenalBase("glass_shard"));
    }

    public static void initSpecialRenders()
    {

    }

    private static Item registerItem(Item item, String name)
    {
        if (!ConfigHandler.itemBlacklist.contains(name))
        {
            GameRegistry.registerItem(item, name);
            BloodArsenal.PROXY.tryHandleItemModel(item, name);
        }

        return item;
    }

    private static Item registerItem(Item item)
    {
        item.setRegistryName(item.getClass().getSimpleName());
        if (item.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Item {} without setting a registry name. Item will not be registered. Please report this.", item.getClass().getCanonicalName());
            return item;
        }

        String itemName = item.getRegistryName().split(":")[1];
        if (!ConfigHandler.itemBlacklist.contains(itemName))
        {
            GameRegistry.registerItem(item);
            BloodArsenal.PROXY.tryHandleItemModel(item, itemName);
        }

        return item;
    }
}
