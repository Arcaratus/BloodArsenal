package com.arc.bloodarsenal.entity;

import com.arc.bloodarsenal.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
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
        if (event.source.getDamageType().equals("player"))
        {
            PotionEffect effect = event.entityLiving.getActivePotionEffect(Potion.weakness);
            rand = Math.random();

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
    }
}
