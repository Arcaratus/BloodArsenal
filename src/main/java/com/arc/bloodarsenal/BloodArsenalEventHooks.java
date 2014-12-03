package com.arc.bloodarsenal;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class BloodArsenalEventHooks
{
    public static double rand = Math.random();

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

        if (entityAttacked instanceof EntityPlayer)
        {
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
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving.isPotionActive(BloodArsenal.bleeding))
        {
            int amplifier = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getAmplifier();
            int duration = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getDuration();
            int damage = ((int)(rand * rand) * (amplifier + 1) + 3);

            if (entityLiving.worldObj.getWorldTime() % (20 / (amplifier + 1)) == 0)
            {
                entityLiving.attackEntityFrom(BloodArsenal.deathFromBlood, damage);
                entityLiving.addPotionEffect(new PotionEffect(Potion.blindness.id, duration, 1));
                entityLiving.hurtResistantTime = Math.min(entityLiving.hurtResistantTime, 20 / (amplifier + 1));
            }
        }

        if (entityLiving instanceof EntityPlayer)
        {
            if (entityLiving.isPotionActive(BloodArsenal.swimming))
            {
                if (entityLiving.isInWater() || entityLiving.isInsideOfMaterial(Material.lava))
                {
                    entityLiving.setAir(300);

                    entityLiving.motionX *= 1.2D;
                    if (entityLiving.motionY > 0.0D)
                    {
                        entityLiving.motionY *= 1.2D;
                    }
                    entityLiving.motionZ *= 1.2D;
                    double maxSpeed = 0.2D;
                    if (entityLiving.motionX > maxSpeed)
                    {
                        entityLiving.motionX = maxSpeed;
                    }
                    else if (entityLiving.motionX < -maxSpeed)
                    {
                        entityLiving.motionX = -maxSpeed;
                    }
                    if (entityLiving.motionY > maxSpeed)
                    {
                        entityLiving.motionY = maxSpeed;
                    }
                    if (entityLiving.motionZ > maxSpeed)
                    {
                        entityLiving.motionZ = maxSpeed;
                    }
                    else if (entityLiving.motionZ < -maxSpeed)
                    {
                        entityLiving.motionZ = -maxSpeed;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving instanceof EntityPlayer)
        {
            if (entityLiving.isPotionActive(BloodArsenal.swimming))
            {
                if (entityLiving.isInWater() || entityLiving.isInsideOfMaterial(Material.lava))
                {
                    event.newSpeed = 1.75F;
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equals("BloodArsenal"))
        {
            BloodArsenalConfig.syncConfig();
            BloodArsenal.logger.info("Refreshing configuration file");
        }
    }
}
