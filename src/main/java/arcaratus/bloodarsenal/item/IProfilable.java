package arcaratus.bloodarsenal.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public interface IProfilable
{
    int getProfileIndex(ItemStack itemStack);

    int getMaxProfiles(ItemStack itemStack);

    void setProfileIndex(ItemStack itemStack, int index);

    NBTTagList getProfiles(ItemStack itemStack);
}
