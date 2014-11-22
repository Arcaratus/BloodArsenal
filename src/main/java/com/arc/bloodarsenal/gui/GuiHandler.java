package com.arc.bloodarsenal.gui;

import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == 0)
        {
            BloodArsenal.logger.info("Open Gui");
//            return new ItemGuiBaseBook();
        }
        return null;
    }
}
