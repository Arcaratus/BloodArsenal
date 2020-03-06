package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;

public class ModifierCritStriker extends Modifier
{
    public ModifierCritStriker()
    {
        super(Constants.Modifiers.CRIT_STRIKER, Constants.Modifiers.CRIT_STRIKER_COUNTER.length, EnumModifierType.HEAD);
    }


}
