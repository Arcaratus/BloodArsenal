package arcaratus.bloodarsenal.item.types;

import WayofTime.bloodmagic.item.types.ISubItem;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum EnumGemTypes implements ISubItem
{
    SELF_SACRIFICE,
    SACRIFICE,
    TARTARIC;

    @Nonnull
    @Override
    public String getInternalName()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    @Nonnull
    @Override
    public ItemStack getStack(int count)
    {
        return new ItemStack(RegistrarBloodArsenalItems.GEM, count, ordinal());
    }
}
