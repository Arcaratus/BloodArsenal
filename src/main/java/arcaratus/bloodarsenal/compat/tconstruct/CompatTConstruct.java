package arcaratus.bloodarsenal.compat.tconstruct;

import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.compat.ICompatibility;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class CompatTConstruct implements ICompatibility
{
    @Override
    public void loadCompatibility(InitializationPhase phase)
    {
        switch (phase)
        {
            case PRE_INIT:
            {
                HandlerTConstruct.preInit();
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                    HandlerTConstruct.initRender();
            }

            case INIT:
            {
                HandlerTConstruct.init();
            }

            case POST_INIT:
            {
                HandlerTConstruct.postInit();
            }
        }
    }

    @Override
    public String getModId()
    {
        return "tconstruct";
    }

    @Override
    public boolean enableCompat()
    {
        return ConfigHandler.compat.tconstructCompatEnabled;
    }
}
