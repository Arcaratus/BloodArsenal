package com.arc.bloodarsenal.common.items.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class PortableAltarBlock extends ItemBlock
{
    public PortableAltarBlock(Block block)
    {
        super(block);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (GuiScreen.isShiftKeyDown())
        {
            addHiddenTooltip(par1ItemStack, par3List);
        }
        else
        {
            addStringToTooltip(StatCollector.translateToLocal("tooltip.shiftinfo"), par3List);
        }
    }

    public void addHiddenTooltip(ItemStack par1ItemStack, List par3List)
    {
        String capacity = par1ItemStack.getTagCompound().getString("capacity");
        int alterTier = par1ItemStack.getTagCompound().getInteger("upgradeLevel");
        int currentEssence = par1ItemStack.getTagCompound().getInteger("currentEssence");

        addStringToTooltip(StatCollector.translateToLocal("tooltip.altarTier") + " &c" + alterTier + "&7", par3List);
        addStringToTooltip(StatCollector.translateToLocal("tooltip.currentEssence") + " &c" + currentEssence + "&7", par3List);
        addStringToTooltip(StatCollector.translateToLocal("tooltip.capacity") + " &c" + capacity + "&7", par3List);
    }

    public void addStringToTooltip(String s, List<String> tooltip)
    {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }
}
