package com.arc.bloodarsenal.items.tinkers;

import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.tools.TinkerTools;

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper
{
    private static Item pattern = TConstructRegistry.getItem("woodPattern");
    
    private static final ItemStack toolRodPattern = TConstructRegistry.getItemStack("toolRodPattern");
    private static final ItemStack pickaxeHeadPattern = TConstructRegistry.getItemStack("pickaxeHeadPattern");
    private static final ItemStack shovelHeadPattern = TConstructRegistry.getItemStack("shovelHeadPattern");
    private static final ItemStack hatchetHeadPattern = TConstructRegistry.getItemStack("hatchetHeadPattern");
    private static final ItemStack swordBladePattern = TConstructRegistry.getItemStack("swordBladePattern");
    private static final ItemStack wideGuardPattern = TConstructRegistry.getItemStack("wideGuardPattern");
    private static final ItemStack handGuardPattern = TConstructRegistry.getItemStack("handGuardPattern");
    private static final ItemStack crossbarPattern = TConstructRegistry.getItemStack("crossbarPattern");
    private static final ItemStack bindingPattern = TConstructRegistry.getItemStack("bindingPattern");
    private static final ItemStack frypanHeadPattern = TConstructRegistry.getItemStack("frypanHeadPattern");
    private static final ItemStack signHeadPattern = TConstructRegistry.getItemStack("signHeadPattern");
    private static final ItemStack knifeBladePattern = TConstructRegistry.getItemStack("knifeBladePattern");
    private static final ItemStack chiselHeadPattern = TConstructRegistry.getItemStack("chiselHeadPattern");
    private static final ItemStack toughRodPattern = TConstructRegistry.getItemStack("toughRodPattern");
    private static final ItemStack toughBindingPattern = TConstructRegistry.getItemStack("toughBindingPattern");
    private static final ItemStack largePlatePattern = TConstructRegistry.getItemStack("largePlatePattern");
    private static final ItemStack broadAxeHeadPattern = TConstructRegistry.getItemStack("broadAxeHeadPattern");
    private static final ItemStack scytheHeadPattern = TConstructRegistry.getItemStack("scytheHeadPattern");
    private static final ItemStack excavatorHeadPattern = TConstructRegistry.getItemStack("excavatorHeadPattern");
    private static final ItemStack largeBladePattern = TConstructRegistry.getItemStack("largeBladePattern");
    private static final ItemStack hammerHeadPattern = TConstructRegistry.getItemStack("hammerHeadPattern");
    private static final ItemStack fullGuardPattern = TConstructRegistry.getItemStack("fullGuardPattern");
    private static final ItemStack arrowHeadPattern = new ItemStack(pattern, 1, 25);

    private static PartHelper _ARROWHEAD = BloodArsenalTinkers.ARROWHEAD;
    private static PartHelper _AXE_HEAD = BloodArsenalTinkers.AXE_HEAD;
    private static PartHelper _BATTLE_SIGN_HEAD = BloodArsenalTinkers.BATTLE_SIGN_HEAD;
    private static PartHelper _BINDING = BloodArsenalTinkers.BINDING;
    private static PartHelper _CHISEL_HEAD = BloodArsenalTinkers.CHISEL_HEAD;
    private static PartHelper _CHUNK = BloodArsenalTinkers.CHUNK;
    private static PartHelper _CROSSBAR = BloodArsenalTinkers.CROSSBAR;
    private static PartHelper _EXCAVATOR_HEAD = BloodArsenalTinkers.EXCAVATOR_HEAD;
    private static PartHelper _FRYPAN_HEAD = BloodArsenalTinkers.FRYPAN_HEAD;
    private static PartHelper _FULL_GUARD = BloodArsenalTinkers.FULL_GUARD;
    private static PartHelper _HAMMER_HEAD = BloodArsenalTinkers.HAMMER_HEAD;
    private static PartHelper _KNIFE_BLADE = BloodArsenalTinkers.KNIFE_BLADE;
    private static PartHelper _LARGE_GUARD = BloodArsenalTinkers.LARGE_GUARD;
    private static PartHelper _LARGE_SWORD_BLADE = BloodArsenalTinkers.LARGE_SWORD_BLADE;
    private static PartHelper _LARGEPLATE = BloodArsenalTinkers.LARGEPLATE;
    private static PartHelper _LUMBERAXE_HEAD = BloodArsenalTinkers.LUMBERAXE_HEAD;
    private static PartHelper _MEDIUM_GUARD = BloodArsenalTinkers.MEDIUM_GUARD;
    private static PartHelper _PICKAXE_HEAD = BloodArsenalTinkers.PICKAXE_HEAD;
    private static PartHelper _SCYTHE_HEAD = BloodArsenalTinkers.SCYTHE_HEAD;
    private static PartHelper _SHOVEL_HEAD = BloodArsenalTinkers.SHOVEL_HEAD;
    private static PartHelper _SWORD_BLADE = BloodArsenalTinkers.SWORD_BLADE;
    private static PartHelper _TOOLROD = BloodArsenalTinkers.TOOLROD;
    private static PartHelper _TOUGHBIND = BloodArsenalTinkers.TOUGHBIND;
    private static PartHelper _TOUGHROD = BloodArsenalTinkers.TOUGHROD;

    public static void addRecipes()
    {
        addPartRecipes();
        addToolRecipes();
        addCraftingRecipes();
    }

    public static void addCraftingRecipes() {}

    public static void addPartRecipes()
    {
        if (BloodArsenal.isTinkersConstructLoaded)
        {
            Item bloodIron = GameRegistry.findItem("BloodArsenal", "blood_infused_iron");

            if (bloodIron != null)
            {
                ItemStack _bloodIron = new ItemStack(bloodIron);

                addPartBuilding(250);
                unregisterMaterial(_bloodIron);
//                registerWithStation(new ItemStack(ModItems.blood_infused_iron, 1), "Blood Infused Iron", 250, new ItemStack(_CHUNK, 1, 250));
                registerWithStation("Blood Infused Iron", "Blood Infused Iron", 250);
            }
        }
    }

    private static void registerWithStation(String ingotName, String materialName, int materialID)
    {
        ArrayList<ItemStack> ingots = OreDictionary.getOres(ingotName);
        if (!ingots.isEmpty())
        {
            for (ItemStack ingot : ingots)
            {
                registerWithStation(ingot, materialName, materialID, new ItemStack(_CHUNK, 1, materialID));
            }
        }
    }

    private static void registerWithStation(ItemStack material, String materialName, int materialID, ItemStack chunk)
    {
        PatternBuilder.instance.registerFullMaterial(material, 2, materialName, chunk, new ItemStack(_TOOLROD, 1, materialID), materialID);
    }

    private static void unregisterMaterial(ItemStack material)
    {
        List<PatternBuilder.ItemKey> materials = PatternBuilder.instance.materials;
        for (int i = 0; i < materials.size(); i++)
        {
            PatternBuilder.ItemKey itemKey = (PatternBuilder.ItemKey)materials.get(i);
            if ((itemKey.item == material.getItem()) && (material.getItemDamage() == itemKey.damage))
            {
                materials.remove(i);
            }
        }
    }

    private static ItemStack getItemStackfromCraftingManager(String itemName)
    {
        List list = CraftingManager.getInstance().getRecipeList();

        ItemStack itemStack = null;
        for (int i = 0; i < list.size(); i++)
        {
            IRecipe recipe = (IRecipe)list.get(i);
            ItemStack currentItemStack = recipe.getRecipeOutput();
            if (currentItemStack != null)
            {
                Item item = currentItemStack.getItem();
                if (item != null)
                {
                    String foundItemName = currentItemStack.getUnlocalizedName();
                    if (foundItemName.equals(itemName))
                    {
                        itemStack = currentItemStack;
                        break;
                    }
                }
            }
        }
        return itemStack;
    }

    private static void addItemStackPartBuilding(ItemStack pattern, int materialID, ItemStack part)
    {
        TConstructRegistry.addPartMapping(pattern.getItem(), pattern.getItemDamage(), materialID, part);
    }

    private static void addPartBuilding(int materialID)
    {
        addItemStackPartBuilding(toolRodPattern, materialID, new ItemStack(_TOOLROD, 1, materialID));
        addItemStackPartBuilding(pickaxeHeadPattern, materialID, new ItemStack(_PICKAXE_HEAD, 1, materialID));
        addItemStackPartBuilding(shovelHeadPattern, materialID, new ItemStack(_SHOVEL_HEAD, 1, materialID));
        addItemStackPartBuilding(hatchetHeadPattern, materialID, new ItemStack(_AXE_HEAD, 1, materialID));
        addItemStackPartBuilding(swordBladePattern, materialID, new ItemStack(_SWORD_BLADE, 1, materialID));
        addItemStackPartBuilding(wideGuardPattern, materialID, new ItemStack(_LARGE_GUARD, 1, materialID));
        addItemStackPartBuilding(handGuardPattern, materialID, new ItemStack(_MEDIUM_GUARD, 1, materialID));
        addItemStackPartBuilding(crossbarPattern, materialID, new ItemStack(_CROSSBAR, 1, materialID));
        addItemStackPartBuilding(bindingPattern, materialID, new ItemStack(_BINDING, 1, materialID));
        addItemStackPartBuilding(frypanHeadPattern, materialID, new ItemStack(_FRYPAN_HEAD, 1, materialID));
        addItemStackPartBuilding(signHeadPattern, materialID, new ItemStack(_BATTLE_SIGN_HEAD, 1, materialID));
        addItemStackPartBuilding(knifeBladePattern, materialID, new ItemStack(_KNIFE_BLADE, 1, materialID));
        addItemStackPartBuilding(chiselHeadPattern, materialID, new ItemStack(_CHISEL_HEAD, 1, materialID));
        addItemStackPartBuilding(toughRodPattern, materialID, new ItemStack(_TOUGHROD, 1, materialID));
        addItemStackPartBuilding(toughBindingPattern, materialID, new ItemStack(_TOUGHBIND, 1, materialID));
        addItemStackPartBuilding(largePlatePattern, materialID, new ItemStack(_LARGEPLATE, 1, materialID));
        addItemStackPartBuilding(broadAxeHeadPattern, materialID, new ItemStack(_LUMBERAXE_HEAD, 1, materialID));
        addItemStackPartBuilding(scytheHeadPattern, materialID, new ItemStack(_SCYTHE_HEAD, 1, materialID));
        addItemStackPartBuilding(excavatorHeadPattern, materialID, new ItemStack(_EXCAVATOR_HEAD, 1, materialID));
        addItemStackPartBuilding(largeBladePattern, materialID, new ItemStack(_LARGE_SWORD_BLADE, 1, materialID));
        addItemStackPartBuilding(hammerHeadPattern, materialID, new ItemStack(_HAMMER_HEAD, 1, materialID));
        addItemStackPartBuilding(fullGuardPattern, materialID, new ItemStack(_FULL_GUARD, 1, materialID));
        addItemStackPartBuilding(arrowHeadPattern, materialID, new ItemStack(_ARROWHEAD, 1, materialID));
    }

    public static void addToolRecipes()
    {
        TConstructRegistry.addToolRecipe(TinkerTools.arrow, new Item[] { _ARROWHEAD, _TOOLROD, TinkerTools.fletching });
        TConstructRegistry.addToolRecipe(TinkerTools.battleaxe, new Item[] { _LUMBERAXE_HEAD, _TOUGHROD, _LUMBERAXE_HEAD, _TOUGHBIND });
        TConstructRegistry.addToolRecipe(TinkerTools.battlesign, new Item[] { _BATTLE_SIGN_HEAD, _TOOLROD });
        TConstructRegistry.addToolRecipe(TinkerTools.broadsword, new Item[] { _SWORD_BLADE, _TOOLROD, _LARGE_GUARD });
        TConstructRegistry.addToolRecipe(TinkerTools.chisel, new Item[] { _CHISEL_HEAD, _TOOLROD });
        TConstructRegistry.addToolRecipe(TinkerTools.cleaver, new Item[] { _LARGE_SWORD_BLADE, _TOUGHROD, _LARGEPLATE, _TOUGHROD });
        TConstructRegistry.addToolRecipe(TinkerTools.cutlass, new Item[] { _SWORD_BLADE, _TOOLROD, _FULL_GUARD });
        TConstructRegistry.addToolRecipe(TinkerTools.dagger, new Item[] { _KNIFE_BLADE, _TOOLROD, _CROSSBAR });
        TConstructRegistry.addToolRecipe(TinkerTools.excavator, new Item[] { _EXCAVATOR_HEAD, _TOUGHROD, _LARGEPLATE, _TOUGHBIND });
        TConstructRegistry.addToolRecipe(TinkerTools.frypan, new Item[] { _FRYPAN_HEAD, _TOOLROD });
        TConstructRegistry.addToolRecipe(TinkerTools.hammer, new Item[] { _HAMMER_HEAD, _TOUGHROD, _LARGEPLATE, _LARGEPLATE });
        TConstructRegistry.addToolRecipe(TinkerTools.hatchet, new Item[] { _AXE_HEAD, _TOOLROD });
        TConstructRegistry.addToolRecipe(TinkerTools.longsword, new Item[] { _SWORD_BLADE, _TOOLROD, _MEDIUM_GUARD });
        TConstructRegistry.addToolRecipe(TinkerTools.lumberaxe, new Item[] { _LUMBERAXE_HEAD, _TOUGHROD, _LARGEPLATE, _TOUGHBIND });
        TConstructRegistry.addToolRecipe(TinkerTools.mattock, new Item[] { _AXE_HEAD, _TOOLROD, _SHOVEL_HEAD });
        TConstructRegistry.addToolRecipe(TinkerTools.pickaxe, new Item[] { _PICKAXE_HEAD, _TOOLROD, _BINDING });
        TConstructRegistry.addToolRecipe(TinkerTools.rapier, new Item[] { _SWORD_BLADE, _TOOLROD, _CROSSBAR });
        TConstructRegistry.addToolRecipe(TinkerTools.scythe, new Item[] { _SCYTHE_HEAD, _TOUGHROD, _TOUGHBIND, _TOUGHROD });
        TConstructRegistry.addToolRecipe(TinkerTools.shortbow, new Item[] { _TOOLROD, TinkerTools.bowstring, _TOOLROD });
        TConstructRegistry.addToolRecipe(TinkerTools.shortbow, new Item[] { TinkerTools.toolRod, TinkerTools.bowstring, _TOOLROD });
        TConstructRegistry.addToolRecipe(TinkerTools.shortbow, new Item[] { _TOOLROD, TinkerTools.bowstring, TinkerTools.toolRod });
        TConstructRegistry.addToolRecipe(TinkerTools.shovel, new Item[] { _SHOVEL_HEAD, _TOOLROD });
    }
}
