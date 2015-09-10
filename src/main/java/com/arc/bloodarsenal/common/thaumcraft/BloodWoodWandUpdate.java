/**
 *  Taken from SpitefulFox's ForbiddenMagic
 *  https://github.com/SpitefulFox/ForbiddenMagic
 */

package com.arc.bloodarsenal.common.thaumcraft;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;

public class BloodWoodWandUpdate implements IWandRodOnUpdate
{
    Aspect[] primals = (Aspect[])Aspect.getPrimalAspects().toArray(new Aspect[0]);

    public BloodWoodWandUpdate() {}

    public void onUpdate(ItemStack itemstack, EntityPlayer player)
    {
        if (player.ticksExisted % 50 == 0)
        {
            try
            {
                SoulNetworkHandler.checkAndSetItemOwner(itemstack, player);
                byte e;
                if (((ItemWandCasting)itemstack.getItem()).getCap(itemstack).getTag().equals("alchemical"))
                {
                    e = 12;
                }
                else if (((ItemWandCasting)itemstack.getItem()).getCap(itemstack).getTag().equals("blood_iron"))
                {
                    e = 10;
                }
                else
                {
                    e = 13;
                }

                for (int x = 0; x < this.primals.length; ++x)
                {
                    int deficit = ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack) - ((ItemWandCasting)itemstack.getItem()).getVis(itemstack, this.primals[x]);
                    if (deficit > 0)
                    {
                        deficit = Math.min(deficit, 100);
                        if (player.capabilities.isCreativeMode)
                        {
                            ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.primals[x], 1, true);
                        }
                        else
                        {
                            if (SoulNetworkHandler.syphonFromNetwork(itemstack, e * deficit) <= 0)
                            {
                                if (this.syphonHealth(player))
                                {
                                    ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.primals[x], 1, true);
                                    return;
                                }

                                return;
                            }

                            ((ItemWandCasting)itemstack.getItem()).addVis(itemstack, this.primals[x], 1, true);
                        }
                    }
                }
            }
            catch (Throwable var6)
            {

            }
        }

    }

    public boolean syphonHealth(EntityPlayer player)
    {
        if (player.getHealth() > 6.0F)
        {
            player.setHealth(player.getHealth() - 3.0F);
            return true;
        }
        else
        {
            return false;
        }
    }
}
