package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ModifierFlame extends Modifier
{
    public ModifierFlame(int level)
    {
        super(Constants.Modifiers.FLAME, Constants.Modifiers.FLAME_COUNTER.length, level, EnumModifierType.HEAD);
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker)
    {
        target.attackEntityFrom(DamageSource.ON_FIRE, (getLevel() + 1));
        target.setFire((getLevel() + 1) * 2);
    }
}
