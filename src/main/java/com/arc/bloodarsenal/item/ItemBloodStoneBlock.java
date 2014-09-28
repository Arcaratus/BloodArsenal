package com.arc.bloodarsenal.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBloodStoneBlock extends ItemBlock
{
    public ItemBloodStoneBlock(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack itemStack)
    {
        String name = "";

        switch (itemStack.getItemDamage())
        {
            case 0:
                name = "blood_stone_1";
                break;

            case 1:
                name = "blood_stone_2";
                break;

            case 2:
                name = "blood_stone_3";
                break;

            case 3:
                name = "blood_stone_4";
                break;

            default:
                name = "blood_stone";
        }

        return getUnlocalizedName() + "." + name;
    }

    public int getMetadata(int par1)
    {
        return par1;
    }
}
