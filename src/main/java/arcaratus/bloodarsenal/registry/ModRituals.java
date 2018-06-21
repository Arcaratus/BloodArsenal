package arcaratus.bloodarsenal.registry;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitualRegistry;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.ritual.*;
import arcaratus.bloodarsenal.ritual.imperfect.*;
import arcaratus.bloodarsenal.util.BALog;
import com.google.common.collect.BiMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ModRituals
{
    public static Ritual INFUSION_RITUAL;
    public static Ritual PURIFICATION_RITUAL;

    public static ImperfectRitual IMPERFECT_LIGHTNING;
    public static ImperfectRitual IMPERFECT_ENCHANT_RESET;
    public static ImperfectRitual IMPERFECT_ICE;
    public static ImperfectRitual IMPERFECT_SNOW;

    public static void initImperfectRituals()
    {
        INFUSION_RITUAL = new RitualInfusion();
        PURIFICATION_RITUAL = new RitualPurification();

        IMPERFECT_LIGHTNING = new ImperfectRitualLightning();
        IMPERFECT_ENCHANT_RESET = new ImperfectRitualEnchantReset();
        IMPERFECT_ICE = new ImperfectRitualIce();
        IMPERFECT_SNOW = new ImperfectRitualSnow();

        RitualRegistry.registerRitual(INFUSION_RITUAL, ConfigHandler.rituals.infusionRitual);
        RitualRegistry.registerRitual(PURIFICATION_RITUAL, ConfigHandler.rituals.purificationRitual);

        ImperfectRitualRegistry.registerRitual(IMPERFECT_LIGHTNING, ConfigHandler.rituals.imperfect.imperfectLightning);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_ENCHANT_RESET, ConfigHandler.rituals.imperfect.imperfectEnchantReset);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_ICE, ConfigHandler.rituals.imperfect.imperfectIce);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_SNOW, ConfigHandler.rituals.imperfect.imperfectSnow);
    }

    public static void overrideRituals()
    {
        BALog.DEFAULT.info("Overriding the Sound of the Cleansing Soul (upgradeRemoveRitual)");
        BALog.DEFAULT.info("Report any issues about the ritual to Blood Arsenal first, NOT Blood Magic");

        try
        {
            Field registryField = RitualRegistry.class.getDeclaredField("registry");
            registryField.setAccessible(true);
            Field orderedIDListField = RitualRegistry.class.getDeclaredField("orderedIdList");
            orderedIDListField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            modifiersField.setInt(registryField, registryField.getModifiers() & ~Modifier.FINAL);
            modifiersField.setInt(orderedIDListField, registryField.getModifiers() & ~Modifier.FINAL);

            BiMap<String, Ritual> registry = RitualRegistry.getRegistry();
            registry.remove(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual.getName());
            registryField.set(null, registry);

            WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual = new RitualModifierRemove();
            RitualRegistry.registerRitual(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual, WayofTime.bloodmagic.ConfigHandler.rituals.ritualUpgradeRemove);

            ArrayList<String> orderedIDList = RitualRegistry.getOrderedIds();
            orderedIDList.remove(orderedIDList.lastIndexOf(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual.getName()));
            orderedIDListField.set(null, orderedIDList);

            registryField.setAccessible(false);
            orderedIDListField.setAccessible(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
