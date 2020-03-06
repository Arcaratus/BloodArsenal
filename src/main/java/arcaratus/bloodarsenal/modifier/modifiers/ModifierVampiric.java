package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;

// Function handled in TrackerHandler
public class ModifierVampiric extends Modifier
{
    public ModifierVampiric()
    {
        super(Constants.Modifiers.VAMPIRIC, Constants.Modifiers.VAMPIRIC_COUNTER.length, EnumModifierType.HEAD);
    }
}