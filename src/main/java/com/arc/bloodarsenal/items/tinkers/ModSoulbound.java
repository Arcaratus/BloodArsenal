package com.arc.bloodarsenal.items.tinkers;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.modifier.IModifyable;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModBoolean;
import tconstruct.modifiers.tools.ModInteger;

import java.util.ArrayList;

public class ModSoulbound extends ModBoolean
{
    public int modifiersRequired = 1;

    public ModSoulbound(ItemStack[] items)
    {
        super(items, 1, "Soulbound", "\u00a74", "Soulbound");
    }

    @Override
    public boolean matches (ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

        String[] traits = ((IModifyable)tool.getItem()).getTraits();
        for(String trait : traits)
            if("ammo".equals(trait))
                return false;

        ItemStack foundOrb = null;
        ItemStack masterOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.masterBloodOrb, 1, OreDictionary.WILDCARD_VALUE);

        for (ItemStack stack : input)
            {
                if (stack == null)
                    continue;
                if (stack.getItem() != masterOrb.getItem())
                    continue;
                if (foundOrb != null)
                    return false;

                foundOrb = stack;
            }

        if (foundOrb == null)
            return false;

        // otherwise check if we have enough modfiers
        else if (tags.getInteger("Modifiers") < modifiersRequired)
            return false;

        // all requirements satisfied!
        return true;
    }

    @Override
    public void modify (ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tags = tool.getTagCompound();

        // update modifiers (only applies if it's not an upgrade)
        if (!tags.hasKey(key))
        {
            int modifiers = tags.getCompoundTag("InfiTool").getInteger("Modifiers");
            modifiers -= modifiersRequired;
            tags.getCompoundTag("InfiTool").setInteger("Modifiers", modifiers);
            addModifierTip(tool, "\u00a74");
        }

        tags.getCompoundTag("InfiTool").setBoolean(key, true);

        // find the battery in the input
        ItemStack inputBattery = null;
        ItemStack masterOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.masterBloodOrb, 1, OreDictionary.WILDCARD_VALUE);

        for (ItemStack stack : input)
            {
                if (stack == null)
                    continue;
                if (stack.getItem() != masterOrb.getItem())
                    continue;

                // we're guaranteed to only find one battery because more are prevented above
                inputBattery = stack;
            }

        tags.setString("Owner", "");
        tags.setInteger(key, 1);
    }
}
