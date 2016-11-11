package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.EnumModifierType;
import arc.bloodarsenal.modifier.Modifier;
import arc.bloodarsenal.registry.Constants;

public class ModifierXP extends Modifier
{
    public ModifierXP(int level)
    {
        super(Constants.Modifiers.XPERIENCED, Constants.Modifiers.XPERIENCED_COUNTER.length, level, EnumModifierType.CORE);
    }
}
