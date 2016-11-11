package arc.bloodarsenal.registry;

import WayofTime.bloodmagic.api.registry.ImperfectRitualRegistry;
import WayofTime.bloodmagic.api.registry.RitualRegistry;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.imperfect.ImperfectRitual;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.ritual.RitualInfusion;
import arc.bloodarsenal.ritual.RitualModifierRemove;
import arc.bloodarsenal.ritual.imperfect.*;
import com.google.common.collect.BiMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ModRituals
{
    public static final Ritual INFUSION_RITUAL;

    public static final ImperfectRitual IMPERFECT_LIGHTNING;
    public static final ImperfectRitual IMPERFECT_ENCHANT_RESET;
    public static final ImperfectRitual IMPERFECT_ICE;
    public static final ImperfectRitual IMPERFECT_SNOW;

    static
    {
        INFUSION_RITUAL = new RitualInfusion();

        IMPERFECT_LIGHTNING = new ImperfectRitualLightning();
        IMPERFECT_ENCHANT_RESET = new ImperfectRitualEnchantReset();
        IMPERFECT_ICE = new ImperfectRitualIce();
        IMPERFECT_SNOW = new ImperfectRitualSnow();

        RitualRegistry.registerRitual(INFUSION_RITUAL, ConfigHandler.infusionRitual);

        ImperfectRitualRegistry.registerRitual(IMPERFECT_LIGHTNING, ConfigHandler.imperfectLightning);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_ENCHANT_RESET, ConfigHandler.imperfectEnchantReset);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_ICE, ConfigHandler.imperfectIce);
        ImperfectRitualRegistry.registerRitual(IMPERFECT_SNOW, ConfigHandler.imperfectSnow);
    }

    public static void overrideRituals()
    {
        BloodArsenal.INSTANCE.getLogger().info("Overriding the Sound of the Cleansing Soul");
        BloodArsenal.INSTANCE.getLogger().info("Report any issues about the ritual to Blood Arsenal first, NOT Blood Magic");

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
            RitualRegistry.registerRitual(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual, WayofTime.bloodmagic.ConfigHandler.ritualUpgradeRemove);

            ArrayList<String> orderedIDList = RitualRegistry.getOrderedIds();
            orderedIDList.remove(orderedIDList.lastIndexOf(WayofTime.bloodmagic.registry.ModRituals.upgradeRemoveRitual.getName()));
            orderedIDListField.set(null, orderedIDList);

            registryField.setAccessible(false);
            orderedIDListField.setAccessible(false);

            BloodArsenal.INSTANCE.getLogger().info("...don't tell TehNut about this...");
            BloodArsenal.INSTANCE.getLogger().info("...cuz he'll probably hunt me down...");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
