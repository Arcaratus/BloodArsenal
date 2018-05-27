package arcaratus.bloodarsenal.item.types;

import WayofTime.bloodmagic.item.types.ISubItem;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum EnumDiamondTypes implements ISubItem
{
    NORMAL,
    INERT,
    INFUSED,
    BOUND;

    @Nonnull
    @Override
    public String getInternalName()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    @Nonnull
    @Override
    public ItemStack getStack()
    {
        return getStack(1);
    }

    @Nonnull
    @Override
    public ItemStack getStack(int count)
    {
        return new ItemStack(RegistrarBloodArsenalItems.BLOOD_DIAMOND, count, ordinal());
    }
}
