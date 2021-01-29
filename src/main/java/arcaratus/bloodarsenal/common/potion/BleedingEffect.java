package arcaratus.bloodarsenal.common.potion;

import arcaratus.bloodarsenal.common.util.DamageSourceBleeding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import wayoftime.bloodmagic.util.helper.PlayerSacrificeHelper;

import javax.annotation.Nonnull;
import java.util.Random;

public class BleedingEffect extends Effect
{
    private static Random random = new Random();

    public BleedingEffect()
    {
        super(EffectType.HARMFUL, 0xFF0000);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity living, int amplifier)
    {
        int damage = (random.nextInt(amplifier + 1) * 2 + random.nextInt(2));

        living.attackEntityFrom(DamageSourceBleeding.INSTANCE, damage);
        living.hurtResistantTime = Math.min(living.hurtResistantTime, 30 / (amplifier + 1));

        PlayerSacrificeHelper.findAndFillAltar(living.getEntityWorld(), living, damage * random.nextInt(101), false);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int i = 30 >> amplifier;
        if (i > 0)
        {
            return duration % i == 0;
        }
        else
        {
            return true;
        }
    }
}
