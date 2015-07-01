package com.arc.bloodarsenal.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockBloodInfusedDiamond extends Block
{
    public BlockBloodInfusedDiamond()
    {
        super(Material.iron);
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
