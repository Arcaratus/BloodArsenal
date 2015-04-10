package com.arc.bloodarsenal.items.block;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import com.arc.bloodarsenal.block.BlockCompactedMRS;
import com.arc.bloodarsenal.block.ModBlocks;
import com.arc.bloodarsenal.tileentity.TileCompactedMRS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class CompactedMRSBlock extends ItemBlock
{
    public CompactedMRSBlock(Block block)
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
            addHiddenTooltip(par1ItemStack, par2EntityPlayer, par3List, par4);
        }
        else
        {
            addStringToTooltip(StatCollector.translateToLocal("tooltip.shiftinfo"), par3List);
        }
    }

    public void addHiddenTooltip(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        String ritualID = par1ItemStack.getTagCompound().getString("ritualName");
        String ritualName = Rituals.getNameOfRitual(ritualID);

        addStringToTooltip(StatCollector.translateToLocal("tooltip.ritualName") + " &c" + ritualName + "&7", par3List);
    }

    public void addStringToTooltip(String s, List<String> tooltip)
    {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }
}
