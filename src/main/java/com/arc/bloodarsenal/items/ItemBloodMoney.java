package com.arc.bloodarsenal.items;

import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemBloodMoney extends Item
{
    private static final int[] MONEY_MULTIPLIER = new int[]{1, 4, 16, 64};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemBloodMoney()
    {
        super();
        setUnlocalizedName("blood_money");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[MONEY_MULTIPLIER.length];

        for (int i = 0; i < MONEY_MULTIPLIER.length; ++i)
        {
            icons[i] = iconRegister.registerIcon("BloodArsenal:" + "blood_money" + MONEY_MULTIPLIER[i]);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        String player = par3EntityPlayer.getCommandSenderName();
        World world = par3EntityPlayer.worldObj;
        LifeEssenceNetwork data = (LifeEssenceNetwork) world.loadItemData(LifeEssenceNetwork.class, player);

        if (data == null)
        {
            data = new LifeEssenceNetwork(player);
            world.setItemData(player, data);
        }

        if (world.isRemote)
        {
            return par1ItemStack;
        }

        if (SpellHelper.isFakePlayer(par2World, par3EntityPlayer))
        {
            return par1ItemStack;
        }

        int meta = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, MONEY_MULTIPLIER.length - 1);

        if (meta == 0)
        {
            SoulNetworkHandler.addCurrentEssenceToMaximum(player, 10000, Integer.MAX_VALUE);
        }
        else if (meta == 1)
        {
            SoulNetworkHandler.addCurrentEssenceToMaximum(player, 40000, Integer.MAX_VALUE);
        }
        else if (meta == 2)
        {
            SoulNetworkHandler.addCurrentEssenceToMaximum(player, 160000, Integer.MAX_VALUE);
        }
        else if (meta == 3)
        {
            SoulNetworkHandler.addCurrentEssenceToMaximum(player, 640000, Integer.MAX_VALUE);
        }

        return par1ItemStack;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, MONEY_MULTIPLIER.length - 1);

        return ("" + "item.blood_money" + MONEY_MULTIPLIER[meta]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, MONEY_MULTIPLIER.length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < MONEY_MULTIPLIER.length; ++meta)
        {
            list.add(new ItemStack(id, 1, meta));
        }
    }
}
