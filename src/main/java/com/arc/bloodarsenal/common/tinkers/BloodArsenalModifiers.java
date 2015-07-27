package com.arc.bloodarsenal.common.tinkers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.crafting.ModifyBuilder;

public class BloodArsenalModifiers
{
    public static void initModifiers()
    {
        ItemStack masterOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.masterBloodOrb, 1, OreDictionary.WILDCARD_VALUE);
        ModifyBuilder.registerModifier(new ModSoulbound(new ItemStack[] {masterOrb}));
    }
}
