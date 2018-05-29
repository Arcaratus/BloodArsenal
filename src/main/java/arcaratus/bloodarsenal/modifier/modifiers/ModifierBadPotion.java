package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;

public class ModifierBadPotion extends Modifier
{
    public ModifierBadPotion()
    {
        super(Constants.Modifiers.BAD_POTION, Constants.Modifiers.BAD_POTION_COUNTER.length, EnumModifierType.HEAD);
        setAltName();
    }

    @Override
    public String getAlternateName(ItemStack itemStack)
    {
        return itemStack.hasTagCompound() ? itemStack.getTagCompound().getString(Constants.NBT.ITEMSTACK_NAME) : "何だよ!";
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker, int level)
    {
        if (random.nextInt(level + 1) >= random.nextInt(getMaxLevel()))
        {
            if (itemStack.hasTagCompound())
            {
                NBTTagCompound data = itemStack.getTagCompound().getCompoundTag(Constants.NBT.ITEMSTACK);
                Potion potion = PotionUtils.getEffectsFromStack(new ItemStack(data)).get(0).getPotion();

                if (potion.isInstant())
                    potion.affectEntity(attacker, attacker, target, level, 1);
                else
                    target.addPotionEffect(new PotionEffect(potion, 20 + 40 * (level + 1), level));

                NewModifiable.incrementModifierTracker(itemStack, this, 1);
            }
        }
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra, int level)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        NBTTagCompound potionTag = new NBTTagCompound();
        String potionName = "";

        if (!extra.isEmpty() && extra.getItem() instanceof ItemPotion)
        {
            extra.writeToNBT(potionTag);
            potionName = PotionUtils.getEffectsFromStack(extra).get(0).getPotion().getName();
        }

        tag.setTag(Constants.NBT.ITEMSTACK, potionTag);
        tag.setString(Constants.NBT.ITEMSTACK_NAME, potionName);
    }

    @Override
    public NBTTagCompound getSpecialNBT(ItemStack itemStack)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        NBTTagCompound specialTag = new NBTTagCompound();
        specialTag.setTag(Constants.NBT.ITEMSTACK, tag.getTag(Constants.NBT.ITEMSTACK));
        specialTag.setString(Constants.NBT.ITEMSTACK_NAME, tag.getString(Constants.NBT.ITEMSTACK_NAME));

        return specialTag;
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        if (tag != null)
        {
            tag.removeTag(Constants.NBT.ITEMSTACK);
            tag.removeTag(Constants.NBT.ITEMSTACK_NAME);
        }
    }
}
