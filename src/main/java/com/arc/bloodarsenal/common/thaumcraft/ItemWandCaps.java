/**
 *  Taken from SpitefulFox's ForbiddenMagic
 *  https://github.com/SpitefulFox/ForbiddenMagic
 */

package com.arc.bloodarsenal.common.thaumcraft;

import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemWandCaps extends Item
{
    public final String[] types = new String[]{"blood_iron"};
    public IIcon[] icon;

    public ItemWandCaps()
    {
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        this.icon = new IIcon[this.types.length];

        for (int x = 0; x < this.types.length; ++x)
        {
            this.icon[x] = ir.registerIcon("BloodArsenal:wand_cap_" + this.types[x]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.icon[p_77617_1_];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int wand = 0; wand < this.types.length; ++wand)
        {
            list.add(new ItemStack(this, 1, wand));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return super.getUnlocalizedName() + "." + this.types[p_77667_1_.getItemDamage()];
    }
}
