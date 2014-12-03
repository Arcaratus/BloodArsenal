package com.arc.bloodarsenal.items.sigil;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class SigilEnder extends EnergyItems implements IBindable
{
    public SigilEnder()
    {
        super();
        setMaxStackSize(1);
        setUnlocalizedName("sigil_of_ender");
        setTextureName("BloodArsenal:sigil_of_ender");
        setCreativeTab(BloodArsenal.BA_TAB);
        setEnergyUsed(1000);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Sticking your hand into the End");

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        par3EntityPlayer.displayGUIChest(par3EntityPlayer.getInventoryEnderChest());
        par2World.playSoundAtEntity(par3EntityPlayer, "mob.endermen.portal", 1F, 1F);

        EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 500);

        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        if (par3EntityPlayer.isSneaking() && !par3EntityPlayer.isSwingInProgress)
        {
            if (!par3EntityPlayer.capabilities.isCreativeMode)
            {
                return par1ItemStack;
            }
            else
            {
                par2World.playSoundAtEntity(par3EntityPlayer, "mob.endermen.portal", 1F, 1F);

                if (!par2World.isRemote)
                {
                    par2World.spawnEntityInWorld(new EntityEnderPearl(par2World, par3EntityPlayer));
                    EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, getEnergyUsed());
                }
            }
        }

        return par1ItemStack;
    }
}
