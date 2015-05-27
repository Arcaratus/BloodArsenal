package com.arc.bloodarsenal.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BloodStoneBlock extends ItemBlock
{
    public BloodStoneBlock(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        String name;

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

            case 4:
                name = "blood_stone_5";
                break;

            default:
                name = "blood_stone";
        }

        return "tile." + name;
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
}
