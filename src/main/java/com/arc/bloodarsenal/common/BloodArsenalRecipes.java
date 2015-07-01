package com.arc.bloodarsenal.common;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import WayofTime.alchemicalWizardry.api.items.ShapedBloodOrbRecipe;
import com.arc.bloodarsenal.common.block.ModBlocks;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.Optional;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BloodArsenalRecipes
{
    public static void registerAltarRecipes()
    {
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModBlocks.blood_tnt), new ItemStack(Blocks.tnt), 3, 10000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(Blocks.log), 2, 3000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModBlocks.blood_infused_iron_block), new ItemStack(Blocks.iron_block), 3, 54000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.item_blood_cake), new ItemStack(Items.cake), 3, 10000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_infused_iron), new ItemStack(Items.iron_ingot), 2, 6000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_orange), new ItemStack(Items.dye, 1, 14), 1, 200, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_infused_diamond_active), new ItemStack(ModItems.blood_infused_diamond_unactive), 4, 80000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.soul_fragment), new ItemStack(ModItems.heart), 4, 75000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_cookie), new ItemStack(Items.cookie), 1, 2000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_infused_glowstone_dust), new ItemStack(Items.glowstone_dust), 3, 5000, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_ball), new ItemStack(Items.snowball), 2, 500, 5, 5, false);
        AltarRecipeRegistry.registerAltarRecipe(new ItemStack(ModItems.blood_money), new ItemStack(Items.paper), 4, 10000, 5, 5, false);
    }

    public static void registerBindingRecipes()
    {
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_bow), new ItemStack(Items.bow));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_sickle), new ItemStack(Items.diamond_hoe));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_shears), new ItemStack(Items.shears));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.blood_infused_diamond_bound), new ItemStack(ModItems.blood_infused_diamond_active));
        BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_igniter), new ItemStack(Items.flint_and_steel));
        //Temporary
        if (BloodArsenal.isHalloween())
        {
            BindingRegistry.registerRecipe(new ItemStack(ModItems.vampire_cape), new ItemStack(Items.leather_chestplate));
            BindingRegistry.registerRecipe(new ItemStack(ModItems.vampire_greaves), new ItemStack(Items.leather_leggings));
            BindingRegistry.registerRecipe(new ItemStack(ModItems.vampire_boots), new ItemStack(Items.leather_boots));
        }
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

        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.blood_stained_ice, 8), "aaa", "aba", "aaa", 'a', Blocks.ice, 'b', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.blood_stained_glass, 8), "aaa", "aba", "aaa", 'a', Blocks.glass, 'b', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.life_infuser), "aba", "aca", "ada", 'a', ModItems.blood_infused_iron, 'b', masterOrb, 'c', WayofTime.alchemicalWizardry.ModBlocks.blockAltar, 'd', new ItemStack(ModItems.blood_infused_diamond_bound));
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.lp_materializer), "aba", "bcb", "aba", 'a', ModItems.blood_infused_iron, 'b', imbuedSlate, 'c', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.compacter), "aaa", "bcb", "ded", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_diamond_bound, 'c', Blocks.piston, 'd', ModBlocks.blood_infused_iron_block, 'e', archmageOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_axe_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_axe_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_pickaxe_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_pickaxe_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_shovel_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_shovel_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_sword_iron), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_iron, 'b', ModItems.blood_infused_sword_wood, 'c', ModItems.amorphic_catalyst, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_diamond), "aba", "bcb", "aba", 'a', Blocks.glass, 'b', Items.diamond, 'c', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_diamond_unactive), "aba", "bcb", "ada", 'a', ModItems.amorphic_catalyst, 'b', ModBlocks.blood_infused_iron_block, 'c', ModItems.blood_diamond, 'd', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_axe_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_axe_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_pickaxe_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_pickaxe_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_shovel_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_shovel_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_infused_sword_diamond), "aaa", "aba", "cdc", 'a', ModItems.blood_infused_diamond_bound, 'b', ModItems.blood_infused_sword_iron, 'c', ModItems.amorphic_catalyst, 'd', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.soul_booster), "aaa", "aba", "aca", 'a', ModItems.soul_fragment, 'b', Blocks.beacon, 'c', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.soul_nullifier), "aaa", "aba", "aca", 'a', ModItems.soul_fragment, 'b', WayofTime.alchemicalWizardry.ModItems.armourInhibitor, 'c', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.sigil_of_swimming), "aba", "cdc", "aea", 'a', Items.water_bucket, 'b', Items.bucket, 'c', Items.lava_bucket, 'd', WayofTime.alchemicalWizardry.ModItems.voidSigil, 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.sigil_of_ender), "aba", "cdc", "efe", 'a', Blocks.obsidian, 'b', Items.ender_eye, 'c', Items.ender_pearl, 'd', imbuedSlate, 'e', Blocks.ender_chest, 'f', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.sigil_of_divinity), "aba", "cde", "fgf", 'a', ModBlocks.blood_infused_glowstone, 'b', new ItemStack(Items.golden_apple, 1, 1), 'c', Items.nether_star, 'd', WayofTime.alchemicalWizardry.ModItems.sigilOfElementalAffinity, 'e', ModItems.blood_infused_diamond_bound, 'f', ModItems.amorphic_catalyst, 'g', transcendentOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.blood_burned_string, 4), "aaa", "aba", "aaa", 'a', Items.string, 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.sigil_of_augmented_holding), "aba", "cde", "fgf", 'a', demonicSlate, 'b', masterOrb, 'c', Items.blaze_rod, 'd', WayofTime.alchemicalWizardry.ModItems.sigilOfHolding, 'e', Items.ghast_tear, 'f', ModItems.blood_infused_iron, 'g', Blocks.chest);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.sigil_of_lightning), "aba", "cdc", "efg", 'a', new ItemStack(ModBlocks.blood_stone, 1, 3), 'b', Blocks.iron_block, 'c', ModBlocks.blood_infused_iron_block, 'd', etherealSlate, 'e', Items.nether_star, 'f', archmageOrb, 'g', ModItems.blood_infused_diamond_bound);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glass_sacrificial_dagger), "aaa", "aba", "cdc", 'a', ModItems.glass_shard, 'b', WayofTime.alchemicalWizardry.ModItems.sacrificialDagger, 'c', ModItems.blood_infused_iron, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glass_dagger_of_sacrifice), "aaa", "aba", "cdc", 'a', ModItems.glass_shard, 'b', WayofTime.alchemicalWizardry.ModItems.daggerOfSacrifice, 'c', ModItems.blood_infused_iron, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glass_helmet), "aaa", "aba", 'a', ModItems.glass_shard, 'b', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glass_chestplate), "aba", "aaa", "aaa", 'a', ModItems.glass_shard, 'b', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glass_leggings), "aaa", "aba", "a a", 'a', ModItems.glass_shard, 'b', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glass_boots), "a a", "aba", 'a', ModItems.glass_shard, 'b', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.transparent_orb), "aba", "bcb", "ded", 'a', ModBlocks.blood_stained_glass, 'b', Blocks.glass, 'c', transcendentOrb, 'd', ModItems.blood_burned_string, 'e', WayofTime.alchemicalWizardry.ModItems.divinationSigil);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.life_imbued_helmet), "aba", "aca", 'a', ModItems.blood_infused_iron, 'b', magicianOrb, 'c', Items.iron_helmet);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.life_imbued_chestplate), "aba", "aca", "aaa", 'a', ModItems.blood_infused_iron, 'b', magicianOrb, 'c', Items.iron_chestplate);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.life_imbued_leggings), "aba", "aca", "a a", 'a', ModItems.blood_infused_iron, 'b', magicianOrb, 'c', Items.iron_leggings);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.life_imbued_boots), "aba", "aca", 'a', ModItems.blood_infused_iron, 'b', magicianOrb, 'c', Items.iron_boots);

        addOreDictRecipe(new ItemStack(ModBlocks.blood_stone), "aaa", "aaa", "aaa", 'a', blankSlate);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_stone, 1, 1), "aaa", "aaa", "aaa", 'a', reinforcedSlate);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_stone, 1, 2), "aaa", "aaa", "aaa", 'a', imbuedSlate);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_stone, 1, 3), "aaa", "aaa", "aaa", 'a', demonicSlate);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_stone, 1, 4), "aaa", "aaa", "aaa", 'a', etherealSlate);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_infused_iron_block), "aaa", "aaa", "aaa", 'a', ModItems.blood_infused_iron);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_stained_ice_packed), "aa", "aa", 'a', ModBlocks.blood_stained_ice);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_infused_glowstone), "aa", "aa", 'a', ModItems.blood_infused_glowstone_dust);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_lamp), "aba", "bcb", "aba", 'a', ModItems.blood_infused_iron, 'b', ModBlocks.blood_stained_glass, 'c', ModBlocks.blood_infused_glowstone);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_infused_diamond_block), "aaa", "aaa", "aaa", 'a', ModItems.blood_infused_diamond_bound);
        addOreDictRecipe(new ItemStack(ModBlocks.blood_torch), "a", "b", 'a', ModItems.bound_igniter, 'b', ModItems.blood_infused_stick);
        addOreDictRecipe(new ItemStack(ModItems.blood_infused_stick), "a", "a", 'a', ModBlocks.blood_infused_planks);
        addOreDictRecipe(new ItemStack(ModItems.blood_infused_pickaxe_wood), "aaa", " b ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        addOreDictRecipe(new ItemStack(ModItems.blood_infused_axe_wood), "aa ", "ab ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        addOreDictRecipe(new ItemStack(ModItems.blood_infused_shovel_wood), " a ", " b ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        addOreDictRecipe(new ItemStack(ModItems.blood_infused_sword_wood), " a ", " a ", " b ", 'a', ModBlocks.blood_infused_planks, 'b', ModItems.blood_infused_stick);
        addOreDictRecipe(new ItemStack(ModItems.blood_money, 1, 1), "aa", "aa", 'a', new ItemStack(ModItems.blood_money));
        addOreDictRecipe(new ItemStack(ModItems.blood_money, 1, 2), "aa", "aa", 'a', new ItemStack(ModItems.blood_money, 1, 1));
        addOreDictRecipe(new ItemStack(ModItems.blood_money, 1, 3), "aa", "aa", 'a', new ItemStack(ModItems.blood_money, 1, 2));

        addShapelessOreDictRecipe(new ItemStack(ModBlocks.blood_infused_planks, 4), ModBlocks.blood_infused_wood);
        addShapelessOreDictRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.blankSlate, 9), ModBlocks.blood_stone);
        addShapelessOreDictRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.reinforcedSlate, 9), new ItemStack(ModBlocks.blood_stone, 1, 1));
        addShapelessOreDictRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.imbuedSlate, 9), new ItemStack(ModBlocks.blood_stone, 1, 2));
        addShapelessOreDictRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.demonicSlate, 9), new ItemStack(ModBlocks.blood_stone, 1, 3));
        addShapelessOreDictRecipe(new ItemStack(WayofTime.alchemicalWizardry.ModItems.baseAlchemyItems, 9, 27), new ItemStack(ModBlocks.blood_stone, 1, 4));
        //This is a long one
        addShapelessOreDictRecipe(new ItemStack(ModItems.amorphic_catalyst), WayofTime.alchemicalWizardry.ModItems.blankSlate, WayofTime.alchemicalWizardry.ModItems.aether, WayofTime.alchemicalWizardry.ModItems.terrae, WayofTime.alchemicalWizardry.ModItems.crystallos, WayofTime.alchemicalWizardry.ModItems.sanctus, WayofTime.alchemicalWizardry.ModItems.magicales, WayofTime.alchemicalWizardry.ModItems.crepitous, WayofTime.alchemicalWizardry.ModItems.incendium, WayofTime.alchemicalWizardry.ModItems.aquasalus);
        addShapelessOreDictRecipe(new ItemStack(ModItems.blood_infused_iron, 9), ModBlocks.blood_infused_iron_block);
        addShapelessOreDictRecipe(new ItemStack(ModItems.orange_juice), ModItems.blood_orange, Items.glass_bottle);
        addShapelessOreDictRecipe(new ItemStack(ModItems.juice_and_cookies), Items.cookie, ModItems.orange_juice);
    }

    @Optional.Method(modid = "Baubles")
    public static void addBaublesRecipe()
    {
        int craftingConstant = OreDictionary.WILDCARD_VALUE;

        ItemStack weakOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.weakBloodOrb, 1, craftingConstant);
//        ItemStack apprenticeOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.apprenticeBloodOrb, 1, craftingConstant);
//        ItemStack magicianOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.magicianBloodOrb, 1, craftingConstant);
        ItemStack masterOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.masterBloodOrb, 1, craftingConstant);
//        ItemStack archmageOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.archmageBloodOrb, 1, craftingConstant);
//        ItemStack transcendentOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.transcendentBloodOrb, 1, craftingConstant);

        addOreDictBloodOrbRecipe(new ItemStack(ModItems.vampire_ring), "ab ", "bcb", " b ", 'a', ModItems.blood_infused_diamond_bound, 'b', Blocks.stone, 'c', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.sacrifice_amulet), "aaa", "aba", "caa", 'a', ModItems.blood_burned_string, 'b', weakOrb, 'c', Items.gold_ingot);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.self_sacrifice_amulet), "aaa", "aba", "caa", 'a', ModItems.blood_burned_string, 'b', weakOrb, 'c', Items.glowstone_dust);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.empowered_self_sacrifice_amulet), "aba", "cdc", "ecf", 'a', ModItems.blood_infused_glowstone_dust, 'b', Items.blaze_rod, 'c', WayofTime.alchemicalWizardry.ModBlocks.runeOfSelfSacrifice, 'd', ModItems.self_sacrifice_amulet, 'e', Blocks.glowstone, 'f', ModItems.amorphic_catalyst);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.empowered_sacrifice_amulet), "aba", "cdc", "ecf", 'a', ModItems.blood_infused_glowstone_dust, 'b', Items.blaze_rod, 'c', WayofTime.alchemicalWizardry.ModBlocks.runeOfSacrifice, 'd', ModItems.sacrifice_amulet, 'e', Blocks.gold_block, 'f', ModItems.amorphic_catalyst);
    }

    public static void addOreDictRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
    }

    public static void addOreDictBloodOrbRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedBloodOrbRecipe(output, recipe));
    }

    public static void addShapelessOreDictRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }
}
