package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;

public class ModifierShadowTool extends Modifier
{
    public ModifierShadowTool(int level)
    {
        super(Constants.Modifiers.SHADOW_TOOL, Constants.Modifiers.SHADOW_TOOL_COUNTER.length, level, EnumModifierType.HANDLE);
    }
}
