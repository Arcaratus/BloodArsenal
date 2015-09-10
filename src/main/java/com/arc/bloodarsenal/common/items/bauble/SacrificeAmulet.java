package com.arc.bloodarsenal.common.items.bauble;

import WayofTime.alchemicalWizardry.api.items.IAltarManipulator;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import baubles.common.network.PacketHandler;
import baubles.common.network.PacketSyncBauble;
import com.arc.bloodarsenal.common.BloodArsenalConfig;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class SacrificeAmulet extends ItemBauble implements IAltarManipulator, IBauble
{
    public SacrificeAmulet()
    {
        super();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void sacrificeHandler(LivingDeathEvent event)
    {
        Entity killer = event.source.getEntity();

        if (killer != null && killer instanceof EntityPlayer && BloodArsenalConfig.baublesIntegration)
        {
            EntityLivingBase victim = event.entityLiving;

            EntityPlayer player = (EntityPlayer) killer;
            InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);

                if (stack != null)
                {
                    if (stack.getItem() == ModItems.sacrifice_amulet)
                    {
                        SacrificeAmulet sacrificeAmulet = (SacrificeAmulet) ModItems.sacrifice_amulet;
                        float victimHealth = victim.getMaxHealth();
                        boolean healthGood = victimHealth > 4.0F;
                        int lpReceived = healthGood ? 200 : 50;
                        boolean shouldExecute = sacrificeAmulet.getStoredLP(stack) < 10000;

                        if (shouldExecute)
                        {
                            sacrificeAmulet.setStoredLP(stack, Math.min(sacrificeAmulet.getStoredLP(stack) + (lpReceived * 2), 10000));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.bauble.sacrifice"));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Stored LP: " + EnumChatFormatting.RED + this.getStoredLP(par1ItemStack));
        }
    }

    @Override
    public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player)
    {
        super.onWornTick(par1ItemStack, player);
    }

    @Override
    public BaubleType getBaubleType(ItemStack par1ItemStack)
    {
        return BaubleType.AMULET;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (world.isRemote)
        {
            return itemStack;
        }

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);

        if (movingobjectposition == null)
        {
            return super.onItemRightClick(itemStack, world, player);
        }
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                TileEntity tile = world.getTileEntity(x, y, z);

                if (!(tile instanceof TEAltar))
                {
                    return super.onItemRightClick(itemStack, world, player);
                }

                TEAltar altar = (TEAltar)tile;

                if (!altar.isActive())
                {
                    int amount = this.getStoredLP(itemStack);
                    if (amount > 0)
                    {
                        int filledAmount = altar.fillMainTank(amount);
                        amount -= filledAmount;
                        this.setStoredLP(itemStack, amount);

                        world.markBlockForUpdate(x, y, z);
                    }
                }
            }
        }

        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("BloodArsenal:sacrifice_amulet");
    }

    public void setStoredLP(ItemStack itemStack, int lp)
    {
        if (!itemStack.hasTagCompound())
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();

        tag.setInteger("storedLP", lp);
    }

    public int getStoredLP(ItemStack itemStack)
    {
        if (!itemStack.hasTagCompound())
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();

        return tag.getInteger("storedLP");
    }
}
