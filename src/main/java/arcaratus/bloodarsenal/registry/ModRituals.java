package arcaratus.bloodarsenal.registry;

import WayofTime.bloodmagic.ritual.Ritual;
import WayofTime.bloodmagic.ritual.RitualRegistry;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitualRegistry;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.ritual.imperfect.*;
import com.google.common.collect.BiMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ModRituals
{
//    public static final Ritual INFUSION_RITUAL;

    public static ImperfectRitual IMPERFECT_LIGHTNING;
    public static ImperfectRitual IMPERFECT_ENCHANT_RESET;
    public static ImperfectRitual IMPERFECT_ICE;
    public static ImperfectRitual IMPERFECT_SNOW;

    public static void initImperfectRituals()
    {
//        INFUSION_RITUAL = new RitualInfusion();
//
        IMPERFECT_LIGHTNING = new ImperfectRitualLightning();
        IMPERFECT_ENCHANT_RESET = new ImperfectRitualEnchantReset();
        IMPERFECT_ICE = new ImperfectRitualIce();
        IMPERFECT_SNOW = new ImperfectRitualSnow();
//
//        RitualRegistry.registerRitual(INFUSION_RITUAL, ConfigHandler.infusionRitual);
//
        ImperfectRitualRegistry.registerRitual(IMPERFECT_LIGHTNING, ConfigHandler.rituals.imperfect.imperfectLightning);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_ENCHANT_RESET, ConfigHandler.rituals.imperfect.imperfectEnchantReset);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_ICE, ConfigHandler.rituals.imperfect.imperfectIce);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_SNOW, ConfigHandler.rituals.imperfect.imperfectSnow);
    }

    public static void overrideRituals()
    {
//        BloodArsenal.INSTANCE.getLogger().info("Overriding the Sound of the Cleansing Soul");
//        BloodArsenal.INSTANCE.getLogger().info("Report any issues about the ritual to Blood Arsenal first, NOT Blood Magic");

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

//            WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual = new RitualModifierRemove();
//            RitualRegistry.registerRitual(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual, WayofTime.bloodmagic.ConfigHandler.ritualUpgradeRemove);

            ArrayList<String> orderedIDList = RitualRegistry.getOrderedIds();
            orderedIDList.remove(orderedIDList.lastIndexOf(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual.getName()));
            orderedIDListField.set(null, orderedIDList);

            registryField.setAccessible(false);
            orderedIDListField.setAccessible(false);

//            BloodArsenal.INSTANCE.getLogger().info("...don't tell TehNut about this...");
//            BloodArsenal.INSTANCE.getLogger().info("...cuz he'll probably hunt me down...");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
