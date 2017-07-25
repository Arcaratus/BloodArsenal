package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.registry.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.world.World;

public class ModifierBeneficialPotion extends Modifier
{
    public ModifierBeneficialPotion(int level)
    {
        super(Constants.Modifiers.BENEFICIAL_POTION, Constants.Modifiers.BENEFICIAL_POTION_COUNTER.length, level, EnumModifierType.HANDLE);
        setAltName();
    }

    @Override
    public String getAlternateName(ItemStack itemStack)
    {
        return itemStack.hasTagCompound() ? itemStack.getTagCompound().getString(Constants.NBT.ITEMSTACK_NAME) : "何だよ!";
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot)
    {
        if (random.nextInt(getLevel() + 1) >= random.nextInt(getMaxLevel()))
        {
            if (entity instanceof EntityPlayer && itemStack.hasTagCompound())
            {
                NBTTagCompound data = itemStack.getTagCompound().getCompoundTag(Constants.NBT.ITEMSTACK);
                Potion potion = PotionUtils.getEffectsFromStack(ItemStack.loadItemStackFromNBT(data)).get(0).getPotion();
                EntityPlayer player = (EntityPlayer) entity;

                if (potion.isInstant() && world.getWorldTime() % (40 * getMaxLevel() - 20 * getLevel()) == 0)
                    potion.affectEntity(player, player, player, getLevel(), 1);
                else
                    player.addPotionEffect(new PotionEffect(potion, 20 + 40 * (getLevel() + 1), getLevel()));

                ModifierTracker.getTracker(this).incrementCounter(StasisModifiable.getStasisModifiable(itemStack), 1);
            }
        }
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        NBTTagCompound potionTag = new NBTTagCompound();
        String potionName = "";
        if (extra != null && extra.getItem() instanceof ItemPotion)
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
        tag.removeTag(Constants.NBT.ITEMSTACK);
        tag.removeTag(Constants.NBT.ITEMSTACK_NAME);
    }
}
