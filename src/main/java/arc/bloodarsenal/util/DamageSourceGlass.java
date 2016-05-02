package arc.bloodarsenal.util;

import WayofTime.bloodmagic.util.helper.TextHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceGlass extends DamageSource
{
    public DamageSourceGlass()
    {
        super("glass");

        setDamageBypassesArmor();
        setDamageIsAbsolute();
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase livingBase)
    {
        return new TextComponentString(TextHelper.localizeEffect("chat.BloodArsenal.damageSourceGlass", livingBase.getName()));
    }
}
