package com.arc.bloodarsenal.item;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBloodOrange extends ItemFood
{
    public ItemBloodOrange()
    {
        super(2, false);
        setUnlocalizedName("blood_orange");
        setCreativeTab(BloodArsenal.BA_TAB);
        setTextureName("BloodArsenal:blood_orange");
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Ironic food source");
    }
}
