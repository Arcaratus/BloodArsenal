package arcaratus.bloodarsenal.registry;

import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.modifier.modifiers.*;

public class ModModifiers
{
    public static Modifier MODIFIER_BAD_POTION;
    public static Modifier MODIFIER_BLOODLUST;
    public static Modifier MODIFIER_FLAME;
    public static Modifier MODIFIER_SHARPNESS;

    public static Modifier MODIFIER_FORTUNATE;
    public static Modifier MODIFIER_LOOTING;
    public static Modifier MODIFIER_SILKY;
    public static Modifier MODIFIER_SMELTING;
    public static Modifier MODIFIER_XPERIENCED;

    public static Modifier MODIFIER_BENEFICIAL_POTION;
    public static Modifier MODIFIER_QUICK_DRAW;
    public static Modifier MODIFIER_SHADOW_TOOL;

    public static Modifier MODIFIER_AOD;
    public static Modifier MODIFIER_SIGIL;

    public static void init()
    {
        final int[] empty = new int[] {};

        MODIFIER_BAD_POTION = ModifierHandler.registerModifier(new ModifierBadPotion(), new ModifierTracker(Constants.Modifiers.BAD_POTION, Constants.Modifiers.BAD_POTION_COUNTER));
        MODIFIER_BLOODLUST = ModifierHandler.registerModifier(new ModifierBloodlust(), new ModifierTracker(Constants.Modifiers.BLOODLUST, Constants.Modifiers.BLOODLUST_COUNTER));
        MODIFIER_FLAME = ModifierHandler.registerModifier(new ModifierFlame(), new ModifierTracker(Constants.Modifiers.FLAME, Constants.Modifiers.FLAME_COUNTER));
        MODIFIER_SHARPNESS = ModifierHandler.registerModifier(new ModifierSharpness(), new ModifierTracker(Constants.Modifiers.SHARPNESS, Constants.Modifiers.SHARPNESS_COUNTER));

        MODIFIER_FORTUNATE = ModifierHandler.registerModifier(new ModifierFortunate(), new ModifierTracker(Constants.Modifiers.FORTUNATE, Constants.Modifiers.FORTUNATE_COUNTER));
        MODIFIER_LOOTING = ModifierHandler.registerModifier(new ModifierLooting(), new ModifierTracker(Constants.Modifiers.LOOTING, Constants.Modifiers.LOOTING_COUNTER));
        MODIFIER_SILKY = ModifierHandler.registerModifier(new ModifierSilky(), new ModifierTracker(Constants.Modifiers.SILKY, empty));
        MODIFIER_SMELTING = ModifierHandler.registerModifier(new ModifierSmelting(), new ModifierTracker(Constants.Modifiers.SMELTING, Constants.Modifiers.SMELTING_COUNTER));
        MODIFIER_XPERIENCED = ModifierHandler.registerModifier(new ModifierXP(), new ModifierTracker(Constants.Modifiers.XPERIENCED, Constants.Modifiers.XPERIENCED_COUNTER));

        MODIFIER_BENEFICIAL_POTION = ModifierHandler.registerModifier(new ModifierBeneficialPotion(), new ModifierTracker(Constants.Modifiers.BENEFICIAL_POTION, Constants.Modifiers.BENEFICIAL_POTION_COUNTER));
        MODIFIER_QUICK_DRAW = ModifierHandler.registerModifier(new ModifierQuickDraw(), new ModifierTracker(Constants.Modifiers.QUICK_DRAW, Constants.Modifiers.QUICK_DRAW_COUNTER));
        MODIFIER_SHADOW_TOOL = ModifierHandler.registerModifier(new ModifierShadowTool(), new ModifierTracker(Constants.Modifiers.SHADOW_TOOL, Constants.Modifiers.SHADOW_TOOL_COUNTER));

        MODIFIER_AOD = ModifierHandler.registerModifier(new ModifierAOD(), new ModifierTracker(Constants.Modifiers.AOD, Constants.Modifiers.AOD_COUNTER));
        MODIFIER_SIGIL = ModifierHandler.registerModifier(new ModifierSigil(), new ModifierTracker(Constants.Modifiers.SIGIL, empty));

        // Incompatibilities registering
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.HEAD, MODIFIER_FLAME, MODIFIER_BAD_POTION);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.HEAD, MODIFIER_SHARPNESS, MODIFIER_BLOODLUST);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.CORE, MODIFIER_SILKY, MODIFIER_SMELTING);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.CORE, MODIFIER_SILKY, MODIFIER_FORTUNATE);
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.HANDLE); // There should be none here since there is only 1 modifier slot for the handle
        ModifierHandler.registerIncompatibleModifiers(EnumModifierType.ABILITY); // Same here
    }
}
