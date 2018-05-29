package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static arcaratus.bloodarsenal.registry.Constants.Misc.ATTACK_SPEED_MODIFIER;

public class ModifierQuickDraw extends Modifier
{
    public ModifierQuickDraw()
    {
        super(Constants.Modifiers.QUICK_DRAW, Constants.Modifiers.QUICK_DRAW_COUNTER.length, EnumModifierType.HANDLE);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, int level)
    {
        if (world.getWorldTime() % 20 == 0)
            NewModifiable.incrementModifierTracker(itemStack, this, 1);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(level);

        multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (level + 1) * 2 / 4, 0));

        return multimap;
    }
}
