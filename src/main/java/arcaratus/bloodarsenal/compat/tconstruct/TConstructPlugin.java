package arcaratus.bloodarsenal.compat.tconstruct;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.compat.CompatibilityPlugin;
import arcaratus.bloodarsenal.compat.ICompatibilityPlugin;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

@CompatibilityPlugin("tconstruct")
public class TConstructPlugin implements ICompatibilityPlugin
{
    public static final Modifier MODIFIER_SERRATED = new ModifierSerrated();
    public static final Modifier MODIFIER_SENTIENCE = new ModifierSentience();

    public static final ITrait TRAIT_LIVING_1 = new TraitLiving(1);
    public static final ITrait TRAIT_LIVING_2 = new TraitLiving(2);

    public static final Material MATERIAL_BLOOD_INFUSED_WOOD = new Material("blood_infused_wood", 0x682318).addTrait(TRAIT_LIVING_1);
    public static final Material MATERIAL_BLOOD_INFUSED_IRON = new Material("blood_infused_iron", 0xCE2516).addTrait(TRAIT_LIVING_2);

    public static final Fluid FLUID_MOLTEN_BLOOD_INFUSED_IRON = new FluidMolten(BloodArsenal.MOD_ID + ".molten_blood_infused_iron", 0x9B1B12);

    @Override
    public void preInit()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FluidRegistry.registerFluid(FLUID_MOLTEN_BLOOD_INFUSED_IRON);
        FluidRegistry.addBucketForFluid(FLUID_MOLTEN_BLOOD_INFUSED_IRON);

        MATERIAL_BLOOD_INFUSED_WOOD.setCraftable(true);

        MATERIAL_BLOOD_INFUSED_IRON.setFluid(FLUID_MOLTEN_BLOOD_INFUSED_IRON);
        MATERIAL_BLOOD_INFUSED_IRON.setCastable(true);

        TinkerRegistry.addMaterial(MATERIAL_BLOOD_INFUSED_WOOD);
        TinkerRegistry.addMaterial(MATERIAL_BLOOD_INFUSED_IRON);
        TinkerSmeltery.registerToolpartMeltingCasting(MATERIAL_BLOOD_INFUSED_IRON);

        TinkerRegistry.addMaterialStats(MATERIAL_BLOOD_INFUSED_WOOD,
                new HeadMaterialStats(234, 5F, 3.6F, HarvestLevels.IRON),
                new HandleMaterialStats(1.4F, 32),
                new ExtraMaterialStats(28));
        TinkerRegistry.addMaterialStats(MATERIAL_BLOOD_INFUSED_IRON,
                new HeadMaterialStats(568, 7.3F, 4.9F, HarvestLevels.DIAMOND),
                new HandleMaterialStats(1.7F, 72),
                new ExtraMaterialStats(49));
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        TinkerRegistry.registerMelting(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), FLUID_MOLTEN_BLOOD_INFUSED_IRON, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_IRON_BLOCK, FLUID_MOLTEN_BLOOD_INFUSED_IRON, Material.VALUE_Block);

        MODIFIER_SERRATED.addItem(EnumBaseTypes.GLASS_SHARD.getStack(), 1, 1);
        MODIFIER_SENTIENCE.addItem(new ItemStack(RegistrarBloodMagicItems.SOUL_GEM, 1, 3), 1, 1);

        MATERIAL_BLOOD_INFUSED_WOOD.addItem(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS, Material.VALUE_Ingot);
        MATERIAL_BLOOD_INFUSED_WOOD.setRepresentativeItem(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        MATERIAL_BLOOD_INFUSED_IRON.addItem("ingotBloodInfusedIron", 1, Material.VALUE_Ingot);
        MATERIAL_BLOOD_INFUSED_IRON.setRepresentativeItem(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        ModelRegisterUtil.registerModifierModel(MODIFIER_SERRATED, new ResourceLocation(BloodArsenal.MOD_ID, "models/item/modifiers/serrated"));
        ModelRegisterUtil.registerModifierModel(MODIFIER_SENTIENCE, new ResourceLocation(BloodArsenal.MOD_ID, "models/item/modifiers/sentience"));

        MATERIAL_BLOOD_INFUSED_WOOD.setRenderInfo(new MaterialRenderInfo.MultiColor(0x6C1E12, 0x7A1E0E, 0x982E1A));
        MATERIAL_BLOOD_INFUSED_IRON.setRenderInfo(new MaterialRenderInfo.Metal(0x9B1B12, 0F, 0.1F, 0F));
    }
}
