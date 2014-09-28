package com.arc.bloodarsenal.entity;

import com.arc.bloodarsenal.item.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class ModLivingDropsEvent
{
    public static double rand;

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event)
    {
        if (event.source.getDamageType().equals("player"))
        {
            rand = Math.random();

            if (event.entityLiving instanceof EntityZombie)
            {
                if (rand < 0.01F)
                {
                    event.entityLiving.dropItem(ModItems.heart, 1);
                }
            }
            if (event.entityLiving instanceof EntityPlayer)
            {
                event.entityLiving.dropItem(ModItems.heart, 1);
            }
        }
    }
}
