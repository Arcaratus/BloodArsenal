package com.arc.bloodarsenal.common.tinkers;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.modifier.IModifyable;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModBoolean;
import tconstruct.util.config.PHConstruct;

import java.util.ArrayList;

public class ModSoulbound extends ModBoolean
{
    public int modifiersRequired = 1;
    private String owner;

    public ModSoulbound(ItemStack[] items)
    {
        super(items, 30, "Soulbound", EnumChatFormatting.DARK_RED.toString(), "Soulbound");
    }

    @Override
    public boolean matches(ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        String[] traits = ((IModifyable)tool.getItem()).getTraits();
        for(String trait : traits)
            if("ammo".equals(trait))
                return false;

        ItemStack foundOrb = null;

        for (ItemStack stack : input)
        {
            if (stack == null)
                continue;
            if (!(stack.getItem() instanceof IBloodOrb))
                continue;
            if (!(((IBloodOrb) stack.getItem()).getOrbLevel() >= 4))
                continue;
            if (SoulNetworkHandler.getOwnerName(stack).equals(""))
                continue;
            if (foundOrb != null)
                return false;

            foundOrb = stack;
        }

        if (foundOrb == null)
            return false;
        if (tags.getBoolean(key))
            return false;
            // otherwise check if we have enough modfiers
        else if (tags.getInteger("Modifiers") < modifiersRequired)
            return false;

        // all requirements satisfied!
        return true;
    }

    @Override
    public void modify(ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tags = tool.getTagCompound();

        // update modifiers (only applies if it's not an upgrade)
        if (!tags.hasKey(key))
        {
            int modifiers = tags.getCompoundTag("InfiTool").getInteger("Modifiers");
            modifiers -= modifiersRequired;
            tags.getCompoundTag("InfiTool").setInteger("Modifiers", modifiers);
            addModifierTip(tool, EnumChatFormatting.DARK_RED + "Soulbound");
        }

        tags.getCompoundTag("InfiTool").setBoolean(key, true);

        // find the battery in the input
        ItemStack inputBattery = null;

        for (ItemStack stack : input)
        {
            if (stack == null)
                continue;
            if (!(stack.getItem() instanceof IBloodOrb))
                continue;
            if (!(((IBloodOrb) stack.getItem()).getOrbLevel() >= 4))
                continue;
            if (SoulNetworkHandler.getOwnerName(stack).equals(""))
                continue;
            if (inputBattery != null)
                return;

            this.owner = SoulNetworkHandler.getOwnerName(stack);
            // we're guaranteed to only find one battery because more are prevented above
            inputBattery = stack;
        }

        tags.setString("Owner", owner);
        tags.setInteger(key, 1);
    }
}
