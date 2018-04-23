package arcaratus.bloodarsenal.modifier;

import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Constructor;
import java.util.*;

public class ModifierHandler
{
    public static List<ModifierTracker> trackers = new ArrayList<>();
    public static HashMap<String, Class<? extends Modifier>> modifierMap = new HashMap<>();
    public static HashMap<String, Constructor<? extends Modifier>> modifierConstructorMap = new HashMap<>();
    public static Set<Pair<EnumModifierType, Set<Modifier>>> incompatibleModifiersMap = new HashSet<>();
    public static HashMap<String, Integer> modifierMaxLevelMap = new HashMap<>();

    public static void registerTracker(ModifierTracker tracker)
    {
        if (trackers.contains(tracker))
            BloodArsenal.INSTANCE.getLogger().error("You utter dimwit. I'm not even going to provide you with an informative error.");

        trackers.add(tracker);
    }

    public static Modifier registerModifier(Modifier modifier)
    {
        Class<? extends Modifier> clazz = modifier.getClass();
        modifierMap.put(modifier.getUniqueIdentifier(), clazz);
        modifierMaxLevelMap.put(modifier.getUniqueIdentifier(), modifier.getMaxLevel());

        try
        {
            Constructor<? extends Modifier> constructor = clazz.getConstructor(int.class);
            if (constructor == null)
                BloodArsenal.INSTANCE.getLogger().error("Error adding soul modifier {} since it doesn't have a frickin valid constructor dummy.", modifier.getUniqueIdentifier());
            else
                modifierConstructorMap.put(modifier.getUniqueIdentifier(), constructor);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return modifier;
    }

    public static void registerIncompatibleModifiers(EnumModifierType type, Modifier... modifiers)
    {
        Set<Modifier> modList = new HashSet<>();
        for (Modifier modifier : modifiers)
        {
            if (type != modifier.getType())
                BloodArsenal.INSTANCE.getLogger().error("Error registering incompatible modifiers since {} does not match modifier type ya idiot.", modifier.getUniqueIdentifier());
            else
                modList.add(modifier);
        }

        incompatibleModifiersMap.add(Pair.of(type, modList));
    }

    public static Modifier generateModifierFromKey(String key, int level, boolean readyForUpgrade)
    {
        return generateModifierFromKey(key, level, readyForUpgrade, null);
    }

    public static Modifier generateModifierFromKey(String key, int level, boolean readyForUpgrade, NBTTagCompound tag)
    {
        Constructor<? extends Modifier> constructor = modifierConstructorMap.get(key);
        if (constructor != null)
        {
            try
            {
                Modifier modifier = constructor.newInstance(level);
                if (tag != null)
                    modifier.readFromNBT(tag);

                modifier.setReadyForUpgrade(readyForUpgrade);
                return modifier;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    // Complexity of O(n^2)
    public static boolean isModifierCompatible(Map<String, Modifier> modifierMap, Modifier modifier)
    {
        for (Pair<EnumModifierType, Set<Modifier>> pair : incompatibleModifiersMap)
            if (pair.getKey() == modifier.getType() && !pair.getValue().isEmpty())
                if (pair.getValue().contains(modifier))
                    for (Modifier incompatMod : pair.getValue())
                        if (!modifier.getUniqueIdentifier().equals(incompatMod.getUniqueIdentifier()) && modifierMap.containsKey(incompatMod.getUniqueIdentifier()))
                            return false;

        return true;
    }
}
