package arc.bloodarsenal.item.block;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.block.BlockBloodInfusedWoodenSlab;
import arc.bloodarsenal.registry.ModBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemBlockBloodInfusedWoodenSlab extends ItemSlab
{
    public ItemBlockBloodInfusedWoodenSlab(String name)
    {
        super(new BlockBloodInfusedWoodenSlab.BlockBloodInfusedWoodenHalfSlab().setUnlocalizedName(BloodArsenal.MOD_ID + ".bloodInfusedWoodenHalfSlab"), (BlockSlab) new BlockBloodInfusedWoodenSlab.BlockBloodInfusedWoodenHalfSlab().setUnlocalizedName(BloodArsenal.MOD_ID + ".bloodInfusedWoodenHalfSlab"), (BlockSlab) ModBlocks.BLOOD_INFUSED_WOODEN_DOUBLE_SLAB);
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }
}
