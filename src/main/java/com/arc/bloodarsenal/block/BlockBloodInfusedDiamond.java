package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

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

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return true;
    }
}
