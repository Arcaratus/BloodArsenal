package com.arc.bloodarsenal.items.tool;

import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class InfusedDiamond extends Item
{
    public InfusedDiamond()
    {
        super();
        setMaxStackSize(1);
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        if (equals(ModItems.blood_diamond))
        {
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_diamond");
        }
        else if (equals(ModItems.blood_infused_diamond_unactive))
        {
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_diamond_unactive");
        }
        else if (equals(ModItems.blood_infused_diamond_active))
        {
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_diamond_active");
        }
        else if (equals(ModItems.blood_infused_diamond_bound))
        {
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_diamond_bound");
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        if (equals(ModItems.blood_infused_diamond_bound))
        {
            return EnumRarity.uncommon;
        }
        else
        {
            return EnumRarity.common;
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (equals(ModItems.blood_infused_diamond_unactive))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_diamond_unactive1"));
            par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_diamond_unactive2"));
        }
        if (equals(ModItems.blood_infused_diamond_active))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_diamond_active1"));
            par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_diamond_active2"));
        }
        if (equals(ModItems.blood_infused_diamond_bound))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_diamond_bound"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return equals(ModItems.blood_infused_diamond_bound);
    }
}
