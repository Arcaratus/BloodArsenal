package com.arc.bloodarsenal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodInfusedPlanks extends Block
{
    public BlockBloodInfusedPlanks()
    {
        super(Material.wood);
        setHardness(3.0F);
        setResistance(6.0F);
        setStepSound(soundTypeWood);
    }
}
