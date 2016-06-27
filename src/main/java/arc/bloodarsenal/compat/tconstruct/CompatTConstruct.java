package arc.bloodarsenal.compat.tconstruct;

import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.compat.ICompatibility;
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
                HandlerTConstruct.init();
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                    HandlerTConstruct.initRender();
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
        return ConfigHandler.tconstructEnabled;
    }
}
