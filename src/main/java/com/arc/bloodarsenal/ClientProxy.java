package com.arc.bloodarsenal;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
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

    public ModelBiped getArmorModel(int id)
    {
        return null;
    }
}
