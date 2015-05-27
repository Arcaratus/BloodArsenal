package com.arc.bloodarsenal.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockInfusedIron extends Block
{
    public BlockInfusedIron()
    {
        super(Material.iron);
        setHardness(7.5F);
        setResistance(5.0F);
        setStepSound(soundTypeMetal);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return true;
    }
}
