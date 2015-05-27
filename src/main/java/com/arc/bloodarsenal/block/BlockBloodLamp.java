package com.arc.bloodarsenal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodLamp extends Block
{
    public BlockBloodLamp()
    {
        super(Material.iron);
        setStepSound(soundTypePiston);
        setHardness(5.0F);
        setResistance(6.0F);
        setLightLevel(1.0F);
    }
}
