package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockBloodStone extends Block
{
    private IIcon blood_stone_1;
    private IIcon blood_stone_2;
    private IIcon blood_stone_3;
    private IIcon blood_stone_4;
    private IIcon blood_stone_5;

    public BlockBloodStone()
    {
        super(Material.iron);
        setBlockName("blood_stone");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypePiston);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blood_stone_1 = iconRegister.registerIcon("BloodArsenal:blood_stone_1");
        blood_stone_2 = iconRegister.registerIcon("BloodArsenal:blood_stone_2");
        blood_stone_3 = iconRegister.registerIcon("BloodArsenal:blood_stone_3");
        blood_stone_4 = iconRegister.registerIcon("BloodArsenal:blood_stone_4");
        blood_stone_5 = iconRegister.registerIcon("BloodArsenal:blood_stone_5");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1Item, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (this.equals(ModBlocks.blood_stone))
        {
            par3List.add(new ItemStack(par1Item, 1, 0));
            par3List.add(new ItemStack(par1Item, 1, 1));
            par3List.add(new ItemStack(par1Item, 1, 2));
            par3List.add(new ItemStack(par1Item, 1, 3));
            par3List.add(new ItemStack(par1Item, 1, 4));
        }
        else
        {
            super.getSubBlocks(par1Item, par2CreativeTabs, par3List);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        switch (meta)
        {
            case 0:
                return blood_stone_1;

            case 1:
                return blood_stone_2;

            case 2:
                return blood_stone_3;

            case 3:
                return blood_stone_4;

            case 4:
                return blood_stone_5;

            default:
                return blood_stone_1;
        }
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }
}
