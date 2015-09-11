package com.arc.bloodarsenal.common.items.bauble;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import com.arc.bloodarsenal.common.BloodArsenalConfig;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class EmpoweredSacrificeAmulet extends SacrificeAmulet implements IBauble, IBindable
{
    public EmpoweredSacrificeAmulet()
    {
        super();
        setHasSubtypes(true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @SubscribeEvent
    public void sacrificeHandler(LivingDeathEvent event)
    {
        Entity killer = event.source.getEntity();

        if (killer != null && killer instanceof EntityPlayerMP && !(killer instanceof FakePlayer) && BloodArsenalConfig.baublesIntegration)
        {
            EntityLivingBase victim = event.entityLiving;

            EntityPlayer player = (EntityPlayer) killer;
            InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);

                if (stack != null)
                {
                    if (stack.getItem() == ModItems.empowered_sacrifice_amulet)
                    {
                        EmpoweredSacrificeAmulet sacrificeAmulet = (EmpoweredSacrificeAmulet) ModItems.empowered_sacrifice_amulet;
                        float victimHealth = victim.getMaxHealth();
                        boolean healthGood = victimHealth > 4.0F;
                        int lpReceived = healthGood ? 200 : 50;
                        boolean shouldExecute = sacrificeAmulet.getStoredLP(stack) < 50000;

                        if (shouldExecute)
                        {
                            sacrificeAmulet.setStoredLP(stack, Math.min(sacrificeAmulet.getStoredLP(stack) + (lpReceived * 5), 50000));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.bauble.empowered_sacrifice"));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            par3List.add("Stored LP: " + EnumChatFormatting.RED + this.getStoredLP(par1ItemStack));

            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    @Override
    public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player)
    {
        if (par1ItemStack.getItemDamage() == 1)
        {
            String owner = par1ItemStack.stackTagCompound.getString("ownerName");
            World world = player.worldObj;

            if (!world.isRemote)
            {
                if (!owner.equals(""))
                {
                    if (player instanceof EntityPlayer)
                    {
                        if (owner.equals(SpellHelper.getUsername((EntityPlayer) player)))
                        {
                            if (!world.isRemote)
                            {
                                if (this.getStoredLP(par1ItemStack) >= 50)
                                {
                                    SoulNetworkHandler.addCurrentEssenceToMaximum(owner, 50, SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(owner)));
                                    setStoredLP(par1ItemStack, getStoredLP(par1ItemStack) - 50);
                                }
                                else if (this.getStoredLP(par1ItemStack) > 0)
                                {
                                    SoulNetworkHandler.addCurrentEssenceToMaximum(owner, this.getStoredLP(par1ItemStack), SoulNetworkHandler.getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(owner)));
                                    setStoredLP(par1ItemStack, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack par1ItemStack)
    {
        return BaubleType.AMULET;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        EnergyItems.checkAndSetItemOwner(itemStack, player);

        if (world.isRemote)
        {
            return itemStack;
        }

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);

        if (player.isSneaking())
        {
            if (getDamage(itemStack) == 1)
            {
                this.setDamage(itemStack, 0);
            }
            else
            {
                setDamage(itemStack, 1);
            }
        }

        if (movingobjectposition == null || movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.MISS)
        {
            return itemStack;
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
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.getItemDamage() == 1)
        {
            return super.getItemStackDisplayName(stack) + " " + StatCollector.translateToLocal("item.empowered_amulet_soul_network");
        }
        else
        {
            return super.getItemStackDisplayName(stack) + " " + StatCollector.translateToLocal("item.empowered_amulet_containing");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("BloodArsenal:sacrifice_amulet");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_, int pass)
    {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_)
    {
        return EnumRarity.rare;
    }
}
