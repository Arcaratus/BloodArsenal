package com.arc.bloodarsenal.common.rituals;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import WayofTime.alchemicalWizardry.common.renderer.AlchemyCircleRenderer;
import com.arc.bloodarsenal.common.BloodArsenalConfig;
import net.minecraft.util.ResourceLocation;

public class RitualRegistry
{
    public static void initRituals()
    {
        String resource = "alchemicalwizardry:textures/models/SimpleTransCircle.png";

        Rituals.registerRitual("ARC001Wither", 1, 75000, new RitualEffectWithering(), "Ritual of Withering", new AlchemyCircleRenderer(new ResourceLocation(resource), 0, 0, 0, 255, 0, 0.501, 0.501, 0, 1.5, false));
        Rituals.registerRitual("ARC002Midas", 1, 5000, new RitualEffectMidas(), "Midas Touch", new AlchemyCircleRenderer(new ResourceLocation(resource), 0, 0, 0, 255, 0, 0.501, 0.501, 0, 2.5, false));
        Rituals.registerRitual("ARC003Enchant", 2, 50000, new RitualEffectEnchant(), "The Enchantress's Spell", new AlchemyCircleRenderer(new ResourceLocation(resource), 0, 0, 0, 255, 0, 0.501, 0.501, 0, 1.5, false));
        Rituals.registerRitual("ARC004MobDisable", 2, 25000, new RitualEffectMobDisable(), "Ritual of Mob Oppression");
        Rituals.registerRitual("ARC005Fisherman", 1, 10000, new RitualEffectFishing(), "Fisherman's Hymn");

        blacklistRituals();
    }

    public static void blacklistRituals()
    {
        if (BloodArsenalConfig.ritualDisabledWither) r("ARC001Wither");
        if (BloodArsenalConfig.ritualDisabledMidas) r("ARC002Midas");
        if (BloodArsenalConfig.ritualDisabledEnchantment) r("ARC003Enchant");
        if (BloodArsenalConfig.ritualDisabledMobOppression) r("ARC004MobDisable");
        if (BloodArsenalConfig.ritualDisabledFisherman) r("ARC005Fisherman");
    }

    private static void r(String ritualID)
    {
        Rituals.ritualMap.remove(ritualID);
        Rituals.keyList.remove(ritualID);
    }
}
