package arc.bloodarsenal.compat.waila;

import arc.bloodarsenal.block.BlockStasisPlate;
import arc.bloodarsenal.compat.waila.provider.DataProviderStasisPlate;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaCallbackHandler
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(new DataProviderStasisPlate(), BlockStasisPlate.class);
        registrar.registerNBTProvider(new DataProviderStasisPlate(), BlockStasisPlate.class);
    }
}
