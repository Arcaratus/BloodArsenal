package com.arc.bloodarsenal;

import com.arc.bloodarsenal.entity.projectile.EntityGatlingProjectile;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.renderer.ItemRenderGatling;
import com.arc.bloodarsenal.renderer.RenderEntityGatlingProjectile;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        MinecraftForgeClient.registerItemRenderer(ModItems.energy_gatling, new ItemRenderGatling());

        RenderingRegistry.registerEntityRenderingHandler(EntityGatlingProjectile.class, new RenderEntityGatlingProjectile());
    }

    public void registerEntities()
    {
    }

    public void registerEvents()
    {
    }

    public World getClientWorld()
    {
        return null;
    }
}
