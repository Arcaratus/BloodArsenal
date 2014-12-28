package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodInfusedDiamond extends Block
{
    public BlockBloodInfusedDiamond()
    {
        super(Material.iron);
        setBlockName("blood_infused_diamond_block");
        setBlockTextureName("BloodArsenal:blood_infused_diamond_block");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(10.0F);
        setResistance(12.0F);
        setStepSound(soundTypeMetal);
    }
}
