package com.arc.bloodarsenal.items;

import com.arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.List;

public class ItemSoulFragment extends Item
{
    public ItemSoulFragment()
    {
        super();
        setUnlocalizedName("soul_fragment");
        setTextureName("BloodAresnal:soul_fragment");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("The raw essence");
        par3List.add("of a soul");
    }
}
