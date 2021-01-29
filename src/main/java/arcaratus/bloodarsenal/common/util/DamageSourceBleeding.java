package arcaratus.bloodarsenal.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DamageSourceBleeding extends DamageSource
{
    public static final DamageSourceBleeding INSTANCE = new DamageSourceBleeding();

    public DamageSourceBleeding()
    {
        super("bleeding");

        setDamageBypassesArmor();
        setDamageIsAbsolute();
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity livingEntity)
    {
        return new TranslationTextComponent("chat.bloodarsenal.damage_source_bleeding", livingEntity.getName());
    }
}
