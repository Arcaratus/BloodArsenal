package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.block.*;
import arc.bloodarsenal.item.block.ItemBlockBloodInfusedWoodenSlab;
import arc.bloodarsenal.item.block.ItemBlockSlate;
import arc.bloodarsenal.tile.TileAltareAenigmatica;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static final Block SLATE;
    public static final Block BLOOD_INFUSED_WOODEN_LOG;
    public static final Block BLOOD_INFUSED_WOODEN_PLANKS;
    public static final Block BLOOD_INFUSED_WOODEN_STAIRS;
    public static final Block BLOOD_INFUSED_WOODEN_DOUBLE_SLAB;
    public static final Block BLOOD_INFUSED_WOODEN_SLAB;
    public static final Block BLOOD_INFUSED_WOODEN_FENCE;
    public static final Block BLOOD_INFUSED_WOODEN_FENCE_GATE;
    public static final Block BLOOD_STAINED_GLASS;
    public static final Block BLOOD_STAINED_GLASS_PANE;
    public static final Block BLOOD_TORCH;
    public static final Block BLOOD_INFUSED_IRON_BLOCK;
    public static final Block BLOOD_INFUSED_GLOWSTONE;
    public static final Block GLASS_SHARD_BLOCK;
    public static final Block BLOOD_BURNED_STRING;
    public static final Block ALTARE_AENIGMATICA;

    static
    {
        SLATE = registerBlock(new ItemBlockSlate(new BlockSlate("slate")));
        BLOOD_INFUSED_WOODEN_LOG = registerBlock(new BlockBloodInfusedWoodenLog("bloodInfusedWoodenLog"));
        BLOOD_INFUSED_WOODEN_PLANKS = registerBlock(new BlockBloodInfusedWoodenPlanks("bloodInfusedWoodenPlanks"));
        BLOOD_INFUSED_WOODEN_STAIRS = registerBlock(new BlockBloodInfusedWoodenStairs("bloodInfusedWoodenStairs", BLOOD_INFUSED_WOODEN_PLANKS));
        BLOOD_INFUSED_WOODEN_DOUBLE_SLAB = registerBlock(new BlockBloodInfusedWoodenSlab.BlockBloodInfusedWoodenDoubleSlab().setUnlocalizedName(BloodArsenal.MOD_ID + ".bloodInfusedWoodenDoubleSlab"));
        BLOOD_INFUSED_WOODEN_SLAB = registerBlock(new ItemBlockBloodInfusedWoodenSlab("bloodInfusedWoodenSlab"));
        BLOOD_INFUSED_WOODEN_FENCE = registerBlock(new BlockBloodInfusedWoodenFence("bloodInfusedWoodenFence"));
        BLOOD_INFUSED_WOODEN_FENCE_GATE = registerBlock(new BlockBloodInfusedWoodenFenceGate("bloodInfusedWoodenFenceGate"));
        BLOOD_STAINED_GLASS = registerBlock(new BlockBloodStainedGlass("bloodStainedGlass"));
        BLOOD_STAINED_GLASS_PANE = registerBlock(new BlockBloodStainedGlassPane("bloodStainedGlassPane"));
        BLOOD_TORCH = registerBlock(new BlockBloodTorch("bloodTorch"));
        BLOOD_INFUSED_IRON_BLOCK = registerBlock(new BlockBloodInfusedIron("bloodInfusedIronBlock"));
        BLOOD_INFUSED_GLOWSTONE = registerBlock(new BlockBloodInfusedGlowstone("bloodInfusedGlowstone"));
        GLASS_SHARD_BLOCK = registerBlock(new BlockGlassShard("glassShardBlock"));
        BLOOD_BURNED_STRING = registerBlock(new BlockBloodBurnedString("bloodBurnedString"));
        ALTARE_AENIGMATICA = registerBlock(new BlockAltareAenigmatica("altareAenigmatica"));
    }

    public static void initTiles()
    {
        GameRegistry.registerTileEntity(TileAltareAenigmatica.class, BloodArsenal.MOD_ID + ":" + TileAltareAenigmatica.class.getSimpleName());
    }

    public static void initSpecialRenders()
    {

    }

    public static Block registerBlock(Block block, String name)
    {
        if (!ConfigHandler.blockBlacklist.contains(name))
        {
            GameRegistry.register(block);
            GameRegistry.register(new ItemBlock(block).setRegistryName(name));
            BloodArsenal.PROXY.tryHandleBlockModel(block, name);
        }

        return block;
    }

    public static Block registerBlock(ItemBlock itemBlock)
    {
        Block block = itemBlock.block;
        block.setRegistryName(block.getClass().getSimpleName());
        if (block.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Block {} without setting a registry name. Block will not be registered. Please report this.", block.getClass().getCanonicalName());
            return block;
        }

        String blockName = block.getRegistryName().toString().split(":")[1];
        if (!ConfigHandler.blockBlacklist.contains(blockName))
        {
            GameRegistry.register(block);
            GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName().getResourcePath()));
            BloodArsenal.PROXY.tryHandleBlockModel(block, blockName);
        }

        return block;
    }

    public static Block registerBlock(Block block)
    {
        block.setRegistryName(block.getClass().getSimpleName());
        if (block.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Block {} without setting a registry name. Block will not be registered. Please report this.", block.getClass().getCanonicalName());
            return null;
        }

        String blockName = block.getRegistryName().toString().split(":")[1];
        if (!ConfigHandler.blockBlacklist.contains(blockName))
        {
            GameRegistry.register(block);
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName().getResourcePath()));
            BloodArsenal.PROXY.tryHandleBlockModel(block, blockName);
        }

        return block;
    }
}
