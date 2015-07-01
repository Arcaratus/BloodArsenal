package com.arc.bloodarsenal.common;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.PacketHandler;
import baubles.common.network.PacketSyncBauble;
import com.arc.bloodarsenal.common.items.ModItems;
import com.arc.bloodarsenal.common.items.armor.GlassArmor;
import com.arc.bloodarsenal.common.items.bauble.EmpoweredSacrificeAmulet;
import com.arc.bloodarsenal.common.items.bauble.EmpoweredSelfSacrificeAmulet;
import com.arc.bloodarsenal.common.items.bauble.SacrificeAmulet;
import com.arc.bloodarsenal.common.items.bauble.SelfSacrificeAmulet;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

public class BloodArsenalEventHooks
{
    private Random rand = new Random();

    @SubscribeEvent
    public void onEntityDamaged(LivingAttackEvent event)
    {
        EntityLivingBase entityAttacked = event.entityLiving;
        Entity entityAttacking = event.source.getSourceOfDamage();
        float damageDone = event.ammount;

        if (entityAttacking != null && entityAttacked != null)
        {
            if (entityAttacking instanceof EntityLivingBase)
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

                if (BloodArsenalConfig.baublesIntegration && Loader.isModLoaded("Baubles"))
                {
                    selfSacrificeHandler(event);
                }

                EntityPlayerMP player = (EntityPlayerMP) entityAttacked;

                if (player.inventory.armorInventory != null)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        ItemStack armorStack = player.inventory.armorInventory[3 - i];

                        if (armorStack != null && armorStack.getItem() instanceof GlassArmor)
                        {
                            if (entityAttacking instanceof EntityLivingBase)
                            {
                                ((EntityLivingBase) entityAttacking).addPotionEffect(new PotionEffect(BloodArsenalConfig.bleedingID, rand.nextInt(3) * 20, rand.nextInt(1)));
                            }
                        }
                    }
                }
            }
        }
    }

    public void こんにちは(EntityPlayer player)
    {
        player.addChatMessage(new ChatComponentText("こんにちは！"));
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        Entity killer = event.source.getEntity();

        if (killer != null && killer instanceof EntityPlayer && BloodArsenalConfig.baublesIntegration && Loader.isModLoaded("Baubles"))
        {
            sacrificeHandler(event);
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving != null)
        {
            if (entityLiving.isPotionActive(BloodArsenal.bleeding))
            {
                int amplifier = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getAmplifier();
                int duration = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getDuration();
                int damage = (rand.nextInt(2) * (amplifier + 1) + rand.nextInt(3));

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
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving != null)
        {
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
    }

    @SubscribeEvent
    public void harvestEvent(BlockEvent.HarvestDropsEvent event)
    {
        Block block = event.block;
        EntityPlayer player = event.harvester;
        Random random = new Random();

        if (!event.world.isRemote && !event.isSilkTouching)
        {
            if (player != null)
            {
                if (block != null && block instanceof BlockGlass)
                {
                    if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint)
                    {
                        event.drops.add(new ItemStack(ModItems.glass_shard));
                        if (random.nextInt() + 5 < 8)
                        {
                            event.drops.add(new ItemStack(ModItems.glass_shard));
                        }
                        event.dropChance = 0.25F;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equals(BloodArsenal.MODID))
        {
            BloodArsenalConfig.syncConfig();
            BloodArsenal.logger.info("Refreshing configuration file");
        }
    }

    @Optional.Method(modid = "Baubles")
    private void sacrificeHandler(LivingDeathEvent event)
    {
        Entity killer = event.source.getEntity();
        EntityLivingBase victim = event.entityLiving;

        EntityPlayer player = (EntityPlayer) killer;
        InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack != null)
            {
                if (stack.getItem() == ModItems.sacrifice_amulet)
                {
                    SacrificeAmulet sacrificeAmulet = (SacrificeAmulet) ModItems.sacrifice_amulet;
                    float victimHealth = victim.getMaxHealth();
                    boolean healthGood = victimHealth > 4.0F;
                    int lpReceived = healthGood ? 200 : 50;
                    boolean shouldExecute = sacrificeAmulet.getStoredLP(stack) < 10000;

                    if (shouldExecute)
                    {
                        sacrificeAmulet.setStoredLP(stack, Math.min(sacrificeAmulet.getStoredLP(stack) + (lpReceived * 2), 10000));
                    }
                }

                if (stack.getItem() == ModItems.empowered_sacrifice_amulet)
                {
                    EmpoweredSacrificeAmulet sacrificeAmulet = (EmpoweredSacrificeAmulet) ModItems.empowered_sacrifice_amulet;
                    float victimHealth = victim.getMaxHealth();
                    boolean healthGood = victimHealth > 4.0F;
                    int lpReceived = healthGood ? 200 : 50;
                    boolean shouldExecute = sacrificeAmulet.getStoredLP(stack) < 50000;

                    if (shouldExecute)
                    {
                        sacrificeAmulet.setStoredLP(stack, Math.min(sacrificeAmulet.getStoredLP(stack) + (lpReceived * 5), 50000));
                    }
                }

                if (player instanceof EntityPlayerMP)
                {
                    PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, i), (EntityPlayerMP) player);
                }
            }
        }
    }

    @Optional.Method(modid = "Baubles")
    private void selfSacrificeHandler(LivingAttackEvent event)
    {
        EntityLivingBase entityAttacked = event.entityLiving;
        float damageDone = event.ammount;

        EntityPlayer player = (EntityPlayer) entityAttacked;
        InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack != null)
            {
                if (stack.getItem() == ModItems.self_sacrifice_amulet)
                {
                    SelfSacrificeAmulet selfSacrificeAmulet = (SelfSacrificeAmulet) ModItems.self_sacrifice_amulet;
                    int lpReceived = (int) damageDone;
                    boolean shouldExecute = selfSacrificeAmulet.getStoredLP(stack) < 10000;

                    if (shouldExecute)
                    {
                        PotionEffect regenEffect = player.getActivePotionEffect(Potion.regeneration);

                        if (regenEffect != null && regenEffect.getAmplifier() >= 2)
                        {
                            selfSacrificeAmulet.setStoredLP(stack, Math.min(selfSacrificeAmulet.getStoredLP(stack) + (lpReceived * 2) / (regenEffect.getAmplifier() + 1), 10000));
                        }
                        else
                        {
                            selfSacrificeAmulet.setStoredLP(stack, Math.min(selfSacrificeAmulet.getStoredLP(stack) + (lpReceived * 2), 10000));
                        }
                    }
                }

                if (stack.getItem() == ModItems.empowered_self_sacrifice_amulet)
                {
                    EmpoweredSelfSacrificeAmulet selfSacrificeAmulet = (EmpoweredSelfSacrificeAmulet) ModItems.empowered_self_sacrifice_amulet;
                    int lpReceived = (int) damageDone;
                    boolean shouldExecute = selfSacrificeAmulet.getStoredLP(stack) < 50000;

                    if (shouldExecute)
                    {
                        PotionEffect regenEffect = player.getActivePotionEffect(Potion.regeneration);

                        if (regenEffect != null && regenEffect.getAmplifier() >= 2)
                        {
                            selfSacrificeAmulet.setStoredLP(stack, Math.min(selfSacrificeAmulet.getStoredLP(stack) + (lpReceived * 5) / (regenEffect.getAmplifier() + 1), 50000));
                        }
                        else
                        {
                            selfSacrificeAmulet.setStoredLP(stack, Math.min(selfSacrificeAmulet.getStoredLP(stack) + (lpReceived * 5), 50000));
                        }
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
