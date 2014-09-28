package com.arc.bloodarsenal.item.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class InfusedIronPickaxe extends ItemPickaxe implements IBindable
{
    private int energyUsed;

    public InfusedIronPickaxe()
    {
        super(BloodArsenal.infusedIron);
        setMaxStackSize(1);
        setUnlocalizedName("blood_infused_pickaxe_iron");
        setTextureName("BloodArsenal:blood_infused_pickaxe_iron");
        setCreativeTab(BloodArsenal.BA_TAB);
        setFull3D();
        setEnergyUsed(75);
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

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }
}
