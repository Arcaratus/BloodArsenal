package arcaratus.bloodarsenal.proxy;

import arcaratus.bloodarsenal.util.handler.EventHandler;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fluids.Fluid;

public class CommonProxy
{
    public void preInit()
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
//        MinecraftForge.EVENT_BUS.register(new TrackerHandler());
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

    public void registerFluidModels(Fluid fluid)
    {
    }

    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters)
    {
        return null;
    }
}
