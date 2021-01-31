package arcaratus.bloodarsenal.integration;

import arcaratus.bloodarsenal.common.block.ModBlocks;
import wayoftime.bloodmagic.api.IBloodMagicAPI;

public class BloodMagicIntegration
{
    public static void integrate()
    {
        IBloodMagicAPI api = IBloodMagicAPI.INSTANCE.getValue();
        api.registerAltarComponent(ModBlocks.BLOOD_INFUSED_GLOWSTONE.get().getDefaultState(), "glowstone");
    }
}
