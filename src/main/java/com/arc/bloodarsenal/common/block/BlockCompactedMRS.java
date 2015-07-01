package com.arc.bloodarsenal.common.block;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import WayofTime.alchemicalWizardry.common.block.BlockMasterStone;
import WayofTime.alchemicalWizardry.common.items.ActivationCrystal;
import com.arc.bloodarsenal.common.tileentity.TileCompactedMRS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockCompactedMRS extends BlockMasterStone
{
    public BlockCompactedMRS()
    {
        super();
        setBlockName("compacted_mrs");
        setHardness(4.0F);
        setResistance(7.5F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:compacted_mrs");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (this.equals(ModBlocks.compacted_mrs))
        {
            par3List.add(new ItemStack(par1, 1, 0));

            for (String ritualName : Rituals.keyList)
            {
                ItemStack stack = new ItemStack(par1, 1, 0);
                NBTTagCompound tag = new NBTTagCompound();
                TileCompactedMRS tile = new TileCompactedMRS();

                NBTTagList tagList = new NBTTagList();

                NBTTagCompound savedTag = new NBTTagCompound();

                if (tile != null)
                {
                    tile.writeToNBT(savedTag);
                }

                tagList.appendTag(savedTag);

                tag.setString("ritualName", ritualName);

                stack.setTagCompound(tag);

                par3List.add(stack);
            }
        }
        else
        {
            super.getSubBlocks(par1, par2CreativeTabs, par3List);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileCompactedMRS)
        {
            NBTTagCompound tag = stack.getTagCompound();

            if (tag != null)
            {
                ((TileCompactedMRS) tile).readNBTOnPlace(tag);
            }
        }
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileCompactedMRS)
        {
            ((TileCompactedMRS) tile).useOnRitualBroken();
        }

        if (!player.capabilities.isCreativeMode)
        {
            dropBlockAsItem(world, x, y, z, meta, 0);
        }

        super.onBlockHarvested(world, x, y, z, meta, player);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> list = new ArrayList();

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileCompactedMRS)
        {
            ItemStack drop = new ItemStack(this);
            NBTTagCompound tag = new NBTTagCompound();
            ((TileCompactedMRS) tile).writeNBTOnHarvest(tag);
            drop.setTagCompound(tag);

            list.add(drop);
        }

        return list;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {
        super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileCompactedMRS)
        {
            ((TileCompactedMRS) tile).useOnRitualBrokenExplosion();
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        TileCompactedMRS tileEntity = (TileCompactedMRS) world.getTileEntity(x, y, z);

        if (tileEntity != null)
        {
            ItemStack playerItem = player.getCurrentEquippedItem();

            if (playerItem == null)
            {
                return false;
            }
            else
            {
                Item item = playerItem.getItem();

                if (!(item instanceof ActivationCrystal))
                {
                    return false;
                }
                else
                {
                    ActivationCrystal acItem = (ActivationCrystal) item;
                    tileEntity.activateRitual(world, acItem.getCrystalLevel(playerItem), playerItem, player, ActivationCrystal.getOwnerName(playerItem));
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        Item item = getItem(world, x, y, z);

        if (item == null)
        {
            return null;
        }

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileCompactedMRS)
        {
            ItemStack drop = new ItemStack(this);
            NBTTagCompound tag = new NBTTagCompound();
            ((TileCompactedMRS) tile).writeNBTOnHarvest(tag);
            drop.setTagCompound(tag);
            return drop;
        }

        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileCompactedMRS();
    }
}
