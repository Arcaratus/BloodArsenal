package com.arc.bloodarsenal.items.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.BloodArsenalConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class VampireRing extends ItemBauble implements IBauble
{
    public VampireRing()
    {
        super();
        setMaxStackSize(1);
        setHasSubtypes(true);
        setUnlocalizedName("vampire_ring");
    }

    @Override
    public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player)
    {
        super.onWornTick(par1ItemStack, player);

        if (player instanceof EntityPlayer && !player.worldObj.isRemote)
        {
            if (player.getActivePotionEffect(BloodArsenal.vampiricAura) != null)
            {
                player.removePotionEffect(BloodArsenalConfig.vampiricAuraID);
            }
            player.addPotionEffect(new PotionEffect(BloodArsenalConfig.vampiricAuraID, Integer.MAX_VALUE, 0, true));
        }
    }

    @Override
    public void onUnequipped(ItemStack par1ItemStack, EntityLivingBase player)
    {
        PotionEffect effect = player.getActivePotionEffect(BloodArsenal.vampiricAura);

        if (effect != null)
        {
            player.removePotionEffect(BloodArsenalConfig.vampiricAuraID);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("BloodArsenal:vampire_ring");
    }

    @Override
    public BaubleType getBaubleType(ItemStack par1ItemStack)
    {
        return BaubleType.RING;
    }
}
