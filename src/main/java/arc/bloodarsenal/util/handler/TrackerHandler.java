package arc.bloodarsenal.util.handler;

import WayofTime.bloodmagic.util.Utils;
import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.modifier.modifiers.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class TrackerHandler
{
    private static Random rand = new Random();

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        EntityLivingBase attackedEntity = event.getEntityLiving();

        if (attackedEntity.getAttackingEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) attackedEntity.getAttackingEntity();
            ItemStack modifiableStack = player.getHeldItemMainhand();

            if (modifiableStack != null && modifiableStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getStasisModifiable(modifiableStack);

                if (modifiable != null)
                {
                    float amount = Math.min(Utils.getModifiedDamage(attackedEntity, event.getSource(), event.getAmount()), attackedEntity.getHealth());

                    if (modifiable.hasModifier(ModifierFlame.class) && source.isFireDamage())
                    {
                        ModifierTracker.incrementCounter(modifiable, ModifierFlame.class, amount);
                    }

                    float reducedAmount = amount / 4;

                    if (modifiable.hasModifier(ModifierBloodlust.class))
                        ModifierTracker.incrementCounter(modifiable, ModifierBloodlust.class, reducedAmount);
                    else if (modifiable.hasModifier(ModifierSharpness.class))
                        ModifierTracker.incrementCounter(modifiable, ModifierSharpness.class, reducedAmount);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        EntityLivingBase attackedEntity = event.getEntityLiving();

        if (event.getLootingLevel() >= 1)
        {
            if (attackedEntity.getAttackingEntity() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) attackedEntity.getAttackingEntity();
                ItemStack modifiableStack = player.getHeldItemMainhand();

                if (modifiableStack != null && modifiableStack.getItem() instanceof IModifiableItem)
                {
                    StasisModifiable modifiable = StasisModifiable.getStasisModifiable(modifiableStack);

                    if (modifiable != null && modifiable.hasModifier(ModifierLooting.class))
                    {
                        ModifierTracker.incrementCounter(modifiable, ModifierLooting.class, 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingXPDrop(LivingExperienceDropEvent event)
    {
        if (event.getAttackingPlayer() != null)
        {
            EntityPlayer player = event.getAttackingPlayer();
            ItemStack itemStack = player.getHeldItemMainhand();
            if (itemStack != null && itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);

                if (modifiable != null && modifiable.hasModifier(ModifierXP.class))
                {
                    ModifierXP modifier = (ModifierXP) modifiable.getModifier(ModifierXP.class);
                    event.setDroppedExperience(event.getOriginalExperience() * (rand.nextInt(modifier.getLevel() + 2) + 1));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onXPPickup(PlayerPickupXpEvent event)
    {
        if (event.getEntityPlayer() != null)
        {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack itemStack = player.getHeldItemMainhand();
            if (itemStack != null && itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);

                if (modifiable != null && modifiable.hasModifier(ModifierXP.class))
                {
                    ModifierTracker.incrementCounter(modifiable, ModifierXP.class, event.getOrb().getXpValue());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getPlayer() != null)
        {
            EntityPlayer player = event.getPlayer();
            ItemStack itemStack = player.getHeldItemMainhand();
            if (itemStack != null && itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);

                if (modifiable != null)
                {
                    if (modifiable.hasModifier(ModifierXP.class) && event.getExpToDrop() > 0)
                    {
                        ModifierXP modifier = (ModifierXP) modifiable.getModifier(ModifierXP.class);
                        event.setExpToDrop(event.getExpToDrop() * (rand.nextInt(modifier.getLevel() + 2) + 1));
                    }
                }
            }
        }
    }
}
