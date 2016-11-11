package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.registry.Constants;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static arc.bloodarsenal.registry.Constants.Misc.ATTACK_SPEED_MODIFIER;

public class ModifierQuickDraw extends Modifier
{
    public ModifierQuickDraw(int level)
    {
        super(Constants.Modifiers.QUICK_DRAW, Constants.Modifiers.QUICK_DRAW_COUNTER.length, level, EnumModifierType.HANDLE);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot)
    {
        if (world.getWorldTime() % 20 == 0)
            ModifierTracker.getTracker(this).incrementCounter(StasisModifiable.getStasisModifiable(itemStack), 1);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers()
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers();

        multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (getLevel() + 1) * 2 / 4, 0));

        return multimap;
    }
}
