/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [May 31, 2014, 10:22:44 PM (GMT)]
 */
package com.arc.bloodarsenal.common.misc;

import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

/*
 *  This portion here means that all this is from
 *  Botania by Vazkii https://github.com/Vazkii/Botania
 */
public class VersionChecker
{
    public static boolean doneChecking = false;
    public static String onlineVersion = "";
    public static boolean triedToWarnPlayer = false;

    public static boolean startedDownload = false;
    public static boolean downloadedFile = false;

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
                int clientBuild = BloodArsenal.VERSION.equals("@VERSION@") ? 0 : Integer.parseInt(BloodArsenal.VERSION.split("1.1-")[1]);

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
