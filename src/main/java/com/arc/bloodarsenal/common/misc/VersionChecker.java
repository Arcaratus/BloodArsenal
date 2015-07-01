package com.arc.bloodarsenal.common.misc;

import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public class VersionChecker
{
    public static boolean doneChecking = false;
    public static String onlineVersion = "";
    public static boolean triedToWarnPlayer = false;

    public static boolean startedDownload = false;
    public static boolean downloadedFile = false;

    private static String BUILD = "GRADLE:BUILD";

    public void init()
    {
        new ThreadVersionChecker();
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (doneChecking && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !triedToWarnPlayer)
        {
            if (!onlineVersion.isEmpty())
            {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                int onlineBuild = Integer.parseInt(onlineVersion.split("1.1-")[1]);
                int clientBuild = Integer.parseInt(BloodArsenal.VERSION);

                if (onlineBuild > clientBuild)
                {
                    player.addChatComponentMessage(new ChatComponentTranslation("ba.versioning.update").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
                    player.addChatComponentMessage(new ChatComponentTranslation("ba.versioning.outdated", BloodArsenal.VERSION, onlineVersion));

                    IChatComponent component = IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocal("ba.versioning.updateMessage").replaceAll("%version%", onlineVersion));
                    player.addChatComponentMessage(component);
                }
            }

            triedToWarnPlayer = true;
        }
    }
}
