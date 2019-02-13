package arcaratus.bloodarsenal.compat.tconstruct;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.LinkedList;
import java.util.List;

public class HandlerTConstruct
{
    public static Modifier modSerrated;

    public static List<BATinkerMaterial> baTinkerMaterials;
    public static Material materialBloodInfusedWood;
    public static Material materialBloodInfusedIron;

    public static FluidMolten moltenBloodInfusedIron;

    public static AbstractTrait traitLiving1;
    public static AbstractTrait traitLiving2;

    private static List<MaterialIntegration> integrationList = new LinkedList<>();

    public static void preInit()
    {
        modSerrated = new ModifierSerrated();
        modSerrated.addItem(EnumBaseTypes.GLASS_SHARD.getStack(), 1, 1);

//        traitLiving1 = new TraitLiving(1);
//        traitLiving2 = new TraitLiving(2);

        baTinkerMaterials = ImmutableList.of(new BATinkerMaterial("blood_infused_wood", 0x682318, "blockBloodInfusedWood", new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS), false)
        {
            public void addStats(List<IMaterialStats> stats)
            {
                stats.add(new HeadMaterialStats(234, 5F, 3.6F, HarvestLevels.IRON));
                stats.add(new HandleMaterialStats(1.4F, 32));
                stats.add(new ExtraMaterialStats(28));
            }

            public void addTraits()
            {
                addTrait(new TraitLiving(1));
            }

            @SideOnly(Side.CLIENT)
            public MaterialRenderInfo createRenderInfo()
            {
                return new MaterialRenderInfo.MultiColor(0x6C1E12, 0x7A1E0E, 0x982E1A);
            }
        }, new BATinkerMaterial("blood_infused_iron", 0x4C1E1A, "BloodInfusedIron", EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), true)
        {
            public void addStats(List<IMaterialStats> stats)
            {
                stats.add(new HeadMaterialStats(568, 7.3F, 4.9F, HarvestLevels.DIAMOND));
                stats.add(new HandleMaterialStats(1.7F, 72));
                stats.add(new ExtraMaterialStats(49));
            }

            public void addTraits()
            {
                addTrait(new TraitLiving(2));
            }

            @SideOnly(Side.CLIENT)
            public MaterialRenderInfo createRenderInfo()
            {
                return new MaterialRenderInfo.Metal(0x9B1B12, 0F, 0.1F, 0F);
            }
        });

        boolean useFluids = TConstruct.pulseManager.isPulseLoaded(TinkerSmeltery.PulseId);

        for (BATinkerMaterial material : baTinkerMaterials)
        {
            if (useFluids && material.getFluid() != null)
            {
                FluidRegistry.registerFluid(material.getFluid());
                if (!FluidRegistry.hasBucket(material.getFluid()))
                    FluidRegistry.addBucketForFluid(material.getFluid());
            }

            if (!material.getRepresentativeStack().isEmpty())
                material.getMaterial().setRepresentativeItem(material.getRepresentativeStack().copy());

            TinkerRegistry.addMaterial(material.getMaterial());
            material.registerTraits();

            for (IMaterialStats stats : material.getStats())
                TinkerRegistry.addMaterialStats(material.getMaterial(), stats);

            if (useFluids && material.getFluid() != null)
            {
                material.getMaterial().setFluid(material.getFluid());
                material.getMaterial().setCastable(true);
            }
            else
            {
                material.getMaterial().setCraftable(true);
            }
        }

//        materialBloodInfusedWood = new Material("bloodInfusedWood", 0x682318);
//        materialBloodInfusedWood.addTrait(traitLiving1);
//        materialBloodInfusedWood.addItem(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS), 1, Material.VALUE_Ingot);
//        materialBloodInfusedWood.setRepresentativeItem(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
//        materialBloodInfusedWood.setCraftable(true);

//        TinkerRegistry.addMaterialStats(materialBloodInfusedWood,
//                ,
//                ,
//                );

//        TinkerRegistry.integrate(materialBloodInfusedWood);

//        materialBloodInfusedIron = new Material("bloodInfusedIron", 0x4c1e1a);
//        materialBloodInfusedIron.addTrait(traitLiving2);
//        materialBloodInfusedIron.addItem(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 1, Material.VALUE_Ingot);
//        materialBloodInfusedIron.setRepresentativeItem(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());
//
//        TinkerRegistry.addMaterialStats(materialBloodInfusedIron,
//                ,
//                ,
//                n);
//
//        moltenBloodInfusedIron = new FluidMolten(materialBloodInfusedIron.getIdentifier(), materialBloodInfusedIron.materialTextColor, new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
//        moltenBloodInfusedIron.setTemperature(910);
//        moltenBloodInfusedIron.setRarity(EnumRarity.UNCOMMON);
//        moltenBloodInfusedIron.setUnlocalizedName(Util.prefix("molten_blood_infused_iron"));
//        moltenBloodInfusedIron.setLuminosity(11);
//        moltenBloodInfusedIron.setDensity(2000);
//
//        FluidRegistry.registerFluid(moltenBloodInfusedIron);
//        if (!FluidRegistry.getBucketFluids().contains(moltenBloodInfusedIron))
//            FluidRegistry.addBucketForFluid(moltenBloodInfusedIron);
//
//        BlockMolten moltenBloodInfusedIronBlock = new BlockMolten(moltenBloodInfusedIron);
//        moltenBloodInfusedIronBlock.setTranslationKey("molten_" + moltenBloodInfusedIron.getName());
//        moltenBloodInfusedIronBlock.setRegistryName(BloodArsenal.MOD_ID.toLowerCase(Locale.ENGLISH), "molten_" + moltenBloodInfusedIron.getName());

//        GameRegistry.register(moltenBloodInfusedIronBlock);
//        GameRegistry.register(new ItemBlock(moltenBloodInfusedIronBlock).setRegistryName(moltenBloodInfusedIronBlock.getRegistryName()));

//        BloodArsenal.PROXY.registerFluidModels(moltenBloodInfusedIron);

//        MaterialIntegration bloodInfusedIronMI = new MaterialIntegration(materialBloodInfusedIron, moltenBloodInfusedIron, "BloodInfusedIron");
//        bloodInfusedIronMI.registerFluidBlock(GameRegistry.findRegistry(Block.class));
//        TinkerRegistry.integrate(bloodInfusedIronMI).toolforge();

//        materialBloodInfusedIron.setCastable(true);
//        materialBloodInfusedIron.setFluid(moltenBloodInfusedIron);
//        materialBloodInfusedIron.addItemIngot("ingotBloodInfusedIron");

//        MaterialIntegration materialIntegration = new MaterialIntegration(materialBloodInfusedWood);
//        integrationList.add(materialIntegration);
////        materialIntegration = new MaterialIntegration(materialBloodInfusedIron, moltenBloodInfusedIron, "BloodInfusedIron").toolforge();
////        integrationList.add(materialIntegration);
//
//        for (MaterialIntegration integration : integrationList)
//            integration.integrate();
    }

    public static void init()
    {
//        for (MaterialIntegration integration : integrationList)
//            integration.integrateRecipes();

        System.out.println("" + OreDictionary.getOres("ingotBloodInfusedIron"));
        for (BATinkerMaterial material : baTinkerMaterials)
        {
            if (material.getFluid() != null && material.getOreDictSuffix() != null)
                TinkerSmeltery.registerOredictMeltingCasting(material.getFluid(), material.getOreDictSuffix());
            else if (material.getOreDictSuffix() != null)
                material.getMaterial().addItem(material.getOreDictSuffix(), 1, 144);

            TinkerSmeltery.registerToolpartMeltingCasting(material.getMaterial());
        }
    }

    public static void postInit()
    {
//        for (MaterialIntegration integration : integrationList)
//            integration.registerRepresentativeItem();
    }

    @SideOnly(Side.CLIENT)
    public static void initRender()
    {
        ModelRegisterUtil.registerModifierModel(modSerrated, new ResourceLocation(BloodArsenal.MOD_ID, "models/item/modifiers/serrated"));

//        materialBloodInfusedWood.setRenderInfo();

//        materialBloodInfusedIron.setRenderInfo();
    }
}
