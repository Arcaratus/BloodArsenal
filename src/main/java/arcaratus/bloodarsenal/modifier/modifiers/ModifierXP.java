package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;

public class ModifierXP extends Modifier
{
    public ModifierXP(int level)
    {
        super(Constants.Modifiers.XPERIENCED, Constants.Modifiers.XPERIENCED_COUNTER.length, level, EnumModifierType.CORE);
    }
}
