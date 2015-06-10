package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class InfusedDiamondSword extends ItemSword implements IBindable
{
    public InfusedDiamondSword()
    {
        super(BloodArsenal.infusedDiamond);
        setMaxStackSize(1);
        setUnlocalizedName("blood_infused_sword_diamond");
        setTextureName("BloodArsenal:blood_infused_sword_diamond");
        setCreativeTab(BloodArsenal.BA_TAB);
        setFull3D();
        setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer , List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.tool.blood_infused_diamond_sword"));

        if (par1ItemStack.getItemDamage() == 0)
        {
            par3List.add("Normal Mode");
        }
        else if (par1ItemStack.getItemDamage() == 1)
        {
            par3List.add("Area Mode");
        }
        else if (par1ItemStack.getItemDamage() == 2)
        {
            par3List.add("Fire Mode");
        }

        if (!(par1ItemStack.stackTagCompound == null))
        {
            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    boolean ignoreLeftClick = false;

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (!ignoreLeftClick && entity instanceof EntityLivingBase && ((EntityLivingBase) entity).hurtTime == 0 && !((EntityLivingBase) entity).isDead)
        {
            switch (ToolCapabilities.getMode(stack))
            {
                case 0:
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        EnergyItems.syphonBatteries(stack, player, 100);
                    }
                    break;
                }
                case 1:
                {
                    int range = 3;
                    List<Entity> entities = player.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
                    ignoreLeftClick = true;

                    for (Entity entity_ : entities)
                    {
                        player.attackTargetEntityWithCurrentItem(entity_);

                        if (!player.capabilities.isCreativeMode)
                        {
                            EnergyItems.syphonBatteries(stack, player, 3000);
                        }
                    }
                    ignoreLeftClick = false;
                    break;
                }
                case 2:
                {
                    EntityLivingBase living = (EntityLivingBase) entity;
                    living.setFire(3);

                    if (!player.capabilities.isCreativeMode)
                    {
                        EnergyItems.syphonBatteries(stack, player, 300);
                    }
                    break;
                }
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        if (par3EntityLivingBase instanceof EntityPlayer)
        {
            EnergyItems.checkAndSetItemOwner(par1ItemStack, (EntityPlayer) par3EntityLivingBase);
        }
        par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.weakness.id, 60, 2));
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            ToolCapabilities.changeMode(par1ItemStack);
        }

        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }
}

