package com.arc.bloodarsenal.items.sigil;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.entity.EntityTeleportationSigil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class TeleportationSigil extends EnergyItems
{
    private int energyUsed;

    public TeleportationSigil()
    {
        super();
        setMaxStackSize(1);
        setEnergyUsed(500);
//        setCreativeTab(BloodArsenal.BA_TAB);
        setUnlocalizedName("teleportation_sigil");
        setTextureName("BloodArsenal:teleportation_sigil");
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
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Like infinte ender pearls!");

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            return par1ItemStack;
        }

        EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, getEnergyUsed());

        if (par3EntityPlayer.capabilities.isCreativeMode)
        {
            return par1ItemStack;
        }
        else
        {
            --par1ItemStack.stackSize;
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(new EntityTeleportationSigil(par2World, par3EntityPlayer));
            }

            return par1ItemStack;
        }
    }
}
