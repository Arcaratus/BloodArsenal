package arc.bloodarsenal.registry;

import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.compat.baubles.CompatBaubles;
import arc.bloodarsenal.compat.tconstruct.CompatTConstruct;
import arc.bloodarsenal.compat.waila.CompatWaila;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

public class ModCompat
{
    private static ArrayList<ICompatibility> compatibilities = new ArrayList<>();

    public static void registerModCompat()
    {
        compatibilities.add(new CompatBaubles());
        compatibilities.add(new CompatWaila());
        compatibilities.add(new CompatTConstruct());
    }

    public static void loadCompat(ICompatibility.InitializationPhase phase)
    {
        for (ICompatibility compatibility : compatibilities)
            if (Loader.isModLoaded(compatibility.getModId()) && compatibility.enableCompat())
                compatibility.loadCompatibility(phase);
    }
}
