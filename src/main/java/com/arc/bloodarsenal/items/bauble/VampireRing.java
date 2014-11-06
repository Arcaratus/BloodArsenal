package com.arc.bloodarsenal.items.bauble;

import baubles.api.BaubleType;
import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.BloodArsenalConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class VampireRing extends ItemBauble
{
    public VampireRing()
    {
        super();
        setUnlocalizedName("vampire_ring");
        setTextureName("BloodArsenal:vampire_ring");
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
    public BaubleType getBaubleType(ItemStack par1ItemStack)
    {
        return BaubleType.RING;
    }
}
