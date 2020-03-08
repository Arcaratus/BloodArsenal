package arcaratus.bloodarsenal.item.block;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemBlockBloodInfusedWoodenSlab extends ItemSlab
{
    public ItemBlockBloodInfusedWoodenSlab()
    {
        super(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB, (BlockSlab) RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB, (BlockSlab) RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_DOUBLE_SLAB);
        setTranslationKey(BloodArsenal.MOD_ID + ".blood_infused_wooden_slab");
        setRegistryName(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB.getRegistryName());
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }
}