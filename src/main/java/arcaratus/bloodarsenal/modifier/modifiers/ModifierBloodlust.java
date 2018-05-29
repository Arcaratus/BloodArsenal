package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import com.google.common.collect.Multimap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import static arcaratus.bloodarsenal.registry.Constants.Misc.ATTACK_DAMAGE_MODIFIER;

public class ModifierBloodlust extends Modifier
{
    private double multiplier = 0;

    public ModifierBloodlust()
    {
        super(Constants.Modifiers.BLOODLUST, Constants.Modifiers.BLOODLUST_COUNTER.length, EnumModifierType.HEAD);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, int level)
    {
        if (world.getWorldTime() % 5 == 0 && random.nextInt(3) < 2 && multiplier > 0)
            multiplier = Math.max(multiplier - (0.02 + ((double) random.nextInt(5) / 100)), 0);
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker, int level)
    {
        multiplier = getMultiplier(multiplier + (random.nextDouble() * (level + 1)) / 6, level);
        NewModifiable.incrementModifierTracker(itemStack, this, 1);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setDouble(Constants.NBT.MULTIPLIER, multiplier);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        multiplier = tag.getDouble(Constants.NBT.MULTIPLIER);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(level);

        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", Math.floor(multiplier / 4 + 1) * (6 + 0.5 * (level + 1)) * Math.pow(1.1375, multiplier), 0));

        return multimap;
    }

    private double getMultiplier(double multiplier, int level)
    {
        double max = 0;
        switch (level + 1)
        {
            case 1:
                max = 4;
                break;
            case 2:
                max = 7;
                break;
            case 3:
                max = 10;
                break;
            case 4:
                max = 12;
                break;
            case 5:
                max = 14;
                break;
            case 6:
                max = 15;
                break;
            case 7:
                max = 16;
                break;
            case 8:
                max = 17;
                break;
        }

        return Math.min(multiplier, max);
    }
}
