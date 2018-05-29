package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ModifierLooting extends Modifier
{
    public ModifierLooting()
    {
        super(Constants.Modifiers.LOOTING, Constants.Modifiers.LOOTING_COUNTER.length, EnumModifierType.CORE);
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra, int level)
    {
        BloodArsenalUtils.writeNBTEnchantment(itemStack, Enchantments.LOOTING, level + 1);
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        BloodArsenalUtils.removeNBTEnchantment(itemStack, Enchantments.LOOTING);
    }
}
