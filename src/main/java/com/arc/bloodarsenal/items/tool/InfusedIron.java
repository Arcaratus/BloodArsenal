package com.arc.bloodarsenal.items.tool;

import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.items.ModItems;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class InfusedIron extends Item
{
    public InfusedIron()
    {
        super();
        setUnlocalizedName("blood_infused_iron");
        setTextureName("BloodArsenal:blood_infused_iron");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Mundane iron infused");
        par3List.add("with life essence");
    }
}
