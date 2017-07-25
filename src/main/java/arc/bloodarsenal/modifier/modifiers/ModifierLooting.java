package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.EnumModifierType;
import arc.bloodarsenal.modifier.Modifier;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ModifierLooting extends Modifier
{
    public ModifierLooting(int level)
    {
        super(Constants.Modifiers.LOOTING, Constants.Modifiers.LOOTING_COUNTER.length, level, EnumModifierType.CORE);
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra)
    {
        BloodArsenalUtils.writeNBTEnchantment(itemStack, Enchantments.LOOTING, getLevel() + 1);
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        BloodArsenalUtils.removeNBTEnchantment(itemStack, Enchantments.LOOTING);
    }
}
