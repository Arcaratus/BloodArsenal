package arc.bloodarsenal.item.block;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemBlockBloodInfusedWoodenSlab extends ItemSlab
{
    public ItemBlockBloodInfusedWoodenSlab(String name)
    {
        super(ModBlocks.bloodInfusedWoodenSlab, (BlockSlab) ModBlocks.bloodInfusedWoodenSlab, (BlockSlab) ModBlocks.bloodInfusedWoodenDoubleSlab);
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.tabBloodArsenal);
    }
}
