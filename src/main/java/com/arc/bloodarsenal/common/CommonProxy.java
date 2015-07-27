package com.arc.bloodarsenal.common;

import com.arc.bloodarsenal.common.entity.EntityBloodTNT;
import com.arc.bloodarsenal.common.tinkers.IClientCode;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.world.World;

public class CommonProxy
{
    public void init()
    {
        registerEntityTrackers();
    }

    public void registerRenders()
    {
        //Absolutely nothing
    }

    public void registerEvents()
    {
    }

    public World getClientWorld()
    {
        return null;
    }

    public void registerEntityTrackers()
    {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityBloodTNT.class, "bloodTNT", entityID);
        EntityRegistry.registerModEntity(EntityBloodTNT.class, "bloodTNT", entityID, BloodArsenal.instance, 64, 100, true);
//        EntityRegistry.registerModEntity(EntityBloodHound.class, "BloodHound", 3, BloodArsenal.instance, 96, 3, true);
    }

    public void initRendering()
    {
        //TODO Auto-generated stub
    }

    public void executeClientCode(IClientCode clientCode) {}
}
