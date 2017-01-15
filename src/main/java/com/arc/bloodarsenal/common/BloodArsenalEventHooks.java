package com.arc.bloodarsenal.common;

import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import com.arc.bloodarsenal.common.items.ModItems;
import com.arc.bloodarsenal.common.items.armor.GlassArmor;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.common.util.FakePlayer;

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

                if (entityAttacked instanceof EntityPlayerMP && !(entityAttacked instanceof FakePlayer))
                {
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
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.entityLiving;

        if (entityLiving != null)
        {
        	if (entityLiving instanceof FakePlayer)
        		return;
        		
            if (entityLiving.isPotionActive(BloodArsenal.bleeding))
            {
                int amplifier = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getAmplifier();
                int duration = entityLiving.getActivePotionEffect(BloodArsenal.bleeding).getDuration();
                int damage = (rand.nextInt(2) * (amplifier + 1) + rand.nextInt(2));

                entityLiving.addPotionEffect(new PotionEffect(Potion.blindness.id, duration, 1));
                if (entityLiving.worldObj.getWorldTime() % (60 / (amplifier + 1)) == 0)
                {
                    entityLiving.attackEntityFrom(BloodArsenal.deathFromBlood, damage);
                    entityLiving.hurtResistantTime = Math.min(entityLiving.hurtResistantTime, 60 / (amplifier + 1));
                    entityLiving.onEntityUpdate();
                }
            }

            if (entityLiving.isPotionActive(BloodArsenal.soulBurn))
            {
                int amplifier = entityLiving.getActivePotionEffect(BloodArsenal.soulBurn).getAmplifier();
                entityLiving.setFire(2);

                if (entityLiving instanceof EntityPlayer)
                {
                    if (entityLiving.worldObj.getWorldTime() % (10 / (amplifier + 1)) == 0)
                    {
                        String owner = entityLiving.getCommandSenderName();
                        World worldSave = MinecraftServer.getServer().worldServers[0];
                        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);

                        if (data == null)
                        {
                            data = new LifeEssenceNetwork(owner);
                            worldSave.setItemData(owner, data);
                        }

                        int lpToMinus = 1000 * (2 ^ (amplifier + 1));

                        SoulNetworkHandler.syphonAndDamageFromNetwork(owner, (EntityPlayer) entityLiving, lpToMinus);
                    }
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
            if (entityLiving instanceof EntityPlayer && !(entityLiving instanceof FakePlayer))
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
        if (GameRegistry.findItem(BloodArsenal.MODID, "glass_shard") != null)
        {
            Block block = event.block;
            EntityPlayer player = event.harvester;
            Random random = new Random();

            if (player != null && player instanceof EntityPlayerMP && !(player instanceof FakePlayer))
            {
                if (!event.world.isRemote && !event.isSilkTouching)
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
                            if (BloodArsenalConfig.isGlassDangerous && random.nextInt(5) > 3)
                            {
                                player.addPotionEffect(new PotionEffect(BloodArsenal.bleeding.id, 4 * 20, random.nextInt(2)));
                            }
                        }
                        else
                        {
                            if (BloodArsenalConfig.isGlassDangerous && random.nextInt(3) == 2)
                            {
                                player.addPotionEffect(new PotionEffect(BloodArsenal.bleeding.id, 8 * 20, random.nextInt(3)));
                            }
                        }
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
}
