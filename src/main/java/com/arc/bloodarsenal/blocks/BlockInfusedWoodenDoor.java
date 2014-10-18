package com.arc.bloodarsenal.blocks;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.tileentity.TileOwned;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockInfusedWoodenDoor extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon[] doorUp;
    @SideOnly(Side.CLIENT)
    private IIcon[] doorDown;

    private String owner;

    public BlockInfusedWoodenDoor()
    {
        super(Material.wood);
        setHardness(4.0F);
        setResistance(6.0F);
        setStepSound(soundTypeWood);
        disableStats();
        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return doorDown[0];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        if (side != 1 && side != 0)
        {
            int i1 = func_150012_g(world, x, y, z);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && side == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && side == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && side == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && side == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && side == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && side == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && side == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && side == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }
            return flag2 ? doorUp[flag1?1:0] : doorDown[flag1?1:0];
        }
        else
        {
            return doorDown[0];
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        doorUp = new IIcon[2];
        doorDown = new IIcon[2];
        doorUp[0] = iconRegister.registerIcon("BloodArsenal:blood_door_wood_upper");
        doorDown[0] = iconRegister.registerIcon("BloodArsenal:blood_door_wood_lower");
        doorUp[1] = new IconFlipped(doorUp[0], true, false);
        doorDown[1] = new IconFlipped(doorDown[0], true, false);
    }

    @Override
    public int getRenderType()
    {
        return 7;
    }

    @Override
    public Item getItemDropped(int i, Random random, int j)
    {
        return (i & 8) != 0 ? null : (blockMaterial == Material.iron ? Items.iron_door : ModItems.item_blood_door_wood);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return blockMaterial == Material.iron ? Items.iron_door : ModItems.item_blood_door_wood;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileOwned && entity instanceof EntityPlayer)
        {

        }
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && (par5 & 8) != 0 && world.getBlock(x, y - 1, z) == this)
        {
            world.setBlockToAir(x, y - 1, z);
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
    {
        int l = func_150012_g(world, x, y, z);
        return (l & 4) != 0;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        func_150011_b(func_150012_g(world, x, y, z));
    }

    public int func_150013_e(IBlockAccess world, int x, int y, int z)
    {
        return func_150012_g(world, x, y, z) & 3;
    }

    public boolean func_150015_f(IBlockAccess world, int x, int y, int z)
    {
        return (func_150012_g(world, x, y, z) & 4) != 0;
    }

    private void func_150011_b(int par1)
    {
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int j = par1 & 3;
        boolean flag = (par1 & 4) != 0;
        boolean flag1 = (par1 & 16) != 0;

        if (j == 0)
        {
            if (flag)
            {
                if (!flag1)
                {
                    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
                else
                {
                    setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
        else if (j == 1)
        {
            if (flag)
            {
                if (!flag1)
                {
                    setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
        }
        else if (j == 2)
        {
            if (flag)
            {
                if (!flag1)
                {
                    setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
            }
            else
            {
                setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (j == 3)
        {
            if (flag)
            {
                if (!flag1)
                {
                    setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
                else
                {
                    setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {}

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        int i1 = func_150012_g(world, x, y, z);
        int j1 = i1 & 7;
        j1 ^= 4;

        if ((i1 & 8) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, j1, 2);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
        }
        else
        {
            world.setBlockMetadataWithNotify(x, y - 1, z, j1, 2);
            world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
        }

        world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
        return true;
    }

    public void func_150014_a(World world, int x, int y, int z, boolean par5)
    {
        int l = func_150012_g(world, x, y, z);
        boolean flag1 = (l & 4) != 0;

        if (flag1 != par5)
        {
            int i1 = l & 7;
            i1 ^= 4;

            if ((l & 8) == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, i1, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            }
            else
            {
                world.setBlockMetadataWithNotify(x, y - 1, z, i1, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            }

            world.playAuxSFXAtEntity((EntityPlayer)null, 1003, x, y, z, 0);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int l = world.getBlockMetadata(x, y, z);

        if ((l & 8) == 0)
        {
            boolean flag = false;

            if (world.getBlock(x, y + 1, z) != this)
            {
                world.setBlockToAir(x, y, z);
                flag = true;
            }

            if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z))
            {
                world.setBlockToAir(x, y, z);
                flag = true;

                if (world.getBlock(x, y + 1, z) == this)
                {
                    world.setBlockToAir(x, y + 1, z);
                }
            }

            if (flag)
            {
                if (!world.isRemote)
                {
                    dropBlockAsItem(world, x, y, z, l, 0);
                }
            }
        }
        else
        {
            if (world.getBlock(x, y - 1, z) != this)
            {
                world.setBlockToAir(x, y, z);
            }

            if (block != this)
            {
                onNeighborBlockChange(world, x, y - 1, z, block);
            }
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 par5, Vec3 par6)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, par5, par6);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return y >= world.getHeight() - 1 ? false : World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public int getMobilityFlag()
    {
        return 2;
    }

    public int func_150012_g(IBlockAccess world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        boolean flag = (l & 8) != 0;
        int i1;
        int j1;

        if (flag)
        {
            i1 = world.getBlockMetadata(x, y - 1, z);
            j1 = l;
        }
        else
        {
            i1 = l;
            j1 = world.getBlockMetadata(x, y + 1, z);
        }

        boolean flag1 = (j1 & 1) != 0;
        return i1 & 7 | (flag ? 8 : 0) | (flag1 ? 16 : 0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileOwned();
    }
}