package arc.bloodarsenal.registry;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.Constants.BloodMagicBlock;
import WayofTime.bloodmagic.api.Constants.BloodMagicItem;
import WayofTime.bloodmagic.api.altar.EnumAltarTier;
import WayofTime.bloodmagic.api.iface.ISigil;
import WayofTime.bloodmagic.api.recipe.ShapedBloodOrbRecipe;
import WayofTime.bloodmagic.api.recipe.ShapelessBloodOrbRecipe;
import WayofTime.bloodmagic.api.registry.*;
import WayofTime.bloodmagic.client.render.alchemyArray.BindingAlchemyCircleRenderer;
import WayofTime.bloodmagic.item.ItemComponent;
import WayofTime.bloodmagic.util.Utils;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
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
        addSanguineInfusionRecipes();
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
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS), "a", "a", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_SLAB);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS_PANE, 16), "aaa", "aaa", 'a', ModBlocks.BLOOD_STAINED_GLASS);
        addOreDictRecipe(new ItemStack(ModBlocks.GLASS_SHARD_BLOCK), "aaa", "aaa", "aaa", 'a', "shardGlass");
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_GLOWSTONE), "aa", "aa", 'a', ModItems.BLOOD_INFUSED_GLOWSTONE_DUST);
        addOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_IRON_BLOCK), "aaa", "aaa", "aaa", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT);
        addOreDictRecipe(new ItemStack(getBMBlock(BloodMagicBlock.BLOOD_RUNE), 4, 3), "aaa", "aba", "aaa", 'a', "stone", 'b', ModItems.GEM_SACRIFICE);
        addOreDictRecipe(new ItemStack(getBMBlock(BloodMagicBlock.BLOOD_RUNE), 4, 4), "aaa", "aba", "aaa", 'a', "stone", 'b', ModItems.GEM_SELF_SACRIFICE);
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE), "aaa", "aaa", "aaa", 'a', getBMItem(BloodMagicItem.SLATE));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 1), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 1));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 2), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 2));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 3), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 3));
        addOreDictRecipe(new ItemStack(ModBlocks.SLATE, 1, 4), "aaa", "aaa", "aaa", 'a', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 4));
        addOreDictRecipe(new ItemStack(ModItems.STYGIAN_DAGGER), "aaa", "aba", "cdc", 'a', "shardGlass", 'b', ModItems.GLASS_DAGGER_OF_SACRIFICE, 'c', ModItems.BLOOD_INFUSED_IRON_INGOT, 'd', ModItems.BLOOD_DIAMOND);
        addOreDictRecipe(new ItemStack(ModItems.STYGIAN_DAGGER, 1, 1), "eae", "aba", "cdc", 'a', "shardGlass", 'b', ModItems.STYGIAN_DAGGER, 'c', ModItems.BLOOD_INFUSED_IRON_INGOT, 'd', ModItems.BLOOD_DIAMOND, 'e', ModBlocks.GLASS_SHARD_BLOCK);
        addOreDictRecipe(new ItemStack(ModItems.STYGIAN_DAGGER, 1, 2), "aaa", "aba", "cdc", 'a', ModBlocks.GLASS_SHARD_BLOCK, 'b', new ItemStack(ModItems.STYGIAN_DAGGER, 1, 1), 'c', ModBlocks.BLOOD_INFUSED_IRON_BLOCK, 'd', ModItems.BLOOD_DIAMOND);

        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS, 8), "aaa", "aba", "aaa", 'a', "blockGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS_PANE, 8), "aaa", "aba", "aaa", 'a', "paneGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GLASS_SACRIFICIAL_DAGGER), "aaa", "aba", "aca", 'a', "shardGlass", 'b', getBMItem(BloodMagicItem.SACRIFICIAL_DAGGER), 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GLASS_DAGGER_OF_SACRIFICE), "aaa", "aba", "aca", 'a', "shardGlass", 'b', getBMItem(BloodMagicItem.DAGGER_OF_SACRIFICE), 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_PICKAXE), "aaa", " c ", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_AXE), "aa ", "ac ", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_AXE), " aa", " ca", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_SHOVEL), " a ", " c ", " b ", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_SICKLE), " aa", " ba", "caa", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_WOODEN_SWORD), " a ", " a ", "cbc", 'a', ModBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', ModItems.BLOOD_INFUSED_STICK);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_PICKAXE), "aaa", "bcb", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_PICKAXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_AXE), "aab", "acb", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_AXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_AXE), "baa", "bca", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_AXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_SHOVEL), " a ", "bcb", "bdb", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_SHOVEL, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_SICKLE), " aa", "bda", "caa", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_SICKLE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_SWORD), "ada", "aca", " b ", 'a', ModItems.BLOOD_INFUSED_IRON_INGOT, 'b', ModItems.BLOOD_INFUSED_STICK, 'c', ModItems.BLOOD_INFUSED_WOODEN_SWORD, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_BURNED_STRING, 7), "aaa", "aba", "aca", 'a', Items.STRING, 'b', Items.FLINT_AND_STEEL, 'c', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GEM_SACRIFICE), "aba", "cdc", "aea", 'a', "ingotGold", 'b', getBMItem(BloodMagicItem.DAGGER_OF_SACRIFICE), 'c', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GEM_SELF_SACRIFICE), "aba", "cdc", "aea", 'a', "dustGlowstone", 'b', getBMItem(BloodMagicItem.SACRIFICIAL_DAGGER), 'c', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.GEM_TARTARIC), "aba", "cdc", "efg", 'a', getBMItem(BloodMagicItem.SOUL_GEM), 'b', "ingotGold", 'c', "blockGlass", 'd', "gemDiamond", 'e', "blockRedstone", 'f', weakOrb, 'g', "blockLapis");
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.ALTARE_AENIGMATICA), "aba", "cdc", "efe", 'a', getBMItem(BloodMagicItem.BLOOD_SHARD), 'b', "gemEmerald", 'c', getBMBlock(BloodMagicBlock.INPUT_ROUTING_NODE), 'd', ItemComponent.getStack(ItemComponent.REAGENT_SIGHT), 'e', ModBlocks.BLOOD_INFUSED_IRON_BLOCK, 'f', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.BLOOD_DIAMOND, 1, 1), "aba", "cdc", "efe", 'a', ModBlocks.BLOOD_INFUSED_GLOWSTONE, 'b', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 4), 'c', new ItemStack(getBMItem(BloodMagicItem.ACTIVATION_CRYSTAL), 1, 1), 'd', ModItems.BLOOD_DIAMOND, 'e', ModBlocks.BLOOD_INFUSED_IRON_BLOCK, 'f', archmageOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModItems.STASIS_PLATE), "aaa", "aba", "cdc", 'a', ModBlocks.BLOOD_STAINED_GLASS, 'b', ModItems.REAGENT_LIGHTNING, 'c', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 2), 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.STASIS_PLATE), "aaa", "aba", "cdc", 'a', ModItems.STASIS_PLATE, 'b', getBMBlock(BloodMagicBlock.ALTAR), 'c', new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 3), 'd', magicianOrb);

        //TODO IS TEMPORARY BUT WILL BE IMPLEMENTED FOR THOSE WHO WANT ACCESS TO TIER 6 <- correct grammar
        if (ConfigHandler.crystalClusterEnabled)
        {
            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(BloodMagicBlock.CRYSTAL)), "aba", "cdc", "efe", 'a', new ItemStack(getBMItem(BloodMagicItem.ACTIVATION_CRYSTAL), 1, 1), 'b', "blockEmerald", 'c', "blockLapis", 'd', Blocks.BEACON, 'e', "blockDiamond", 'f', archmageOrb);
            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(BloodMagicBlock.CRYSTAL), 4, 1), "aa", "aa", 'a', getBMBlock(BloodMagicBlock.CRYSTAL));
        }

        //SHAPELESS
        addShapelessOreDictRecipe(new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_PLANKS, 2), ModBlocks.BLOOD_INFUSED_WOODEN_LOG);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS), ModBlocks.BLOOD_STAINED_GLASS);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS_PANE), ModBlocks.BLOOD_STAINED_GLASS_PANE);
        addShapelessOreDictRecipe(new ItemStack(ModItems.BLOOD_INFUSED_IRON_INGOT, 9), ModBlocks.BLOOD_INFUSED_IRON_BLOCK);
        addShapelessOreDictRecipe(new ItemStack(getBMItem(BloodMagicItem.SLATE), 9), ModBlocks.SLATE);
        addShapelessOreDictRecipe(new ItemStack(getBMItem(BloodMagicItem.SLATE), 9, 1), new ItemStack(ModBlocks.SLATE, 1, 1));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(BloodMagicItem.SLATE), 9, 2), new ItemStack(ModBlocks.SLATE, 1, 2));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(BloodMagicItem.SLATE), 9, 3), new ItemStack(ModBlocks.SLATE, 1, 3));
        addShapelessOreDictRecipe(new ItemStack(getBMItem(BloodMagicItem.SLATE), 9, 4), new ItemStack(ModBlocks.SLATE, 1, 4));

        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS), "blockGlass", weakOrb);
        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.BLOOD_STAINED_GLASS_PANE), "paneGlass", weakOrb);
    }

    public static void addAltarRecipes()
    {
        //ONE

        //TWO
        addAltarRecipe("logWood", new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_LOG), EnumAltarTier.TWO, 2000, 5, 5);
        addAltarRecipe(new ItemStack(Items.DYE, 1, 14), new ItemStack(ModItems.BLOOD_ORANGE), EnumAltarTier.TWO, 500, 10, 10);

        //THREE
        addAltarRecipe("dustGlowstone", new ItemStack(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST), EnumAltarTier.THREE, 2500, 5, 5);
        addAltarRecipe(new ItemStack(ModItems.INERT_BLOOD_INFUSED_IRON_INGOT), new ItemStack(ModItems.BLOOD_INFUSED_IRON_INGOT), EnumAltarTier.THREE, 5000, 5, 5);

        //FOUR

        //FIVE

        //SIX
        addAltarRecipe(new ItemStack(ModItems.BLOOD_DIAMOND, 1, 1), new ItemStack(ModItems.BLOOD_DIAMOND, 1, 2), EnumAltarTier.SIX, 500000, 25, 25);
    }

    public static void addHellfireForgeRecipes()
    {
        addForgeRecipe(new ItemStack(ModItems.INERT_BLOOD_INFUSED_IRON_INGOT), 128, 32, Items.IRON_INGOT, ModItems.BLOOD_INFUSED_GLOWSTONE_DUST, ItemComponent.getStack(ItemComponent.REAGENT_BINDING), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()));
        addForgeRecipe(new ItemStack(ModItems.REAGENT_SWIMMING), 100, 40, ItemComponent.getStack(ItemComponent.REAGENT_WATER), Items.PRISMARINE_SHARD, Items.GLASS_BOTTLE, Items.FISH);
        addForgeRecipe(new ItemStack(ModItems.REAGENT_ENDER), 2200, 800, ItemComponent.getStack(ItemComponent.REAGENT_TELEPOSITION), Items.ENDER_EYE, Blocks.ENDER_CHEST, Items.END_CRYSTAL);
//        addForgeRecipe(new ItemStack(ModItems.SIGIL_AUGMENTED_HOLDING), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), getBMItem(BloodMagicItem.SIGIL_HOLDING), new ItemStack(Blocks.CHEST, 8), "leather");
        addForgeRecipe(new ItemStack(ModItems.SIGIL_AUGMENTED_HOLDING), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), new ItemStack(getBMItem(BloodMagicItem.SIGIL_HOLDING)), new ItemStack(Blocks.CHEST, 8), "leather");
        addForgeRecipe(new ItemStack(ModItems.REAGENT_DIVINITY), 16384, 16384, new ItemStack(ModItems.REAGENT_LIGHTNING, 32), new ItemStack(getBMBlock(BloodMagicBlock.CRYSTAL), 8), new ItemStack(Items.GOLDEN_APPLE, 4, 1), new ItemStack(Items.NETHER_STAR, 16));
        addForgeRecipe(new ItemStack(ModItems.BLOOD_DIAMOND), 1024, 512, Items.DIAMOND, UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()), Items.DRAGON_BREATH, ModBlocks.BLOOD_INFUSED_GLOWSTONE);

        addForgeRecipe(new ItemStack(ModItems.SIGIL_SENTIENCE), 8192, 4096, new ItemStack(getBMItem(BloodMagicItem.SOUL_GEM), 1, 3), getBMItem(BloodMagicItem.SIGIL_BLOOD_LIGHT), ModItems.GEM_TARTARIC, getBMItem(BloodMagicItem.SIGIL_AIR));
    }

    public static void addAlchemyArrayRecipes()
    {
        AlchemyArrayRecipeRegistry.registerRecipe(ItemComponent.getStack(ItemComponent.REAGENT_BINDING), new ItemStack(ModItems.BLOOD_INFUSED_STICK), new AlchemyArrayEffectBinding("boundSword", Utils.setUnbreakable(new ItemStack(ModItems.BOUND_STICK))), new BindingAlchemyCircleRenderer());
        AlchemyArrayRecipeRegistry.registerRecipe(ItemComponent.getStack(ItemComponent.REAGENT_BINDING), Item.REGISTRY.containsKey(new ResourceLocation("extrautils2", "sickle_diamond")) ? new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("extrautils2", "sickle_diamond"))) : new ItemStack(Items.DIAMOND_HOE), new AlchemyArrayEffectBinding("boundAxe", Utils.setUnbreakable(new ItemStack(ModItems.BOUND_SICKLE))));

        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_SWIMMING), new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 1), new ItemStack(ModItems.SIGIL_SWIMMING));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_ENDER), new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 3), new ItemStack(ModItems.SIGIL_ENDER));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_LIGHTNING), new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 3), new ItemStack(ModItems.SIGIL_LIGHTNING));
        AlchemyArrayRecipeRegistry.registerCraftingRecipe(new ItemStack(ModItems.REAGENT_DIVINITY), new ItemStack(getBMItem(BloodMagicItem.SLATE), 1, 4), new ItemStack(ModItems.SIGIL_DIVINITY));
    }

    public static void addSanguineInfusionRecipes()
    {
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(ModItems.STASIS_AXE), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_AXE)), ModItems.BLOOD_INFUSED_IRON_AXE, ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE, new ItemStack(ModBlocks.SLATE, 1, 3), ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE);
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(ModItems.STASIS_PICKAXE), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_PICKAXE)), ModItems.BLOOD_INFUSED_IRON_PICKAXE, ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE, new ItemStack(ModBlocks.SLATE, 1, 3), ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE);
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(ModItems.STASIS_SHOVEL), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_SHOVEL)), ModItems.BLOOD_INFUSED_IRON_SHOVEL, ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE, new ItemStack(ModBlocks.SLATE, 1, 3), ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE);
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(ModItems.STASIS_SWORD), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_SWORD)), ModItems.BLOOD_INFUSED_IRON_SWORD, ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE, new ItemStack(ModBlocks.SLATE, 1, 3), ModItems.STASIS_PLATE, ModItems.BLOOD_DIAMOND, ModItems.STASIS_PLATE);

        //new ExtraItemStackInputs(new ItemStack(Items.NETHER_WART, 32), new ItemStack(Items.GLOWSTONE_DUST, 32), new ItemStack(Items.REDSTONE, 32), new ItemStack(Items.FERMENTED_SPIDER_EYE, 32)),
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_BAD_POTION, 1, ItemSplashPotion.class, new ItemStack(Items.GLASS_BOTTLE, 4), new ItemStack(Items.NETHER_WART, 16), new ItemStack(Items.FERMENTED_SPIDER_EYE, 8), new ItemStack(Items.GUNPOWDER, 16), new ItemStack(Items.SPIDER_EYE, 8), new ItemStack(Items.GLOWSTONE_DUST, 16), new ItemStack(Items.NETHER_WART, 16), new RecipeFilter(x -> !PotionUtils.getEffectsFromStack(x).isEmpty() && PotionUtils.getEffectsFromStack(x).get(0).getPotion().isBadEffect()));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_BLOODLUST, 1, new ItemStack(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST, 9), new ItemStack(Blocks.REDSTONE_BLOCK, 3), new ItemStack(ModBlocks.SLATE, 1, 2), new ItemStack(ModItems.GLASS_SHARD, 16), new ItemStack(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST, 9), new ItemStack(Blocks.REDSTONE_BLOCK, 3), new ItemStack(ModBlocks.SLATE, 1, 2), new ItemStack(ModItems.GLASS_SHARD, 16));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_FLAME, 1, new ItemStack(Items.FLINT, 9), new ItemStack(Items.COAL, 9), new ItemStack(Items.FIRE_CHARGE, 8), new ItemStack(Items.BLAZE_ROD, 8), new ItemStack(Items.FLINT, 9), new ItemStack(Items.BLAZE_POWDER, 8), new ItemStack(Items.FIRE_CHARGE, 8), new ItemStack(Items.COAL, 9, 1));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SHARPNESS, 1, new ItemStack(Items.QUARTZ, 9), new ItemStack(Items.IRON_INGOT, 4), new ItemStack(Items.FLINT, 9), new ItemStack(Blocks.QUARTZ_BLOCK, 9), new ItemStack(ModItems.GLASS_SHARD, 9), new ItemStack(Blocks.QUARTZ_BLOCK, 9), new ItemStack(Items.FLINT, 9), new ItemStack(Items.IRON_INGOT, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_FORTUNATE, 1, new ItemStack(Items.DYE, 12, 4), new ItemStack(Blocks.LAPIS_BLOCK, 4), new ItemStack(Items.EMERALD, 12), new ItemStack(Blocks.EMERALD_BLOCK, 4), new ItemStack(Items.DYE, 12, 4), new ItemStack(Blocks.EMERALD_BLOCK, 4), new ItemStack(Items.EMERALD, 12), new ItemStack(Blocks.LAPIS_BLOCK, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_LOOTING, 1, new ItemStack(Items.DYE, 12, 4), new ItemStack(Blocks.LAPIS_BLOCK, 4), new ItemStack(Items.QUARTZ, 12), new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.DYE, 12, 4), new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.EMERALD, 12), new ItemStack(Blocks.LAPIS_BLOCK, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SILKY, 1, new ItemStack(Items.EMERALD, 32), new ItemStack(ModItems.BLOOD_BURNED_STRING, 16), new ItemStack(Items.GOLD_NUGGET, 63), new ItemStack(Blocks.EMERALD_ORE, 16), new ItemStack(ModItems.BLOOD_BURNED_STRING, 16), new ItemStack(Items.GOLD_NUGGET, 63), new ExtraItemStackInputs(new ItemStack(Items.EMERALD)));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SMELTING, 1, new ItemStack(Blocks.FURNACE, 16), new ItemStack(Blocks.COAL_BLOCK, 32), new ItemStack(Items.BLAZE_ROD, 16), new ItemStack(Blocks.NETHER_BRICK, 32), new ItemStack(Blocks.FURNACE, 16), new ItemStack(Blocks.COAL_BLOCK, 32), new ItemStack(Items.BLAZE_ROD, 16), new ItemStack(Blocks.NETHER_BRICK, 32));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_XPERIENCED, 1, new ItemStack(Items.EXPERIENCE_BOTTLE, 4), new ItemStack(Items.EMERALD, 8), new ItemStack(Items.BOOK, 4), new ItemStack(Items.GOLD_INGOT, 9), new ItemStack(Blocks.BOOKSHELF, 8), new ItemStack(Items.EMERALD, 8), new ItemStack(Items.BOOK, 4), new ItemStack(Items.GOLD_INGOT, 9));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_BENEFICIAL_POTION, 1, ItemPotion.class, new ItemStack(Items.GLASS_BOTTLE, 4), new ItemStack(Items.NETHER_WART, 16), new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.GUNPOWDER, 16), new ItemStack(Items.SPECKLED_MELON, 8), new ItemStack(Items.GLOWSTONE_DUST, 16), new ItemStack(Items.NETHER_WART, 16), new RecipeFilter(x -> !PotionUtils.getEffectsFromStack(x).isEmpty() && PotionUtils.getEffectsFromStack(x).get(0).getPotion().isBeneficial()));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_QUICK_DRAW, 1, new ItemStack(Items.FEATHER, 16), new ItemStack(Items.DYE, 8, 3), new ItemStack(Items.FEATHER, 16), new ItemStack(Items.DYE, 8, 3));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SHADOW_TOOL, 1, new ItemStack(Blocks.OBSIDIAN, 16), new ItemStack(Items.STICK, 8), ItemComponent.getStack(ItemComponent.REAGENT_BRIDGE), new ItemStack(ModItems.BLOOD_INFUSED_STICK, 4), new ItemStack(Blocks.OBSIDIAN, 16), new ItemStack(Items.STICK, 8), ItemComponent.getStack(ItemComponent.REAGENT_BRIDGE), new ItemStack(ModItems.BLOOD_INFUSED_STICK, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_AOD, 1, new ItemStack(Blocks.TNT, 9), new ItemStack(Items.GUNPOWDER, 8), new ItemStack(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST, 8), ItemComponent.getStack(ItemComponent.REAGENT_BINDING), new ItemStack(Blocks.TNT, 9), new ItemStack(Items.GUNPOWDER, 8), new ItemStack(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST, 8), ItemComponent.getStack(ItemComponent.REAGENT_BINDING));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SIGIL, 1, ISigil.class, new ItemStack(getBMItem(BloodMagicItem.SLATE), 4, 2), new ItemStack(Items.ITEM_FRAME), new ItemStack(getBMItem(BloodMagicItem.SLATE), 4, 2), new ItemStack(Blocks.IRON_BARS, 32));
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

    public static Item getBMItem(BloodMagicItem item)
    {
        return BloodMagicAPI.getItem(item);
    }

    public static Block getBMBlock(String name)
    {
        return BloodMagicAPI.getBlock(name);
    }

    public static Block getBMBlock(BloodMagicBlock block)
    {
        return BloodMagicAPI.getBlock(block);
    }
}
