package arcaratus.bloodarsenal.item.block;

import arcaratus.bloodarsenal.block.BlockSlate;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSlate extends ItemBlock
{
    public ItemBlockSlate(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + BlockSlate.names[stack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }
}
