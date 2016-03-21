package arc.bloodarsenal.proxy;

import WayofTime.bloodmagic.util.helper.InventoryRenderHelperV2;

public class CommonProxy
{
    public InventoryRenderHelperV2 getRenderHelper()
    {
        return null;
    }

    public void preInit()
    {
//        MinecraftForge.EVENT_BUS.register(new EventHandler());
        registerRenderers();
    }

    public void init()
    {

    }

    public void postInit()
    {

    }

    public void registerRenderers()
    {

    }
}
