package arcaratus.bloodarsenal.util;

import WayofTime.bloodmagic.util.helper.TextHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

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
    public ITextComponent getDeathMessage(EntityLivingBase livingBase)
    {
        return new TextComponentString(TextHelper.localizeEffect("chat.bloodarsenal.damage_source_glass", livingBase.getName()));
    }
}
