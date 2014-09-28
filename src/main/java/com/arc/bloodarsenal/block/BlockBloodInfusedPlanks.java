package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodInfusedPlanks extends Block
{
    public BlockBloodInfusedPlanks()
    {
        super(Material.wood);
        setBlockName("blood_infused_planks");
        setBlockTextureName("BloodArsenal:blood_infused_planks");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(3.0F);
        setResistance(6.0F);
        setStepSound(soundTypeWood);
    }
}
