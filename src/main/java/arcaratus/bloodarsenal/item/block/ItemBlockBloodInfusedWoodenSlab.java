package arcaratus.bloodarsenal.item.block;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.BlockBloodInfusedWoodenSlab;
import arcaratus.bloodarsenal.registry.ModBlocks;
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
