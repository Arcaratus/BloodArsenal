package com.arc.bloodarsenal.common.entity.projectile;

import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBloodBall extends EntityThrowable
{
    public EntityBloodBall(World world)
    {
        super(world);
    }

    public EntityBloodBall(World world, EntityLivingBase entity)
    {
        super(world, entity);
    }

    public EntityBloodBall(World world, double so, double me, double thing)
    {
        super(world, so, me, thing);
    }

    protected void onImpact(MovingObjectPosition object)
    {
        if (object.entityHit != null)
        {
            byte b0 = 1;

            object.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)b0);
            EntityLivingBase thrower = this.getThrower();

            if (thrower instanceof EntityPlayer)
            {
                String throwerName = thrower.getCommandSenderName();
                World worldSave = MinecraftServer.getServer().worldServers[0];
                LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, throwerName);

                if (data == null)
                {
                    data = new LifeEssenceNetwork(throwerName);
                    worldSave.setItemData(throwerName, data);
                }

                SoulNetworkHandler.addCurrentEssenceToMaximum(throwerName, 50, data.maxOrb);
            }
        }

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}
