package com.arc.bloodarsenal.common.tinkers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class TConEvents
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleStich(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != 0) {
            return;
        }
        BloodArsenalTinkers.bloodInfusedIron.setIcons(event.map.registerIcon("TConIntegration.bedrock"));

/*        TextureAtlasSprite sprite = new TextureBedrockLava("extrautils:bedrockFluid", "lava_still");
        event.map.setTextureEntry("extrautils:bedrockFluid", sprite);
        if (BloodArsenalTinkers.bloodInfusedIron != null) {
            BloodArsenalTinkers.bloodInfusedIron.setIcons(sprite);
        }
        sprite = new TextureBedrockLava("extrautils:bedrockFluid_flowing", "lava_flow");
        if ((event.map.setTextureEntry("extrautils:bedrockFluid_flowing", sprite)) &&
                (BloodArsenalTinkers.bloodInfusedIron != null)) {
            BloodArsenalTinkers.bloodInfusedIron.setFlowingIcon(sprite);
        }
*/    }
}
