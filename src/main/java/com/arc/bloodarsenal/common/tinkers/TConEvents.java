package com.arc.bloodarsenal.common.tinkers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class TConEvents
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleStich(TextureStitchEvent.Pre event)
    {
        if (event.map.getTextureType() == 0)
        {
            if (BloodArsenalTinkers.bloodInfusedIronFluid != null)
            {
                BloodArsenalTinkers.bloodInfusedIronFluid.setIcons(event.map.registerIcon("BloodArsenal:molten_blood_infused_iron_still"), event.map.registerIcon("BloodArsenal:molten_blood_infused_iron_flow"));
            }
        }
    }
}
