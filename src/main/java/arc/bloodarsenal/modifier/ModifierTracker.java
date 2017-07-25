package arc.bloodarsenal.modifier;

import WayofTime.bloodmagic.util.Utils;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

public class ModifierTracker
{
    public static HashMap<String, ModifierTracker> trackerMap = new HashMap<>();

    public double counter = 0;

    public static HashMap<IModifiable, HashMap<Class<? extends Modifier>, Double>> changeMap = new HashMap<>();
    public final int[] COUNTERS_NEEDED;

    private boolean isDirty = false;
    protected String name;

    private final Modifier modifier;

    private ModifierTracker(Modifier modifier, int[] countersNeeded)
    {
        this.modifier = modifier;
        COUNTERS_NEEDED = countersNeeded;
    }

    public static ModifierTracker newTracker(Modifier modifier, int[] countersNeeded)
    {
        ModifierTracker tracker = new ModifierTracker(modifier, countersNeeded);
        trackerMap.put(modifier.getUniqueIdentifier(), tracker);
        return tracker;
    }

    public static ModifierTracker getTracker(Modifier modifier)
    {
        return trackerMap.getOrDefault(modifier.getUniqueIdentifier(), null);
    }

    public static ModifierTracker getTracker(Class<? extends Modifier> clazz)
    {
        String name = "";

        try
        {
            Method method = clazz.getDeclaredMethod("getUniqueIdentifier");
            name = (String) method.invoke(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return trackerMap.getOrDefault(name, null);
    }

    protected String getName()
    {
        return modifier.getName();
    }

    public String getUniqueIdentifier()
    {
        return BloodArsenal.MOD_ID + ".tracker." + getName();
    }

    public Modifier getModifier()
    {
        return modifier;
    }

    /**
     * When called the ModifierTracker should reset all of its data, including
     * upgrades.
     */
    public void resetTracker()
    {
        counter = 0;
    }

    public void readFromNBT(NBTTagCompound tag)
    {
        counter = tag.getDouble(getUniqueIdentifier());
    }

    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setDouble(getUniqueIdentifier(), counter);
    }

    public boolean onTick(IModifiable modifiable)
    {
        if (changeMap.containsKey(modifiable) && changeMap.get(modifiable).containsKey(modifier.getClass()))
        {
            double change = Math.abs(changeMap.get(modifiable).get(modifier.getClass()));
            if (change > 0)
            {
                counter += Math.abs(changeMap.get(modifiable).get(modifier.getClass()));
                HashMap<Class<? extends Modifier>, Double> lol = changeMap.get(modifiable);
                lol.put(modifier.getClass(), 0D);
                changeMap.put(modifiable, lol);
                markDirty();

                return true;
            }
        }

        return false;
    }

    public Modifier getNextModifier(HashMap<String, Modifier> modifierMap)
    {
        Modifier modifier = Modifier.EMPTY_MODIFIER;
        for (Entry<String, Modifier> entry : modifierMap.entrySet())
            if (entry.getValue().getClass().isInstance(this.modifier))
                modifier = entry.getValue();

        if (modifier != Modifier.EMPTY_MODIFIER)
            for (int i = COUNTERS_NEEDED.length - 1; i > 0; i--)
                if (counter >= COUNTERS_NEEDED[i])
                    return modifier.getLevel() < i ? this.modifier.newCopy(i) : modifier;

        return modifier;
    }

    /**
     * Used to obtain the progress from the current level to the next level.
     * <p>
     * 0.0 being 0% - 1.0 being 100%.
     *
     * @return the progress from the current level to the next level.
     */
    public double getProgress(int currentLevel)
    {
        return Utils.calculateStandardProgress(counter, COUNTERS_NEEDED, currentLevel);
    }

    public final boolean isDirty()
    {
        return isDirty;
    }

    public final void markDirty()
    {
        isDirty = true;
    }

    public final void resetDirty()
    {
        isDirty = false;
    }

    public boolean providesModifier(String key)
    {
        return key.equals(getUniqueIdentifier());
    }

    public void onModifierAdded(Modifier modifier)
    {
        if (modifier.getClass().isInstance(this.modifier))
        {
            int level = modifier.getLevel();
            if (level < COUNTERS_NEEDED.length)
            {
                counter = Math.max(counter, COUNTERS_NEEDED[level]);
                markDirty();
            }
        }
    }

    public void incrementCounter(IModifiable modifiable, double increment)
    {
        if (changeMap.containsKey(modifiable) && changeMap.get(modifiable).containsKey(modifier.getClass()))
        {
            HashMap<Class<? extends Modifier>, Double> lol = changeMap.get(modifiable);
            lol.put(modifier.getClass(), changeMap.get(modifiable).get(modifier.getClass()) + increment);
            changeMap.put(modifiable, lol);
        }
        else
        {
            HashMap<Class<? extends Modifier>, Double> lol = new HashMap<>();
            lol.put(modifier.getClass(), increment);
            changeMap.put(modifiable, lol);
        }
    }

    public static void incrementCounter(IModifiable modifiable, Class<? extends Modifier> clazz, double increment)
    {
        if (changeMap.containsKey(modifiable) && changeMap.get(modifiable).containsKey(clazz))
        {
            HashMap<Class<? extends Modifier>, Double> lol = changeMap.get(modifiable);
            lol.put(clazz, changeMap.get(modifiable).get(clazz) + increment);
            changeMap.put(modifiable, lol);
        }
        else
        {
            HashMap<Class<? extends Modifier>, Double> lol = new HashMap<>();
            lol.put(clazz, increment);
            changeMap.put(modifiable, lol);
        }
    }
}
