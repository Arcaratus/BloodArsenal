package com.arc.bloodarsenal.items.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Arrays;

public final class ToolCapabilities
{
    public int count;

    public static Material[] materialsPick = new Material[]{ Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil };
    public static Material[] materialsShovel = new Material[]{ Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay };
    public static Material[] materialsAxe = new Material[]{ Material.coral, Material.leaves, Material.plants, Material.wood };

    public static int getMode(ItemStack tool)
    {
        return tool.getItemDamage();
    }

    public static int getNextMode(int mode)
    {
        return mode == 2 ? 0 : mode + 1;
    }

    public static void changeMode(ItemStack tool)
    {
        int mode = getMode(tool);
        tool.setItemDamage(getNextMode(mode));
    }

    public static boolean isRightMaterial(Material material, Material[] materialsListing)
    {
        for (Material mat : materialsListing)
        {
            if (material == mat)
            {
                return true;
            }
        }
        return false;
    }

    public static void removeBlocksInIteration(EntityPlayer player, World world, int x, int y, int z, int xs, int ys, int zs, int xe, int ye, int ze, Block block, Material[] materialsListing)
    {
        float blockHardness = (block == null) ? 1.0f : block.getBlockHardness(world, x, y, z);

        for (int x1 = xs; x1 < xe; x1++)
        {
            for (int y1 = ys; y1 < ye; y1++)
            {
                for (int z1 = zs; z1 < ze; z1++)
                {
                    ToolCapabilities.removeBlockWithDrops(player, world, x1 + x, y1 + y, z1 + z, x, y, z, block, materialsListing);
                }
            }
        }
    }

    public static void removeBlockWithDrops(EntityPlayer player, World world, int x, int y, int z, int bx, int by, int bz, Block block, Material[] materialsListing)
    {
        if (!world.blockExists(x, y, z))
        {
            return;
        }

        Block blk = world.getBlock(x, y, z);

        if (block != null && blk != block)
        {
            return;
        }

        int meta = world.getBlockMetadata(x, y, z);
        Material mat = world.getBlock(x, y, z).getMaterial();

        if (blk != null && !blk.isAir(world, x, y, z) && ((blk.getPlayerRelativeBlockHardness(player, world, x, y, z) != 0)))
        {
            if (!blk.canHarvestBlock(player, meta) || !isRightMaterial(mat, materialsListing))
            {
                return;
            }

            if (!player.capabilities.isCreativeMode)
            {
                int localMeta = world.getBlockMetadata(x, y, z);

                if (blk.removedByPlayer(world, player, x, y, z))
                {
                    blk.onBlockDestroyedByPlayer(world, x, y, z, localMeta);
                }

                blk.harvestBlock(world, player, x, y, z, localMeta);
                blk.onBlockHarvested(world, x, y, z, localMeta, player);
            } 
            else 
            {
                world.setBlockToAir(x, y, z);
            }
        }
    }

    public static int lastx = 0;
    public static int lasty = 0;
    public static int lastz = 0;
    public static double lastdistance = 0.0D;

    public static boolean isWood(IBlockAccess world, int x, int y, int z)
    {
        Block blk = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);

        if (blk == Blocks.air)
        {
            return false;
        }

        if (blk.canSustainLeaves(world, x, y, z))
        {
            return true;
        }

        if (InfusedDiamondAxe.oreDictLogs.contains(Arrays.asList(new Object[]{blk, Integer.valueOf(metadata)})))
        {
            return true;
        }
        return false;
    }

    public static boolean breakFurthestBlock(World world, int x, int y, int z, Block block, EntityPlayer player)
    {
        lastx = x;
        lasty = y;
        lastz = z;
        lastdistance = 0.0D;

        findBlocks(world, x, y, z, block);

        boolean worked = harvestBlock(world, player, lastx, lasty, lastz);

        if (worked)
        {
            for (int xx = -3; xx <= 3; xx++)
            {
                for (int yy = -3; yy <= 3; yy++)
                {
                    for (int zz = -3; zz <= 3; zz++)
                    {
                        world.scheduleBlockUpdate(lastx + xx, lasty + yy, lastz + zz, world.getBlock(lastx + xx, lasty + yy, lastz + zz), 150 + world.rand.nextInt(150));
                    }
                }
            }
        }
        return worked;
    }

    public static void findBlocks(World world, int x, int y, int z, Block block)
    {
        for (int xx = -2; xx <= 2; xx++)
        {
            for (int yy = 2; yy >= -2; yy--)
            {
                for (int zz = -2; zz <= 2; zz++)
                {
                    if (Math.abs(lastx + xx - x) > 24)
                    {
                        return;
                    }

                    if (Math.abs(lasty + yy - y) > 48)
                    {
                        return;
                    }

                    if (Math.abs(lastz + zz - z) > 24)
                    {
                        return;
                    }

                    if ((world.getBlock(lastx + xx, lasty + yy, lastz + zz) == block) && (isWood(world, lastx + xx, lasty + yy, lastz + zz)) && (block.getBlockHardness(world, lastx + xx, lasty + yy, lastz + zz) >= 0.0F))
                    {
                        double xd = lastx + xx - x;
                        double yd = lasty + yy - y;
                        double zd = lastz + zz - z;
                        double d = xd * xd + yd * yd + zd * zd;

                        if (d > lastdistance)
                        {
                            lastdistance = d;
                            lastx += xx;
                            lasty += yy;
                            lastz += zz;
                            findBlocks(world, x, y, z, block);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean harvestBlock(World world, EntityPlayer player, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);

        if (block.getBlockHardness(world, x, y, z) < 0.0F)
        {
            return false;
        }

        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (metadata << 12));

        boolean flag = false;

        if (player.capabilities.isCreativeMode)
        {
            flag = removeBlock(world, x, y, z, player);
        }
        else
        {
            boolean flag1 = false;

            if (block != null)
            {
                flag1 = block.canHarvestBlock(player, metadata);
            }
            flag = removeBlock(world, x, y, z, player);

            if (flag && flag1)
            {
                block.harvestBlock(world, player, x, y, z, metadata);
            }
        }
        return true;
    }

    private static boolean removeBlock(World world, int par1, int par2, int par3, EntityPlayer player)
    {
        Block block = world.getBlock(par1, par2, par3);
        int metadata = world.getBlockMetadata(par1, par2, par3);

        if (block != null)
        {
            block.onBlockHarvested(world, par1, par2, par3, metadata, player);
        }

        boolean flag = (block != null) && (block.removedByPlayer(world, player, par1, par2, par3));

        if (block != null && flag)
        {
            block.onBlockDestroyedByPlayer(world, par1, par2, par3, metadata);
        }
        return flag;
    }

    /*
     *  @author mDiyo
     */

    public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;

        if (!world.isRemote && player instanceof EntityPlayer)
        {
            d1 += 1.62D;
        }

        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;

        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }

        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, par3);
    }
}
