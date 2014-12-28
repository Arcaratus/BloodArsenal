package com.arc.bloodarsenal;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.PacketHandler;
import baubles.common.network.PacketSyncBauble;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.items.bauble.SacrificeAmulet;
import com.arc.bloodarsenal.items.bauble.SelfSacrificeAmulet;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
        float damageDone = event.ammount;

        if (entityAttacking != null && entityAttacking instanceof EntityLivingBase)
        {
            if (((EntityLivingBase) entityAttacking).isPotionActive(BloodArsenal.vampiricAura))
            {
                float drainedHealth = damageDone / 4;

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

        if (entityAttacked instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityAttacked;
            InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);

                if (stack != null && stack.getItem() == ModItems.self_sacrifice_amulet)
                {
                    SelfSacrificeAmulet selfSacrificeAmulet = (SelfSacrificeAmulet) ModItems.self_sacrifice_amulet;
                    int lpReceived = (int) damageDone;
                    boolean shouldExecute = selfSacrificeAmulet.getStoredLP(stack) < 10000;

                    if (shouldExecute)
                    {
                        PotionEffect regenEffect = player.getActivePotionEffect(Potion.regeneration);

                        if (regenEffect != null && regenEffect.getAmplifier() >= 2)
                        {
                            selfSacrificeAmulet.setStoredLP(stack, Math.min(selfSacrificeAmulet.getStoredLP(stack) + lpReceived / 2, 10000));
                        }
                        else
                        {
                            selfSacrificeAmulet.setStoredLP(stack, Math.min(selfSacrificeAmulet.getStoredLP(stack) + lpReceived, 10000));
                        }
                    }

                    if (player instanceof EntityPlayerMP)
                    {
                        PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, i), (EntityPlayerMP) player);
                    }
                }
            }
        }
    }

    public void こんちは(EntityPlayer player)
    {
        player.addChatMessage(new ChatComponentText("こんにちは！"));
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        Entity killer = event.source.getEntity();
        EntityLivingBase victim = event.entityLiving;

        if (killer instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) killer;
            InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);

                if (stack != null && stack.getItem() == ModItems.sacrifice_amulet)
                {
                    SacrificeAmulet sacrificeAmulet = (SacrificeAmulet) ModItems.sacrifice_amulet;
                    float victimHealth = victim.getMaxHealth();
                    boolean healthGood = victimHealth > 4.0F;
                    int lpReceived = healthGood ? 200 : 50;
                    boolean shouldExecute = sacrificeAmulet.getStoredLP(stack) < 10000;

                    if (shouldExecute)
                    {
                        sacrificeAmulet.setStoredLP(stack, Math.min(sacrificeAmulet.getStoredLP(stack) + lpReceived, 10000));
                    }

                    if (player instanceof EntityPlayerMP)
                    {
                        PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, i), (EntityPlayerMP) player);
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
