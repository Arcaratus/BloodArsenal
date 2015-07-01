package com.arc.bloodarsenal.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockBloodInfusedWood extends BlockRotatedPillar
{
    @SideOnly(Side.CLIENT)
    private IIcon top;

    public BlockBloodInfusedWood()
    {
        super(Material.wood);
        setHardness(3.0F);
        setResistance(6.0F);
        setStepSound(soundTypeWood);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getSideIcon(int par1)
    {
        return blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getTopIcon(int par1)
    {
        return top;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_wood");
        top = iconRegister.registerIcon("BloodArsenal:blood_infused_wood_top");
    }
}
