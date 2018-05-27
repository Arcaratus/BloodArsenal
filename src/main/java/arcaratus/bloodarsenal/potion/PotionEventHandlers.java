package arcaratus.bloodarsenal.potion;

import WayofTime.bloodmagic.util.helper.PlayerSacrificeHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public class PotionEventHandlers
{
    private Random rand = new Random();

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLiving = event.getEntityLiving();

        if (entityLiving.isPotionActive(RegistrarBloodArsenal.BLEEDING))
        {
            int amplifier = entityLiving.getActivePotionEffect(RegistrarBloodArsenal.BLEEDING).getAmplifier();
            int damage = (rand.nextInt(amplifier + 1) * 2 + rand.nextInt(2));

            if (entityLiving.getEntityWorld().getWorldTime() % (30 / (amplifier + 1)) == 0)
            {
                entityLiving.attackEntityFrom(BloodArsenal.getDamageSourceBleeding(), damage);
                entityLiving.hurtResistantTime = Math.min(entityLiving.hurtResistantTime, 30 / (amplifier + 1));
                entityLiving.onEntityUpdate();

                PlayerSacrificeHelper.findAndFillAltar(entityLiving.getEntityWorld(), entityLiving, damage * rand.nextInt(101), false);
            }
        }
    }
}
