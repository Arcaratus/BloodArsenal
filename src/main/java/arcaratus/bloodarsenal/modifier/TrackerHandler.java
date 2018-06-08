package arcaratus.bloodarsenal.modifier;

import WayofTime.bloodmagic.util.Utils;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.registry.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public class TrackerHandler
{
    private static Random rand = new Random();

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getImmediateSource();
        EntityLivingBase attackedEntity = event.getEntityLiving();

        if (attackedEntity.getAttackingEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) attackedEntity.getAttackingEntity();
            ItemStack modifiableStack = player.getHeldItemMainhand();

            if (!modifiableStack.isEmpty() && modifiableStack.getItem() instanceof IModifiableItem)
            {
                NewModifiable modifiable = NewModifiable.getModifiableFromStack(modifiableStack);
                float amount = Math.min(Utils.getModifiedDamage(attackedEntity, event.getSource(), event.getAmount()), attackedEntity.getHealth());

                if (modifiable.hasModifier(Constants.Modifiers.FLAME) && source.isFireDamage())
                    NewModifiable.incrementModifierTracker(modifiableStack, Constants.Modifiers.FLAME);

                float reducedAmount = amount / 4;

                if (modifiable.hasModifier(Constants.Modifiers.BLOODLUST))
                    NewModifiable.incrementModifierTracker(modifiableStack, Constants.Modifiers.BLOODLUST, reducedAmount);
                else if (modifiable.hasModifier(Constants.Modifiers.SHARPNESS))
                    NewModifiable.incrementModifierTracker(modifiableStack, Constants.Modifiers.SHARPNESS, reducedAmount);
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

                if (!modifiableStack.isEmpty() && modifiableStack.getItem() instanceof IModifiableItem)
                {
                    NewModifiable modifiable = NewModifiable.getModifiableFromStack(modifiableStack);

                    if (modifiable.hasModifier(Constants.Modifiers.LOOTING))
                        NewModifiable.incrementModifierTracker(modifiableStack, Constants.Modifiers.LOOTING);
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
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IModifiableItem)
            {
                NewModifiable modifiable = NewModifiable.getModifiableFromStack(itemStack);

                if (modifiable.hasModifier(Constants.Modifiers.XPERIENCED))
                {
                    ModifierTracker xpTracker = modifiable.getTrackerForModifier(Constants.Modifiers.XPERIENCED);
                    event.setDroppedExperience(event.getOriginalExperience() * (rand.nextInt(xpTracker.getLevel() + 2) + 1));
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
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IModifiableItem)
            {
                NewModifiable modifiable = NewModifiable.getModifiableFromStack(itemStack);

                if (modifiable.hasModifier(Constants.Modifiers.XPERIENCED))
                {
                    NewModifiable.incrementModifierTracker(itemStack, Constants.Modifiers.XPERIENCED, (double) event.getOrb().getXpValue() / 4D);
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
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IModifiableItem)
            {
                NewModifiable modifiable = NewModifiable.getModifiableFromStack(itemStack);

                if (modifiable.hasModifier(Constants.Modifiers.XPERIENCED) && event.getExpToDrop() > 0)
                {
                    ModifierTracker xpTracker = modifiable.getTrackerForModifier(Constants.Modifiers.XPERIENCED);
                    event.setExpToDrop(event.getExpToDrop() * (rand.nextInt(xpTracker.getLevel() + 2) + 1));
                }
            }
        }
    }
}
