package com.arc.bloodarsenal.blocks;

import com.arc.bloodarsenal.BloodArsenal;
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
        setBlockName("blood_stained_glass");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(1.0F);
        setResistance(4.0F);
        setStepSound(soundTypeGlass);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int quantityDropped(Random par1Random) {return 0;}

    protected boolean canSilkHarvest() {return true;}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_stained_glass");
    }
}
