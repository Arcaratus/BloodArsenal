package com.arc.bloodarsenal.blocks;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Random;

public class BlockBloodStainedPackedIce extends Block
{
    public BlockBloodStainedPackedIce()
    {
        super(Material.packedIce);
        slipperiness = 0.99F;
        setBlockName("blood_stained_ice_packed");
        setBlockTextureName("BloodArsenal:blood_stained_ice_packed");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(1.0F);
        setResistance(3.0F);
        setStepSound(soundTypeGlass);
    }

    public int quantityDropped(Random par1)
    {
        return 0;
    }
}
