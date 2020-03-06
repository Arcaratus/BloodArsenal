package arcaratus.bloodarsenal.modifier;

import WayofTime.bloodmagic.iface.IActivatable;
import WayofTime.bloodmagic.util.Utils;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.registry.BloodArsenalSounds;
import arcaratus.bloodarsenal.registry.ModModifiers;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
    public static void onEntityHurt(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getImmediateSource();
        EntityLivingBase attackedEntity = event.getEntityLiving();

        if (sourceEntity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) sourceEntity;
            ItemStack modifiableStack = player.getHeldItemMainhand();

            if (modifiableStack.getItem() instanceof IActivatable && ((IActivatable) modifiableStack.getItem()).getActivated(modifiableStack))
            {
                if (!modifiableStack.isEmpty() && modifiableStack.getItem() instanceof IModifiableItem)
                {
                    StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(modifiableStack);
                    float amount = Math.min(Utils.getModifiedDamage(attackedEntity, event.getSource(), event.getAmount()), attackedEntity.getHealth());

                    modifiable.checkAndIncrementTracker(modifiableStack, ModModifiers.MODIFIER_FLAME);

                    if (modifiable.hasModifier(ModModifiers.MODIFIER_CRIT_STRIKER))
                    {
                        int level = modifiable.getTrackerForModifier(ModModifiers.MODIFIER_CRIT_STRIKER).getLevel() + 1;
                        if (rand.nextInt(10) < level)
                        {
                            modifiable.incrementModifierTracker(modifiableStack, ModModifiers.MODIFIER_CRIT_STRIKER);
                            event.setAmount(amount * ((float) level / 2F));
                            BloodArsenalUtils.sendPlayerMessage(player, "chat.bloodarsenal.crit", true);
                            player.playSound(BloodArsenalSounds.SOUND_CRIT, 0.2F, 1);
                        }
                    }

                    float reducedAmount;

                    if (modifiable.hasModifier(ModModifiers.MODIFIER_VAMPIRIC))
                    {
                        reducedAmount = 1F + amount * (modifiable.getTrackerForModifier(ModModifiers.MODIFIER_VAMPIRIC).getLevel() + 1F) / 10F;
                        if (modifiable.checkAndIncrementTracker(modifiableStack, ModModifiers.MODIFIER_VAMPIRIC, reducedAmount))
                            player.heal(reducedAmount);
                    }

                    reducedAmount = amount / 4;

                    modifiable.checkAndIncrementTracker(modifiableStack, ModModifiers.MODIFIER_BLOODLUST, reducedAmount);
                    modifiable.checkAndIncrementTracker(modifiableStack, ModModifiers.MODIFIER_SHARPNESS, reducedAmount);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event)
    {
        EntityLivingBase attackedEntity = event.getEntityLiving();

        if (event.getLootingLevel() >= 1)
        {
            if (attackedEntity.getAttackingEntity() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) attackedEntity.getAttackingEntity();
                ItemStack modifiableStack = player.getHeldItemMainhand();

                if (!modifiableStack.isEmpty() && modifiableStack.getItem() instanceof IActivatable && ((IActivatable) modifiableStack.getItem()).getActivated(modifiableStack) && modifiableStack.getItem() instanceof IModifiableItem)
                {
                    StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(modifiableStack);
                    modifiable.checkAndIncrementTracker(modifiableStack, ModModifiers.MODIFIER_LOOTING);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingXPDrop(LivingExperienceDropEvent event)
    {
        if (event.getAttackingPlayer() != null)
        {
            EntityPlayer player = event.getAttackingPlayer();
            ItemStack itemStack = player.getHeldItemMainhand();
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IActivatable && ((IActivatable) itemStack.getItem()).getActivated(itemStack) && itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(itemStack);

                if (modifiable.hasModifier(ModModifiers.MODIFIER_XPERIENCED))
                {
                    ModifierTracker xpTracker = modifiable.getTrackerForModifier(ModModifiers.MODIFIER_XPERIENCED);
                    event.setDroppedExperience(event.getOriginalExperience() * (rand.nextInt(xpTracker.getLevel() + 2) + 1));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onXPPickup(PlayerPickupXpEvent event)
    {
        if (event.getEntityPlayer() != null)
        {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack itemStack = player.getHeldItemMainhand();
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IActivatable && ((IActivatable) itemStack.getItem()).getActivated(itemStack) &&  itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(itemStack);

                modifiable.checkAndIncrementTracker(itemStack, ModModifiers.MODIFIER_XPERIENCED, (double) event.getOrb().getXpValue() / 4D);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getPlayer() != null)
        {
            EntityPlayer player = event.getPlayer();
            ItemStack itemStack = player.getHeldItemMainhand();
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IActivatable && ((IActivatable) itemStack.getItem()).getActivated(itemStack) &&  itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(itemStack);

                if (modifiable.hasModifier(ModModifiers.MODIFIER_XPERIENCED) && event.getExpToDrop() > 0)
                {
                    ModifierTracker xpTracker = modifiable.getTrackerForModifier(ModModifiers.MODIFIER_XPERIENCED);
                    event.setExpToDrop(event.getExpToDrop() * (rand.nextInt(xpTracker.getLevel() + 2) + 1));
                }
            }
        }
    }
}
