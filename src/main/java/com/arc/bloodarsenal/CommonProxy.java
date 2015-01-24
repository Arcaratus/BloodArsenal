package com.arc.bloodarsenal;

import com.arc.bloodarsenal.entity.EntityBloodTNT;
import com.arc.bloodarsenal.entity.projectile.EntityBloodBall;
import com.arc.bloodarsenal.entity.projectile.EntityGatlingProjectile;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.world.World;

public class CommonProxy
{
    public void registerRenders()
    {
        //Absolutely nothing
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

    public void registerTileEntities()
    {
    }

    public void registerEntityTrackers()
    {
        EntityRegistry.registerModEntity(EntityGatlingProjectile.class, "gatlingProjectile", 0, BloodArsenal.instance, 128, 5, true);
        EntityRegistry.registerModEntity(EntityBloodTNT.class, "bloodTNT", 1, BloodArsenal.instance, 64, 5, true);
        EntityRegistry.registerModEntity(EntityBloodBall.class, "bloodBall", 2, BloodArsenal.instance, 128, 5, true);
    }

    public void initRendering()
    {
        //TODO Auto-generated stub
    }
}
