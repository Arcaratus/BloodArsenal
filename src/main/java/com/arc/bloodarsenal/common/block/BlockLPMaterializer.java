package com.arc.bloodarsenal.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import WayofTime.alchemicalWizardry.common.items.sigil.holding.SigilOfHolding;

import com.arc.bloodarsenal.common.tileentity.TileLPMaterializer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLPMaterializer extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon output;
    @SideOnly(Side.CLIENT)
    private IIcon side;

    public BlockLPMaterializer()
    {
        super(Material.iron);
        setHardness(7.0F);
        setResistance(14.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 0 || par1 == 1 ? output : side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockSide, int x, int y, int z, int side)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:lp_materializer_output");
        output = iconRegister.registerIcon("BloodArsenal:lp_materializer_output");
        side = iconRegister.registerIcon("BloodArsenal:lp_materializer_side");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        TileLPMaterializer tileEntity = (TileLPMaterializer) world.getTileEntity(x, y, z);

        if (tileEntity == null)
        {
            return false;
        }

        ItemStack playerItem = player.getCurrentEquippedItem();

        if (playerItem != null)// <--
        {
            if (playerItem.getItem().equals(ModItems.divinationSigil))
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
            else if (playerItem.getItem() == Items.bucket)
            {
                if (tileEntity.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME)
                {
                    --playerItem.stackSize;
                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.bucketLife));
                    tileEntity.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                }
            }

            if (tileEntity.getStackInSlot(0) == null && playerItem.getItem() instanceof IBloodOrb)
            {
                ItemStack newItem = playerItem.copy();
                newItem.stackSize = 1;
                --playerItem.stackSize;
                tileEntity.setInventorySlotContents(0, newItem);
            }
        }
        else if (player.isSneaking())
        {
            if (tileEntity.getStackInSlot(0) != null)
            {
                ItemStack newItem = tileEntity.getStackInSlot(0).copy();
                newItem.stackSize = 1;
                player.inventory.addItemStackToInventory(newItem);
                tileEntity.setInventorySlotContents(0, null);
            }
        }

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
        return new TileLPMaterializer();
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }
}
