package com.arc.bloodarsenal;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class BloodArsenalEventHooks
{
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
    }

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equals(BloodArsenal.MODID))
		{
			BloodArsenalConfig.syncConfig();
			BloodArsenal.logger.info("Refreshing configuration file.");
		}
	}
}
