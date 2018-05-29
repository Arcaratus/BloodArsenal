package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ModifierFlame extends Modifier
{
    public ModifierFlame()
    {
        super(Constants.Modifiers.FLAME, Constants.Modifiers.FLAME_COUNTER.length, EnumModifierType.HEAD);
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker, int level)
    {
        target.attackEntityFrom(DamageSource.ON_FIRE, (level + 1));
        target.setFire((level + 1) * 2);
    }
}
