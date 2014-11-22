package com.arc.bloodarsenal.items;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
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
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (AlchemicalWizardry.wimpySettings)
        {
            par3List.add("Bright red in color");
        }
        else
        {
            par3List.add("Fresh from a body");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if (AlchemicalWizardry.wimpySettings)
        {
            itemIcon = iconRegister.registerIcon("BloodArsenal:heart2");
        }
        else
        {
            itemIcon = iconRegister.registerIcon("BloodArsenal:heart");
        }
    }
}
