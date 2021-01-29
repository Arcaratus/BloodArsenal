package arcaratus.bloodarsenal.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DamageSourceGlass extends DamageSource
{
    public static final DamageSourceGlass INSTANCE = new DamageSourceGlass();

    public DamageSourceGlass()
    {
        super("glass");

        setDamageBypassesArmor();
        setDamageIsAbsolute();
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity livingEntity)
    {
        return new TranslationTextComponent("chat.bloodarsenal.damage_source_glass", livingEntity.getName());
    }
}
