package com.arc.bloodarsenal.items.tool;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class InfusedStick extends Item
{
    public InfusedStick()
    {
        super();
        setUnlocalizedName("blood_infused_stick");
        setTextureName("BloodArsenal:blood_infused_stick");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("It's a stick");
        par3List.add("infused with life!");
    }
}
