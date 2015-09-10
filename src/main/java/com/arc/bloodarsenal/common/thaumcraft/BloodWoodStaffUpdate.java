/**
 *  Taken from SpitefulFox's ForbiddenMagic
 *  https://github.com/SpitefulFox/ForbiddenMagic
 */

package com.arc.bloodarsenal.common.thaumcraft;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;

public class BloodWoodStaffUpdate implements IWandRodOnUpdate
{
    Aspect[] primals = (Aspect[])Aspect.getPrimalAspects().toArray(new Aspect[0]);

    public BloodWoodStaffUpdate() {}

    @Override
    public void onUpdate(ItemStack itemstack, EntityPlayer player)
    {
        if (player.ticksExisted % 10 == 0)
        {
            try
            {
                if (!checkHotbar(itemstack, player)) return;

                SoulNetworkHandler.checkAndSetItemOwner(itemstack, player);

                int cost;
                if (((ItemWandCasting)itemstack.getItem()).getCap(itemstack).getTag().equals("alchemical"))
                {
                    cost = 12;
                }
                else if (((ItemWandCasting)itemstack.getItem()).getCap(itemstack).getTag().equals("blood_iron"))
                {
                    cost = 10;
                }
                else
                {
                    cost = 13;
                }

                for (int x = 0; x < primals.length; x++)
                {
                    int deficit = ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack) - ((ItemWandCasting)itemstack.getItem()).getVis(itemstack, primals[x]);
                    if (deficit > 0)
                    {
                        deficit = Math.min(deficit, 100);
                        if (player.capabilities.isCreativeMode)
                        {
                            ((ItemWandCasting) itemstack.getItem()).addVis(itemstack, primals[x], 1, true);
                        }
                        else if (SoulNetworkHandler.syphonFromNetwork(itemstack, cost * deficit) > 0)
                        {
                            ((ItemWandCasting) itemstack.getItem()).addVis(itemstack, primals[x], 1, true);
                            player.attackEntityFrom(DamageSource.starve, 1F);
                        }
                        else if (syphonHealth(player))
                        {
                            ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, primals[x], 1, true);
                            return;
                        }
                        else
                        {
                            return;
                        }
                    }
                }

            }
            catch (Exception e) {}
        }

    }

    public boolean syphonHealth(EntityPlayer player)
    {
        if (player.getHealth() > 0)
        {
            player.setHealth(player.getHealth() - 3);
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean checkHotbar(ItemStack stack, EntityPlayer player)
    {
        for (int x = 0; x < 9; ++x)
        {
            ItemStack item = player.inventory.getStackInSlot(x);
            if (item == stack)
            {
                return true;
            }
        }
        return false;
    }
}
