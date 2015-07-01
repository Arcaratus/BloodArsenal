package com.arc.bloodarsenal.common.items.tool;

import net.minecraft.item.ItemStack;

public interface IFillable
{
    int getMaxLP();

    int getLPStored(ItemStack container);

    void incrementLPStored(ItemStack itemStack, int incrementAmount);
}
