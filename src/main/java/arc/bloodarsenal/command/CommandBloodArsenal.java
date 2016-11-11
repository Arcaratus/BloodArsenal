package arc.bloodarsenal.command;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.command.sub.SubCommandStasis;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandBloodArsenal extends CommandTreeBase
{
    public CommandBloodArsenal()
    {
        addSubcommand(new SubCommandStasis());
    }

    @Override
    public String getName()
    {
        return "bloodarsenal";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/bloodarsenal help";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public static void displayHelpString(ICommandSender commandSender, String display, Object... info)
    {
        commandSender.sendMessage(new TextComponentString(TextHelper.localizeEffect(display, info)));
    }

    public static void displayErrorString(ICommandSender commandSender, String display, Object... info)
    {
        commandSender.sendMessage(new TextComponentString(TextHelper.localizeEffect(display, info)));
    }

    public static void displaySuccessString(ICommandSender commandSender, String display, Object... info)
    {
        commandSender.sendMessage(new TextComponentString(TextHelper.localizeEffect(display, info)));
    }
}
