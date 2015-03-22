package com.arc.bloodarsenal.items;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBloodArsenal extends Item
{
    private static final String[] ITEM_NAMES = new String[] {"amorphic_catalyst", "blood_infused_stick", "blood_infused_iron", "soul_fragment", "orange_juice", "blood_infused_glowstone_dust", "wolf_hide"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemBloodArsenal()
    {
        super();
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
/*        icons = new IIcon[ITEM_NAMES.length];

        for (int i = 0; i < ITEM_NAMES.length; ++i)
        {
            icons[i] = iconRegister.registerIcon("BloodArsenal:" + ITEM_NAMES[i]);
        }

        if (AlchemicalWizardry.wimpySettings)
        {
            ModItems.heart.setTextureName("BloodArsenal:heart2");
        }
        else
        {
            ModItems.heart.setTextureName("BloodArsenal:heart");
        }
        */

        if (this.equals(ModItems.amorphic_catalyst))
            itemIcon = iconRegister.registerIcon("BloodArsenal:amorphic_catalyst");
        else if (this.equals(ModItems.blood_infused_iron))
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_iron");
        else if (this.equals(ModItems.soul_fragment))
            itemIcon = iconRegister.registerIcon("BloodArsenal:soul_fragment");
        else if (this.equals(ModItems.heart))
            itemIcon = iconRegister.registerIcon("BloodArsenal:heart2");
        else if (this.equals(ModItems.blood_infused_stick))
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_stick");
        else if (this.equals(ModItems.blood_cookie))
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_cookie");
        else if (this.equals(ModItems.orange_juice))
            itemIcon = iconRegister.registerIcon("BloodArsenal:orange_juice");
        else if (this.equals(ModItems.blood_infused_glowstone_dust))
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_infused_glowstone_dust");
        else if (this.equals(ModItems.wolf_hide))
            itemIcon = iconRegister.registerIcon("BloodArsenal:wolf_hide");
        else if (this.equals(ModItems.blood_burned_string))
            itemIcon = iconRegister.registerIcon("BloodArsenal:blood_burned_string");
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (this.equals(ModItems.amorphic_catalyst))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.itemba.amorphic_catalyst"));
        }
        if (this.equals(ModItems.blood_infused_stick))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.itemba.blood_infused_stick"));
        }
        if (this.equals(ModItems.blood_infused_iron))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.itemba.blood_infused_iron1"));
            par3List.add(StatCollector.translateToLocal("tooltip.itemba.blood_infused_iron2"));
        }
        if (this.equals(ModItems.heart))
        {
            if (AlchemicalWizardry.wimpySettings)
            {
                par3List.add(StatCollector.translateToLocal("tooltip.itemba.heart1"));
            }
            else
            {
                par3List.add(StatCollector.translateToLocal("tooltip.itemba.heart2"));
            }
        }
        if (this.equals(ModItems.soul_fragment))
        {
            par3List.add(StatCollector.translateToLocal("tooltip.itemba.soul_fragment"));
        }
    }
}
