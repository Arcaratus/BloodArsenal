package com.arc.bloodarsenal.common.block.fluid;

import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class MoltenBloodInfusedIron extends BlockFluidClassic
{
    private IIcon stillIcon;
    private IIcon flowingIcon;
    private String stillIconTexture;
    private String flowIconTexture;

    public MoltenBloodInfusedIron(Fluid fluid, String unlocalizedName, String stillIconTexture, String flowIconTexture)
    {
        super(fluid, Material.lava);
        setResistance(100.0F);
        setLightLevel(1.0F);
        setCreativeTab(BloodArsenal.BA_TAB);
        setBlockName(unlocalizedName);

        this.stillIconTexture = (BloodArsenal.MODID + ":" + stillIconTexture);
        this.flowIconTexture = (BloodArsenal.MODID + ":" + flowIconTexture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon)
    {
        this.stillIcon = icon.registerIcon(this.stillIconTexture);
        this.flowingIcon = icon.registerIcon(this.flowIconTexture);

        getFluid().setIcons(this.stillIcon, this.flowingIcon);
    }

    public IIcon getStillIcon()
    {
        return this.stillIcon;
    }

    public IIcon getFlowingIcon()
    {
        return this.flowingIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149735_b(int side, int meta)
    {
        if (side <= 1)
        {
            return this.stillIcon;
        }

        return this.flowingIcon;
    }
}
