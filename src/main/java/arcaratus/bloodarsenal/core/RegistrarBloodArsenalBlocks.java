package arcaratus.bloodarsenal.core;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.*;
import arcaratus.bloodarsenal.block.fluid.BlockFluidRefinedLifeEssence;
import arcaratus.bloodarsenal.fluid.FluidCore;
import arcaratus.bloodarsenal.tile.TileAltareAenigmatica;
import arcaratus.bloodarsenal.tile.TileBloodCapacitor;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
@ObjectHolder(BloodArsenal.MOD_ID)
@SuppressWarnings("unused")
public class RegistrarBloodArsenalBlocks
{
    public static final Block SLATE = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_LOG = Blocks.AIR;
    public static Block BLOOD_INFUSED_WOODEN_PLANKS = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_STAIRS = Blocks.AIR;
    public static Block BLOOD_INFUSED_WOODEN_DOUBLE_SLAB = Blocks.AIR;
    public static Block BLOOD_INFUSED_WOODEN_SLAB = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_FENCE = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_FENCE_GATE = Blocks.AIR;
    public static final Block BLOOD_STAINED_GLASS = Blocks.AIR;
    public static final Block BLOOD_STAINED_GLASS_PANE = Blocks.AIR;
    public static final Block BLOOD_TORCH = Blocks.AIR;
    public static final Block BLOOD_INFUSED_IRON_BLOCK = Blocks.AIR;
    public static final Block BLOOD_INFUSED_GLOWSTONE = Blocks.AIR;
    public static final Block GLASS_SHARD_BLOCK = Blocks.AIR;
    public static final Block BLOCK_BLOOD_BURNED_STRING = Blocks.AIR;
    public static final Block ALTARE_AENIGMATICA = Blocks.AIR;
    public static final Block STASIS_PLATE = Blocks.AIR;

    public static final Block BLOOD_CAPACITOR = Blocks.AIR;

    public static final Block REFINED_LIFE_ESSENCE = Blocks.AIR;

    public static Fluid FLUID_REFINED_LIFE_ESSENCE;

    static List<Block> blocks;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        FLUID_REFINED_LIFE_ESSENCE = new FluidCore("refined_life_essence").setDensity(1200).setViscosity(1200);
        FluidRegistry.registerFluid(FLUID_REFINED_LIFE_ESSENCE);
        FluidRegistry.addBucketForFluid(FLUID_REFINED_LIFE_ESSENCE);

        // Needed because it has to register first for things to work properly
        BLOOD_INFUSED_WOODEN_PLANKS = registerBlock(event, new BlockBloodInfusedWoodenPlanks("blood_infused_wooden_planks"));
        BLOOD_INFUSED_WOODEN_SLAB = registerBlock(event, new BlockBloodInfusedWoodenSlab.Half("blood_infused_wooden_slab"));
        BLOOD_INFUSED_WOODEN_DOUBLE_SLAB = registerBlock(event, new BlockBloodInfusedWoodenSlab.Double("blood_infused_wooden_double_slab"));

        blocks = Lists.newArrayList(
                new BlockSlate("slate"),
                new BlockBloodInfusedWoodenLog("blood_infused_wooden_log"),
                new BlockBloodInfusedWoodenStairs(BLOOD_INFUSED_WOODEN_PLANKS.getDefaultState(), "blood_infused_wooden_stairs"),
                new BlockBloodInfusedWoodenFence("blood_infused_wooden_fence"),
                new BlockBloodInfusedWoodenFenceGate("blood_infused_wooden_fence_gate"),
                new BlockBloodStainedGlass("blood_stained_glass"),
                new BlockBloodStainedGlassPane("blood_stained_glass_pane"),
                new BlockBloodTorch("blood_torch"),
                new BlockBloodInfusedIron("blood_infused_iron_block"),
                new BlockBloodInfusedGlowstone("blood_infused_glowstone"),
                new BlockGlassShards("glass_shards"),
                new BlockBloodBurnedString("block_blood_burned_string"),
                new BlockAltareAenigmatica("altare_aenigmatica"),
                new BlockStasisPlate("stasis_plate"),
                new BlockFluidRefinedLifeEssence("refined_life_essence"),
                new BlockBloodCapacitor("blood_capacitor")
        );

        event.getRegistry().registerAll(blocks.toArray(new Block[0]));

        blocks.add(BLOOD_INFUSED_WOODEN_PLANKS);
        blocks.add(BLOOD_INFUSED_WOODEN_SLAB);

        registerTileEntities();
    }

    private static void registerTileEntities()
    {
        registerTile(TileAltareAenigmatica.class, "altare_aenigmatica");
        registerTile(TileStasisPlate.class, "stasis_plate");
        registerTile(TileBloodCapacitor.class, "blood_capacitor");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomStateMapper(REFINED_LIFE_ESSENCE, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(state.getBlock().getRegistryName(), "fluid");
            }
        });
    }

    private static Block registerBlock(RegistryEvent.Register<Block> event, Block block)
    {
        event.getRegistry().register(block);
        return block;
    }

    private static void registerTile(Class<? extends TileEntity> tile, String name)
    {
        GameRegistry.registerTileEntity(tile, new ResourceLocation(BloodArsenal.MOD_ID, name));
    }
}
