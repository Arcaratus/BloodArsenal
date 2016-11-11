package arc.bloodarsenal.potion;

import WayofTime.bloodmagic.api.util.helper.PlayerSacrificeHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class PotionEventHandlers
{
    private Random rand = new Random();

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.getEntityLiving();

        if (entityLiving.isPotionActive(ModPotions.BLEEDING))
        {
            int amplifier = entityLiving.getActivePotionEffect(ModPotions.BLEEDING).getAmplifier();
            int damage = (rand.nextInt(3) * (amplifier + 1) + rand.nextInt(2));

//            entityLiving.addPotionEffect(new PotionEffect(Potion.getPotionById(), duration, 1));
            if (entityLiving.getEntityWorld().getWorldTime() % (36 / (amplifier + 1)) == 0)
            {
                entityLiving.attackEntityFrom(BloodArsenal.getDamageSourceBleeding(), damage);
                entityLiving.hurtResistantTime = Math.min(entityLiving.hurtResistantTime, 36 / (amplifier + 1));
                entityLiving.onEntityUpdate();

                PlayerSacrificeHelper.findAndFillAltar(entityLiving.getEntityWorld(), entityLiving, damage * rand.nextInt(101), false);
            }
        }
    }
}
