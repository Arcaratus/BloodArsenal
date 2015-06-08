package com.arc.bloodarsenal.items.armor;

import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.renderer.model.ModelVampireCostume;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Calendar;

public class VampireCostume extends ItemArmor
{
    private static IIcon chestIcon;
    private static IIcon leggingsIcon;
    private static IIcon bootsIcon;

    private static final boolean tryComplexRendering = true;

    public VampireCostume(int armorType)
    {
        super(BloodArsenal.vampireArmor, 0, armorType);
        setMaxDamage(0);
    }

    ModelBiped model1 = null;
    ModelBiped model2 = null;
    ModelBiped model = null;

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
        if (tryComplexRendering)
        {
            int type = ((ItemArmor) itemStack.getItem()).armorType;

            if (this.model1 == null)
            {
                this.model1 = new ModelVampireCostume(1.0f, false, true, false, true);
            }

            if (this.model2 == null)
            {
                this.model2 = new ModelVampireCostume(0.5f, false, false, true, false);
            }

            if (type == 1 || type == 3)
            {
                this.model = model1;
            }
            else
            {
                this.model = model2;
            }

            if (this.model != null)
            {
                this.model.bipedHead.showModel = (type == 1);
                this.model.bipedHeadwear.showModel = (type == 1);
                this.model.bipedBody.showModel = (type == 1);
                this.model.bipedLeftLeg.showModel = (type == 2 || type == 3);
                this.model.bipedRightLeg.showModel = (type == 2 || type == 3);
                this.model.isSneak = entityLiving.isSneaking();

                this.model.isRiding = entityLiving.isRiding();
                this.model.isChild = entityLiving.isChild();

                this.model.aimedBow = false;
                this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);

                if (entityLiving instanceof EntityPlayer)
                {
                    if (((EntityPlayer) entityLiving).getItemInUseDuration() > 0)
                    {
                        EnumAction enumaction = ((EntityPlayer) entityLiving).getItemInUse().getItemUseAction();

                        if (enumaction == EnumAction.block)
                        {
                            this.model.heldItemRight = 3;
                        }
                        else if (enumaction == EnumAction.bow)
                        {
                            this.model.aimedBow = true;
                        }
                    }
                }
            }

            return model;
        }
        else
        {
            return super.getArmorModel(entityLiving, itemStack, armorSlot);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("AlchemicalWizardry:SheathedItem");
        chestIcon = iconRegister.registerIcon("BloodArsenal:vampire_cape");
        leggingsIcon = iconRegister.registerIcon("BloodArsenal:vampire_greaves");
        bootsIcon = iconRegister.registerIcon("BloodArsenal:vampire_boots");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        if (equals(ModItems.vampire_cape))
        {
            return chestIcon;
        }

        if (equals(ModItems.vampire_greaves))
        {
            return leggingsIcon;
        }

        if (equals(ModItems.vampire_boots))
        {
            return bootsIcon;
        }

        return itemIcon;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if (tryComplexRendering)
        {
            return "BloodArsenal:models/armor/VampireCostume.png";
        }

        if (entity instanceof EntityLivingBase)
        {
            if (this.getIsInvisible(stack))
            {
                if (this == ModItems.vampire_cape || this == ModItems.vampire_boots)
                {
                    return "BloodArsenal:models/armor/VampireCostume_invisible.png";
                }

                if (this == ModItems.vampire_greaves)
                {
                    return "BloodArsenal:models/armor/VampireCostume_invisible2.png";
                }
            }
        }

        if (this == ModItems.vampire_cape || this == ModItems.vampire_boots)
        {
            return "AlchemicalWizardry:models/armor/boundArmour_layer_1.png";
        }

        if (this == ModItems.vampire_greaves)
        {
            return "AlchemicalWizardry:models/armor/boundArmour_layer_2.png";
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack armor)
    {
        if (player.getActivePotionEffect(Potion.damageBoost) != null)
        {
            player.removePotionEffect(Potion.damageBoost.id);
        }
        player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2, 1));

        setIsInvisible(armor, player.isPotionActive(Potion.invisibility.id));
    }

    public boolean getIsInvisible(ItemStack itemStack)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

        if (tag != null)
        {
            return tag.getBoolean("invisible");
        }

        return false;
    }

    public void setIsInvisible(ItemStack itemStack, boolean invisible)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

        if (tag == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
            tag = itemStack.getTagCompound();
        }

        tag.setBoolean("invisible", invisible);
    }
}
