package com.arc.bloodarsenal.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import java.util.Random;

public class BlockBloodStainedGlass extends BlockBreakable
{
    public BlockBloodStainedGlass()
    {
        super("blood_stained_glass", Material.glass, false);
        setHardness(1.0F);
        setResistance(4.0F);
        setStepSound(soundTypeGlass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random par1Random) {return 0;}

    @Override
    protected boolean canSilkHarvest() {return true;}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_stained_glass");
    }
}
