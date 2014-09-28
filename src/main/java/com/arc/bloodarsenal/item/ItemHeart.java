package com.arc.bloodarsenal.item;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemHeart extends Item
{
    public ItemHeart()
    {
        super();
        setMaxStackSize(1);
        setUnlocalizedName("heart");
        setTextureName("BloodArsenal:heart");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Fresh from a body");
    }
}
