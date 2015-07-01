package com.arc.bloodarsenal.common.entity;

import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class ModLivingDropsEvent
{
    public static double rand;

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event)
    {
        rand = Math.random();

        if (event.source.getDamageType().equals("player"))
        {
            PotionEffect effect = event.entityLiving.getActivePotionEffect(Potion.weakness);

            if (event.entityLiving instanceof EntityZombie)
            {
                if (effect != null)
                {
                    if (rand < 0.01F)
                    {
                        event.entityLiving.dropItem(ModItems.heart, 1);
                    }
                }
            }

            if (event.entityLiving instanceof EntityVillager)
            {
                if (effect != null)
                {
                    if (rand < 0.25F)
                    {
                        event.entityLiving.dropItem(ModItems.heart, 1);
                    }
                }
            }

            if (event.entityLiving instanceof EntityPlayer)
            {
                if (effect != null)
                {
                    event.entityLiving.dropItem(ModItems.heart, 1);
                }
            }
        }

        if (event.entityLiving instanceof EntityWolf)
        {
            if (rand < 0.4F)
            {
                event.entityLiving.dropItem(ModItems.wolf_hide, 1);
            }
        }
    }
}
