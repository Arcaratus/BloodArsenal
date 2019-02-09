package arcaratus.bloodarsenal.registry;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.client.render.alchemyArray.BindingAlchemyCircleRenderer;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.core.registry.AlchemyArrayRecipeRegistry;
import WayofTime.bloodmagic.iface.ISigil;
import WayofTime.bloodmagic.item.ItemSlate;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import WayofTime.bloodmagic.util.Utils;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import arcaratus.bloodarsenal.recipe.RecipeFilter;
import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import com.google.common.collect.Sets;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

public class ModRecipes
{
    public static Set<ItemStack> PURIFICATION_1;

    public static void init()
    {
        PURIFICATION_1 = Sets.newHashSet(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.REDSTONE), new ItemStack(Items.BLAZE_POWDER), EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack());

        addOreDictItems();
        addAlchemyArrayRecipes();
        addSanguineInfusionRecipes();
    }

    public static void addOreDictItems()
    {
        OreDictionary.registerOre("shardGlass", EnumBaseTypes.GLASS_SHARD.getStack());
        OreDictionary.registerOre("ingotBloodInfusedIron", EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());
    }

    public static void addAlchemyArrayRecipes()
    {
        AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(), EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), new AlchemyArrayEffectBinding("boundSword", Utils.setUnbreakable(new ItemStack(RegistrarBloodArsenalItems.BOUND_STICK))), new BindingAlchemyCircleRenderer());
        AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(), Item.REGISTRY.containsKey(new ResourceLocation("extrautils2", "sickle_diamond")) ? new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("extrautils2", "sickle_diamond"))) : new ItemStack(Items.DIAMOND_HOE), new AlchemyArrayEffectBinding("boundAxe", Utils.setUnbreakable(new ItemStack(RegistrarBloodArsenalItems.BOUND_SICKLE))));
        AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(), new ItemStack(Items.FLINT_AND_STEEL), new AlchemyArrayEffectBinding("boundAxe", Utils.setUnbreakable(new ItemStack(RegistrarBloodArsenalItems.BOUND_IGNITER))));
        AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(), new ItemStack(Items.SHEARS), new AlchemyArrayEffectBinding("boundAxe", Utils.setUnbreakable(new ItemStack(RegistrarBloodArsenalItems.BOUND_SHEARS))));
    }

    public static void addSanguineInfusionRecipes()
    {
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_AXE), 50000, new ItemStack(RegistrarBloodMagicItems.BOUND_AXE), p(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_AXE, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1));
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_PICKAXE), 50000, new ItemStack(RegistrarBloodMagicItems.BOUND_PICKAXE), p(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_PICKAXE, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1));
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_SHOVEL), 50000, new ItemStack(RegistrarBloodMagicItems.BOUND_SHOVEL), p(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SHOVEL, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1));
        SanguineInfusionRecipeRegistry.registerSanguineInfusionRecipe(new ItemStack(RegistrarBloodArsenalItems.STASIS_SWORD), 50000, new ItemStack(RegistrarBloodMagicItems.BOUND_SWORD), p(RegistrarBloodArsenalItems.BLOOD_INFUSED_IRON_SWORD, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 3), 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1), p(RegistrarBloodArsenalItems.BLOOD_DIAMOND, 1), p(EnumBaseTypes.STASIS_PLATE.getStack(), 1));

        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.BAD_POTION, 1, ItemSplashPotion.class, p(Items.GLASS_BOTTLE, 4), p(Items.NETHER_WART, 16), p(Items.FERMENTED_SPIDER_EYE, 8), p(Items.GUNPOWDER, 16), p(Items.SPIDER_EYE, 8), p(Items.GLOWSTONE_DUST, 16), p(Items.NETHER_WART, 16), p(new RecipeFilter(stack -> !PotionUtils.getEffectsFromStack(stack).isEmpty() && PotionUtils.getEffectsFromStack(stack).get(0).getPotion().isBadEffect()), 0));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.BLOODLUST, 1, p(EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack(), 9), p(Blocks.REDSTONE_BLOCK, 3), p(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 2), 1), p(EnumBaseTypes.GLASS_SHARD.getStack(), 16), p(EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack(), 9), p(Blocks.REDSTONE_BLOCK, 3), p(new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 2), 1), p(EnumBaseTypes.GLASS_SHARD.getStack(), 16));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.FLAME, 1, p(Items.FLINT, 8), p(Items.COAL, 8), p(Items.FIRE_CHARGE, 8), p(Items.BLAZE_POWDER, 8), p(Items.FLINT, 8), p(Items.BLAZE_POWDER, 8), p(Items.FIRE_CHARGE, 8), p(Items.COAL, 8));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.SHARPNESS, 1, p(Items.QUARTZ, 9), p(Items.IRON_INGOT, 4), p(Items.FLINT, 9), p(Blocks.QUARTZ_BLOCK, 9), p(EnumBaseTypes.GLASS_SHARD.getStack(), 9), p(Blocks.QUARTZ_BLOCK, 9), p(Items.FLINT, 9), p(Items.IRON_INGOT, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.FORTUNATE, 1, p(new ItemStack(Items.DYE, 1, 4), 12), p(Blocks.LAPIS_BLOCK, 4), p(Items.EMERALD, 12), p(Blocks.EMERALD_BLOCK, 4), p(new ItemStack(Items.DYE, 1, 4), 12), p(Blocks.EMERALD_BLOCK, 4), p(Items.EMERALD, 12), p(Blocks.LAPIS_BLOCK, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.LOOTING, 1, p(new ItemStack(Items.DYE, 1, 4), 12), p(Blocks.LAPIS_BLOCK, 4), p(Items.QUARTZ, 12), p(new ItemStack(Items.SKULL, 1, 1), 1), p(new ItemStack(Items.DYE, 1, 4), 12), p(new ItemStack(Items.SKULL, 1, 1), 1), p(Items.EMERALD, 12), p(Blocks.LAPIS_BLOCK, 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.SILKY, 1, p(Items.EMERALD, 32), p(Items.STRING, 16), p(Items.GOLD_NUGGET, 63), p(Blocks.EMERALD_ORE, 16), p(Items.STRING, 16), p(Items.GOLD_NUGGET, 63));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.SMELTING, 1, p(Blocks.FURNACE, 16), p(Blocks.COAL_BLOCK, 32), p(Items.BLAZE_ROD, 16), p(Blocks.NETHER_BRICK, 32), p(Blocks.FURNACE, 16), p(Blocks.COAL_BLOCK, 32), p(Items.BLAZE_ROD, 16), p(Blocks.NETHER_BRICK, 32));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.XPERIENCED, 1, p(Items.EXPERIENCE_BOTTLE, 4), p(Items.EMERALD, 8), p(Items.BOOK, 4), p(Items.GOLD_INGOT, 9), p(Blocks.BOOKSHELF, 8), p(Items.EMERALD, 8), p(Items.BOOK, 4), p(Items.GOLD_INGOT, 9));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.BENEFICIAL_POTION, 1, ItemPotion.class, p(Items.GLASS_BOTTLE, 4), p(Items.NETHER_WART, 16), p(Items.REDSTONE, 8), p(Items.GUNPOWDER, 16), p(Items.SPECKLED_MELON, 8), p(Items.GLOWSTONE_DUST, 16), p(Items.NETHER_WART, 16), p(new RecipeFilter(stack -> !PotionUtils.getEffectsFromStack(stack).isEmpty() && PotionUtils.getEffectsFromStack(stack).get(0).getPotion().isBeneficial()), 1));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.QUICK_DRAW, 1, p(Items.FEATHER, 16), p(new ItemStack(Items.DYE, 1, 3), 8), p(Items.FEATHER, 16), p(new ItemStack(Items.DYE, 1, 3), 8));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.SHADOW_TOOL, 1, p(Blocks.OBSIDIAN, 16), p(Items.STICK, 8), p(ComponentTypes.REAGENT_BRIDGE.getStack(), 1), p(EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 4), p(Blocks.OBSIDIAN, 16), p(Items.STICK, 8), p(ComponentTypes.REAGENT_BRIDGE.getStack(), 1), p(EnumBaseTypes.BLOOD_INFUSED_STICK.getStack(), 4));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.AOD, 1, p(Blocks.TNT, 9), p(Items.GUNPOWDER, 8), p(EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack(), 8), p(ComponentTypes.REAGENT_BINDING.getStack(), 1), p(Blocks.TNT, 9), p(Items.GUNPOWDER, 8), p(EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack(), 8), p(ComponentTypes.REAGENT_BINDING.getStack(), 1));
        SanguineInfusionRecipeRegistry.registerModificationRecipe(10000, Constants.Modifiers.SIGIL, 1, ISigil.class, p(ItemSlate.SlateType.IMBUED.getStack(), 4), p(Items.ITEM_FRAME, 1), p(ItemSlate.SlateType.IMBUED.getStack(), 4), p(Blocks.IRON_BARS, 32));
    }

    private static Pair<Object, Integer> p(Object o, int i)
    {
        return Pair.of(o, i);
    }
}
