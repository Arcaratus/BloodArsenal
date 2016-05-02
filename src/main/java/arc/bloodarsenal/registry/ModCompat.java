package arc.bloodarsenal.registry;

import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.compat.baubles.CompatBaubles;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class ModCompat
{
    private static ArrayList<ICompatibility> compatibilities = new ArrayList<>();

    public static void registerModCompat()
    {
        if (ConfigHandler.baublesEnabled) compatibilities.add(new CompatBaubles());
    }

    public static void loadCompat(ICompatibility.InitializationPhase phase)
    {
        for (ICompatibility compatibility : compatibilities)
            if (Loader.isModLoaded(compatibility.getModId()) && compatibility.enableCompat())
                compatibility.loadCompatibility(phase);
    }
}
