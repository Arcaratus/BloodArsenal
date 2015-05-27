package com.arc.bloodarsenal.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.Random;

public class BlockBloodStainedIce extends BlockBreakable
{
    public BlockBloodStainedIce()
    {
        super("blood_stained_ice", Material.ice, false);
        slipperiness = 0.99F;
        setTickRandomly(true);
        setLightOpacity(3);
        setHardness(1.0F);
        setResistance(2.0F);
        setStepSound(soundTypeGlass);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
    }

    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
        par2EntityPlayer.addExhaustion(0.025F);

        if (canSilkHarvest(par1World, par2EntityPlayer, par3, par4, par5, par6) && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
        {
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            ItemStack itemstack = createStackedBlock(par6);

            if (itemstack != null) items.add(itemstack);

            ForgeEventFactory.fireBlockHarvesting(items, par1World, this, par3, par4, par5, par6, 0, 1.0f, true, par2EntityPlayer);
            for (ItemStack is : items)
                dropBlockAsItem(par1World, par3, par4, par5, is);
        }
        else
        {
            if (par1World.provider.isHellWorld)
            {
                par1World.setBlockToAir(par3, par4, par5);
                return;
            }

            int i1 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
            harvesters.set(par2EntityPlayer);
            dropBlockAsItem(par1World, par3, par4, par5, par6, i1);
            harvesters.set(null);
            Material material = par1World.getBlock(par3, par4 - 1, par5).getMaterial();

            if (material.blocksMovement() || material.isLiquid())
            {
                par1World.setBlock(par3, par4, par5, Blocks.flowing_water);
            }
        }
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5)
    {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - getLightOpacity())
        {
            if (par1World.provider.isHellWorld)
            {
                par1World.setBlock(par2, par3, par4, Blocks.air);
                return;
            }

            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlock(par2, par3, par4, Blocks.water);
        }
    }

    public int getMobilityFlag()
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_stained_ice");
    }
}
