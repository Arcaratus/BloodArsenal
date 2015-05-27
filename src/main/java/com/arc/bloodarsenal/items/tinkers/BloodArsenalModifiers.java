package com.arc.bloodarsenal.items.tinkers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.crafting.ModifyBuilder;

public class BloodArsenalModifiers
{
    public static void init()
    {

    }

    public static void initModifiers()
    {
        int craftingConstant = OreDictionary.WILDCARD_VALUE;

        ItemStack masterOrb = new ItemStack(WayofTime.alchemicalWizardry.ModItems.masterBloodOrb, 1, craftingConstant);
        ModifyBuilder.registerModifier(new ModSoulbound());
    }
}
