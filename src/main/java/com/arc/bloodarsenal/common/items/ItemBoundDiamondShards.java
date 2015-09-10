package com.arc.bloodarsenal.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBoundDiamondShards extends Item
{
    private final String[] shardTypes = new String[]{"", "air", "earth", "fire", "water", "dusk", "dawn"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemBoundDiamondShards()
    {
        super();
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[shardTypes.length];

        for (int i = 0; i < shardTypes.length; ++i)
        {
            icons[i] = iconRegister.registerIcon("BloodArsenal:" + "bound_diamond_shard_" + shardTypes[i]);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, shardTypes.length - 1);

        return "item.bound_diamond_shard_" + shardTypes[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, shardTypes.length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < shardTypes.length; ++meta)
        {
            list.add(new ItemStack(id, 1, meta));
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.boundDiamondShard.shard"));
    }
}
