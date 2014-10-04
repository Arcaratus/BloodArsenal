package com.arc.bloodarsenal.items;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemAmorphicCatalyst extends Item
{
    public ItemAmorphicCatalyst()
    {
        super();
        setUnlocalizedName("amorphic_catalyst");
        setTextureName("BloodArsenal:amorphic_catalyst");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("A coagulated mess");
    }
}
