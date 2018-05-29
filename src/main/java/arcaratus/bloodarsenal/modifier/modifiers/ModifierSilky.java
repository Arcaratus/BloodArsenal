package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ModifierSilky extends Modifier
{
    public ModifierSilky()
    {
        super(Constants.Modifiers.SILKY, 1, EnumModifierType.CORE);
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra, int level)
    {
        BloodArsenalUtils.writeNBTEnchantment(itemStack, Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        BloodArsenalUtils.removeNBTEnchantment(itemStack, Enchantments.SILK_TOUCH);
    }
}
