package arcaratus.bloodarsenal.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * For registering purposes
 */
public interface IBABlock
{
    default ItemBlock getItemBlock(Block block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }
}
