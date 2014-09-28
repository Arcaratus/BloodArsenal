package com.arc.bloodarsenal.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityTeleportationSigil extends EntityThrowable
{
    public EntityTeleportationSigil(World par1World)
    {
        super(par1World);
    }

    public EntityTeleportationSigil(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }

    @SideOnly(Side.CLIENT)
    public EntityTeleportationSigil(World par2World, double par2, double par4, double par6)
    {
        super(par2World, par2, par4, par6);
    }

    public void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        for (int i = 0; i < 32; ++i)
        {
            worldObj.spawnParticle("portal", posX, posY + rand.nextDouble() * 2.0D, posZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
        }

        if (!worldObj.isRemote)
        {
            if (getThrower() != null && getThrower() instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)getThrower();

                if (entityplayermp.playerNetServerHandler.func_147362_b().isChannelOpen() && entityplayermp.worldObj == worldObj)
                {
                    EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, posX, posY, posZ, 5.0F);
                    if (!MinecraftForge.EVENT_BUS.post(event))
                    {
                        if (getThrower().isRiding())
                        {
                            getThrower().mountEntity((Entity)null);
                        }

                        getThrower().setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
                        getThrower().fallDistance = 0.0F;
                    }
                }
            }
            setDead();
        }
    }
}
