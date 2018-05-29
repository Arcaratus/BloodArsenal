package arcaratus.bloodarsenal.modifier;

import WayofTime.bloodmagic.util.helper.NBTHelper;
import arcaratus.bloodarsenal.item.ItemModifierTome;
import arcaratus.bloodarsenal.registry.Constants;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

public class ModifierHelper
{
    public static Pair<Modifier, ModifierTracker> getModifierAndTracker(ItemStack itemStack)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            return Pair.of(ModifierHandler.getModifierFromKey(getKey(itemStack)), ModifierHandler.getTrackerFromKey(getKey(itemStack), getLevel(itemStack)));
        }

        return Pair.of(Modifier.EMPTY_MODIFIER, null);
    }

    public static String getKey(ItemStack itemStack)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            NBTHelper.checkNBT(itemStack);
            return itemStack.getTagCompound().getString(Constants.NBT.KEY);
        }

        return "";
    }

    public static int getLevel(ItemStack itemStack)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            NBTHelper.checkNBT(itemStack);
            return itemStack.getTagCompound().getInteger(Constants.NBT.LEVEL);
        }

        return 0;
    }

    public static boolean getReady(ItemStack itemStack)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            NBTHelper.checkNBT(itemStack);
            return itemStack.getTagCompound().getBoolean(Constants.NBT.READY_TO_UPGRADE);
        }

        return false;
    }

    public static void setKey(ItemStack itemStack, String key)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            NBTHelper.checkNBT(itemStack);
            itemStack.getTagCompound().setString(Constants.NBT.KEY, key);
        }
    }

    public static void setLevel(ItemStack itemStack, int level)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            NBTHelper.checkNBT(itemStack);
            itemStack.getTagCompound().setInteger(Constants.NBT.LEVEL, level);
        }
    }

    public static void setReady(ItemStack itemStack, boolean ready)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            NBTHelper.checkNBT(itemStack);
            itemStack.getTagCompound().setBoolean(Constants.NBT.READY_TO_UPGRADE, ready);
        }
    }
}
