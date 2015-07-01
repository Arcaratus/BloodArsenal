package com.arc.bloodarsenal.common.items.sigil.holding;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class AHPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("AugmentedHolding");

    public static void init()
    {
        INSTANCE.registerMessage(AHPacketProcessor.class, AHPacketProcessor.class, 0, Side.SERVER);
    }
}
