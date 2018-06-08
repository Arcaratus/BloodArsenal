package arcaratus.bloodarsenal.command.sub;

import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.command.CommandBloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.Locale;

public class SubCommandStasis extends CommandBase
{
    @Override
    public String getName()
    {
        return "stasis";
    }

    @Override
    public String getUsage(ICommandSender commandSender)
    {
        return TextHelper.localizeEffect("commands.stasis.usage");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender commandSender, String[] args) throws CommandException
    {
        if (commandSender.getEntityWorld().isRemote)
            return;

        if (args.length > 1)
        {
            if (args[0].equalsIgnoreCase("help"))
                return;

            try
            {
                EntityPlayer player = CommandBase.getCommandSenderAsPlayer(commandSender);
                ItemStack heldStack = player.getHeldItemMainhand();

                if (!heldStack.isEmpty() && heldStack.getItem() instanceof IModifiableItem)
                {
                    try
                    {
                        ValidCommands command = ValidCommands.valueOf(args[0].toUpperCase(Locale.ENGLISH));
                        command.run(heldStack, commandSender, args.length > 0 && args.length < 2, args);
                    }
                    catch (IllegalArgumentException e) {}
                }
            }
            catch (PlayerNotFoundException e)
            {
                CommandBloodArsenal.displayErrorString(commandSender, e.getLocalizedMessage());
            }
        }
        else
        {
            CommandBloodArsenal.displayErrorString(commandSender, "commands.error.arg.missing");
        }
    }

    private enum ValidCommands
    {
        ADD("commands.stasis.add.help")
            {
                @Override
                public void run(ItemStack itemStack, ICommandSender sender, boolean displayHelp, String... args)
                {
                    if (displayHelp)
                    {
                        CommandBloodArsenal.displayHelpString(sender, help);
                        return;
                    }

                    if (args.length >= 2 && args.length < 4)
                    {
                        int level = args.length == 3 && Utils.isInteger(args[2]) ? Integer.parseInt(args[2]) : 0;
                        Modifier modifier = ModifierHandler.generateModifierFromKey(BloodArsenal.MOD_ID + ".modifier." + args[1], level, false);
                        StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);
                        modifiable.applyModifier(modifier);
                        NBTHelper.checkNBT(itemStack);
                        modifier.removeSpecialNBT(itemStack); // Needed to reset NBT data
                        modifier.writeSpecialNBT(itemStack);
                        if (!StasisModifiable.hasModifiable(itemStack))
                            StasisModifiable.setStasisModifiable(itemStack, StasisModifiable.getStasisModifiable(itemStack));
                        StasisModifiable.setStasisModifiable(itemStack, modifiable);
                        CommandBloodArsenal.displaySuccessString(sender, "commands.stasis.add.success", TextHelper.localize(modifier.getUnlocalizedName()), level + 1);
                    }
                    else
                    {
                        CommandBloodArsenal.displayErrorString(sender, "commands.error.arg.missing");
                    }
                }
            },
        REMOVE("commands.stasis.remove.help")
            {
                @Override
                public void run(ItemStack itemStack, ICommandSender sender, boolean displayHelp, String... args)
                {
                    if (displayHelp)
                    {
                        CommandBloodArsenal.displayHelpString(sender, help);
                        return;
                    }

                    if (args.length == 2)
                    {
                        Modifier modifier = ModifierHandler.generateModifierFromKey(BloodArsenal.MOD_ID + ".modifier." + args[1], 0, false);
                        StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);
                        modifiable.removeModifier(modifier);
                        NBTHelper.checkNBT(itemStack);
                        modifier.removeSpecialNBT(itemStack);
                        if (!StasisModifiable.hasModifiable(itemStack))
                            StasisModifiable.setStasisModifiable(itemStack, StasisModifiable.getStasisModifiable(itemStack));
                        StasisModifiable.setStasisModifiable(itemStack, modifiable);
                        CommandBloodArsenal.displaySuccessString(sender, "commands.stasis.remove.success", TextHelper.localize(modifier.getUnlocalizedName()));
                    }
                    else
                    {
                        CommandBloodArsenal.displayErrorString(sender, "commands.error.arg.missing");
                    }
                }
            };

        public String help;

        ValidCommands(String help)
        {
            this.help = help;
        }

        public abstract void run(ItemStack itemStack, ICommandSender sender, boolean displayHelp, String... args);
    }
}
