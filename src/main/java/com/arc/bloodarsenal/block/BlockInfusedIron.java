package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

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

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return true;
    }
}
