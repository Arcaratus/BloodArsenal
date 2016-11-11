package arc.bloodarsenal.modifier;

import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import arc.bloodarsenal.item.ItemModifierTome;
import arc.bloodarsenal.registry.Constants;
import net.minecraft.item.ItemStack;

public class ModifierHelper
{
    public static Modifier getModifier(ItemStack itemStack)
    {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemModifierTome)
        {
            return ModifierHandler.generateModifierFromKey(getKey(itemStack), getLevel(itemStack), getReady(itemStack));
        }

        return Modifier.EMPTY_MODIFIER;
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
