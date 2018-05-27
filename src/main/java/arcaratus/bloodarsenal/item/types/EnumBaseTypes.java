package arcaratus.bloodarsenal.item.types;

import WayofTime.bloodmagic.item.types.ISubItem;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum EnumBaseTypes implements ISubItem
{
    GLASS_SHARD,
    BLOOD_INFUSED_STICK,
    BLOOD_INFUSED_GLOWSTONE_DUST,
    INERT_BLOOD_INFUSED_IRON_INGOT,
    BLOOD_INFUSED_IRON_INGOT,
    STASIS_PLATE,
    REAGENT_SWIMMING,
    REAGENT_ENDER,
    REAGENT_LIGHTNING,
    REAGENT_DIVINITY,
    ;

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
        return new ItemStack(RegistrarBloodArsenalItems.BASE_ITEM, count, ordinal());
    }
}
