package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.EnumModifierType;
import arc.bloodarsenal.modifier.Modifier;
import arc.bloodarsenal.registry.Constants;

public class ModifierShadowTool extends Modifier
{
    public ModifierShadowTool(int level)
    {
        super(Constants.Modifiers.SHADOW_TOOL, Constants.Modifiers.SHADOW_TOOL_COUNTER.length, level, EnumModifierType.HANDLE);
    }
}
