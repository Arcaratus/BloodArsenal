package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.EnumModifierType;
import arc.bloodarsenal.modifier.Modifier;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ModifierSilky extends Modifier
{
    public ModifierSilky(int level)
    {
        super(Constants.Modifiers.SILKY, 1, level, EnumModifierType.CORE);
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra)
    {
        BloodArsenalUtils.writeNBTEnchantment(itemStack, Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        BloodArsenalUtils.removeNBTEnchantment(itemStack, Enchantments.SILK_TOUCH);
    }
}
