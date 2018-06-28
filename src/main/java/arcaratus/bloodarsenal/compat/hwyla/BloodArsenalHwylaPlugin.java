package arcaratus.bloodarsenal.compat.hwyla;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.compat.hwyla.provider.DataProviderBloodCapacitor;
import arcaratus.bloodarsenal.compat.hwyla.provider.DataProviderStasisPlate;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileBloodCapacitor;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import mcp.mobius.waila.api.*;

@WailaPlugin
public class BloodArsenalHwylaPlugin implements IWailaPlugin
{
    @Override
    public void register(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(DataProviderStasisPlate.INSTANCE, TileStasisPlate.class);
        registrar.registerNBTProvider(DataProviderStasisPlate.INSTANCE, TileStasisPlate.class);
        registrar.addConfig(BloodArsenal.MOD_ID, Constants.Compat.WAILA_CONFIG_STASIS_PLATE, true);

        registrar.registerBodyProvider(DataProviderBloodCapacitor.INSTANCE, TileBloodCapacitor.class);
        registrar.registerNBTProvider(DataProviderBloodCapacitor.INSTANCE, TileBloodCapacitor.class);
        registrar.addConfig(BloodArsenal.MOD_ID, Constants.Compat.WAILA_CONFIG_BLOOD_CAPACITOR, true);
    }
}
