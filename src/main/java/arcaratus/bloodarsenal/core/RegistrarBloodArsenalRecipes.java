package arcaratus.bloodarsenal.core;

import WayofTime.bloodmagic.altar.AltarTier;
import WayofTime.bloodmagic.api.IBloodMagicRecipeRegistrar;
import WayofTime.bloodmagic.api.impl.BloodMagicRecipeRegistrar;
import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.item.ItemSlate;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.item.types.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public class RegistrarBloodArsenalRecipes
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        OreDictionary.registerOre("shardGlass", EnumBaseTypes.GLASS_SHARD.getStack());
        OreDictionary.registerOre("blockBloodInfusedWood", RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        OreDictionary.registerOre("ingotBloodInfusedIron", EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());
    }

    public static void registerAltarRecipes(IBloodMagicRecipeRegistrar registrar)
    {
        // ONE

        // TWO
        registrar.addBloodAltar(new OreIngredient("logWood"), new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_LOG), AltarTier.TWO.ordinal(), 2000, 5, 5);
        registrar.addBloodAltar(Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 14)), new ItemStack(RegistrarBloodArsenalItems.BLOOD_ORANGE), AltarTier.TWO.ordinal(), 500, 10, 10);

        // THREE
        registrar.addBloodAltar(new OreIngredient("dustGlowstone"), EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack(), AltarTier.THREE.ordinal(), 2500, 5, 5);
        registrar.addBloodAltar(Ingredient.fromStacks(EnumBaseTypes.INERT_BLOOD_INFUSED_IRON_INGOT.getStack()), EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), AltarTier.THREE.ordinal(), 5000, 5, 5);

        // FOUR

        // FIVE
        registrar.addBloodAltar(Ingredient.fromStacks(EnumDiamondTypes.INERT.getStack()), EnumDiamondTypes.INFUSED.getStack(), AltarTier.FIVE.ordinal(), 100000, 25, 25);

        // SIX
    }

    public static void registerTartaricForgeRecipes(BloodMagicRecipeRegistrar registrar)
    {
        registrar.addTartaricForge(EnumBaseTypes.INERT_BLOOD_INFUSED_IRON_INGOT.getStack(), 128, 32, Items.IRON_INGOT, EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack(), ComponentTypes.REAGENT_BINDING.getStack(), FluidUtil.getFilledBucket(new FluidStack(BlockLifeEssence.getLifeEssence(), 0)));
        registrar.addTartaricForge(EnumBaseTypes.REAGENT_SWIMMING.getStack(), 100, 40, ComponentTypes.REAGENT_WATER.getStack(), Items.PRISMARINE_SHARD, Items.GLASS_BOTTLE, Items.FISH);
        registrar.addTartaricForge(EnumBaseTypes.REAGENT_ENDER.getStack(), 1600, 800, ComponentTypes.REAGENT_TELEPOSITION.getStack(), Items.ENDER_EYE, Blocks.ENDER_CHEST, Items.END_CRYSTAL);
        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING), 2000, 1600, ComponentTypes.REAGENT_HOLDING.getStack(), RegistrarBloodMagicItems.SIGIL_HOLDING, Blocks.CHEST, "leather");
        registrar.addTartaricForge(EnumBaseTypes.REAGENT_DIVINITY.getStack(), 16384, 16384, EnumBaseTypes.REAGENT_LIGHTNING.getStack(32), new ItemStack(RegistrarBloodMagicBlocks.DECORATIVE_BRICK, 8, 2), new ItemStack(Items.GOLDEN_APPLE, 4, 1), new ItemStack(Items.NETHER_STAR, 16));
        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.BLOOD_DIAMOND), 1024, 512, Items.DIAMOND, FluidUtil.getFilledBucket(new FluidStack(BlockLifeEssence.getLifeEssence(), 0)), Items.DRAGON_BREATH, RegistrarBloodArsenalBlocks.BLOOD_INFUSED_GLOWSTONE);

        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.SIGIL_SENTIENCE), 8192, 4096, new ItemStack(RegistrarBloodMagicItems.SOUL_GEM, 1, 3), RegistrarBloodMagicItems.SIGIL_BLOOD_LIGHT, EnumGemTypes.TARTARIC.getStack(), RegistrarBloodMagicItems.SIGIL_AIR);

        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 1), 60, 40, RegistrarBloodArsenalItems.SOUL_PENDANT, EnumGemTypes.TARTARIC.getStack(), "blockRedstone", "blockLapis");
        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 2), 240, 100, new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 1), EnumGemTypes.TARTARIC.getStack(), "blockGold", ItemSlate.SlateType.IMBUED.getStack());
        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 3), 1000, 600, new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 2), RegistrarBloodMagicItems.BLOOD_SHARD, ItemSlate.SlateType.DEMONIC.getStack(), RegistrarBloodMagicItems.ITEM_DEMON_CRYSTAL);
        registrar.addTartaricForge(new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 4), 4000, 3000, new ItemStack(RegistrarBloodArsenalItems.SOUL_PENDANT, 1, 3), EnumDiamondTypes.INERT.getStack(), new ItemStack(RegistrarBloodArsenalBlocks.SLATE, 1, 4), Items.NETHER_STAR);
    }

    public static void registerAlchemyArrayRecipes(BloodMagicRecipeRegistrar registrar)
    {
        registrar.addAlchemyArray(EnumBaseTypes.REAGENT_SWIMMING.getStack(), ItemSlate.SlateType.REINFORCED.getStack(), new ItemStack(RegistrarBloodArsenalItems.SIGIL_SWIMMING), new ResourceLocation("bloodmagic", "textures/models/AlchemyArrays/WaterSigil.png"));
        registrar.addAlchemyArray(EnumBaseTypes.REAGENT_ENDER.getStack(), ItemSlate.SlateType.DEMONIC.getStack(), new ItemStack(RegistrarBloodArsenalItems.SIGIL_ENDER), null);
        registrar.addAlchemyArray(EnumBaseTypes.REAGENT_LIGHTNING.getStack(), ItemSlate.SlateType.DEMONIC.getStack(), new ItemStack(RegistrarBloodArsenalItems.SIGIL_LIGHTNING), null);
        registrar.addAlchemyArray(EnumBaseTypes.REAGENT_DIVINITY.getStack(), ItemSlate.SlateType.ETHEREAL.getStack(), new ItemStack(RegistrarBloodArsenalItems.SIGIL_DIVINITY), null);
    }
}
