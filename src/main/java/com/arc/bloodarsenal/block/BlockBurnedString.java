package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockBurnedString extends Block
{
    public BlockBurnedString()
    {
        super(Material.circuits);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        setTickRandomly(true);
    }

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 10;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public int getRenderType()
    {
        return 30;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return ModItems.blood_burned_string;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        int l = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        boolean flag = (l & 4) == 4;
        boolean flag1 = (l & 2) == 2;

        if (!flag1)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!flag)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return ModItems.blood_burned_string;
    }

    @Override
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        int l = World.doesBlockHaveSolidTopSurface(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_) ? 0 : 2;
        p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, l, 3);
        this.func_150138_a(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, l);
    }

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        this.func_150138_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_ | 1);
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
    {
        if (!p_149681_1_.isRemote)
        {
            if (p_149681_6_.getCurrentEquippedItem() != null && p_149681_6_.getCurrentEquippedItem().getItem() == Items.shears)
            {
                p_149681_1_.setBlockMetadataWithNotify(p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_ | 8, 4);
            }
        }
    }

    private void func_150138_a(World p_150138_1_, int p_150138_2_, int p_150138_3_, int p_150138_4_, int p_150138_5_)
    {
        int i1 = 0;

        while (i1 < 2)
        {
            int j1 = 1;

            while (true)
            {
                if (j1 < 42)
                {
                    int k1 = p_150138_2_ + Direction.offsetX[i1] * j1;
                    int l1 = p_150138_4_ + Direction.offsetZ[i1] * j1;
                    Block block = p_150138_1_.getBlock(k1, p_150138_3_, l1);

                    if (block == Blocks.tripwire_hook)
                    {
                        int i2 = p_150138_1_.getBlockMetadata(k1, p_150138_3_, l1) & 3;

                        if (i2 == Direction.rotateOpposite[i1])
                        {
                            Blocks.tripwire_hook.func_150136_a(p_150138_1_, k1, p_150138_3_, l1, false, p_150138_1_.getBlockMetadata(k1, p_150138_3_, l1), true, j1, p_150138_5_);
                        }
                    }
                    else if (block == Blocks.tripwire)
                    {
                        ++j1;
                        continue;
                    }
                }

                ++i1;
                break;
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        if (!p_149670_1_.isRemote)
        {
            if ((p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_) & 1) != 1)
            {
                this.func_150140_e(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_);
            }

            p_149670_5_.setFire(1);
        }
    }

    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (!p_149674_1_.isRemote)
        {
            if ((p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_) & 1) == 1)
            {
                this.func_150140_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
            }
        }
    }

    private void func_150140_e(World p_150140_1_, int p_150140_2_, int p_150140_3_, int p_150140_4_)
    {
        int l = p_150140_1_.getBlockMetadata(p_150140_2_, p_150140_3_, p_150140_4_);
        boolean flag = (l & 1) == 1;
        boolean flag1 = false;
        List list = p_150140_1_.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getBoundingBox((double)p_150140_2_ + this.minX, (double)p_150140_3_ + this.minY, (double)p_150140_4_ + this.minZ, (double)p_150140_2_ + this.maxX, (double)p_150140_3_ + this.maxY, (double)p_150140_4_ + this.maxZ));

        if (!list.isEmpty())
        {
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                Entity entity = (Entity)iterator.next();

                if (!entity.doesEntityNotTriggerPressurePlate())
                {
                    flag1 = true;
                    break;
                }
            }
        }

        if (flag1 && !flag)
        {
            l |= 1;
        }

        if (!flag1 && flag)
        {
            l &= -2;
        }

        if (flag1 != flag)
        {
            p_150140_1_.setBlockMetadataWithNotify(p_150140_2_, p_150140_3_, p_150140_4_, l, 3);
            this.func_150138_a(p_150140_1_, p_150140_2_, p_150140_3_, p_150140_4_, l);
        }

        if (flag1)
        {
            p_150140_1_.scheduleBlockUpdate(p_150140_2_, p_150140_3_, p_150140_4_, this, this.tickRate(p_150140_1_));
        }
    }

    @SideOnly(Side.CLIENT)
    public static boolean func_150139_a(IBlockAccess p_150139_0_, int p_150139_1_, int p_150139_2_, int p_150139_3_, int p_150139_4_, int p_150139_5_)
    {
        int j1 = p_150139_1_ + Direction.offsetX[p_150139_5_];
        int k1 = p_150139_3_ + Direction.offsetZ[p_150139_5_];
        Block block = p_150139_0_.getBlock(j1, p_150139_2_, k1);
        boolean flag = (p_150139_4_ & 2) == 2;
        int l1;

        if (block == Blocks.tripwire_hook)
        {
            l1 = p_150139_0_.getBlockMetadata(j1, p_150139_2_, k1);
            int i2 = l1 & 3;
            return i2 == Direction.rotateOpposite[p_150139_5_];
        }
        else if (block == Blocks.tripwire)
        {
            l1 = p_150139_0_.getBlockMetadata(j1, p_150139_2_, k1);
            boolean flag1 = (l1 & 2) == 2;
            return flag == flag1;
        }
        else
        {
            return false;
        }
    }
}
