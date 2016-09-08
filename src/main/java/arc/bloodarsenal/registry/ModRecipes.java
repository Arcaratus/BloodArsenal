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
        ItemStack weakOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_WEAK);
        ItemStack apprenticeOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_APPRENTICE);
        ItemStack magicianOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_MAGICIAN);
        ItemStack masterOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_MASTER);
        ItemStack archmageOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_ARCHMAGE);
        ItemStack transcendentOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_TRANSCENDENT);

        //SHAPED
        addOreDictRecipe(new ItemStack(ModItems.BLOOD_INFUSED_STICK, 2), "a", "a", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_SLAB, 6), "aaa", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_STAIRS, 4), "a  ", "aa ", "aaa", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_FENCE, 3), "aba", "aba", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS, 'b', ModItems.BLOOD_INFUSED_STICK);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_FENCE_GATE), "aba", "aba", 'a', ModItems.BLOOD_INFUSED_STICK, 'b', ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS), "a", "a", 'a',  ModBlocks.BLOOD_INFUSED_WOODEN_SLAB);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS_PANE, 16), "aaa", "aaa", 'a', ModBlocks.BLOOD_STAINED_GLASS);
        addOreDictRecipe(new ItemStack(ModBlocks.GLASS_SHARD_BLOCK), "aaa", "aaa", "aaa", 'a', "shardGlass");
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_GLOWSTONE), "aa", "aa", 'a', ModItems.BLOOD_INFUSED_GLOWSTONE_DUST);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_IRON_BLOCK), "aaa", "aaa", "aaa", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT);
        addOreDictRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.BLOOD_RUNE), 4, 3), "aaa", "aba", "aaa", 'a', "stone", 'b', ModItems.GEM_SACRIFICE);
        addOreDictRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.BLOOD_RUNE), 4, 4), "aaa", "aba", "aaa", 'a', "stone", 'b', ModItems.GEM_SELF_SACRIFICE);
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE), "aaa", "aaa", "aaa", 'a', getBMItem(Constants.BloodMagicItem.SLATE));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 1), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 2), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 2));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 3), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 3));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 4), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 4));

        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS, 8), "aaa", "aba", "aaa", 'a', "blockGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS_PANE, 8), "aaa", "aba", "aaa", 'a', "paneGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GLASS_SACRIFICIAL_DAGGER), "aaa", "aba", "aca", 'a', "shardGlass", 'b', getBMItem(Constants.BloodMagicItem.SACRIFICIAL_DAGGER), 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GLASS_DAGGER_OF_SACRIFICE), "aaa", "aba", "aca", 'a', "shardGlass", 'b', getBMItem(Constants.BloodMagicItem.DAGGER_OF_SACRIFICE), 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_PICKAXE), "aaa", " c ", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_AXE), "aa ", "ac ", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_AXE), " aa", " ca", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_SHOVEL), " a ", " c ", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_SWORD), " a ", " a ", "cbc", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_PICKAXE), "aaa", "bcb", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_PICKAXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_AXE), "aab", "acb", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_AXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_AXE), "baa", "bca", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_AXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_SHOVEL), " a ", "bcb", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_SHOVEL, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_SWORD), "ada", "aca", " b ", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_SWORD, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_BURNED_STRING, 7), "aaa", "aba", "aca", 'a', Items.STRING, 'b', Items.FLINT_AND_STEEL, 'c', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GEM_SACRIFICE), "aba", "cdc", "aea", 'a', "ingotGold", 'b', getBMItem(Constants.BloodMagicItem.DAGGER_OF_SACRIFICE), 'c', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GEM_SELF_SACRIFICE), "aba", "cdc", "aea", 'a', "dustGlowstone", 'b', getBMItem(Constants.BloodMagicItem.SACRIFICIAL_DAGGER), 'c', new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GEM_TARTARIC), "aba", "cdc", "efg", 'a', getBMItem(Constants.BloodMagicItem.SOUL_GEM), 'b', "ingotGold", 'c', "blockGlass", 'd', "gemDiamond", 'e', "blockRedstone", 'f', weakOrb, 'g', "blockLapis");
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.ALTARE_AENIGMATICA), "aba", "cdc", "efe", 'a', getBMItem(Constants.BloodMagicItem.BLOOD_SHARD), 'b', "gemEmerald", 'c', getBMBlock(Constants.BloodMagicBlock.INPUT_ROUTING_NODE), 'd', ItemComponent.getStack(ItemComponent.REAGENT_SIGHT), 'e', ModBlocks.BLOOD_INFUSED_IRON_BLOCK, 'f', masterOrb);

        //TODO IS TEMPORARY BUT WILL BE IMPLEMENTED FOR THOSE WHO WANT ACCESS TO TIER 6
        if (ConfigHandler.crystalClusterEnabled)
        {
            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.CRYSTAL)), "aba", "cdc", "efe", 'a', new ItemStack(getBMItem(Constants.BloodMagicItem.ACTIVATION_CRYSTAL), 1, 1), 'b', "blockEmerald", 'c', "blockLapis", 'd', Blocks.BEACON, 'e', "blockDiamond", 'f', archmageOrb);
            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(Constants.BloodMagicBlock.CRYSTAL), 4, 1), "aa", "aa", 'a', getBMBlock(Constants.BloodMagicBlock.CRYSTAL));
        }

        //SHAPELESS
        addShapelessOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS, 2), ModBlocks.BLOOD_INFUSED_WOODEN_LOG);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS), ModBlocks.BLOOD_STAINED_GLASS);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS_PANE), ModBlocks.BLOOD_STAINED_GLASS_PANE);
        addShapelessOreDictRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_INGOT, 9), ModBlocks.BLOOD_INFUSED_IRON_BLOCK);
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9), ModBlocks.SLATE);
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 1), new ItemStack(ModBlocks.SLATE, 1, 1));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 2), new ItemStack(ModBlocks.SLATE, 1, 2));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 3), new ItemStack(ModBlocks.SLATE, 1, 3));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 9, 4), new ItemStack(ModBlocks.SLATE, 1, 4));

        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS), "blockGlass", weakOrb);
        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS_PANE), "paneGlass", weakOrb);
    }

    public static void addAltarRecipes()
    {
        //ONE

        //TWO
        addAltarRecipe("logWood", new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_LOG), EnumAltarTier.TWO, 5000, 5, 5);
        addAltarRecipe(new ItemStack(Items.DYE, 1, 14), new ItemStack(ModItems.BLOOD_ORANGE), EnumAltarTier.TWO, 500, 10, 10);

        //THREE
        addAltarRecipe("dustGlowstone", new ItemStack(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST), EnumAltarTier.THREE, 2500, 5, 5);
        addAltarRecipe(new ItemStack(ModItems.INERT_BLOOD_INFUSED_IRON_INGOT), new ItemStack(ModItems.BLOOD_INFUSED_IRON_INGOT), EnumAltarTier.THREE, 10000, 5, 5);

        //FOUR

        //FIVE

        //SIX
    }

    public static void addHellfireForgeRecipes()
    {
        addForgeRecipe(new ItemStack(ModItems.INERT_BLOOD_INFUSED_IRON_INGOT), 256, 32, Items.IRON_INGOT, ModItems.BLOOD_INFUSED_GLOWSTONE_DUST, ItemComponent.getStack(ItemComponent.REAGENT_BINDING), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()));
        addForgeRecipe(new ItemStack(ModItems.REAGENT_SWIMMING), 100, 40, ItemComponent.getStack(ItemComponent.REAGENT_WATER), Items.PRISMARINE_SHARD, Items.GLASS_BOTTLE, Items.FISH);
        addForgeRecipe(new ItemStack(ModItems.REAGENT_ENDER), 2200, 800, ItemComponent.getStack(ItemComponent.REAGENT_TELEPOSITION), Items.ENDER_EYE, Blocks.ENDER_CHEST, Items.END_CRYSTAL);
//        addForgeRecipe(new ItemStack(ModItems.SIGIL_AUGMENTED_HOLDING), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING), new ItemStack(Blocks.CHEST, 8), "leather");
        addForgeRecipe(new ItemStack(ModItems.SIGIL_AUGMENTED_HOLDING), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), new ItemStack(getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING)), new ItemStack(Blocks.CHEST, 8), "leather");
        addForgeRecipe(new ItemStack(ModItems.REAGENT_DIVINITY), 16384, 16384, new ItemStack(ModItems.REAGENT_LIGHTNING, 32), new ItemStack(getBMBlock(Constants.BloodMagicBlock.CRYSTAL), 8), new ItemStack(Items.GOLDEN_APPLE, 4, 1), new ItemStack(Items.NETHER_STAR, 16));
    }

    public static void addAlchemyArrayRecipes()
    {
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_SWIMMING), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 1), new ItemStack(ModItems.SIGIL_SWIMMING));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_ENDER), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 3), new ItemStack(ModItems.SIGIL_ENDER));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_LIGHTNING), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 3), new ItemStack(ModItems.SIGIL_LIGHTNING));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_DIVINITY), new ItemStack(getBMItem(Constants.BloodMagicItem.SLATE), 1, 4), new ItemStack(ModItems.SIGIL_DIVINITY));
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
