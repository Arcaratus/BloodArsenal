package arcaratus.bloodarsenal.registry;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.altar.AltarTier;
import WayofTime.bloodmagic.client.render.alchemyArray.BindingAlchemyCircleRenderer;
import WayofTime.bloodmagic.core.*;
import WayofTime.bloodmagic.core.registry.*;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import WayofTime.bloodmagic.orb.IBloodOrb;
import WayofTime.bloodmagic.util.Utils;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import arcaratus.bloodarsenal.item.types.EnumGemTypes;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.io.*;
import java.util.*;

public class ModRecipes
{
    public static void init()
    {
        addOreDictItems();
//        addCraftingRecipes();
        addAltarRecipes();
        addHellfireForgeRecipes();
        addAlchemyArrayRecipes();
        addSanguineInfusionRecipes();
    }

    public static void addOreDictItems()
    {
        OreDictionary.registerOre("shardGlass", EnumBaseTypes.GLASS_SHARD.getStack());
        OreDictionary.registerOre("ingotBloodInfusedIron", EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());
    }

    public static void addCraftingRecipes()
    {
        ItemStack weakOrb = OrbRegistry.getOrbStack(RegistrarBloodMagic.ORB_WEAK);
        ItemStack apprenticeOrb = OrbRegistry.getOrbStack(RegistrarBloodMagic.ORB_APPRENTICE);
        ItemStack magicianOrb = OrbRegistry.getOrbStack(RegistrarBloodMagic.ORB_MAGICIAN);
        ItemStack masterOrb = OrbRegistry.getOrbStack(RegistrarBloodMagic.ORB_MASTER);
        ItemStack archmageOrb = OrbRegistry.getOrbStack(RegistrarBloodMagic.ORB_ARCHMAGE);
        ItemStack transcendentOrb = OrbRegistry.getOrbStack(RegistrarBloodMagic.ORB_TRANSCENDENT);

        //SHAPED
//        addOreDictRecipe(EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(2), "a", "a", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB, 6), "aaa", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_STAIRS, 4), "a  ", "aa ", "aaa", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_FENCE, 3), "aba", "aba", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS, 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_FENCE_GATE), "aba", "aba", 'a', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'b', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS), "a", "a", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS_PANE, 16), "aaa", "aaa", 'a', RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.GLASS_SHARD_BLOCK), "aaa", "aaa", "aaa", 'a', "shardGlass");
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_GLOWSTONE), "aa", "aa", 'a', EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack());
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_IRON_BLOCK), "aaa", "aaa", "aaa", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());
//        addOreDictRecipe(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE, 4, 3), "aaa", "aba", "aaa", 'a', "stone", 'b', EnumGemTypes.SACRIFICE.getStack());
//        addOreDictRecipe(new ItemStack(RegistrarBloodMagicBlocks.BLOOD_RUNE, 4, 4), "aaa", "aba", "aaa", 'a', "stone", 'b', EnumGemTypes.SELF_SACRIFICE.getStack());
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.SLATE), "aaa", "aaa", "aaa", 'a', RegistrarBloodMagicItems.SLATE);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 1), "aaa", "aaa", "aaa", 'a', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 1));
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 2), "aaa", "aaa", "aaa", 'a', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 2));
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), "aaa", "aaa", "aaa", 'a', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 3));
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 4), "aaa", "aaa", "aaa", 'a', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 4));
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalItems.STYGIAN_DAGGER), "aaa", "aba", "cdc", 'a', "shardGlass", 'b', RegistrarBloodArsenalItems.GLASS_DAGGER_OF_SACRIFICE, 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_INGOT, 'd', RegistrarBloodArsenalItems.BLOOD_DIAMOND);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalItems.STYGIAN_DAGGER, 1, 1), "eae", "aba", "cdc", 'a', "shardGlass", 'b', RegistrarBloodArsenalItems.STYGIAN_DAGGER, 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_INGOT, 'd', RegistrarBloodArsenalItems.BLOOD_DIAMOND, 'e', RegistrarBloodArsenalBlocks.GLASS_SHARD_BLOCK);
//        addOreDictRecipe(new ItemStack(RegistrarBloodArsenalItems.STYGIAN_DAGGER, 1, 2), "aaa", "aba", "cdc", 'a', RegistrarBloodArsenalBlocks.GLASS_SHARD_BLOCK, 'b', new ItemStack(RegistrarBloodArsenalItems.STYGIAN_DAGGER, 1, 1), 'c', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_IRON_BLOCK, 'd', RegistrarBloodArsenalItems.BLOOD_DIAMOND);

        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS, 8), "aaa", "aba", "aaa", 'a', "blockGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS_PANE, 8), "aaa", "aba", "aaa", 'a', "paneGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.GLASS_SACRIFICIAL_DAGGER), "aaa", "aba", "aca", 'a', "shardGlass", 'b', RegistrarBloodMagicItems.SACRIFICIAL_DAGGER, 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.GLASS_DAGGER_OF_SACRIFICE), "aaa", "aba", "aca", 'a', "shardGlass", 'b', RegistrarBloodMagicItems.DAGGER_OF_SACRIFICE, 'c', apprenticeOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_PICKAXE), "aaa", " c ", " b ", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_AXE), "aa ", "ac ", " b ", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_AXE), " aa", " ca", " b ", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_SHOVEL), " a ", " c ", " b ", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_SICKLE), " aa", " ba", "caa", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_SWORD), " a ", " a ", "cbc", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG, 'b', apprenticeOrb, 'c', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack());
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_PICKAXE), "aaa", "bcb", "bdb", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_PICKAXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_AXE), "aab", "acb", "bdb", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_AXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_AXE), "baa", "bca", "bdb", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_AXE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SHOVEL), " a ", "bcb", "bdb", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_SHOVEL, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SICKLE), " aa", "bda", "caa", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_SICKLE, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SWORD), "ada", "aca", " b ", 'a', EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 'b', EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 'c', RegistrarBloodArsenalItems.BLOOD_INFUSED_WOODEN_SWORD, 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_BURNED_STRING, 7), "aaa", "aba", "aca", 'a', Items.STRING, 'b', Items.FLINT_AND_STEEL, 'c', weakOrb);
        addOreDictBloodOrbRecipe(EnumGemTypes.SACRIFICE.getStack(), "aba", "cdc", "aea", 'a', "ingotGold", 'b', RegistrarBloodMagicItems.DAGGER_OF_SACRIFICE, 'c', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(EnumGemTypes.SELF_SACRIFICE.getStack(), "aba", "cdc", "aea", 'a', "dustGlowstone", 'b', RegistrarBloodMagicItems.SACRIFICIAL_DAGGER, 'c', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 1), 'd', "gemDiamond", 'e', apprenticeOrb);
        addOreDictBloodOrbRecipe(EnumGemTypes.TARTARIC.getStack(), "aba", "cdc", "efg", 'a', RegistrarBloodMagicItems.SOUL_GEM, 'b', "ingotGold", 'c', "blockGlass", 'd', "gemDiamond", 'e', "blockRedstone", 'f', weakOrb, 'g', "blockLapis");
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalBlocks.ALTARE_AENIGMATICA), "aba", "cdc", "efe", 'a', RegistrarBloodMagicItems.BLOOD_SHARD, 'b', "gemEmerald", 'c', RegistrarBloodMagicBlocks.INPUT_ROUTING_NODE, 'd', ComponentTypes.REAGENT_SIGHT.getStack(), 'e', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_IRON_BLOCK, 'f', masterOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1, 1), "aba", "cdc", "efe", 'a', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_GLOWSTONE, 'b', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 4), 'c', new ItemStack(RegistrarBloodMagicItems.ACTIVATION_CRYSTAL, 1, 1), 'd', RegistrarBloodArsenalItems.BLOOD_DIAMOND, 'e', RegistrarBloodArsenalBlocks.BLOOD_INFUSED_IRON_BLOCK, 'f', archmageOrb);
        addOreDictBloodOrbRecipe(EnumBaseTypes.STASIS_PLATE.getStack(), "aaa", "aba", "cdc", 'a', RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS, 'b', EnumBaseTypes.REAGENT_LIGHTNING.getStack(), 'c', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 2), 'd', magicianOrb);
        addOreDictBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalBlocks.STASIS_PLATE), "aaa", "aba", "cdc", 'a', EnumBaseTypes.STASIS_PLATE.getStack(), 'b', RegistrarBloodMagicBlocks.ALTAR, 'c', new ItemStack(RegistrarBloodMagicItems.SLATE, 1, 3), 'd', magicianOrb);

        //TODO IS TEMPORARY BUT WILL BE IMPLEMENTED FOR THOSE WHO WANT ACCESS TO TIER 6 <- correct grammar
        if (ConfigHandler.misc.crystalClusterEnabled)
        {
//            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(BloodMagicBlock.CRYSTAL)), "aba", "cdc", "efe", 'a', new ItemStack(getBMItem(BloodMagicItem.ACTIVATION_CRYSTAL), 1, 1), 'b', "blockEmerald", 'c', "blockLapis", 'd', Blocks.BEACON, 'e', "blockDiamond", 'f', archmageOrb);
//            addOreDictBloodOrbRecipe(new ItemStack(getBMBlock(BloodMagicBlock.CRYSTAL), 4, 1), "aa", "aa", 'a', getBMBlock(BloodMagicBlock.CRYSTAL));
        }

        //SHAPELESS
        addShapelessOreDictRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS, 2), RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS), RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS);
        addShapelessOreDictRecipe(new ItemStack(Blocks.GLASS_PANE), RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS_PANE);
        addShapelessOreDictRecipe(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(9), RegistrarBloodArsenalBlocks.BLOOD_INFUSED_IRON_BLOCK);
        addShapelessOreDictRecipe(new ItemStack(RegistrarBloodMagicItems.SLATE, 9), RegistrarBloodArsenalBlocks.SLATE);
        addShapelessOreDictRecipe(new ItemStack(RegistrarBloodMagicItems.SLATE, 9, 1), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 1));
        addShapelessOreDictRecipe(new ItemStack(RegistrarBloodMagicItems.SLATE, 9, 2), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 2));
        addShapelessOreDictRecipe(new ItemStack(RegistrarBloodMagicItems.SLATE, 9, 3), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3));
        addShapelessOreDictRecipe(new ItemStack(RegistrarBloodMagicItems.SLATE, 9, 4), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 4));
//
        addShapelessBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS), "blockGlass", weakOrb);
        addShapelessBloodOrbRecipe(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_STAINED_GLASS_PANE), "paneGlass", weakOrb);
    }

    public static void addAltarRecipes()
    {
        //ONE

        //TWO
//        addAltarRecipe("logWood", new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG), AltarTier.TWO, 2000, 5, 5);
//        addAltarRecipe(new ItemStack(Items.DYE, 1, 14), new ItemStack(RegistrarBloodArsenalItems.BLOOD_ORANGE), EnumAltarTier.TWO, 500, 10, 10);
//
//        //THREE
//        addAltarRecipe("dustGlowstone", new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_GLOWSTONE_DUST), EnumAltarTier.THREE, 2500, 5, 5);
//        addAltarRecipe(new ItemStack(RegistrarBloodArsenalItems.INERT_BLOOD_INFUSED_IRON_INGOT), new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_INGOT), EnumAltarTier.THREE, 5000, 5, 5);
//
//        //FOUR
//
//        //FIVE
//
//        //SIX
//        addAltarRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1, 1), new ItemStack(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1, 2), EnumAltarTier.SIX, 500000, 25, 25);
    }

    public static void addHellfireForgeRecipes()
    {
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.INERT_BLOOD_INFUSED_IRON_INGOT), 128, 32, Items.IRON_INGOT, RegistrarBloodArsenalItems.BLOOD_INFUSED_GLOWSTONE_DUST, ItemComponent.getStack(ItemComponent.REAGENT_BINDING), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()));
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.REAGENT_SWIMMING), 100, 40, ItemComponent.getStack(ItemComponent.REAGENT_WATER), Items.PRISMARINE_SHARD, Items.GLASS_BOTTLE, Items.FISH);
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.REAGENT_ENDER), 2200, 800, ItemComponent.getStack(ItemComponent.REAGENT_TELEPOSITION), Items.ENDER_EYE, Blocks.ENDER_CHEST, Items.END_CRYSTAL);
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), getBMItem(BloodMagicItem.SIGIL_HOLDING), new ItemStack(Blocks.CHEST, 8), "leather");
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING), 2000, 600, ItemComponent.getStack(ItemComponent.REAGENT_HOLDING), new ItemStack(getBMItem(BloodMagicItem.SIGIL_HOLDING)), new ItemStack(Blocks.CHEST, 8), "leather");
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.REAGENT_DIVINITY), 16384, 16384, new ItemStack(RegistrarBloodArsenalItems.REAGENT_LIGHTNING, 32), new ItemStack(getBMBlock(BloodMagicBlock.CRYSTAL), 8), new ItemStack(Items.GOLDEN_APPLE, 4, 1), new ItemStack(Items.NETHER_STAR, 16));
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.BLOOD_DIAMOND), 1024, 512, Items.DIAMOND, UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()), Items.DRAGON_BREATH, RegistrarBloodArsenalBlocks.BLOOD_INFUSED_GLOWSTONE);
//
//        addForgeRecipe(new ItemStack(RegistrarBloodArsenalItems.SIGIL_SENTIENCE), 8192, 4096, new ItemStack(getBMItem(BloodMagicItem.SOUL_GEM), 1, 3), getBMItem(BloodMagicItem.SIGIL_BLOOD_LIGHT), RegistrarBloodArsenalItems.GEM_TARTARIC, getBMItem(BloodMagicItem.SIGIL_AIR));
    }

    public static void addAlchemyArrayRecipes()
    {
        AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(), EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), new AlchemyArrayEffectBinding("boundSword", Utils.setUnbreakable(new ItemStack(RegistrarBloodArsenalItems.BOUND_STICK))), new BindingAlchemyCircleRenderer());
        AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(), Item.REGISTRY.containsKey(new ResourceLocation("extrautils2", "sickle_diamond")) ? new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("extrautils2", "sickle_diamond"))) : new ItemStack(Items.DIAMOND_HOE), new AlchemyArrayEffectBinding("boundAxe", Utils.setUnbreakable(new ItemStack(RegistrarBloodArsenalItems.BOUND_SICKLE))));
    }

    public static void addSanguineInfusionRecipes()
    {
//        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_AXE), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_AXE)), RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_AXE, RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE, new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE);
//        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_PICKAXE), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_PICKAXE)), RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_PICKAXE, RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE, new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE);
//        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_SHOVEL), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_SHOVEL)), RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SHOVEL, RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE, new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE);
//        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_SWORD), 50000, new ItemStack(getBMItem(BloodMagicItem.BOUND_SWORD)), RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SWORD, RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE, new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), RegistrarBloodArsenalItems.STASIS_PLATE, RegistrarBloodArsenalItems.BLOOD_DIAMOND, RegistrarBloodArsenalItems.STASIS_PLATE);
//
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_BAD_POTION, 1, ItemSplashPotion.class, new ItemStack(Items.GLASS_BOTTLE, 4), new ItemStack(Items.NETHER_WART, 16), new ItemStack(Items.FERMENTED_SPIDER_EYE, 8), new ItemStack(Items.GUNPOWDER, 16), new ItemStack(Items.SPIDER_EYE, 8), new ItemStack(Items.GLOWSTONE_DUST, 16), new ItemStack(Items.NETHER_WART, 16), new RecipeFilter(stack -> !PotionUtils.getEffectsFromStack(stack).isEmpty() && PotionUtils.getEffectsFromStack(stack).get(0).getPotion().isBadEffect()));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_BLOODLUST, 1, new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_GLOWSTONE_DUST, 9), new ItemStack(Blocks.REDSTONE_BLOCK, 3), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 2), new ItemStack(RegistrarBloodArsenalItems.GLASS_SHARD, 16), new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_GLOWSTONE_DUST, 9), new ItemStack(Blocks.REDSTONE_BLOCK, 3), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 2), new ItemStack(RegistrarBloodArsenalItems.GLASS_SHARD, 16));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_FLAME, 1, new ItemStack(Items.FLINT, 9), new ItemStack(Items.COAL, 9), new ItemStack(Items.FIRE_CHARGE, 8), new ItemStack(Items.BLAZE_ROD, 8), new ItemStack(Items.FLINT, 9), new ItemStack(Items.BLAZE_POWDER, 8), new ItemStack(Items.FIRE_CHARGE, 8), new ItemStack(Items.COAL, 9, 1));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SHARPNESS, 1, new ItemStack(Items.QUARTZ, 9), new ItemStack(Items.IRON_INGOT, 4), new ItemStack(Items.FLINT, 9), new ItemStack(Blocks.QUARTZ_BLOCK, 9), new ItemStack(RegistrarBloodArsenalItems.GLASS_SHARD, 9), new ItemStack(Blocks.QUARTZ_BLOCK, 9), new ItemStack(Items.FLINT, 9), new ItemStack(Items.IRON_INGOT, 4));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_FORTUNATE, 1, new ItemStack(Items.DYE, 12, 4), new ItemStack(Blocks.LAPIS_BLOCK, 4), new ItemStack(Items.EMERALD, 12), new ItemStack(Blocks.EMERALD_BLOCK, 4), new ItemStack(Items.DYE, 12, 4), new ItemStack(Blocks.EMERALD_BLOCK, 4), new ItemStack(Items.EMERALD, 12), new ItemStack(Blocks.LAPIS_BLOCK, 4));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_LOOTING, 1, new ItemStack(Items.DYE, 12, 4), new ItemStack(Blocks.LAPIS_BLOCK, 4), new ItemStack(Items.QUARTZ, 12), new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.DYE, 12, 4), new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.EMERALD, 12), new ItemStack(Blocks.LAPIS_BLOCK, 4));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SILKY, 1, new ItemStack(Items.EMERALD, 32), new ItemStack(RegistrarBloodArsenalItems.BLOOD_BURNED_STRING, 16), new ItemStack(Items.GOLD_NUGGET, 63), new ItemStack(Blocks.EMERALD_ORE, 16), new ItemStack(RegistrarBloodArsenalItems.BLOOD_BURNED_STRING, 16), new ItemStack(Items.GOLD_NUGGET, 63));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SMELTING, 1, new ItemStack(Blocks.FURNACE, 16), new ItemStack(Blocks.COAL_BLOCK, 32), new ItemStack(Items.BLAZE_ROD, 16), new ItemStack(Blocks.NETHER_BRICK, 32), new ItemStack(Blocks.FURNACE, 16), new ItemStack(Blocks.COAL_BLOCK, 32), new ItemStack(Items.BLAZE_ROD, 16), new ItemStack(Blocks.NETHER_BRICK, 32));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_XPERIENCED, 1, new ItemStack(Items.EXPERIENCE_BOTTLE, 4), new ItemStack(Items.EMERALD, 8), new ItemStack(Items.BOOK, 4), new ItemStack(Items.GOLD_INGOT, 9), new ItemStack(Blocks.BOOKSHELF, 8), new ItemStack(Items.EMERALD, 8), new ItemStack(Items.BOOK, 4), new ItemStack(Items.GOLD_INGOT, 9));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_BENEFICIAL_POTION, 1, ItemPotion.class, new ItemStack(Items.GLASS_BOTTLE, 4), new ItemStack(Items.NETHER_WART, 16), new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.GUNPOWDER, 16), new ItemStack(Items.SPECKLED_MELON, 8), new ItemStack(Items.GLOWSTONE_DUST, 16), new ItemStack(Items.NETHER_WART, 16), new RecipeFilter(stack -> !PotionUtils.getEffectsFromStack(stack).isEmpty() && PotionUtils.getEffectsFromStack(stack).get(0).getPotion().isBeneficial()));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_QUICK_DRAW, 1, new ItemStack(Items.FEATHER, 16), new ItemStack(Items.DYE, 8, 3), new ItemStack(Items.FEATHER, 16), new ItemStack(Items.DYE, 8, 3));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SHADOW_TOOL, 1, new ItemStack(Blocks.OBSIDIAN, 16), new ItemStack(Items.STICK, 8), ItemComponent.getStack(ItemComponent.REAGENT_BRIDGE), new ItemStack(EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 4), new ItemStack(Blocks.OBSIDIAN, 16), new ItemStack(Items.STICK, 8), ItemComponent.getStack(ItemComponent.REAGENT_BRIDGE), new ItemStack(EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 4));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_AOD, 1, new ItemStack(Blocks.TNT, 9), new ItemStack(Items.GUNPOWDER, 8), new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_GLOWSTONE_DUST, 8), ItemComponent.getStack(ItemComponent.REAGENT_BINDING), new ItemStack(Blocks.TNT, 9), new ItemStack(Items.GUNPOWDER, 8), new ItemStack(RegistrarBloodArsenalItems.BLOOD_INFUSED_GLOWSTONE_DUST, 8), ItemComponent.getStack(ItemComponent.REAGENT_BINDING));
//        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, ModModifiers.MODIFIER_SIGIL, 1, ISigil.class, new ItemStack(getBMItem(BloodMagicItem.SLATE), 4, 2), new ItemStack(Items.ITEM_FRAME), new ItemStack(getBMItem(BloodMagicItem.SLATE), 4, 2), new ItemStack(Blocks.IRON_BARS, 32));
    }

    public static void addOreDictRecipe(ItemStack output, Object... recipe)
    {
//        addShapedRecipe(output, recipe);
    }
//
    public static void addOreDictBloodOrbRecipe(ItemStack output, Object... recipe)
    {
        addShapedRecipe(output, recipe);
    }
//
    public static void addShapelessOreDictRecipe(ItemStack output, Object... recipe)
    {
//        addShapelessRecipe(output, recipe);
    }
//
    public static void addShapelessBloodOrbRecipe(ItemStack output, Object... recipe)
    {
        addShapelessRecipe(output, recipe);
    }

    public static void addAltarRecipe(List<ItemStack> input, ItemStack output, AltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(input, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addAltarRecipe(ItemStack input, ItemStack output, AltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(input, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addAltarRecipe(String oreDictInput, ItemStack output, AltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(oreDictInput, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addForgeRecipe(ItemStack output, double minWill, double drain, Object... recipe)
    {
        TartaricForgeRecipeRegistry.registerRecipe(output, minWill, drain, recipe);
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File RECIPE_DIR = null;
    private static final Set<String> USED_OD_NAMES = new TreeSet<>();

    private static void setupDir() {
        if (RECIPE_DIR == null) {
            RECIPE_DIR = BloodArsenal.configDir.toPath().resolve("../recipes/").toFile();
        }

        if (!RECIPE_DIR.exists()) {
            RECIPE_DIR.mkdir();
        }
    }


    // EXPERIMENTAL: JSONs generated will definitely not work in 1.12.2 and below, and may not even work when 1.13 comes out
    // When Forge 1.13 is fully released, I will fix this to be correct
    // Usage: Replace calls to GameRegistry.addSmelting with this
    private static void addSmelting(ItemStack in, ItemStack result, float xp) { addSmelting(in, result, xp, 200); }
    private static void addSmelting(ItemStack in, ItemStack result, float xp, int cookTime) {
        setupDir();

        // GameRegistry.addSmelting(in, out, xp);
        Map<String, Object> json = new HashMap<>();
        json.put("type", "minecraft:smelting");
        json.put("ingredient", serializeItem(in));
        json.put("result", serializeItem(result)); // vanilla jsons just have a string?
        json.put("experience", xp);
        json.put("cookingtime", cookTime);

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
        File f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addShapedRecipe(ItemStack result, Object... components) {
        setupDir();

        // GameRegistry.addShapedRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        List<String> pattern = new ArrayList<>();
        int i = 0;
        while (i < components.length && components[i] instanceof String) {
            pattern.add((String) components[i]);
            i++;
        }
        json.put("pattern", pattern);

        boolean isOreDict = false;
        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;
        for (; i < components.length; i++) {
            Object o = components[i];
            if (o instanceof Character) {
                if (curKey != null)
                    throw new IllegalArgumentException("Provided two char keys in a row");
                curKey = (Character) o;
            } else {
                if (curKey == null)
                    throw new IllegalArgumentException("Providing object without a char key");
                if (o instanceof String)
                    isOreDict = true;
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }
        json.put("key", key);
        json.put("type", "forge:ore_shaped");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
        File f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addShapelessRecipe(ItemStack result, Object... components)
    {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (Object o : components) {
            if (o instanceof String)
                isOreDict = true;
            ingredients.add(serializeItem(o));
        }
        json.put("ingredients", ingredients);
        json.put("type", "forge:ore_shapeless");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
        File f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");
        }


        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> serializeItem(Object thing) {
        if (thing instanceof Item) {
            return serializeItem(new ItemStack((Item) thing));
        }
        if (thing instanceof Block) {
            return serializeItem(new ItemStack((Block) thing));
        }
        if (thing instanceof ItemStack) {
            ItemStack stack = (ItemStack) thing;
            Map<String, Object> ret = new HashMap<>();
            if (stack.getItem() instanceof IBloodOrb) {
                ret.put("type", "bloodmagic:orb");
                ret.put("orb", "bloodmagic:" + ((IBloodOrb) stack.getItem()).getOrb(stack).getName());
            }
            else
            {
                ret.put("item", stack.getItem().getRegistryName().toString());
                if (stack.getItem().getHasSubtypes() || stack.getItemDamage() != 0) {
                    ret.put("data", stack.getItemDamage());
                }
                if (stack.getCount() > 1) {
                    ret.put("count", stack.getCount());
                }

                if (stack.hasTagCompound()) {
                    ret.put("type", "minecraft:item_nbt");
                    ret.put("nbt", stack.getTagCompound().toString());
                }
            }

            return ret;
        }
        if (thing instanceof String) {
            Map<String, Object> ret = new HashMap<>();
            USED_OD_NAMES.add((String) thing);
            ret.put("item", "#" + ((String) thing).toUpperCase(Locale.ROOT));
            return ret;
        }

        throw new IllegalArgumentException("Not a block, item, stack, or od name");
    }

    // Call this after you are done generating
    private static void generateConstants() {
        List<Map<String, Object>> json = new ArrayList<>();
        for (String s : USED_OD_NAMES) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", s.toUpperCase(Locale.ROOT));
            entry.put("ingredient", ImmutableMap.of("type", "forge:ore_dict", "ore", s));
            json.add(entry);
        }

        try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_constants.json"))) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
