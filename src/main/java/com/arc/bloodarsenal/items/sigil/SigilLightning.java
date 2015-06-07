package com.arc.bloodarsenal.items.sigil;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.api.items.interfaces.ISigil;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class SigilLightning extends EnergyItems implements IBindable, ISigil
{
    public SigilLightning()
    {
        super();
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        int mode = par1ItemStack.getItemDamage();
        par3List.add(StatCollector.translateToLocal("tooltip.sigil.lightning"));
        par3List.add(StatCollector.translateToLocal("tooltip.sigil.lightning2") + (mode + 1));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        EnergyItems.checkAndSetItemOwner(itemStack, player);

        if (itemStack.stackTagCompound == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        if (player.isSneaking())
        {
            if (!player.isSwingInProgress)
            {
                changeMode(itemStack, true);
            }

            if (world.isRemote)
            {
                int mode = itemStack.getItemDamage();
                IChatComponent chatComponent = new ChatComponentText(StatCollector.translateToLocal("tooltip.sigil.lightning2") + (mode + 1));
                player.addChatComponentMessage(chatComponent);
            }

            player.swingItem();
        }
        else
        {
            int mode = itemStack.getItemDamage();
            MovingObjectPosition mop = SigilUtils.getTargetBlock(world, (player.prevPosX + (player.posX - player.prevPosX)),
                    (player.prevPosY + (player.posY - player.prevPosY) + 1.62 - player.yOffset),
                    (player.prevPosZ + (player.posZ - player.prevPosZ)),
                    (player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw)),
                    (player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch)), false, 128.0);

            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mop.sideHit != -1)
            {
                double ex = mop.hitVec.xCoord;
                double wy = mop.hitVec.yCoord;
                double zee = mop.hitVec.zCoord;

                switch (mop.sideHit)
                {
                    case 0:
                        wy -= 2.0;
                        break;
                    case 1:
                        break;
                    case 2:
                        zee -= 0.5;
                        break;
                    case 3:
                        zee += 0.5;
                        break;
                    case 4:
                        ex -= 0.5;
                        break;
                    case 5:
                        ex += 0.5;
                        break;
                }

                int cost = 0;

                switch (mode)
                {
                    case 0:
                        cost = 8000;
                        break;
                    case 1:
                        cost = 32000;
                        break;
                    case 2:
                        cost = 54000;
                        break;
                    case 3:
                        cost = 104000;
                        break;
                    case 4:
                        cost = 168000;
                        break;
                }

                if (EnergyItems.getCurrentEssence(itemStack.getTagCompound().getString("ownerName")) >= cost || player.capabilities.isCreativeMode)
                {
                    switch (mode)
                    {
                        case 0:
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee));
                            break;
                        case 1:
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee + 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee + 1));
                            break;
                        case 2:
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 2, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 2, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee + 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 1, wy, zee + 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee - 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 1, wy, zee - 2));
                            break;
                        case 3:
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 3, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 3, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 2, wy, zee + 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 2, wy, zee - 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 2, wy, zee + 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 2, wy, zee - 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee + 3));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee - 3));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee + 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee - 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 1, wy, zee + 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 1, wy, zee - 1));
                            break;
                        case 4:
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 3, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 3, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 2, wy, zee + 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 2, wy, zee - 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 2, wy, zee + 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 2, wy, zee - 2));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee + 3));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee - 3));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee + 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 1, wy, zee - 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 1, wy, zee + 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 1, wy, zee - 1));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 7, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 5, wy, zee + 5));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee + 7));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex + 5, wy, zee - 5));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 7, wy, zee));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 5, wy, zee - 5));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex, wy, zee - 7));
                            world.spawnEntityInWorld(new EntityLightningBolt(world, ex - 5, wy, zee + 5));
                            break;
                    }

                    player.swingItem();
                    EnergyItems.syphonBatteries(itemStack, player, cost);
                }
            }
        }

        return itemStack;
    }

    private void changeMode(ItemStack itemStack, boolean next)
    {
        int mode = itemStack.getItemDamage();
        if (next)
        {
            if (mode < 4)
            {
                mode++;
            }
            else
            {
                mode = 0;
            }
        }

        itemStack.setItemDamage(mode);
    }
}
