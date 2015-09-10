package com.arc.bloodarsenal.common.items.armor;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.items.ModItems;
import com.arc.bloodarsenal.common.items.tool.IFillable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;
import java.util.Random;

public class ElementalLifeImbuedArmor extends ItemArmor implements ISpecialArmor, IFillable
{
    private final int maxLP = 100000;
    private String LP_STORED = "LPStored";

    public ElementalLifeImbuedArmor(int armorType)
    {
        super(BloodArsenal.elementalLifeImbuedArmor, 0, armorType);
    }

    /** IFillable */
    @Override
    public int getMaxLP()
    {
        return maxLP;
    }

    @Override
    public void incrementLPStored(ItemStack itemStack, int incrementAmount)
    {
        setCurrentLPStored(itemStack, getLPStored(itemStack) + incrementAmount);
    }

    @Override
    public int getLPStored(ItemStack container)
    {
        if (container.stackTagCompound == null)
        {
            setCurrentLPStored(container, 0);
        }

        return container.stackTagCompound.getInteger(LP_STORED);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.armor.elementalLifeImbuedArmor"));
        par3List.add(StatCollector.translateToLocal("tooltip.fillable.currentAmount") + " " + EnumChatFormatting.RED + par1ItemStack.getTagCompound().getInteger(LP_STORED));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if (this != ModItems.life_imbued_leggings)
        {
            return "BloodArsenal:models/armor/life_imbued_layer_1.png";
        }
        else
        {
            return "BloodArsenal:models/armor/life_imbued_layer_2.png";
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack)
    {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return maxLP;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.rare;
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        super.setDamage(stack, 0);
    }

    @Override
    public boolean isDamaged(ItemStack stack)
    {
        return true;
    }

    @Override
    public int getDisplayDamage(ItemStack stack)
    {
        if (stack.stackTagCompound == null)
        {
            setCurrentLPStored(stack, 0);
        }

        return maxLP - stack.stackTagCompound.getInteger(LP_STORED);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.stackTagCompound == null || !stack.stackTagCompound.getBoolean("CreativeTab");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(setCurrentLPStored(new ItemStack(item, 1, 0), 0));
        list.add(setCurrentLPStored(new ItemStack(item, 1, 0), maxLP));
    }

    private ItemStack setCurrentLPStored(ItemStack itemStack, int amount)
    {
        if (itemStack.stackTagCompound == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        itemStack.getTagCompound().setInteger(LP_STORED, amount);
        return itemStack;
    }

    protected int getBaseAbsorption()
    {
        return 18;
    }

    protected int getAbsorptionRatio()
    {
        switch (armorType)
        {
            case 0:
                return 15;
            case 1:
                return 40;
            case 2:
                return 30;
            case 3:
                return 15;
        }

        return 0;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        if (getLPStored(armor) >= getLPPerDamage(armor))
        {
            return Math.min(getBaseAbsorption(), 20) * getAbsorptionRatio() / 100;
        }
        return 0;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
    {
        final double absorbRatio = 0.5;

        if (source.isUnblockable())
        {
            int absorbMax = getLPPerDamage(armor) > 0 ? 25 * getLPStored(armor) / getLPPerDamage(armor) : 0;
            return new ArmorProperties(0, absorbRatio * getArmorMaterial().getDamageReductionAmount(armorType) * 0.025, absorbMax);
        }

        int absorbMax = getLPPerDamage(armor) > 0 ? 25 * getLPStored(armor) / getLPPerDamage(armor) : 0;
        return new ArmorProperties(0, absorbRatio * getArmorMaterial().getDamageReductionAmount(armorType) * 0.05, absorbMax);
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack armor, DamageSource source, int damage, int slot)
    {
        subtractLP(armor, damage * getLPPerDamage(armor), false);
    }

    protected int getLPPerDamage(ItemStack stack)
    {
        int unbreakingLevel = MathHelper.clamp_int(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack), 0, 4);
        return 100 * (5 - unbreakingLevel) / 5;
    }

    public int subtractLP(ItemStack container, int maxExtract, boolean simulate)
    {
        if (container.stackTagCompound == null)
        {
            setCurrentLPStored(container, 0);
        }

        int stored = container.stackTagCompound.getInteger(LP_STORED);
        int extract = Math.min(maxExtract, stored);

        if (!simulate)
        {
            stored -= extract;
            container.stackTagCompound.setInteger(LP_STORED, stored);
        }

        return extract;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack armor)
    {
        if (hasArmorSetItem(player, 0) && hasArmorSetItem(player, 1) && hasArmorSetItem(player, 2) && hasArmorSetItem(player, 3))
        {
            if (player.getHealth() < player.getMaxHealth() && !player.isPotionActive(AlchemicalWizardry.customPotionInhibit))
            {
                if (world.getWorldTime() % 50 == 0)
                {
                    Random random = new Random();

                    if (random.nextInt(9) > 7)
                    {
                        LifeImbuedArmor thisArmor = (LifeImbuedArmor) armor.getItem();
                        if (thisArmor.getLPStored(armor) >= 2000)
                        {
                            float healHealth = player.getMaxHealth() - player.getHealth();
                            player.heal(healHealth);
                            thisArmor.subtractLP(armor, ((int) healHealth) * 500, false);
                        }
                    }
                }
            }
        }
    }

    public boolean hasArmorSetItem(EntityPlayer player, int i)
    {
        ItemStack stack = player.inventory.armorInventory[3 - i];

        if (stack == null)
        {
            return false;
        }

        switch (i)
        {
            case 0: return stack.getItem() == ModItems.life_imbued_helmet_water;
            case 1: return stack.getItem() == ModItems.life_imbued_chestplate_fire;
            case 2: return stack.getItem() == ModItems.life_imbued_leggings_earth;
            case 3: return stack.getItem() == ModItems.life_imbued_boots_air;
        }

        return false;
    }
}
