package com.arc.bloodarsenal.items.tinkers;

import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import tconstruct.library.util.IToolPart;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PartHelper extends Item implements IToolPart
{
    protected String defaultTexture;
    protected int defaultMaterialID;
    protected String unlocalizedPartName;
    protected HashMap<Integer, String> unlocalizedMaterialNames = new HashMap();
    protected HashMap<Integer, String> textures = new HashMap();
    protected HashMap<Integer, IIcon> icons = new HashMap();
    protected static final String texturePath = "BloodArsenal".toLowerCase(Locale.ENGLISH) + ":" + "tinkers/parts/";

    public PartHelper(String defaultUnlocalizedPartName, int defaultMaterialID, String defaultTexture)
    {
        setCreativeTab(BloodArsenal.BA_TAB);
        setMaxStackSize(64);
        setMaxDamage(0);
        setHasSubtypes(true);

        setUnlocalizedName("default." + defaultUnlocalizedPartName);
        this.unlocalizedPartName = defaultUnlocalizedPartName;
        this.defaultMaterialID = defaultMaterialID;
        this.defaultTexture = (texturePath + defaultTexture);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon)
    {
        this.itemIcon = icon.registerIcon(this.defaultTexture);
        for (Map.Entry<Integer, String> texture : this.textures.entrySet())
        {
            int materialID = (Integer)texture.getKey().intValue();
            String textureName = (String)texture.getValue();

            IIcon _icon = icon.registerIcon(texturePath + textureName);

            this.icons.put(Integer.valueOf(materialID), _icon);
        }
    }

    public int getMaterialID(ItemStack itemStack)
    {
        int damageValue = itemStack.getItemDamage();
        if (damageValue >= 100)
        {
            return itemStack.getItemDamage();
        }
        return this.defaultMaterialID;
    }

    public void addMaterial(int materialId, String unlocalizedName, String texture)
    {
        this.unlocalizedMaterialNames.put(Integer.valueOf(materialId), unlocalizedName);
        this.textures.put(Integer.valueOf(materialId), texture);
    }

    public IIcon getIconFromDamage(int damageValue)
    {
        if (this.icons.containsKey(Integer.valueOf(damageValue)))
        {
            return (IIcon)this.icons.get(Integer.valueOf(damageValue));
        }
        return this.itemIcon;
    }

    public void getSubItems(Item item, CreativeTabs tab, List itemStackList)
    {
        if (!this.unlocalizedMaterialNames.isEmpty())
        {
            for (Map.Entry<Integer, String> subItem : this.unlocalizedMaterialNames.entrySet())
            {
                int materialId = (Integer)subItem.getKey().intValue();
                itemStackList.add(new ItemStack(item, 1, materialId));
            }
        }
        else
        {
            itemStackList.add(new ItemStack(this, 1));
        }
    }

    public String getUnlocalizedName(ItemStack itemStack)
    {
        int damageValue = itemStack.getItemDamage();

        if (this.unlocalizedMaterialNames.containsKey(Integer.valueOf(damageValue)))
        {
            return "item." + (String)this.unlocalizedMaterialNames.get(Integer.valueOf(damageValue)) + "." + this.unlocalizedPartName;
        }
        return getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack itemStack)
    {
        int damageValue = itemStack.getItemDamage();
        String displayName;

        if (this.unlocalizedMaterialNames.containsKey(Integer.valueOf(damageValue)))
        {
            String unlocalizedMaterialName = (String)this.unlocalizedMaterialNames.get(Integer.valueOf(damageValue));
            if ((StatCollector.canTranslate("part." + this.unlocalizedPartName)) && (StatCollector.canTranslate("material." + unlocalizedMaterialName)))
            {
                String localizedPartName = StatCollector.translateToLocal("part." + this.unlocalizedPartName);
                String localizedMaterialName = StatCollector.translateToLocal("material." + unlocalizedMaterialName);
                displayName = localizedPartName.replaceAll("@material@", localizedMaterialName);
            }
            else
            {
                displayName = getUnlocalizedName(itemStack) + ".name";
            }
        }
        else
        {
            displayName = getUnlocalizedName() + ".name";
        }
        return displayName;
    }
}
