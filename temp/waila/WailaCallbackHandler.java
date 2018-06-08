package arcaratus.bloodarsenal.compat.waila;

import arcaratus.bloodarsenal.block.BlockStasisPlate;
import arcaratus.bloodarsenal.compat.waila.provider.DataProviderStasisPlate;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaCallbackHandler
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(new DataProviderStasisPlate(), BlockStasisPlate.class);
        registrar.registerNBTProvider(new DataProviderStasisPlate(), BlockStasisPlate.class);
    }
}
