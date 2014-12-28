package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.BlockTorch;

public class BlockBloodTorch extends BlockTorch
{
    public BlockBloodTorch()
    {
        super();
        setBlockName("blood_torch");
        setBlockTextureName("BloodArsenal:blood_torch");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(0.0F);
        setLightLevel(0.9375F);
        setStepSound(soundTypeWood);
    }
}
