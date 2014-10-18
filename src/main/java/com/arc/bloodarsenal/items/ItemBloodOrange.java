package com.arc.bloodarsenal.items;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBloodOrange extends ItemFood
{
    public ItemBloodOrange()
    {
        super(2, 0.2F, false);
        setUnlocalizedName("blood_orange");
        setTextureName("BloodArsenal:blood_orange");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Completely organic");
    }
}
