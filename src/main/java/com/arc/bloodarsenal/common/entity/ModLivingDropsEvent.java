package com.arc.bloodarsenal.common.entity;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public class ModLivingDropsEvent
{
    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event)
    {
        Random random = new Random();

        if (GameRegistry.findItem(BloodArsenal.MODID, "heart") != null)
        {
            if (event.source.getDamageType().equals("player"))
            {
                PotionEffect effect = event.entityLiving.getActivePotionEffect(Potion.weakness);

                if (event.entityLiving instanceof EntityZombie)
                {
                    if (effect != null)
                    {
                        if (random.nextFloat() < 0.01F)
                        {
                            event.entityLiving.dropItem(ModItems.heart, 1);
                        }
                    }
                }

                if (event.entityLiving instanceof EntityVillager)
                {
                    if (effect != null)
                    {
                        if (random.nextFloat() < 0.25F)
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

        if (GameRegistry.findItem(BloodArsenal.MODID, "wolf_hide") != null)
        {
            if (event.entityLiving instanceof EntityWolf)
            {
                if (random.nextFloat() < 0.4F)
                {
                    event.entityLiving.dropItem(ModItems.wolf_hide, 1);
                }
            }
        }
    }
}
