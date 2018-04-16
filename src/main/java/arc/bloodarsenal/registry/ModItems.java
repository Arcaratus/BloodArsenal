package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.item.*;
import arc.bloodarsenal.item.block.ItemBlockSpecialBloodArsenal;
import arc.bloodarsenal.item.sigil.*;
import arc.bloodarsenal.item.stasis.*;
import arc.bloodarsenal.item.tool.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

public class ModItems
{
    public static final Item GLASS_SHARD;
    public static final Item BLOOD_INFUSED_STICK;
    public static final Item BLOOD_BURNED_STRING;
    public static final Item BLOOD_ORANGE;
    public static final Item BLOOD_DIAMOND;
    public static final Item BLOOD_INFUSED_WOODEN_PICKAXE;

    public static final Item BLOOD_INFUSED_WOODEN_AXE;
    public static final Item BLOOD_INFUSED_WOODEN_SHOVEL;
    public static final Item BLOOD_INFUSED_WOODEN_SICKLE;
    public static final Item BLOOD_INFUSED_WOODEN_SWORD;
    public static final Item BLOOD_INFUSED_GLOWSTONE_DUST;
    public static final Item INERT_BLOOD_INFUSED_IRON_INGOT;
    public static final Item BLOOD_INFUSED_IRON_INGOT;
    public static final Item BLOOD_INFUSED_IRON_PICKAXE;
    public static final Item BLOOD_INFUSED_IRON_AXE;
    public static final Item BLOOD_INFUSED_IRON_SHOVEL;
    public static final Item BLOOD_INFUSED_IRON_SICKLE;
    public static final Item BLOOD_INFUSED_IRON_SWORD;
    public static final Item GLASS_SACRIFICIAL_DAGGER;

    public static final Item GLASS_DAGGER_OF_SACRIFICE;
    public static final Item STYGIAN_DAGGER;
    public static final Item GEM_SACRIFICE;

    public static final Item GEM_SELF_SACRIFICE;
    public static final Item GEM_TARTARIC;
    public static final Item SIGIL_SWIMMING;

    public static final Item SIGIL_ENDER;
    public static final Item SIGIL_AUGMENTED_HOLDING;
    public static final Item SIGIL_LIGHTNING;
    public static final Item SIGIL_DIVINITY;
    public static final Item SIGIL_SENTIENCE;
    public static final Item REAGENT_SWIMMING;

    public static final Item REAGENT_ENDER;
    public static final Item REAGENT_LIGHTNING;
    public static final Item REAGENT_DIVINITY;

    public static final Item BOUND_STICK;
    public static final Item BOUND_SICKLE;

    public static final Item STASIS_PLATE;
    public static final Item STASIS_PICKAXE;
    public static final Item STASIS_AXE;
    public static final Item STASIS_SHOVEL;
    public static final Item STASIS_SWORD;

    public static final Item MODIFIER_TOME;

    public static final Item WARP_BLADE;

    public static final Item.ToolMaterial BLOOD_INFUSED_WOOD = EnumHelper.addToolMaterial("BloodInfusedWoodMaterial", 1, 186, 5.5F, 1.0F, 13);
    public static final Item.ToolMaterial BLOOD_INFUSED_IRON = EnumHelper.addToolMaterial("BloodInfusedIronMaterial", 3, 954, 7.25F, 2.7F, 21);
    public static final Item.ToolMaterial STASIS = EnumHelper.addToolMaterial("Stasis", 4, 0, 9F, 4.2F, 0);

    static
    {
        GLASS_SHARD = registerItemUniquely(new ItemBloodArsenalBase("glassShard"));
        BLOOD_INFUSED_STICK = registerItemUniquely(new ItemBloodArsenalBase("bloodInfusedStick"));
        BLOOD_BURNED_STRING = registerItemUniquely(new ItemBlockSpecialBloodArsenal("bloodBurnedString", ModBlocks.BLOOD_BURNED_STRING));
        BLOOD_ORANGE = registerItem(new ItemBloodOrange("bloodOrange"));
        BLOOD_DIAMOND = registerItem(new ItemBloodDiamond("bloodDiamond"));

        BLOOD_INFUSED_WOODEN_PICKAXE = registerItem(new ItemBloodInfusedWoodenPickaxe());
        BLOOD_INFUSED_WOODEN_AXE = registerItem(new ItemBloodInfusedWoodenAxe());
        BLOOD_INFUSED_WOODEN_SHOVEL = registerItem(new ItemBloodInfusedWoodenShovel());
        BLOOD_INFUSED_WOODEN_SICKLE = registerItem(new ItemBloodInfusedWoodenSickle());
        BLOOD_INFUSED_WOODEN_SWORD = registerItem(new ItemBloodInfusedWoodenSword());
        BLOOD_INFUSED_GLOWSTONE_DUST = registerItemUniquely(new ItemBloodArsenalBase("bloodInfusedGlowstoneDust"));
        INERT_BLOOD_INFUSED_IRON_INGOT = registerItemUniquely(new ItemBloodArsenalBase("inertBloodInfusedIronIngot"));
        BLOOD_INFUSED_IRON_INGOT = registerItemUniquely(new ItemBloodArsenalBase("bloodInfusedIronIngot"));
        BLOOD_INFUSED_IRON_PICKAXE = registerItem(new ItemBloodInfusedIronPickaxe());
        BLOOD_INFUSED_IRON_AXE = registerItem(new ItemBloodInfusedIronAxe());
        BLOOD_INFUSED_IRON_SHOVEL = registerItem(new ItemBloodInfusedIronShovel());
        BLOOD_INFUSED_IRON_SICKLE = registerItem(new ItemBloodInfusedIronSickle());
        BLOOD_INFUSED_IRON_SWORD = registerItem(new ItemBloodInfusedIronSword());

        GLASS_SACRIFICIAL_DAGGER = registerItem(new ItemGlassSacrificialDagger("glassSacrificialDagger"));
        GLASS_DAGGER_OF_SACRIFICE = registerItem(new ItemGlassDaggerOfSacrifice("glassDaggerOfSacrifice"));
        STYGIAN_DAGGER = registerItem(new ItemStygianDagger("stygianDagger"));

        GEM_SACRIFICE = registerItemUniquely(new ItemGem("sacrifice"));
        GEM_SELF_SACRIFICE = registerItemUniquely(new ItemGem("selfSacrifice"));
        GEM_TARTARIC = registerItemUniquely(new ItemGem("tartaric"));

        SIGIL_SWIMMING = registerItem(new ItemSigilSwimming());
        SIGIL_ENDER = registerItem(new ItemSigilEnder());
        SIGIL_AUGMENTED_HOLDING = registerItem(new ItemSigilAugmentedHolding());
        SIGIL_LIGHTNING = registerItem(new ItemSigilLightning());
        SIGIL_DIVINITY = registerItem(new ItemSigilDivinity());
        SIGIL_SENTIENCE = registerItem(new ItemSigilSentience());

        REAGENT_SWIMMING = registerItemUniquely(new ItemBloodArsenalBase("reagentSwimming"));
        REAGENT_ENDER = registerItemUniquely(new ItemBloodArsenalBase("reagentEnder"));
        REAGENT_LIGHTNING = registerItemUniquely(new ItemBloodArsenalBase("reagentLightning"));
        REAGENT_DIVINITY = registerItemUniquely(new ItemBloodArsenalBase("reagentDivinity"));

        BOUND_STICK = registerItem(new ItemBoundStick("boundStick"));
        BOUND_SICKLE = registerItem(new ItemBoundSickle());

        STASIS_PLATE = registerItemUniquely(new ItemBloodArsenalBase("stasisPlate"));
        STASIS_PICKAXE = registerItem(new ItemStasisPickaxe());
        STASIS_AXE = registerItem(new ItemStasisAxe());
        STASIS_SHOVEL = registerItem(new ItemStasisShovel());
        STASIS_SWORD = registerItem(new ItemStasisSword());

        MODIFIER_TOME = registerItem(new ItemModifierTome("modifierTome"));

        WARP_BLADE = registerItem(new ItemWarpBlade());
    }

    public static void addOreDictItems()
    {
        OreDictionary.registerOre("shardGlass", GLASS_SHARD);
        OreDictionary.registerOre("ingotBloodInfusedIron", BLOOD_INFUSED_IRON_INGOT);
    }

    @SideOnly(Side.CLIENT)
    public static void initSpecialRenders()
    {
        final ResourceLocation holdingLoc = new ResourceLocation("bloodarsenal", "item/ItemSigilAugmentedHolding");
        ModelLoader.setCustomMeshDefinition(SIGIL_AUGMENTED_HOLDING, stack -> stack.hasTagCompound() && stack.getTagCompound().hasKey(Constants.NBT.COLOR) ? new ModelResourceLocation(holdingLoc, "type=color") : new ModelResourceLocation(holdingLoc, "type=normal"));
        ModelLoader.registerItemVariants(SIGIL_AUGMENTED_HOLDING, new ModelResourceLocation(holdingLoc, "type=normal"));
        ModelLoader.registerItemVariants(SIGIL_AUGMENTED_HOLDING, new ModelResourceLocation(holdingLoc, "type=color"));
    }

    public static Item registerItem(Item item)
    {
        item.setRegistryName(item.getClass().getSimpleName());
        if (item.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Item {} without setting a registry name. Item will not be registered. Please report this.", item.getClass().getCanonicalName());
            return item;
        }

        String itemName = item.getRegistryName().toString().split(":")[1];
        if (!ConfigHandler.itemBlacklist.contains(itemName))
        {
            GameRegistry.register(item);
            BloodArsenal.PROXY.tryHandleItemModel(item, itemName);
        }

        return item;
    }

    // To be used with ItemBloodArsenalBase and similar classes
    public static Item registerItemUniquely(Item item)
    {
        item.setRegistryName(item.getClass().getSimpleName() + "." + WordUtils.capitalize(item.getUnlocalizedName().substring(18)));
        if (item.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Item {} without setting a registry name. Item will not be registered. Please report this.", item.getClass().getCanonicalName());
            return item;
        }

        String itemName = item.getRegistryName().toString().split(":")[1];
        if (!ConfigHandler.itemBlacklist.contains(itemName))
        {
            GameRegistry.register(item);
            BloodArsenal.PROXY.tryHandleItemModel(item, itemName.split("[.]")[0]);
        }

        return item;
    }
}
