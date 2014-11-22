package com.arc.bloodarsenal.blocks;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBloodInfusedRedstoneLamp extends Block
{
    private boolean isOn = false;
    
    public BlockBloodInfusedRedstoneLamp()
    {
        super(Material.redstoneLight);

        setBlockName("blood_infused_redstone_lamp");
        setBlockTextureName("BloodArsenal:blood_infused_redstone_lamp_off");
        setHardness(0.5F);
        setResistance(0.75F);
        setStepSound(soundTypeGlass);
        setCreativeTab(BloodArsenal.BA_TAB);

        if (isOn)
        {
            setLightLevel(1.0F);
            setBlockTextureName("BloodArsenal:blood_infused_redstone_lamp_on");
        }
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
            if (isOn && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            }
            else if (!isOn && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                isOn = true;
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!world.isRemote)
        {
            if (isOn && !world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                world.scheduleBlockUpdate(x, y, z, this, 4);
            }
            else if (!isOn && world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                isOn = true;
            }
        }
    }

    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote && isOn && !world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            isOn = true;
        }
    }
}
