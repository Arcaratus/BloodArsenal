package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.item.ItemBloodArsenalBase;
import arc.bloodarsenal.item.tool.*;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

public class ModItems
{
    public static Item glassShard;
    public static Item bloodInfusedStick;
    public static Item bloodInfusedWoodenPickaxe;
    public static Item bloodInfusedWoodenAxe;
    public static Item bloodInfusedWoodenShovel;
    public static Item bloodInfusedWoodenSword;
    public static Item bloodInfusedIron;
    public static Item glassSacrificialDagger;
    public static Item glassDaggerOfSacrifice;

    public static Item.ToolMaterial bloodInfusedWoodMaterial = EnumHelper.addToolMaterial("BloodInfusedWoodMaterial", 1, 186, 5.5F, 1.0F, 18);

    public static void init()
    {
        glassShard = registerItemUniquely(new ItemBloodArsenalBase("glassShard"));
        bloodInfusedStick = registerItemUniquely(new ItemBloodArsenalBase("bloodInfusedStick"));
        bloodInfusedWoodenPickaxe = registerItem(new ItemBloodInfusedWoodenPickaxe());
        bloodInfusedWoodenAxe = registerItem(new ItemBloodInfusedWoodenAxe());
        bloodInfusedWoodenShovel = registerItem(new ItemBloodInfusedWoodenShovel());
        bloodInfusedWoodenSword = registerItem(new ItemBloodInfusedWoodenSword());
        bloodInfusedIron = registerItem(new ItemBloodArsenalBase("bloodInfusedIron"));
        glassSacrificialDagger = registerItem(new ItemGlassSacrificialDagger("glassSacrificialDagger"));
        glassDaggerOfSacrifice = registerItem(new ItemGlassDaggerOfSacrifice("glassDaggerOfSacrifice"));

        addOreDictItems();
    }

    public static void addOreDictItems()
    {
        OreDictionary.registerOre("glassShard", glassShard);
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
