package com.arc.bloodarsenal;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import WayofTime.alchemicalWizardry.api.items.ShapedBloodOrbRecipe;
import com.arc.bloodarsenal.block.ModBlocks;
import com.arc.bloodarsenal.items.ModItems;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class BloodArsenalRecipes
{
    public static void registerAltarRecipes()
    {
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModBlocks.blood_tnt), new ItemStack(Blocks.tnt), 3, 5000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(Blocks.log), 2, 3000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModBlocks.blood_infused_iron_block), new ItemStack(Blocks.iron_block), 3, 72000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.item_blood_cake), new ItemStack(Items.cake), 3, 10000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_infused_iron), new ItemStack(Items.iron_ingot), 3, 8000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_orange), new ItemStack(Items.dye, 1, 14), 1, 200, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_infused_diamond_active), new ItemStack(ModItems.blood_infused_diamond_unactive), 4, 80000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.soul_fragment), new ItemStack(ModItems.heart), 4, 10000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_cookie), new ItemStack(Items.cookie), 1, 2000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_infused_glowstone_dust), new ItemStack(Items.glowstone_dust), 3, 2500, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_ball), new ItemStack(Items.snowball), 2, 500, 5, 5, false);
    }

    public static void registerBindingRecipes()
    {
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_bow), new ItemStack(Items.bow));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_sickle), new ItemStack(Items.diamond_hoe));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_shears), new ItemStack(Items.shears));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.blood_infused_diamond_bound), new ItemStack(ModItems.blood_infused_diamond_active));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_igniter), new ItemStack(Items.flint_and_steel));
        //Temporary
        BindingRegistry.registerRecipe(new ItemStack(ModItems.vampire_cape), new ItemStack(Items.leather_chestplate));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.vampire_greaves), new ItemStack(Items.leather_leggings));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.vampire_boots), new ItemStack(Items.leather_boots));
    }

    public static void registerRecipes()
    {
        int craftingConstant = OreDictionary.WILDCARD_VALUE;

        ItemStack blankSlate = new ItemStack(WayofTime.alchemicalWizardry.ModItems.blankSlate);
        ItemStack reinforcedSlate = new ItemStack(WayofTime.alchemicalWizardry.ModItems.reinforcedSlate);
        ItemStack imbuedSlate = new ItemStack(WayofTime.alchemicalWizardry.ModItems.imbuedSlate);
        ItemStack demonicSlate = new ItemStack(WayofTime.alchemicalWizardry.ModItems.demonicSlate);
        ItemStack etherealSlate = new ItemStack(WayofTime.alchemicalWizardry.ModItems.baseItems, 1, 27);
        ItemStack weakOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.weakBloodOrb, 1, craftingConstant);
        ItemStack apprenticeOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.apprenticeBloodOrb, 1, craftingConstant);
        ItemStack magicianOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.magicianBloodOrb, 1, craftingConstant);
        ItemStack masterOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.masterBloodOrb, 1, craftingConstant);
        ItemStack archmageOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.archmageBloodOrb, 1, craftingConstant);
        ItemStack transcendentOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.transcendentBloodOrb, 1, craftingConstant);

        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModBlocks.blood_stained_ice, 8), "aaa", "aba", "aaa", 'a', Blocks.ice, 'b', apprenticeOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModBlocks.blood_stained_glass, 8), "aaa", "aba", "aaa", 'a', Blocks.glass, 'b', apprenticeOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_axe_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_axe_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_pickaxe_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_pickaxe_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_shovel_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_shovel_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_sword_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_sword_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_diamond), "aba", "bcb", "aba", 'a', Blocks.glass, 'b', Items.diamond, 'c', masterOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_diamond_unactive), "aba", "bcb", "ada", 'a', ModItems.amorphic_catalyst, 'b', ModBlocks.blood_infused_iron_block, 'c', ModItems.blood_diamond, 'd', masterOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_axe_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_axe_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_pickaxe_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_pickaxe_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_shovel_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_shovel_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.blood_infused_sword_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_sword_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.soul_booster), "aaa", "aba", "aca", 'a', ModItems.soul_fragment, 'b', Blocks.beacon, 'c', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.soul_nullifier), "aaa", "aba", "aca", 'a', ModItems.soul_fragment, 'b', WayofTime.alchemicalWizardry.ModItems.armourInhibitor, 'c', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.sigil_of_swimming), "aba", "cdc", "aea", 'a', Items.water_bucket, 'b', Items.bucket, 'c', Items.lava_bucket, 'd', WayofTime.alchemicalWizardry.ModItems.voidSigil, 'e', apprenticeOrb));
//        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.self_sacrifice_amulet), "aaa", "aba", "caa", 'a', Items.string, 'b', weakOrb, 'c', WayofTime.alchemicalWizardry.ModBlocks.runeOfSelfSacrifice));
//        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.sacrifice_amulet), "aaa", "aba", "caa", 'a', Items.string, 'b', weakOrb, 'c', WayofTime.alchemicalWizardry.ModBlocks.runeOfSacrifice));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.sigil_of_ender), "aba", "cdc", "efe", 'a', Blocks.obsidian, 'b', Items.ender_eye, 'c', Items.ender_pearl, 'd', imbuedSlate, 'e', Blocks.ender_chest, 'f', magicianOrb));
        GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.sigil_of_divinity), "aba", "cde", "fgf", 'a', ModBlocks.blood_infused_glowstone, 'b', new ItemStack(Items.golden_apple, 1, 1), 'c', Items.nether_star, 'd', WayofTime.alchemicalWizardry.ModItems.sigilOfElementalAffinity, 'e', ModItems.blood_infused_diamond_bound, 'f', ModItems.amorphic_catalyst, 'g', transcendentOrb));

        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_stone), "aaa", "aaa", "aaa", 'a', blankSlate);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_stone, 1, 1), "aaa", "aaa", "aaa", 'a', reinforcedSlate);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_stone, 1, 2), "aaa", "aaa", "aaa", 'a', imbuedSlate);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_stone, 1, 3), "aaa", "aaa", "aaa", 'a', demonicSlate);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_stone, 1, 4), "aaa", "aaa", "aaa", 'a', etherealSlate);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_infused_iron_block), "aaa", "aaa", "aaa", 'a', ModItems.blood_infused_iron);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_door_wood), "aa", "aa", "aa", 'a', ModBlocks.blood_infused_planks);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_stained_ice_packed), "aa", "aa", 'a', ModBlocks.blood_stained_ice);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_door_wood), "aa", "aa", "aa", 'a', ModBlocks.blood_infused_planks);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_door_iron), "aa", "aa", "aa", 'a', ModItems.blood_infused_iron);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_infused_glowstone), "aa", "aa", 'a', ModItems.blood_infused_glowstone_dust);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_lamp), "aba", "bcb", "aba", 'a', ModItems.blood_infused_iron, 'b', ModBlocks.blood_stained_glass, 'c', ModBlocks.blood_infused_glowstone);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_infused_diamond_block), "aaa", "aaa", "aaa", 'a', ModItems.blood_infused_diamond_bound);
        GameRegistry.addRecipe(new ItemStack(ModItems.blood_infused_stick), "a", "a", 'a', ModBlocks.blood_infused_planks);
        GameRegistry.addRecipe(new ItemStack(ModItems.blood_infused_pickaxe_wood), "aaa", " b ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        GameRegistry.addRecipe(new ItemStack(ModItems.blood_infused_axe_wood), "aa ", "ab ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        GameRegistry.addRecipe(new ItemStack(ModItems.blood_infused_shovel_wood), " a ", " b ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        GameRegistry.addRecipe(new ItemStack(ModItems.blood_infused_sword_wood), " a ", " a ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blood_infused_planks, 4), ModBlocks.blood_infused_wood);
        GameRegistry.addShapelessRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.blankSlate, 9), ModBlocks.blood_stone);
        GameRegistry.addShapelessRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.reinforcedSlate, 9), new ItemStack(ModBlocks.blood_stone, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.imbuedSlate, 9), new ItemStack(ModBlocks.blood_stone, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.demonicSlate, 9), new ItemStack(ModBlocks.blood_stone, 1, 3));
        //This is a long one
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.amorphic_catalyst), WayofTime.alchemicalWizardry.ModItems.blankSlate, WayofTime.alchemicalWizardry.ModItems.aether, WayofTime.alchemicalWizardry.ModItems.terrae, WayofTime.alchemicalWizardry.ModItems.crystallos, WayofTime.alchemicalWizardry.ModItems.sanctus, WayofTime.alchemicalWizardry.ModItems.magicales, WayofTime.alchemicalWizardry.ModItems.crepitous, WayofTime.alchemicalWizardry.ModItems.incendium, WayofTime.alchemicalWizardry.ModItems.aquasalus);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.blood_infused_iron, 9), ModBlocks.blood_infused_iron_block);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.orange_juice), ModItems.blood_orange, Items.glass_bottle);
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.juice_and_cookies), Items.cookie, ModItems.orange_juice);

        if (Loader.isModLoaded("Baubles"))
        {
//            GameRegistry.addRecipe(new ShapedBloodOrbRecipe(new ItemStack(ModItems.vampire_ring),"ab ", "bcb", " b ", 'a', ModItems.blood_infused_diamond_bound, 'b', Blocks.stone, 'c', masterOrb));
//            GameRegistry.addRecipe(new ItemStack(ModItems.self_sacrifice_amulet), "a", 'a', new ItemStack(Items.slime_ball));
        }

        {
            ItemStack igniter = new ItemStack(ModItems.bound_igniter);

            GameRegistry.addRecipe(new ItemStack(ModBlocks.blood_torch), "a", "b", 'a', igniter, 'b', ModItems.blood_infused_stick);
        }
    }
}
