package com.arc.bloodarsenal.common.items;

import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBloodMoney extends Item
{
    private final int[] MONEY_MULTIPLIER = new int[]{1, 4, 16, 64};

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
        int maxLP = SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(player));
        int currentEssence = SoulNetworkHandler.getCurrentEssence(player);
        int CONSTANT = 10000;

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
            if ((maxLP - currentEssence) >= (MONEY_MULTIPLIER[meta] * CONSTANT) && maxLP >= (MONEY_MULTIPLIER[meta] * CONSTANT))
            {
                SoulNetworkHandler.addCurrentEssenceToMaximum(player, MONEY_MULTIPLIER[meta] * CONSTANT, maxLP);
            }
            else
            {
                par3EntityPlayer.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.bloodMoney.tooMuch")));
                return par1ItemStack;
            }
        }
        else if (meta == 1)
        {
            if ((maxLP - currentEssence) >= (MONEY_MULTIPLIER[meta] * CONSTANT) && maxLP >= (MONEY_MULTIPLIER[meta] * CONSTANT))
            {
                SoulNetworkHandler.addCurrentEssenceToMaximum(player, MONEY_MULTIPLIER[meta] * CONSTANT, maxLP);
            }
            else
            {
                par3EntityPlayer.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.bloodMoney.tooMuch")));
                return par1ItemStack;
            }
        }
        else if (meta == 2)
        {
            if ((maxLP - currentEssence) >= (MONEY_MULTIPLIER[meta] * CONSTANT) && maxLP >= (MONEY_MULTIPLIER[meta] * CONSTANT))
            {
                SoulNetworkHandler.addCurrentEssenceToMaximum(player, MONEY_MULTIPLIER[meta] * CONSTANT, maxLP);
            }
            else
            {
                par3EntityPlayer.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.bloodMoney.tooMuch")));
                return par1ItemStack;
            }
        }
        else if (meta == 3)
        {
            if ((maxLP - currentEssence) >= (MONEY_MULTIPLIER[meta] * CONSTANT) && maxLP >= (MONEY_MULTIPLIER[meta] * CONSTANT))
            {
                SoulNetworkHandler.addCurrentEssenceToMaximum(player, MONEY_MULTIPLIER[meta] * CONSTANT, maxLP);
            }
            else
            {
                par3EntityPlayer.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.bloodMoney.tooMuch")));
                return par1ItemStack;
            }
        }

        ItemStack currentStack = par3EntityPlayer.inventory.getCurrentItem();
        --currentStack.stackSize;
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

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.bloodMoney.money"));
    }
}
