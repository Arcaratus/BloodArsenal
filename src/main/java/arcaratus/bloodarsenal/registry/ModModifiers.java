package arcaratus.bloodarsenal.registry;

import arcaratus.bloodarsenal.Constants;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.modifier.modifiers.*;

public class ModModifiers
{
    public static final Modifier MODIFIER_BAD_POTION;
    public static final Modifier MODIFIER_BLOODLUST;
    public static final Modifier MODIFIER_FLAME;
    public static final Modifier MODIFIER_SHARPNESS;

    public static final Modifier MODIFIER_FORTUNATE;
    public static final Modifier MODIFIER_LOOTING;
    public static final Modifier MODIFIER_SILKY;
    public static final Modifier MODIFIER_SMELTING;
    public static final Modifier MODIFIER_XPERIENCED;

    public static final Modifier MODIFIER_BENEFICIAL_POTION;
    public static final Modifier MODIFIER_QUICK_DRAW;
    public static final Modifier MODIFIER_SHADOW_TOOL;

    public static final Modifier MODIFIER_AOD;
    public static final Modifier MODIFIER_SIGIL;

    static
    {
        MODIFIER_BAD_POTION = ModifierHandler.registerModifier(new ModifierBadPotion(0));
        MODIFIER_BLOODLUST = ModifierHandler.registerModifier(new ModifierBloodlust(0));
        MODIFIER_FLAME = ModifierHandler.registerModifier(new ModifierFlame(0));
        MODIFIER_SHARPNESS = ModifierHandler.registerModifier(new ModifierSharpness(0));

        MODIFIER_FORTUNATE = ModifierHandler.registerModifier(new ModifierFortunate(0));
        MODIFIER_LOOTING = ModifierHandler.registerModifier(new ModifierLooting(0));
        MODIFIER_SILKY = ModifierHandler.registerModifier(new ModifierSilky(0));
        MODIFIER_SMELTING = ModifierHandler.registerModifier(new ModifierSmelting(0));
        MODIFIER_XPERIENCED = ModifierHandler.registerModifier(new ModifierXP(0));

        MODIFIER_BENEFICIAL_POTION = ModifierHandler.registerModifier(new ModifierBeneficialPotion(0));
        MODIFIER_QUICK_DRAW = ModifierHandler.registerModifier(new ModifierQuickDraw(0));
        MODIFIER_SHADOW_TOOL = ModifierHandler.registerModifier(new ModifierShadowTool(0));

        MODIFIER_AOD = ModifierHandler.registerModifier(new ModifierAOD(0));
        MODIFIER_SIGIL = ModifierHandler.registerModifier(new ModifierSigil(0));
    }

    public static void init()
    {
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_BAD_POTION, Constants.Modifiers.BAD_POTION_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_BLOODLUST, Constants.Modifiers.BLOODLUST_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_FLAME, Constants.Modifiers.FLAME_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_SHARPNESS, Constants.Modifiers.SHARPNESS_COUNTER));

        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_FORTUNATE, Constants.Modifiers.FORTUNATE_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_LOOTING, Constants.Modifiers.LOOTING_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_SMELTING, Constants.Modifiers.SMELTING_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_XPERIENCED, Constants.Modifiers.XPERIENCED_COUNTER));

        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_BENEFICIAL_POTION, Constants.Modifiers.BENEFICIAL_POTION_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_QUICK_DRAW, Constants.Modifiers.QUICK_DRAW_COUNTER));
        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_SHADOW_TOOL, Constants.Modifiers.SHADOW_TOOL_COUNTER));

        ModifierHandler.registerTracker(ModifierTracker.newTracker(MODIFIER_AOD, Constants.Modifiers.AOD_COUNTER));

        // Incompatibilities registering
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.HEAD, MODIFIER_FLAME, MODIFIER_BAD_POTION);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.HEAD, MODIFIER_SHARPNESS, MODIFIER_BLOODLUST);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.CORE, MODIFIER_SILKY, MODIFIER_SMELTING);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.CORE, MODIFIER_SILKY, MODIFIER_FORTUNATE);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.HANDLE); // There should be none here since there is only 1 modifier slot for the handle
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.ABILITY); // Same here
    }
}
