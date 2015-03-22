package com.arc.bloodarsenal.items.bauble;

import WayofTime.alchemicalWizardry.api.items.IAltarManipulator;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class SacrificeAmulet extends ItemBauble implements IAltarManipulator, IBauble
{
    public SacrificeAmulet()
    {
        super();
        setUnlocalizedName("sacrifice_amulet");
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Collecting some blood!");

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Stored LP: " + this.getStoredLP(par1ItemStack));
        }
    }

    @Override
    public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player)
    {
        super.onWornTick(par1ItemStack, player);
    }

    @Override
    public BaubleType getBaubleType(ItemStack par1ItemStack)
    {
        return BaubleType.AMULET;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (world.isRemote)
        {
            return itemStack;
        }

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);

        if (movingobjectposition == null)
        {
            return super.onItemRightClick(itemStack, world, player);
        }
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                TileEntity tile = world.getTileEntity(x, y, z);

                if (!(tile instanceof TEAltar))
                {
                    return super.onItemRightClick(itemStack, world, player);
                }

                TEAltar altar = (TEAltar)tile;

                if (!altar.isActive())
                {
                    int amount = this.getStoredLP(itemStack);
                    if (amount > 0)
                    {
                        int filledAmount = altar.fillMainTank(amount);
                        amount -= filledAmount;
                        this.setStoredLP(itemStack, amount);

                        world.markBlockForUpdate(x, y, z);
                    }
                }
            }
        }

        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("BloodArsenal:sacrifice_amulet");
    }

    public void setStoredLP(ItemStack itemStack, int lp)
    {
        if (!itemStack.hasTagCompound())
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();

        tag.setInteger("storedLP", lp);
    }

    public int getStoredLP(ItemStack itemStack)
    {
        if (!itemStack.hasTagCompound())
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();

        return tag.getInteger("storedLP");
    }
}
