package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class InfusedWoodSword extends ItemSword implements IBindable
{
    private int energyUsed;

    public InfusedWoodSword()
    {
        super(BloodArsenal.infusedWood);
        setMaxStackSize(1);
        setUnlocalizedName("blood_infused_sword_wood");
        setTextureName("BloodArsenal:blood_infused_sword_wood");
        setCreativeTab(BloodArsenal.BA_TAB);
        setFull3D();
        setEnergyUsed(50);
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
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        if (par3EntityLivingBase instanceof EntityPlayer)
        {
            EnergyItems.checkAndSetItemOwner(par1ItemStack, (EntityPlayer) par3EntityLivingBase);

            if (!EnergyItems.syphonBatteries(par1ItemStack, (EntityPlayer) par3EntityLivingBase, getEnergyUsed()))
            {

            }
        }
        par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.weakness.id, 60, 2));
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}
