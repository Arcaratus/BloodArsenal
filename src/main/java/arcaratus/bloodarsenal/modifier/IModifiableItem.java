package arcaratus.bloodarsenal.modifier;

import net.minecraft.item.ItemStack;

public interface IModifiableItem
{
    IModifiable getModifiable(ItemStack itemStack);


}
