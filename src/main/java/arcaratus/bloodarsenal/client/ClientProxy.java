package arcaratus.bloodarsenal.client;

import arcaratus.bloodarsenal.common.block.ModBlocks;
import arcaratus.bloodarsenal.common.core.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        registerRenderTypes();
        registerEntityRenderers();
    }

    private static void registerRenderTypes()
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.FRACTURED_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.BLOOD_STAINED_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPARKLING_BLOOD_STAINED_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.BLOOD_STAINED_GLASS_PANE.get(), RenderType.getTranslucent());
    }

    private static void registerEntityRenderers()
    {
//        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOID, manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
    }

    @Override
    public boolean isTheClientPlayer(LivingEntity entity)
    {
        return entity == Minecraft.getInstance().player;
    }

    @Override
    public PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
