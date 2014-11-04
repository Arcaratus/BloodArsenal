package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BoundFlintAndSteel extends Item implements IBindable
{
    private int energyUsed;
    
    private static IIcon active;
    private static IIcon passive;

    public BoundFlintAndSteel()
    {
        super();
        setUnlocalizedName("bound_flint_and_steel");
        setMaxStackSize(1);
        setCreativeTab(BloodArsenal.BA_TAB);
        setEnergyUsed(50);
    }

    public void setEnergyUsed(int i)
    {
        energyUsed = i;
    }

    public int getEnergyUsed()
    {
        return energyUsed;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = stack.stackTagCompound;

        if (tag.getBoolean("isActive"))
        {
            return active;
        }
        else
        {
            return passive;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            setActivated(par1ItemStack, !getActivated(par1ItemStack));
            par1ItemStack.stackTagCompound.setInteger("worldTimeDelay", (int) (par2World.getWorldTime() - 1) % 100);
            return par1ItemStack;
        }

        if (!getActivated(par1ItemStack))
        {
            return par1ItemStack;
        }

        return par1ItemStack;
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ)
    {
        NBTTagCompound tag = itemStack.stackTagCompound;

        if (tag.getBoolean("isActive"))
        {
            if (!player.capabilities.isCreativeMode)
            {
                EnergyItems.syphonBatteries(itemStack, player, 50);
            }

            if (hitSide == 0)
            {
                --y;
            }

            if (hitSide == 1)
            {
                ++y;
            }

            if (hitSide == 2)
            {
                --z;
            }

            if (hitSide == 3)
            {
                ++z;
            }

            if (hitSide == 4)
            {
                --x;
            }

            if (hitSide == 5)
            {
                ++x;
            }

            if (!player.canPlayerEdit(x, y, z, hitSide, itemStack))
            {
                return false;
            }
            else
            {
                if (world.isAirBlock(x, y, z))
                {
                    world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                    world.setBlock(x, y, z, Blocks.fire);
                }
            }

	        return true;
        }

        return false;
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

        par1ItemStack.setItemDamage(0);
        return;
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
    public boolean onLeftClickEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, Entity par3Entity)
    {
        EntityLivingBase living = (EntityLivingBase) par3Entity;
        living.setFire(4);
        EnergyItems.syphonBatteries(par1ItemStack, par2EntityPlayer, 50);

        return super.onLeftClickEntity(par1ItemStack, par2EntityPlayer, par3Entity);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Burning souls...");

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

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("BloodArsenal:bound_flint_and_steel");
        active = iconRegister.registerIcon("BloodArsenal:bound_flint_and_steel");
        passive = iconRegister.registerIcon("AlchemicalWizardry:SheathedItem");
    }
}
