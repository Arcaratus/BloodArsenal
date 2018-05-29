package arcaratus.bloodarsenal.modifier;

import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class ModifierHandler
{
    public static HashMap<String, Pair<Modifier, ModifierTracker>> modifierMap = new HashMap<>();
    public static Set<Pair<EnumModifierType, Set<Modifier>>> incompatibleModifiersMap = new HashSet<>();
    public static HashMap<String, Integer> modifierMaxLevelMap = new HashMap<>();

    public static Modifier registerModifier(Modifier modifier, ModifierTracker tracker)
    {
        modifierMap.put(modifier.getUniqueIdentifier(), Pair.of(modifier, tracker));
        modifierMaxLevelMap.put(modifier.getUniqueIdentifier(), modifier.getMaxLevel());

        return modifier;
    }

    public static void registerIncompatibleModifiers(EnumModifierType type, Modifier... modifiers)
    {
        Set<Modifier> modList = new HashSet<>();
        for (Modifier modifier : modifiers)
        {
//            if (type != modifier.getType())
//                BloodArsenal.INSTANCE.getLogger().error("Error registering incompatible modifiers since {} does not match modifier type ya idiot.", modifier.getUniqueIdentifier());
//            else
                modList.add(modifier);
        }

        incompatibleModifiersMap.add(Pair.of(type, modList));
    }

    public static Modifier getModifierFromKey(String key)
    {
        return getModifierFromKey(key, null);
    }

    public static Modifier getModifierFromKey(String key, NBTTagCompound tag)
    {
        Modifier modifier = modifierMap.get(key).getLeft();
        if (tag != null)
            modifier.readFromNBT(tag);

        return modifier;
    }

    public static ModifierTracker getTrackerFromKey(String key, int level)
    {
        return modifierMap.get(key).getRight().copy(level);
    }

    /**
     * Checks for any existing Modifier that is incompatible with the Modifier
     * Complexity of O(n^2)
     *
     * @return true if compatible, false otherwise
     */
    public static boolean isModifierCompatible(Collection<String> existingModifiers, Modifier modifier)
    {
        for (Pair<EnumModifierType, Set<Modifier>> pair : incompatibleModifiersMap)
            if (pair.getKey() == modifier.getType() && !pair.getValue().isEmpty())
                if (pair.getValue().contains(modifier))
                    for (Modifier incompatMod : pair.getValue())
                        if (!modifier.getUniqueIdentifier().equals(incompatMod.getUniqueIdentifier()) && existingModifiers.contains(incompatMod.getUniqueIdentifier()))
                            return false;

        return true;
    }
}
