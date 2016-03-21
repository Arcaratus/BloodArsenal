package arc.bloodarsenal.proxy;

import WayofTime.bloodmagic.util.helper.InventoryRenderHelperV2;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModItems;

public class ClientProxy extends CommonProxy
{
    private InventoryRenderHelperV2 renderHelper;

    @Override
    public InventoryRenderHelperV2 getRenderHelper()
    {
        return renderHelper;
    }

    @Override
    public void preInit()
    {
        super.preInit();

        renderHelper = new InventoryRenderHelperV2(BloodArsenal.DOMAIN);

        ModItems.initRenders();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }
}
