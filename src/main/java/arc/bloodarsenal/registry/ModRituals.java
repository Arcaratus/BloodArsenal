package arc.bloodarsenal.registry;

import WayofTime.bloodmagic.api.registry.ImperfectRitualRegistry;
import WayofTime.bloodmagic.api.registry.RitualRegistry;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.imperfect.ImperfectRitual;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.ritual.RitualInfusion;
import arc.bloodarsenal.ritual.imperfect.ImperfectRitualLightning;

public class ModRituals
{
    public static final Ritual INFUSION_RITUAL;

    public static final ImperfectRitual IMPERFECT_LIGHTNING;

    static
    {
        INFUSION_RITUAL = new RitualInfusion();

        IMPERFECT_LIGHTNING = new ImperfectRitualLightning();

        RitualRegistry.registerRitual(INFUSION_RITUAL, ConfigHandler.infusionRitual);

        ImperfectRitualRegistry.registerRitual(IMPERFECT_LIGHTNING, ConfigHandler.imperfectLightning);
    }

    public static void 안녕하세요()
    {

    }
}
