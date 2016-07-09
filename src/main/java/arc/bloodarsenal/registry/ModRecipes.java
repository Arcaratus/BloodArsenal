package arc.bloodarsenal.registry;

import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.altar.EnumAltarTier;
import WayofTime.bloodmagic.api.recipe.ShapedBloodOrbRecipe;
import WayofTime.bloodmagic.api.recipe.ShapelessBloodOrbRecipe;
import WayofTime.bloodmagic.api.registry.AlchemyArrayRecipeRegistry;
import WayofTime.bloodmagic.api.registry.AltarRecipeRegistry;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import WayofTime.bloodmagic.api.registry.TartaricForgeRecipeRegistry;
import WayofTime.bloodmagic.item.ItemComponent;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.util.TartaricForgeSigilHoldingRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

public class ModRecipes
{
    public static void init()
    {
        addCraftingRecipes();
        addAltarRecipes();
        addHellfireForgeRecipes();
        addAlchemyArrayRecipes();
    }

    public static void addCraftingRecipes()
    {
        ItemStack weakOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbWeak);
        ItemStack apprenticeOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbApprentice);
        ItemStack magicianOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbMagician);
        ItemStack masterOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbMaster);
        ItemStack archmageOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbArchmage);
        ItemStack transcendentOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbTranscendent);

        //SHAPED
        addOreDictRecipe(new ItemStack(ModItems.bloodInfusedStick, 2), "a", "a", 'a', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenSlab, 6), "aaa", 'a', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenStairs, 4), "a  ", "aa ", "aaa", 'a', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenFence, 3), "aba", "aba", 'a', ModBlocks.bloodInfusedWoodenPlanks, 'b', ModItems.bloodInfusedStick);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenFenceGate), "aba", "aba", 'a', ModItems.bloodInfusedStick, 'b', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenPlanks), "a", "a", 'a',  ModBlocks.bloodInfusedWoodenSlab);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodStainedGlassPane, 16), "aaa", "aaa", 'a', ModBlocks.bloodStainedGlass);
        addOreDictRecipe(new ItemStack(ModBlocks.glassShardBlock), "aaa", "aaa", "aaa", 'a', ModItems.glassShard);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedGlowstone), "aa", "aa", 'a', ModItems.bloodInfusedGlowstoneDust);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedIronBlock), "aaa", "aaa", "aaa", 'a', ModItems.bloodInfusedIronIngot);
        addOreDictRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.BLOOD_RUNE), 4, 3), "aaa", "aba", "aaa", 'a', "stone", 'b', ModItems.gemSacrifice);
        addOreDictRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.BLOOD_RUNE), 4, 4), "aaa", "aba", "aaa", 'a', "stone", 'b', ModItems.gemSelfSacrifice);
        addOreDictRecipe(new ItemStack(ModBlocks.slate), "aaa", "aaa", "aaa", 'a', getBMItem(Constants.BloodMagicItem.SLATE));
        addOreDictRecipe(new ItemStack(ModBlocks.slate, 1, 1), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1));
        addOreDictRecipe(new ItemStack(ModBlocks.slate, 1, 2), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 2));
        addOreDictRecipe(new ItemStack(ModBlocks.slate, 1, 3), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 3));
        addOreDictRecipe(new ItemStack(ModBlocks.slate, 1, 4), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 4));

        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlass, 8), "aaa", "aba", "aaa", 'a', "blockGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlassPane, 8), "aaa", "aba", "aaa", 'a', "paneGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glassSacrificialDagger), "aaa", "aba", "aca", 'a', "glassShard", 'b', getBMItem(Constants.BloodMagicItem.SACRIFICIAL_DAGGER), 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.glassDaggerOfSacrifice), "aaa", "aba", "aca", 'a', "glassShard", 'b', getBMItem(Constants.BloodMagicItem.DAGGER_OF_SACRIFICE), 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedWoodenPickaxe), "aaa", " c ", " b ", 'a', ModBlocks.bloodInfusedWoodenLog, 'b', apprenticeOrb, 'c', ModItems.bloodInfusedStick);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedWoodenAxe), "aa ", "ac ", " b ", 'a', ModBlocks.bloodInfusedWoodenLog, 'b', apprenticeOrb, 'c', ModItems.bloodInfusedStick);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedWoodenAxe), " aa", " ca", " b ", 'a', ModBlocks.bloodInfusedWoodenLog, 'b', apprenticeOrb, 'c', ModItems.bloodInfusedStick);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedWoodenShovel), " a ", " c ", " b ", 'a', ModBlocks.bloodInfusedWoodenLog, 'b', apprenticeOrb, 'c', ModItems.bloodInfusedStick);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedWoodenSword), " a ", " a ", "cbc", 'a', ModBlocks.bloodInfusedWoodenLog, 'b', apprenticeOrb, 'c', ModItems.bloodInfusedStick);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedIronPickaxe), "aaa", "bcb", "bdb", 'a', ModItems.bloodInfusedIronIngot, 'b', ModItems.bloodInfusedStick, 'c', ModItems.bloodInfusedWoodenPickaxe, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedIronAxe), "aab", "acb", "bdb", 'a', ModItems.bloodInfusedIronIngot, 'b', ModItems.bloodInfusedStick, 'c', ModItems.bloodInfusedWoodenAxe, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedIronAxe), "baa", "bca", "bdb", 'a', ModItems.bloodInfusedIronIngot, 'b', ModItems.bloodInfusedStick, 'c', ModItems.bloodInfusedWoodenAxe, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedIronShovel), " a ", "bcb", "bdb", 'a', ModItems.bloodInfusedIronIngot, 'b', ModItems.bloodInfusedStick, 'c', ModItems.bloodInfusedWoodenShovel, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodInfusedIronSword), "ada", "aca", " b ", 'a', ModItems.bloodInfusedIronIngot, 'b', ModItems.bloodInfusedStick, 'c', ModItems.bloodInfusedWoodenSword, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.bloodBurnedString, 7), "aaa", "aba", "aca", 'a', Items.STRING, 'b', Items.FLINT_AND_STEEL, 'c', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.gemSacrifice), "aba", "cdc", "aea", 'a', "ingotGold", 'b', getBMItem(Constants.BloodMagicItem.DAGGER_OF_SACRIFICE), 'c', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.gemSelfSacrifice), "aba", "cdc", "aea", 'a', "dustGlowstone", 'b', getBMItem(Constants.BloodMagicItem.SACRIFICIAL_DAGGER), 'c', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.gemTartaric), "aba", "cdc", "efg", 'a', getBMItem(Constants.BloodMagicItem.SOUL_GEM), 'b', "ingotGold", 'c', "blockGlass", 'd', "gemDiamond", 'e', "blockRedstone", 'f', weakOrb, 'g', "blockLapis");
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.altareAenigmatica), "aba", "cdc", "efe", 'a', getBMItem(Constants.BloodMagicItem.BLOOD_SHARD), 'b', "gemEmerald", 'c', getBMBlock(Constants.BloodMagicBlock.INPUT_ROUTING_NODE), 'd', ItemComponent.getStack(ItemComponent.REAGENT_SIGHT), 'e', ModBlocks.bloodInfusedIronBlock, 'f', masterOrb);

        //TODO IS TEMPORARY BUT WILL BE IMPLEMENTED FOR THOSE WHO WANT ACCESS TO TIER 6
        if (ConfigHandler.crystalClusterEnabled)
            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.CRYSTAL)), "aba", "cdc", "efe", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.ACTIVATION_CRYSTAL), 1, 1), 'b', "blockEmerald", 'c', "blockLapis", 'd', Blocks.BEACON, 'e', "blockDiamond", 'f', archmageOrb);

        //SHAPELESS
        addShapelessOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenPlanks, 2), ModBlocks.bloodInfusedWoodenLog);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS), ModBlocks.bloodStainedGlass);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS_PANE), ModBlocks.bloodStainedGlassPane);
        addShapelessOreDictRecipe(new ItemStack(ModItems.bloodInfusedIronIngot, 9), ModBlocks.bloodInfusedIronBlock);
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9), ModBlocks.slate);
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 1), new ItemStack(ModBlocks.slate, 1, 1));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 2), new ItemStack(ModBlocks.slate, 1, 2));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 3), new ItemStack(ModBlocks.slate, 1, 3));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 4), new ItemStack(ModBlocks.slate, 1, 4));

        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlass), "blockGlass", weakOrb);
        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlassPane), "paneGlass", weakOrb);
    }

    public static void addAltarRecipes()
    {
        //ONE

        //TWO
        addAltarRecipe("logWood", new ItemStack(ModBlocks.bloodInfusedWoodenLog), EnumAltarTier.TWO, 5000, 5, 5);
        addAltarRecipe(new ItemStack(Items.DYE, 1, 14), new ItemStack(ModItems.bloodOrange), EnumAltarTier.TWO, 500, 10, 10);

        //THREE
        addAltarRecipe("dustGlowstone", new ItemStack(ModItems.bloodInfusedGlowstoneDust), EnumAltarTier.THREE, 2500, 5, 5);
        addAltarRecipe(new ItemStack(ModItems.inertBloodInfusedIronIngot), new ItemStack(ModItems.bloodInfusedIronIngot), EnumAltarTier.THREE, 10000, 5, 5);

        //FOUR

        //FIVE

        //SIX
    }

    public static void addHellfireForgeRecipes()
    {
        addForgeRecipe(new ItemStack(ModItems.inertBloodInfusedIronIngot), 256, 32, Items.IRON_INGOT, ModItems.bloodInfusedGlowstoneDust, ItemComponent.getStack(ItemComponent.REAGENT_BINDING), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()));
        addForgeRecipe(new ItemStack(ModItems.reagentSwimming), 100, 40, ItemComponent.getStack(ItemComponent.REAGENT_WATER), Items.PRISMARINE_SHARD, Items.GLASS_BOTTLE, Items.FISH);
        addForgeRecipe(new ItemStack(ModItems.reagentEnder), 2200, 800, ItemComponent.getStack(ItemComponent.REAGENT_TELEPOSITION), Items.ENDER_EYE, Blocks.ENDER_CHEST, Items.END_CRYSTAL);
//        addForgeRecipe(new ItemStack(ModItems.sigilAugmentedHolding), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING), new ItemStack(Blocks.CHEST, 8), "leather");
        TartaricForgeRecipeRegistry.registerRecipe(new TartaricForgeSigilHoldingRecipe(new ItemStack(ModItems.sigilAugmentedHolding), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), new ItemStack(getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING)), new ItemStack(Blocks.CHEST, 8), "leather"));
        addForgeRecipe(new ItemStack(ModItems.reagentDivinity), 16384, 16384, new ItemStack(ModItems.reagentLightning, 32), new ItemStack(getBMBlock(Constants.BloodMagicBlock.CRYSTAL), 8), new ItemStack(Items.GOLDEN_APPLE, 4, 1), new ItemStack(Items.NETHER_STAR, 16));
    }

    public static void addAlchemyArrayRecipes()
    {
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.reagentSwimming), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1), new ItemStack(ModItems.sigilSwimming));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.reagentEnder), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 3), new ItemStack(ModItems.sigilEnder));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.reagentLightning), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 3), new ItemStack(ModItems.sigilLightning));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.reagentDivinity), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 4), new ItemStack(ModItems.sigilDivinity));
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

    public static void addShapelessBloodOrbRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessBloodOrbRecipe(output, recipe));
    }

    public static void addAltarRecipe(List<ItemStack> input, ItemStack output, EnumAltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(input, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addAltarRecipe(ItemStack input, ItemStack output, EnumAltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(input, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addAltarRecipe(String oreDictInput, ItemStack output, EnumAltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(oreDictInput, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addForgeRecipe(ItemStack output, double minWill, double drain, Object... recipe)
    {
        TartaricForgeRecipeRegistry.registerRecipe(output, minWill, drain, recipe);
    }

    public static Item getBMItem(String name)
    {
        return BloodMagicAPI.getItem(name);
    }

    public static Item getBMItem(Constants.BloodMagicItem item)
    {
        return BloodMagicAPI.getItem(item);
    }

    public static Block getBMBlock(String name)
    {
        return BloodMagicAPI.getBlock(name);
    }

    public static Block getBMBlock(Constants.BloodMagicBlock block)
    {
        return BloodMagicAPI.getBlock(block);
    }
}
