package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.block.BlockBloodInfusedWood;
import arc.bloodarsenal.item.block.ItemBlockBloodInfusedWood;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static Block blood_infused_wood;

    public static void init()
    {
        blood_infused_wood = registerBlock(new BlockBloodInfusedWood(), ItemBlockBloodInfusedWood.class);

        initTiles();
    }

    public static void initTiles()
    {

    }

    public static void initSpecialRenders()
    {

    }

    private static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlock, String name)
    {
        if (!ConfigHandler.blockBlacklist.contains(name))
        {
            GameRegistry.registerBlock(block, itemBlock);
            BloodArsenal.PROXY.tryHandleBlockModel(block, name);
        }

        return block;
    }

    private static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlock)
    {
        block.setRegistryName(block.getClass().getSimpleName());
        if (block.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Block {} without setting a registry name. Block will not be registered. Please report this.", block.getClass().getCanonicalName());
            return block;
        }

        String blockName = block.getRegistryName().split(":")[1];
        if (!ConfigHandler.blockBlacklist.contains(blockName))
        {
            GameRegistry.registerBlock(block, itemBlock);
            BloodArsenal.PROXY.tryHandleBlockModel(block, blockName);
        }

        return block;
    }

    private static Block registerBlock(Block block)
    {
        block.setRegistryName(block.getClass().getSimpleName());
        if (block.getRegistryName() == null)
        {
            BloodArsenal.INSTANCE.getLogger().error("Attempted to register Block {} without setting a registry name. Block will not be registered. Please report this.", block.getClass().getCanonicalName());
            return null;
        }

        String blockName = block.getRegistryName().split(":")[1];
        if (!ConfigHandler.blockBlacklist.contains(blockName))
        {
            GameRegistry.registerBlock(block);
            BloodArsenal.PROXY.tryHandleBlockModel(block, blockName);
        }

        return block;
    }
}
