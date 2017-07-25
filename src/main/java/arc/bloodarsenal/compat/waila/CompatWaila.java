package arc.bloodarsenal.compat.waila;

import arc.bloodarsenal.compat.ICompatibility;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CompatWaila implements ICompatibility
{
    @Override
    public void loadCompatibility(InitializationPhase phase)
    {
        if (phase == InitializationPhase.INIT)
            FMLInterModComms.sendMessage(getModId(), "register", "arc.bloodarsenal.compat.waila.WailaCallbackHandler.callbackRegister");
    }

    @Override
    public String getModId()
    {
        return "waila";
    }

    @Override
    public boolean enableCompat()
    {
        return true;
    }
}
