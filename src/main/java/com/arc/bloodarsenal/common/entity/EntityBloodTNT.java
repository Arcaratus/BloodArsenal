package com.arc.bloodarsenal.common.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBloodTNT extends Entity
{
    public int fuse;
    private EntityLivingBase tntPlacedBy;
    
    public EntityBloodTNT(World par1World)
    {
        super(par1World);
        preventEntitySpawning = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
    }

    public EntityBloodTNT(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLivingBase)
    {
        this(par1World);
        setPosition(par2, par4, par6);
        float f = (float)(Math.random() * Math.PI * 2.0D);
        motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
        motionY = 0.20000000298023224D;
        motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
        fuse = 60;
        prevPosX = par2;
        prevPosY = par4;
        prevPosZ = par6;
        tntPlacedBy = par8EntityLivingBase;
    }

    @Override
    public void entityInit() {}

    @Override
    public boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.03999999910593033D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863D;
        motionY *= 0.9800000190734863D;
        motionZ *= 0.9800000190734863D;

        if (onGround)
        {
            motionY *= -0.5D;
        }

        if (fuse-- <= 0)
        {
            setDead();

            if (!worldObj.isRemote)
            {
                explode();
            }
        }
        else
        {
            worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode()
    {
        float f = 6.0F;
        worldObj.createExplosion(this, posX, posY, posZ, f, true);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1)
    {
        par1.setByte("Fuse", (byte) fuse);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1)
    {
        fuse = par1.getByte("Fuse");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    public EntityLivingBase getTntPlacedBy()
    {
        return tntPlacedBy;
    }
}
