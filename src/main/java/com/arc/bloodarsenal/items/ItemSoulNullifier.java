package com.arc.bloodarsenal.items;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public class ItemSoulNullifier extends Item implements IBindable
{
    public ItemSoulNullifier()
    {
        super();
        setMaxStackSize(1);
        setUnlocalizedName("soul_nullifier");
        setTextureName("BloodArsenal:soul_nullifier");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        setActivated(par1ItemStack, !getActivated(par1ItemStack));

        if (par3EntityPlayer.isSneaking())
        {
            par1ItemStack.stackTagCompound.setInteger("worldTimeDelay", (int) (par2World.getWorldTime() - 1) % 100);
            return par1ItemStack;
        }

        if (!getActivated(par1ItemStack))
        {
            return par1ItemStack;
        }

        return par1ItemStack;
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (!(par3Entity instanceof EntityPlayer))
        {
            return;
        }

        EntityPlayer par3EntityPlayer = (EntityPlayer) par3Entity;

        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        if (par2World.getWorldTime() % 100 == par1ItemStack.stackTagCompound.getInteger("worldTimeDelay") && par1ItemStack.stackTagCompound.getBoolean("isActive"))
        {
            if (!par3EntityPlayer.capabilities.isCreativeMode)
            {
                EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 50);
            }
        }

        if (par1ItemStack.stackTagCompound.getBoolean("isActive") && !par2World.isRemote)
        {
            if (par3EntityPlayer.ticksExisted % 20 == 0)
            {
                boolean removed = false;
                Collection<PotionEffect> potions = par3EntityPlayer.getActivePotionEffects();

                if (par3EntityPlayer.isBurning())
                {
                    par3EntityPlayer.extinguish();
                    removed = true;
                }
                else for (PotionEffect potion : potions)
                {
                    int id = potion.getPotionID();
                    boolean badEffect;

                    badEffect = ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[id], new String[]{"isBadEffect", "field_76418_K"});

                    if (badEffect)
                    {
                        par3EntityPlayer.removePotionEffect(id);
                        removed = true;
                        break;
                    }
                }

                if (removed)
                {
                    EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 500);
                }
            }
        }
    }

    public void setActivated(ItemStack par1ItemStack, boolean newActivated)
    {
        NBTTagCompound itemTag = par1ItemStack.stackTagCompound;

        if (itemTag == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        itemTag.setBoolean("isActive", newActivated);
    }

    public boolean getActivated(ItemStack par1ItemStack)
    {
        NBTTagCompound itemTag = par1ItemStack.stackTagCompound;

        if (itemTag == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        return itemTag.getBoolean("isActive");
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.itemba.soul_nullifier1"));
        par3List.add(StatCollector.translateToLocal("tooltip.itemba.soul_nullifier2"));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            if (par1ItemStack.stackTagCompound.getBoolean("isActive"))
            {
                par3List.add("Activated");
            }
            else
            {
                par3List.add("Deactivated");
            }

            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }
}
