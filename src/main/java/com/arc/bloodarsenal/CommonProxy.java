package com.arc.bloodarsenal;

import com.arc.bloodarsenal.tileentity.TilePortableAltar;
import cpw.mods.fml.common.registry.GameRegistry;
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
        GameRegistry.registerTileEntity(TilePortableAltar.class, "containerPortableAltar");
    }

    public void initRendering()
    {
        //TODO Auto-generated stub
    }
}
