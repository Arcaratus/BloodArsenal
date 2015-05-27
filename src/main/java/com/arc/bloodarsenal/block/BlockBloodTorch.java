package com.arc.bloodarsenal.block;

import net.minecraft.block.BlockTorch;

public class BlockBloodTorch extends BlockTorch
{
    public BlockBloodTorch()
    {
        super();
        setHardness(0.0F);
        setLightLevel(0.9375F);
        setStepSound(soundTypeWood);
    }
}
