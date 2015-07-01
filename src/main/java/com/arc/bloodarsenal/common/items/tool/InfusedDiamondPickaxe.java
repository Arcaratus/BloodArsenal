package com.arc.bloodarsenal.common.items.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class InfusedDiamondPickaxe extends ItemPickaxe implements IBindable
{
    private int energyUsed;

    public InfusedDiamondPickaxe()
    {
        super(BloodArsenal.infusedIron);
        setMaxStackSize(1);
        setUnlocalizedName("blood_infused_pickaxe_diamond");
        setTextureName("BloodArsenal:blood_infused_pickaxe_diamond");
        setCreativeTab(BloodArsenal.BA_TAB);
        setFull3D();
        setHasSubtypes(true);
        setEnergyUsed(300);
    }

    public void setEnergyUsed(int par1)
    {
        energyUsed = par1;
    }

    public int getEnergyUsed()
    {
        return energyUsed;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer , List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_infused_diamond_pickaxe"));

        if (par1ItemStack.getItemDamage() == 0)
        {
            par3List.add("Normal Mode");
        }
        else if (par1ItemStack.getItemDamage() == 1)
        {
            par3List.add("Line Mode");
        }
        else if (par1ItemStack.getItemDamage() == 2)
        {
            par3List.add("Square Mode");
        }

        if (!(par1ItemStack.stackTagCompound == null))
        {
            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            ToolCapabilities.changeMode(par1ItemStack);
        }

        return par1ItemStack;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3Block, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
        if (par7EntityLivingBase instanceof EntityPlayer)
        {
            EnergyItems.syphonBatteries(par1ItemStack, (EntityPlayer) par7EntityLivingBase, getEnergyUsed());
        }

        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack par1ItemStack, int x, int y, int z, EntityPlayer par5EntityPlayer)
    {
        World world = par5EntityPlayer.worldObj;
        Material mat = world.getBlock(x, y, z).getMaterial();
        if (!ToolCapabilities.isRightMaterial(mat, ToolCapabilities.materialsPick))
        {
            return false;
        }

        MovingObjectPosition block = ToolCapabilities.raytraceFromEntity(world, par5EntityPlayer, true, 4.5);
        if (block == null)
        {
            return false;
        }

        ForgeDirection direction = ForgeDirection.getOrientation(block.sideHit);

        switch (ToolCapabilities.getMode(par1ItemStack))
        {
            case 0:
            {
                if (!par5EntityPlayer.capabilities.isCreativeMode)
                {
                    EnergyItems.syphonBatteries(par1ItemStack, par5EntityPlayer, 100);
                }
                break;
            }
            case 1:
            {
                if (!par5EntityPlayer.capabilities.isCreativeMode)
                {
                    EnergyItems.syphonBatteries(par1ItemStack, par5EntityPlayer, 1000);
                }

                int xo = -direction.offsetX;
                int yo = -direction.offsetY;
                int zo = -direction.offsetZ;

                ToolCapabilities.removeBlocksInIteration(par5EntityPlayer, world, x, y, z, xo >= 0 ? 0 : -10, yo >= 0 ? 0 : -10, zo >= 0 ? 0 : -10, xo > 0 ? 10 : 1, yo > 0 ? 10 : 1, zo > 0 ? 10 : 1, null, ToolCapabilities.materialsPick);
                break;
            }
            case 2:
            {
                if (!par5EntityPlayer.capabilities.isCreativeMode)
                {
                    EnergyItems.syphonBatteries(par1ItemStack, par5EntityPlayer, 2500);
                }

                boolean doX = direction.offsetX == 0;
                boolean doY = direction.offsetY == 0;
                boolean doZ = direction.offsetZ == 0;

                ToolCapabilities.removeBlocksInIteration(par5EntityPlayer, world, x, y, z, doX ? -2 : 0, doY ? -1 : 0, doZ ? -2 : 0, doX ? 3 : 1, doY ? 4 : 1, doZ ? 3 : 1, null, ToolCapabilities.materialsPick);
                break;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }
}