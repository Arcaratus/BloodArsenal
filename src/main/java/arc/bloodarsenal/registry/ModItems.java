package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.item.ItemBloodArsenalBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.text.WordUtils;

public class ModItems
{
    public static Item glassShard;
    public static Item bloodInfusedStick;

    public static Item bloodInfusedWoodenSlabItem;

    public static void init()
    {
        glassShard = registerItemUniquely(new ItemBloodArsenalBase("glassShard"));
        bloodInfusedStick = registerItemUniquely(new ItemBloodArsenalBase("bloodInfusedStick"));
    }

    public static void initSpecialRenders()
    {

    }

    public static Item registerItem(Item item, String name)
    {
        if (!ConfigHandler.itemBlacklist.contains(name))
        {
            GameRegistry.registerItem(item, name);
            BloodArsenal.PROXY.tryHandleItemModel(item, name);
        }

        return item;
    }

    public static Item registerItem(Item item)
    {
        item.setRegistryName(item.getClass().getSimpleName());
        if (item.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Item {} without setting a registry name. Item will not be registered. Please report this.", item.getClass().getCanonicalName());
            return item;
        }

        System.out.println("REGISTRY: " + item.getRegistryName());
        String itemName = item.getRegistryName().toString().split(":")[1];
        if (!ConfigHandler.itemBlacklist.contains(itemName))
        {
            GameRegistry.registerItem(item);
            BloodArsenal.PROXY.tryHandleItemModel(item, itemName);
        }

        return item;
    }

    public static Item registerItemUniquely(Item item)
    {
        item.setRegistryName(item.getClass().getSimpleName() + "." + WordUtils.capitalize(item.getUnlocalizedName().substring(18)));
        if (item.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Item {} without setting a registry name. Item will not be registered. Please report this.", item.getClass().getCanonicalName());
            return item;
        }

        System.out.println("REGISTRY: " + item.getRegistryName());
        String itemName = item.getRegistryName().toString().split(":")[1];
        if (!ConfigHandler.itemBlacklist.contains(itemName))
        {
            GameRegistry.registerItem(item);
            BloodArsenal.PROXY.tryHandleItemModel(item, itemName.split("[.]")[0]);
        }

        return item;
    }
}
