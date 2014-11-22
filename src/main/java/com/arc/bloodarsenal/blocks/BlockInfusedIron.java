package com.arc.bloodarsenal.blocks;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInfusedIron extends Block
{
    public BlockInfusedIron()
    {
        super(Material.iron);
        setBlockName("blood_infused_iron_block");
        setBlockTextureName("BloodArsenal:blood_infused_iron_block");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(7.5F);
        setResistance(5.0F);
        setStepSound(soundTypeMetal);
    }
}
