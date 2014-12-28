package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodLamp extends Block
{
    public BlockBloodLamp()
    {
        super(Material.iron);
        setBlockName("blood_lamp");
        setBlockTextureName("BloodArsenal:blood_lamp");
        setCreativeTab(BloodArsenal.BA_TAB);
        setStepSound(soundTypePiston);
        setHardness(5.0F);
        setResistance(6.0F);
        setLightLevel(1.0F);
    }
}
