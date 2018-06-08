package arcaratus.bloodarsenal.modifier;

import WayofTime.bloodmagic.util.Utils;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.registry.Constants;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Mutable partner to the Modifier class
 */
public class ModifierTracker
{
    private double counter;

    public final int[] COUNTERS_NEEDED;

    private int level;
    private boolean readyToUpgrade;

    private boolean isDirty = false;
    protected String name;

    private final String modifierKey;

    public ModifierTracker(String modifierKey, int[] countersNeeded)
    {
        this.modifierKey = modifierKey;
        COUNTERS_NEEDED = countersNeeded;

        counter = 0;
        level = 0;
        readyToUpgrade = false;
    }

    public ModifierTracker copy(int level)
    {
        ModifierTracker tracker = new ModifierTracker(modifierKey, COUNTERS_NEEDED);
        tracker.setLevel(level);
        return tracker;
    }

    protected String getName()
    {
        return modifierKey;
    }

    public double getCounter()
    {
        return counter;
    }

    public String getUniqueIdentifier()
    {
        return BloodArsenal.MOD_ID + ".tracker." + getName();
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public boolean isReadyToUpgrade()
    {
        return readyToUpgrade;
    }

    public void setCounter(double counter)
    {
        this.counter = counter;
    }

    public void setReadyToUpgrade(boolean upgrade)
    {
        readyToUpgrade = upgrade;
    }

    /**
     * When called, the ModifierTracker should reset all of its data, including
     * upgrades.
     */
    public void resetCounter()
    {
        counter = 0;
    }

    public void readFromNBT(NBTTagCompound tag)
    {
        counter = tag.getDouble(Constants.NBT.COUNTER);
        level = tag.getInteger(Constants.NBT.LEVEL);
        readyToUpgrade = tag.getBoolean(Constants.NBT.READY_TO_UPGRADE);
    }

    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setDouble(Constants.NBT.COUNTER, counter);
        tag.setInteger(Constants.NBT.LEVEL, level);
        tag.setBoolean(Constants.NBT.READY_TO_UPGRADE, readyToUpgrade);
    }

    // TODO: Make the notification for Modifier upgrades work properly
    public boolean onTick()
    {
//        if (changeMap.containsKey(modifiable) && changeMap.get(modifiable).containsKey(modifier.getClass()))
//        {
//            double change = Math.abs(changeMap.get(modifiable).get(modifier.getClass()));
//            if (change > 0)
//            {
//                counter += Math.abs(changeMap.get(modifiable).get(modifier.getClass()));
//                HashMap<Class<? extends Modifier>, Double> lol = changeMap.get(modifiable);
//                lol.put(modifier.getClass(), 0D);
//                changeMap.put(modifiable, lol);
//                markDirty();
//
//                return true;
//            }
//        }
//
//        return false;

        if (level <= COUNTERS_NEEDED.length - 1 && readyToUpgrade)
        {
            markDirty();
            return true;
        }

        return false;
    }

    /**
     * Used to obtain the progress from the current level to the next level.
     * <p>
     * 0.0 being 0% - 1.0 being 100%.
     *
     * @return the progress from the current level to the next level.
     */
    public double getProgress()
    {
        return Utils.calculateStandardProgress(counter, COUNTERS_NEEDED, level);
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

    public void onModifierUpgraded()
    {
        if (level < COUNTERS_NEEDED.length)
        {
            level = Math.min(++level, COUNTERS_NEEDED.length - 1);
            counter = Math.max(counter, COUNTERS_NEEDED[level]);
            setReadyToUpgrade(false);
            markDirty();
        }
    }

    public void incrementCounter(double increment)
    {
        if (readyToUpgrade)
            return;

        counter += increment;

        if (level < COUNTERS_NEEDED.length - 1 && counter >= COUNTERS_NEEDED[level + 1])
        {
            setReadyToUpgrade(true);
            resetCounter();
        }

        markDirty();
    }
}
