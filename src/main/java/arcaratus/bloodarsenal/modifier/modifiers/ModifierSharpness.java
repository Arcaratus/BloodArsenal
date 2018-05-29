package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import static arcaratus.bloodarsenal.registry.Constants.Misc.ATTACK_DAMAGE_MODIFIER;

public class ModifierSharpness extends Modifier
{
    public ModifierSharpness()
    {
        super(Constants.Modifiers.SHARPNESS, Constants.Modifiers.SHARPNESS_COUNTER.length, EnumModifierType.HEAD);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(level);

        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6 + 2 * Math.pow((level + 1), 1.75), 0));

        return multimap;
    }
}
