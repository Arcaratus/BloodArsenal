package arcaratus.bloodarsenal;

import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.ritual.RitualBloodBurner;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * This class is here to get around registry shenanigans
 * Temporary until I figure something else out
 */
public class RegistryShenanigans
{
    public static void init()
    {
        RitualBloodBurner.IGNITERS.add(RegistrarBloodArsenalItems.BOUND_IGNITER);
        RitualBloodBurner.ACCEPTABLE_FLUIDS.add(FluidRegistry.getFluid("refined_life_essence"));
    }
}
