package com.arc.bloodarsenal.items.sigil;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.api.items.interfaces.ISigil;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenalConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class SigilEnder extends EnergyItems implements IBindable, ISigil
{
    public SigilEnder()
    {
        super();
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.sigil.ender1"));
        par3List.add(StatCollector.translateToLocal("tooltip.sigil.ender2"));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        EnergyItems.checkAndSetItemOwner(itemStack, player);

        Vec3 playerPrevPos = Vec3.createVectorHelper(player.prevPosX, player.prevPosY, player.prevPosZ);

        if (itemStack.stackTagCompound == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        if (player.isSneaking() && !player.isSwingInProgress)
        {
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

                Vec3 vec3 = Vec3.createVectorHelper(ex, wy, zee);
                double distance = playerPrevPos.distanceTo(vec3);

                if (EnergyItems.getCurrentEssence(itemStack.getTagCompound().getString("ownerName")) >= distance * BloodArsenalConfig.enderSigilTeleportMultiplier || player.capabilities.isCreativeMode)
                {
                    for (int k = 0; k < 8; ++k)
                    {
                        world.spawnParticle("portal", player.posX + (world.rand.nextDouble() - 0.5D) * (double)player.width, player.posY + world.rand.nextDouble() * (double)player.height - 0.25D, player.posZ + (world.rand.nextDouble() - 0.5D) * (double)player.width, 0, 0, 0);
                        world.spawnParticle("portal", ex + (world.rand.nextDouble() - 0.5D) * (double)player.width, wy + world.rand.nextDouble() * (double) player.height - 0.25D, zee + (world.rand.nextDouble() - 0.5D) * (double)player.width, 0, 0, 0);
                    }

                    player.setPositionAndUpdate(ex, wy, zee);
                    EnergyItems.syphonBatteries(itemStack, player, (int) distance * BloodArsenalConfig.enderSigilTeleportMultiplier);
                    world.playSoundAtEntity(player, "mob.endermen.portal", 1F, 1F);
                    player.swingItem();
                    player.setVelocity(0.0D, 0.0D, 0.0D);
                }
            }
        }
        else if (!player.isSneaking())
        {
            player.displayGUIChest(player.getInventoryEnderChest());
            world.playSoundAtEntity(player, "mob.endermen.portal", 1F, 1F);
            EnergyItems.syphonBatteries(itemStack, player, BloodArsenalConfig.enderSigilOpenCost);
        }

        return itemStack;
    }
}
