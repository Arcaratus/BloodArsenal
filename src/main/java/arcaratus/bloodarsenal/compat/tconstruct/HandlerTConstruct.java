package arcaratus.bloodarsenal.compat.tconstruct;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.block.BlockMolten;

import java.util.*;

public class HandlerTConstruct
{
    public static Modifier modSerrated;

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

        traitLiving1 = new TraitLiving(1);
        traitLiving2 = new TraitLiving(2);

        materialBloodInfusedWood = new Material("bloodInfusedWood", 0x682318);
        materialBloodInfusedWood.addTrait(traitLiving1);
        materialBloodInfusedWood.addItem(new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS), 1, Material.VALUE_Ingot);
        materialBloodInfusedWood.setRepresentativeItem(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS);
        materialBloodInfusedWood.setCraftable(true);

        TinkerRegistry.addMaterialStats(materialBloodInfusedWood,
                new HeadMaterialStats(234, 5F, 3.6F, HarvestLevels.IRON),
                new HandleMaterialStats(1.4F, 32),
                new ExtraMaterialStats(28));

        TinkerRegistry.integrate(materialBloodInfusedWood);

        materialBloodInfusedIron = new Material("bloodInfusedIron", 0x4c1e1a);
        materialBloodInfusedIron.addTrait(traitLiving2);
        materialBloodInfusedIron.addItem(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack(), 1, Material.VALUE_Ingot);
        materialBloodInfusedIron.setRepresentativeItem(EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack());

        TinkerRegistry.addMaterialStats(materialBloodInfusedIron,
                new HeadMaterialStats(568, 7.3F, 4.9F, HarvestLevels.DIAMOND),
                new HandleMaterialStats(1.7F, 72),
                new ExtraMaterialStats(49));

        moltenBloodInfusedIron = new FluidMolten(materialBloodInfusedIron.getIdentifier(), materialBloodInfusedIron.materialTextColor, new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
        moltenBloodInfusedIron.setTemperature(910);
        moltenBloodInfusedIron.setRarity(EnumRarity.UNCOMMON);
        moltenBloodInfusedIron.setUnlocalizedName(Util.prefix("molten_blood_infused_iron"));
        moltenBloodInfusedIron.setLuminosity(11);
        moltenBloodInfusedIron.setDensity(2000);

        FluidRegistry.registerFluid(moltenBloodInfusedIron);
        if (!FluidRegistry.getBucketFluids().contains(moltenBloodInfusedIron))
            FluidRegistry.addBucketForFluid(moltenBloodInfusedIron);

        BlockMolten moltenBloodInfusedIronBlock = new BlockMolten(moltenBloodInfusedIron);
        moltenBloodInfusedIronBlock.setUnlocalizedName("molten_" + moltenBloodInfusedIron.getName());
        moltenBloodInfusedIronBlock.setRegistryName(BloodArsenal.MOD_ID.toLowerCase(Locale.ENGLISH), "molten_" + moltenBloodInfusedIron.getName());

//        GameRegistry.register(moltenBloodInfusedIronBlock);
//        GameRegistry.register(new ItemBlock(moltenBloodInfusedIronBlock).setRegistryName(moltenBloodInfusedIronBlock.getRegistryName()));

        BloodArsenal.PROXY.registerFluidModels(moltenBloodInfusedIron);

//        MaterialIntegration bloodInfusedIronMI = new MaterialIntegration(materialBloodInfusedIron, moltenBloodInfusedIron, "BloodInfusedIron");
//        bloodInfusedIronMI.registerFluidBlock(GameRegistry.findRegistry(Block.class));
//        TinkerRegistry.integrate(bloodInfusedIronMI).toolforge();

//        materialBloodInfusedIron.setCastable(true);
//        materialBloodInfusedIron.setFluid(moltenBloodInfusedIron);
//        materialBloodInfusedIron.addItemIngot("ingotBloodInfusedIron");

        MaterialIntegration materialIntegration = new MaterialIntegration(materialBloodInfusedWood);
        integrationList.add(materialIntegration);
//        materialIntegration = new MaterialIntegration(materialBloodInfusedIron, moltenBloodInfusedIron, "BloodInfusedIron").toolforge();
//        integrationList.add(materialIntegration);

        for (MaterialIntegration integration : integrationList)
            integration.integrate();
    }

    public static void init()
    {
//        for (MaterialIntegration integration : integrationList)
//            integration.integrateRecipes();
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

        materialBloodInfusedWood.setRenderInfo(new MaterialRenderInfo.MultiColor(0x6C1E12, 0x7A1E0E, 0x982E1A));

        materialBloodInfusedIron.setRenderInfo(new MaterialRenderInfo.Metal(0xBA5952, 0F, 0.1F, 0F));
    }
}
