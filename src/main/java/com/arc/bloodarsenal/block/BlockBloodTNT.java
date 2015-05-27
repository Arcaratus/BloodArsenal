package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.entity.EntityBloodTNT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBloodTNT extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon top;
    @SideOnly(Side.CLIENT)
    private IIcon bottom;
    
    public BlockBloodTNT()
    {
        super(Material.tnt);
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 0 ? bottom : (par1 == 1 ? top : blockIcon);
    }

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);

        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5Block)
    {
        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
    {
        if (!par1World.isRemote)
        {
            EntityBloodTNT entitytntprimed = new EntityBloodTNT(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), par5Explosion.getExplosivePlacedBy());
            entitytntprimed.fuse = par1World.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            par1World.spawnEntityInWorld(entitytntprimed);
        }
    }

    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        spawnTNT(par1World, par2, par3, par4, par5, (EntityLivingBase) null);
    }

    public void spawnTNT(World par1World, int par2, int par3, int par4, int par5, EntityLivingBase par6EntityLivingBase)
    {
        if (!par1World.isRemote)
        {
            if ((par5 & 1) == 1)
            {
                EntityBloodTNT entitytntprimed = new EntityBloodTNT(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), par6EntityLivingBase);
                par1World.spawnEntityInWorld(entitytntprimed);
                par1World.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
            }
        }
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5, int par6, float par7, float par8, float par9)
    {
        if (par5.getCurrentEquippedItem() != null && par5.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
        {
            spawnTNT(par1World, par2, par3, par4, 1, par5);
            par1World.setBlockToAir(par2, par3, par4);
            par5.getCurrentEquippedItem().damageItem(1, par5);
            return true;
        }
        else
        {
            return super.onBlockActivated(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
        }
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity instanceof EntityArrow && !par1World.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow)par5Entity;

            if (entityarrow.isBurning())
            {
                spawnTNT(par1World, par2, par3, par4, 1, entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityarrow.shootingEntity : null);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }

    public boolean canDropFromExplosion(Explosion par1Explosion)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_tnt_side");
        top = iconRegister.registerIcon("BloodArsenal:blood_tnt_top");
        bottom = iconRegister.registerIcon("BloodArsenal:blood_tnt_bottom");
    }
}
