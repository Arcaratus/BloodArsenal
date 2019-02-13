package arcaratus.bloodarsenal.registry;

import arcaratus.bloodarsenal.compat.ICompatibility;
import arcaratus.bloodarsenal.compat.tconstruct.CompatTConstruct;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class ModCompat
{
    private static ArrayList<ICompatibility> compatibilities = new ArrayList<>();

    public static void registerModCompat()
    {
//        compatibilities.add(new CompatWaila());
        compatibilities.add(new CompatTConstruct());
    }

    public static void loadCompat(ICompatibility.InitializationPhase phase)
    {
        for (ICompatibility compatibility : compatibilities)
            if (Loader.isModLoaded(compatibility.getModId()) && compatibility.enableCompat())
                compatibility.loadCompatibility(phase);
    }
}
