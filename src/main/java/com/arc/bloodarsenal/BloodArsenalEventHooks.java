package com.arc.bloodarsenal;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class BloodArsenalEventHooks
{
    public final float speed = 0.025F;

    @SubscribeEvent
    public void onEntityDamaged(LivingAttackEvent event)
    {
        EntityLivingBase entityAttacked = event.entityLiving;
        Entity entityAttacking = event.source.getSourceOfDamage();

        if (entityAttacking != null && entityAttacking instanceof EntityLivingBase)
        {
            if (((EntityLivingBase) entityAttacking).isPotionActive(BloodArsenal.vampiricAura))
            {
                float damage = entityAttacked.getMaxHealth() - entityAttacked.getHealth();
                float drainedHealth = damage / 4;

                ((EntityLivingBase) entityAttacking).setHealth(((EntityLivingBase) entityAttacking).getHealth() + drainedHealth);
            }
        }

        if (entityAttacked.isPotionActive(BloodArsenal.swimming))
        {
            if (entityAttacked.isInsideOfMaterial(Material.lava))
            {
                if (event.source.isFireDamage())
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving.isPotionActive(BloodArsenal.bleeding))
        {
            int i = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getAmplifier();
            int j = i + 1;

//            if (entityLiving.worldObj.getWorldTime() % (20 / (i + 1)) == 0)
//            {
//                entityLiving.attackEntityFrom(BloodArsenal.deathFromBlood, 2);
//                entityLiving.hurtResistantTime = Math.min(entityLiving.hurtResistantTime, 20 / (i + 1));
//            }
        }

        if (entityLiving.isPotionActive(BloodArsenal.swimming))
        {
            if (entityLiving instanceof EntityPlayer)
            {
                EntityLivingBase player = (EntityPlayer) entityLiving;
                if (player.isInWater())
                {
                    player.motionX *= 1.2D;
                    if (player.motionY > 0.0D)
                    {
                        player.motionY *= 1.2D;
                    }
                    player.motionZ *= 1.2D;
                    double maxSpeed = 0.2D;
                    if (player.motionX > maxSpeed)
                    {
                        player.motionX = maxSpeed;
                    }
                    else if (player.motionX < -maxSpeed)
                    {
                        player.motionX = -maxSpeed;
                    }
                    if (player.motionY > maxSpeed)
                    {
                        player.motionY = maxSpeed;
                    }
                    if (player.motionZ > maxSpeed)
                    {
                        player.motionZ = maxSpeed;
                    }
                    else if (player.motionZ < -maxSpeed)
                    {
                        player.motionZ = -maxSpeed;
                    }
                }
            }

/*            if (entityLiving.isInsideOfMaterial(Material.lava))
            {
                if (entityLiving instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entityLiving;

                    entityLiving.extinguish();

                    if ((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F)
                    {
                        player.moveFlying(0F, 1F, player.capabilities.isFlying ? speed : speed);
                    }
                }
            }
*/        }
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving.isPotionActive(BloodArsenal.swimming))
        {
            if (entityLiving.isInsideOfMaterial(Material.water) || entityLiving.isInsideOfMaterial(Material.lava))
            {
                event.newSpeed = 1.75F;
            }
        }
    }
}
