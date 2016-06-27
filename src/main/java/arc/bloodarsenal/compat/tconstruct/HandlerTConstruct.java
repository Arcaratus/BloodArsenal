package arc.bloodarsenal.compat.tconstruct;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModBlocks;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.TinkerIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ToolClientProxy;

public class HandlerTConstruct
{
    public static Modifier modSerrated;

    public static Material materialBloodInfusedWood;
    public static Material materialBloodInfusedIron;

    public static FluidMolten moltenBloodInfusedIron;

    public static AbstractTrait traitLiving1;
    public static AbstractTrait traitLiving2;

    public static void init()
    {
        modSerrated = new ModifierSerrated();
        modSerrated.addItem(ModItems.glassShard, 1, 1);

        traitLiving1 = new TraitLiving(1);
        traitLiving2 = new TraitLiving(2);

        materialBloodInfusedWood = new Material("bloodInfusedWood", 0x7B1D0C);
        materialBloodInfusedWood.addTrait(traitLiving1);
        materialBloodInfusedWood.addItem(new ItemStack(ModBlocks.bloodInfusedWoodenPlanks), 1, Material.VALUE_Ingot);
        materialBloodInfusedWood.setRepresentativeItem(ModBlocks.bloodInfusedWoodenPlanks);
        materialBloodInfusedWood.setCraftable(true);

        TinkerRegistry.addMaterial(materialBloodInfusedWood);
        TinkerRegistry.addMaterialStats(materialBloodInfusedWood,
                new HeadMaterialStats(234, 5F, 3.6F, HarvestLevels.IRON),
                new HandleMaterialStats(1.4F, 32),
                new ExtraMaterialStats(28));

        materialBloodInfusedIron = new Material("bloodInfusedIron", 0xA93027);
        materialBloodInfusedIron.addTrait(traitLiving2);
        materialBloodInfusedIron.addItem(ModItems.bloodInfusedIronIngot, 1, Material.VALUE_Ingot);
        materialBloodInfusedIron.setRepresentativeItem(ModItems.bloodInfusedIronIngot);


        TinkerRegistry.addMaterial(materialBloodInfusedIron);
        TinkerRegistry.addMaterialStats(materialBloodInfusedIron,
                new HeadMaterialStats(568, 7.3F, 4.9F, HarvestLevels.DIAMOND),
                new HandleMaterialStats(1.7F, 72),
                new ExtraMaterialStats(49));

        moltenBloodInfusedIron = new FluidMolten(materialBloodInfusedIron.getIdentifier(), materialBloodInfusedIron.materialTextColor
//                0xd55b5b
        );
        moltenBloodInfusedIron.setTemperature(910);
        moltenBloodInfusedIron.setRarity(EnumRarity.UNCOMMON);
        moltenBloodInfusedIron.setUnlocalizedName(BloodArsenal.MOD_ID + ".moltenBloodInfusedIron");

        FluidRegistry.registerFluid(moltenBloodInfusedIron);

        TinkerIntegration.integrate(materialBloodInfusedIron, moltenBloodInfusedIron, "BloodInfusedIron");

        if (!FluidRegistry.getBucketFluids().contains(moltenBloodInfusedIron))
            FluidRegistry.addBucketForFluid(moltenBloodInfusedIron);

        materialBloodInfusedIron.setCastable(true);
        materialBloodInfusedIron.setFluid(moltenBloodInfusedIron);

        TinkerFluids.registerMoltenBlock(moltenBloodInfusedIron);
        TinkerFluids.proxy.registerFluidModels(moltenBloodInfusedIron);
        TinkerSmeltery.registerToolpartMeltingCasting(materialBloodInfusedIron);
    }

    @SideOnly(Side.CLIENT)
    public static void initRender()
    {
        ((ToolClientProxy) TinkerTools.proxy).registerModifierModel(modSerrated, new ResourceLocation(BloodArsenal.MOD_ID, "models/item/modifiers/serrated"));

        materialBloodInfusedWood.setRenderInfo(new MaterialRenderInfo.MultiColor(0x6C1E12, 0x7A1E0E, 0x982E1A));

        materialBloodInfusedIron.setRenderInfo(new MaterialRenderInfo.Metal(0xA93027, 0F, 0.1F, 0F));
    }
}
