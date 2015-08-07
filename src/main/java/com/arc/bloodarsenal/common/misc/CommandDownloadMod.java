package com.arc.bloodarsenal.common.misc;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

/*
 *  This portion here means that all this is from
 *  Botania by Vazkii https://github.com/Vazkii/Botania
 */
public class CommandDownloadMod extends CommandBase
{
    private static final boolean ENABLED = true;

    @Override
    public String getCommandName()
    {
        return "bloodarsenal-download-latest";
    }

    @Override
    public String getCommandUsage(ICommandSender var1)
    {
        return "/bloodarsenal-download-latest <version>";
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2)
    {
        if (!ENABLED)
            var1.addChatMessage(new ChatComponentTranslation("ba.versioning.disabled").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));

        else if (var2.length == 1)
            if (VersionChecker.downloadedFile)
                var1.addChatMessage(new ChatComponentTranslation("ba.versioning.downloadedAlready").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            else if (VersionChecker.startedDownload)
                var1.addChatMessage(new ChatComponentTranslation("ba.versioning.downloadingAlready").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            else new ThreadDownloadMod("BloodArsenal " + var2[0] + ".jar");
    }
}
