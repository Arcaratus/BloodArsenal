package arcaratus.bloodarsenal.core;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.*;
import arcaratus.bloodarsenal.tile.TileAltareAenigmatica;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.List;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
@ObjectHolder(BloodArsenal.MOD_ID)
@SuppressWarnings("unused")
public class RegistrarBloodArsenalBlocks
{
    public static final Block SLATE = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_LOG = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_PLANKS = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_STAIRS = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_DOUBLE_SLAB = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_SLAB = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_FENCE = Blocks.AIR;
    public static final Block BLOOD_INFUSED_WOODEN_FENCE_GATE = Blocks.AIR;
    public static final Block BLOOD_STAINED_GLASS = Blocks.AIR;
    public static final Block BLOOD_STAINED_GLASS_PANE = Blocks.AIR;
    public static final Block BLOOD_TORCH = Blocks.AIR;
    public static final Block BLOOD_INFUSED_IRON_BLOCK = Blocks.AIR;
    public static final Block BLOOD_INFUSED_GLOWSTONE = Blocks.AIR;
    public static final Block GLASS_SHARD_BLOCK = Blocks.AIR;
    public static final Block BLOOD_BURNED_STRING = Blocks.AIR;
    public static final Block ALTARE_AENIGMATICA = Blocks.AIR;
    public static final Block STASIS_PLATE = Blocks.AIR;

    static List<Block> blocks;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        blocks = Lists.newArrayList(
                new BlockSlate("slate"),
                new BlockBloodInfusedWoodenLog("blood_infused_wooden_log"),
                new BlockBloodInfusedWoodenPlanks("blood_infused_wooden_planks"),
                new BlockBloodInfusedWoodenStairs("blood_infused_wooden_stairs"),
                new BlockBloodInfusedWoodenSlab.Double("blood_infused_wooden_double_slab"),
                new BlockBloodInfusedWoodenSlab.Half("blood_infused_wooden_half_slab"),
                new BlockBloodInfusedWoodenFence("blood_infused_wooden_fence"),
                new BlockBloodInfusedWoodenFenceGate("blood_infused_wooden_fence_gate"),
                new BlockBloodStainedGlass("blood_stained_glass"),
                new BlockBloodStainedGlassPane("blood_stained_glass_pane"),
                new BlockBloodTorch("blood_torch"),
                new BlockBloodInfusedIron("blood_infused_iron_block"),
                new BlockBloodInfusedGlowstone("blood_infused_glowstone"),
                new BlockGlassShards("glass_shards"),
                new BlockBloodBurnedString("blood_burned_string"),
                new BlockAltareAenigmatica("altare_aenigmatica"),
                new BlockStasisPlate("stasis_plate")
        );

        event.getRegistry().registerAll(blocks.toArray(new Block[0]));

        registerTileEntities();
    }

    private static void registerTileEntities()
    {
        registerTile(TileAltareAenigmatica.class, "altare_aenigmatica");
        registerTile(TileStasisPlate.class, "stasis_plate");
    }

    private static void registerTile(Class<? extends TileEntity> tile, String name)
    {
        GameRegistry.registerTileEntity(tile, BloodArsenal.MOD_ID + ":" + name);
    }
}
