package arc.bloodarsenal.item.block;

import arc.bloodarsenal.block.BlockBloodInfusedWood;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBloodInfusedWood extends ItemBlock
{
    public ItemBlockBloodInfusedWood(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + BlockBloodInfusedWood.names[stack.getItemDamage()];
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }
}
