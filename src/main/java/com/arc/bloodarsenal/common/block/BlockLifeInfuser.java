package com.arc.bloodarsenal.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.common.items.sigil.holding.SigilOfHolding;

import com.arc.bloodarsenal.common.tileentity.TileLifeInfuser;

public class BlockLifeInfuser extends BlockContainer
{
    public BlockLifeInfuser()
    {
        super(Material.iron);
        setHardness(6.0F);
        setResistance(12.0F);
        setStepSound(soundTypePiston);

        setBlockBounds(0F, 0F, 0F, 1F, 0.875F, 1F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        TileLifeInfuser tileEntity = (TileLifeInfuser) world.getTileEntity(x, y, z);

        if (tileEntity == null || player.isSneaking())
        {
            return false;
        }

        ItemStack playerItem = player.getCurrentEquippedItem();
        ItemStack bucket = new ItemStack(Items.bucket);

        if (playerItem != null)// <--
        {
            if (playerItem.getItem().equals(ModItems.divinationSigil) || playerItem.getItem().equals(ModItems.itemSeerSigil))
            {
                if (player.worldObj.isRemote)
                {
                    world.markBlockForUpdate(x, y, z);
                }
                else
                {
                    tileEntity.sendChatInfoToPlayer(player);
                }
                return true;
            }
            else if (playerItem.getItem().equals(ModItems.sigilOfHolding))
            {
                ItemStack item = ((SigilOfHolding) playerItem.getItem()).getCurrentSigil(playerItem);

                if (item != null && item.getItem().equals(ModItems.divinationSigil))
                {
                    if (player.worldObj.isRemote)
                    {
                        world.markBlockForUpdate(x, y, z);
                    }
                    else
                    {
                        tileEntity.sendChatInfoToPlayer(player);
                    }

                    return true;
                }
            }
            else if (playerItem.getItem().equals(ModItems.bucketLife))
            {
                if (tileEntity.getFluid().amount <= tileEntity.capacity - FluidContainerRegistry.BUCKET_VOLUME)
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        --playerItem.stackSize;
                        player.inventory.addItemStackToInventory(bucket);
                    }

                    tileEntity.fillMainTank(FluidContainerRegistry.BUCKET_VOLUME);
                }
            }
            else if (tileEntity.getStackInSlot(0) == null)
            {
                ItemStack newItem = playerItem.copy();
                newItem.stackSize = 1;
                --playerItem.stackSize;
                tileEntity.setInventorySlotContents(0, newItem);
            }
        }
        else if (tileEntity.getStackInSlot(0) != null && playerItem == null)
        {
            player.inventory.addItemStackToInventory(tileEntity.getStackInSlot(0));
            tileEntity.setInventorySlotContents(0, null);
        }
        world.markBlockForUpdate(x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6)
    {
        dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z)
    {
        Random rand = new Random();
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
        {
            return;
        }

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.stackSize > 0)
            {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                if (item.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileLifeInfuser();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
