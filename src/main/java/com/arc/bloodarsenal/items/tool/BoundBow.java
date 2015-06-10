package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import java.util.List;

public class BoundBow extends ItemBow implements IBindable
{
    private static IIcon active;
    private static IIcon active_1;
    private static IIcon active_2;
    private static IIcon active_3;
    private static IIcon passive;

    private int energyUsed;

    public BoundBow()
    {
        setMaxStackSize(1);
        setMaxDamage(0);
        setUnlocalizedName("bound_bow");
        setCreativeTab(BloodArsenal.BA_TAB);
        setEnergyUsed(100);
        setFull3D();
    }

    public void setEnergyUsed(int i)
    {
        energyUsed = i;
    }

    public int getEnergyUsed()
    {
        return energyUsed;
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        int j = getMaxItemUseDuration(par1ItemStack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow))
        {
            float f = (float)j / 6.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 3.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
                entityarrow.setFire(100);
            }

            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
                entityarrow.setDamage(entityarrow.getDamage() * 1.259178F);
            }
        }

        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 200);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            setActivated(par1ItemStack, !getActivated(par1ItemStack));
            par1ItemStack.stackTagCompound.setInteger("worldTimeDelay", (int) (par2World.getWorldTime() - 1) % 100);
            return par1ItemStack;
        }

        if (!getActivated(par1ItemStack))
        {
            return par1ItemStack;
        }

        ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Items.arrow))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    @Override
    public int getItemEnchantability()
    {
        return 1;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 36000;
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (!(par3Entity instanceof EntityPlayer))
        {
            return;
        }

        EntityPlayer par3EntityPlayer = (EntityPlayer) par3Entity;

        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        if (par2World.getWorldTime() % 100 == par1ItemStack.stackTagCompound.getInteger("worldTimeDelay") && par1ItemStack.stackTagCompound.getBoolean("isActive"))
        {
            if (!par3EntityPlayer.capabilities.isCreativeMode)
            {
                EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 50);
            }
        }

        par1ItemStack.setItemDamage(0);
    }

    public void setActivated(ItemStack par1ItemStack, boolean newActivated)
    {
        NBTTagCompound itemTag = par1ItemStack.stackTagCompound;

        if (itemTag == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        itemTag.setBoolean("isActive", newActivated);
    }

    public boolean getActivated(ItemStack par1ItemStack)
    {
        NBTTagCompound itemTag = par1ItemStack.stackTagCompound;

        if (itemTag == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        return itemTag.getBoolean("isActive");
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.tool.bound_bow"));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            if (par1ItemStack.stackTagCompound.getBoolean("isActive"))
            {
                par3List.add("Activated");
            }
            else
            {
                par3List.add("Deactivated");
            }

            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("BloodArsenal:bound_bow");
        active = iconRegister.registerIcon("BloodArsenal:bound_bow");
        active_1 = iconRegister.registerIcon("BloodArsenal:bound_bow_pulling_0");
        active_2 = iconRegister.registerIcon("BloodArsenal:bound_bow_pulling_1");
        active_3 = iconRegister.registerIcon("BloodArsenal:bound_bow_pulling_2");
        passive = iconRegister.registerIcon("AlchemicalWizardry:SheathedItem");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = stack.stackTagCompound;

        if (tag.getBoolean("isActive"))
        {
            if (useRemaining > 0)
            {
                int draw = stack.getMaxItemUseDuration() - useRemaining;

                if (draw > 17)
                {
                    return active_3;
                }
                else if (draw > 13)
                {
                    return active_2;
                }
                else if (draw > 0)
                {
                    return active_1;
                }
            }
            return active;
        }
        else
        {
            return passive;
        }
    }
}
