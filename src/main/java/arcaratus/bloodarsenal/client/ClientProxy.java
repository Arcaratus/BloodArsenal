package arcaratus.bloodarsenal.client;

import arcaratus.bloodarsenal.common.core.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy implements IProxy
{
    @Override
    public void registerHandlers()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event)
    {
        registerEntityRenderers();
    }

    private static void registerEntityRenderers()
    {
//        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOID, manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
    }
}
