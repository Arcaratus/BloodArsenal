package arcaratus.bloodarsenal.modifier;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.registry.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import joptsimple.internal.Strings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an object that stores Modifiers and their respective Trackers
 * Writes everything to NBT
 */
public class StasisModifiable implements IModifiable
{
    private Map<String, Pair<Modifier, ModifierTracker>> modifierMap = new HashMap<>();

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers()
    {
        Multimap<String, AttributeModifier> attributeMap = HashMultimap.create();

        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            if (modifier == Modifier.EMPTY_MODIFIER)
                continue;

            attributeMap.putAll(modifier.getAttributeModifiers(tracker.getLevel()));
        }

        return attributeMap;
    }

    @Override
    public boolean hasModifier(String modifierKey)
    {
        return !Strings.isNullOrEmpty(modifierKey) && modifierMap.containsKey(modifierKey);
    }

    @Override
    public boolean hasModifier(Modifier modifier)
    {
        if (modifier == null || modifier == Modifier.EMPTY_MODIFIER)
            return false;

        String modifierKey = modifier.getUniqueIdentifier();
        return !Strings.isNullOrEmpty(modifierKey) && modifierMap.containsKey(modifierKey);
    }

    @Override
    public boolean canApplyModifier(Modifier modifier, int level)
    {
        if (hasModifier(modifier))
        {
            ModifierTracker existingModifier = getTrackerForModifier(modifier);
            if (!existingModifier.isReadyToUpgrade() || existingModifier.getLevel() < level)
                return false;
        }

        if (!ModifierHandler.isModifierCompatible(modifierMap.keySet(), modifier))
            return false;

        for (EnumModifierType modType : EnumModifierType.values()) // Checks for max modifiers for modifier type
        {
            int count = 0;
            for (Pair<Modifier, ModifierTracker> pair : modifierMap.values())
                if (pair.getLeft().getType() == modType)
                    count++;

            if (count > modType.getMax())
                return false;
        }

        return true;
    }

    @Override
    public boolean markModifierReady(ItemStack itemStack, EntityPlayer player, Modifier modifier)
    {
        if (modifier == Modifier.EMPTY_MODIFIER)
            return false;

        String key = modifier.getUniqueIdentifier();
        if (hasModifier(key))
        {
            ModifierTracker tracker = modifierMap.get(key).getRight();
            // TODO fix this method
            if (!tracker.isReadyToUpgrade())
            {
//                tracker.setReadyToUpgrade(true);
                String name = modifierMap.get(key).getLeft().hasAltName() ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
                player.sendStatusMessage(new TextComponentString(TextHelper.localizeEffect("chat.bloodarsenal.modifier_ready", name, tracker.getLevel() + 1)), false);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean applyModifier(Pair<Modifier, ModifierTracker> modifierPair)
    {
        String key = modifierPair.getLeft().getUniqueIdentifier();
        if (!hasModifier(key))
        {
            modifierMap.put(key, modifierPair);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeModifier(Modifier modifier)
    {
        String key = modifier.getUniqueIdentifier();
        if (hasModifier(key))
        {
            modifierMap.remove(key);

            return true;
        }

        return false;
    }

    @Override
    public boolean upgradeModifier(Modifier modifier)
    {
        String key = modifier.getUniqueIdentifier();
        if (hasModifier(key))
        {
            ModifierTracker tracker = modifierMap.get(key).getRight();
            if (tracker.isReadyToUpgrade())
                tracker.onModifierUpgraded();
        }

        return false;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot)
    {
        if (world.isRemote)
            return;

        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            if (modifier == Modifier.EMPTY_MODIFIER)
                continue;

            modifier.onUpdate(itemStack, world, entity, itemSlot, tracker.getLevel());

            if (tracker.onTick() && entity instanceof EntityPlayer)
                markModifierReady(itemStack, (EntityPlayer) entity, modifier);
        }

        setMod(itemStack);
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker)
    {
        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            if (modifier == Modifier.EMPTY_MODIFIER)
                continue;

            modifier.hitEntity(itemStack, target, attacker, tracker.getLevel());
        }

        setMod(itemStack);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player)
    {
        if (world.isRemote)
            return;

        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            if (modifier == Modifier.EMPTY_MODIFIER)
                continue;

            modifier.onBlockDestroyed(itemStack, world, state, pos, player, tracker.getLevel());
        }

        setMod(itemStack);
    }

    @Override
    public void onRelease(ItemStack itemStack, World world, EntityPlayer player, int charge)
    {
        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            if (modifier == Modifier.EMPTY_MODIFIER || modifier.getType() != EnumModifierType.ABILITY)
                continue;

            modifier.onRelease(itemStack, world, player, charge, tracker.getLevel());
        }

        setMod(itemStack);
    }

    @Override
    public void onRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            if (modifier == Modifier.EMPTY_MODIFIER || modifier.getType() != EnumModifierType.ABILITY)
                continue;

            modifier.onRightClick(itemStack, world, player, tracker.getLevel());
        }

        setMod(itemStack);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        NBTTagList modTags = tag.getTagList(Constants.NBT.MODIFIERS, 10);
        if (modTags != null)
        {
            for (int i = 0; i < modTags.tagCount(); i++)
            {
                NBTTagCompound modTag = modTags.getCompoundTagAt(i);
                String key = modTag.getString(Constants.NBT.KEY);
                int level = modTag.getInteger(Constants.NBT.LEVEL);
                NBTTagCompound nbtTag = modTag.getCompoundTag(Constants.NBT.MODIFIER);

                Modifier modifier = ModifierHandler.getModifierFromKey(key, nbtTag);
                modifier.readFromNBT(nbtTag);

                ModifierTracker tracker = ModifierHandler.getTrackerFromKey(key, level);

                String trackerKey = tracker.getUniqueIdentifier();
                NBTTagCompound trackerTag = tag.getCompoundTag(trackerKey);
                if (!trackerTag.isEmpty())
                    tracker.readFromNBT(trackerTag);

                if (modifier != Modifier.EMPTY_MODIFIER)
                    modifierMap.put(key, Pair.of(modifier, tracker));
                else
                    modTags.removeTag(i);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag, boolean forceWrite)
    {
        NBTTagList tags = new NBTTagList();
        for (Map.Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
        {
            NBTTagCompound modifierTag = new NBTTagCompound();

            Modifier modifier = entry.getValue().getLeft();
            ModifierTracker tracker = entry.getValue().getRight();
            NBTTagCompound nbtTag = new NBTTagCompound();
            modifier.writeToNBT(nbtTag);

            if (forceWrite || tracker.isDirty())
            {
                String key = tracker.getUniqueIdentifier();
                NBTTagCompound trackerTag = new NBTTagCompound();
                tracker.writeToNBT(trackerTag);
                tag.setTag(key, trackerTag);
                tracker.resetDirty();
            }

            modifierTag.setString(Constants.NBT.KEY, modifier.getUniqueIdentifier());
            modifierTag.setInteger(Constants.NBT.LEVEL, tracker.getLevel());
            modifierTag.setTag(Constants.NBT.MODIFIER, nbtTag);

            tags.appendTag(modifierTag);
        }

        tag.setTag(Constants.NBT.MODIFIERS, tags);
    }

    /**
     * Writes the StasisModifiable to the NBTTag. This will only write the trackers
     * that are dirty.
     */
    @Override
    public void writeDirtyToNBT(NBTTagCompound tag)
    {
        writeToNBT(tag, false);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        writeToNBT(tag, true);
    }

    public Map<String, Pair<Modifier, ModifierTracker>> getModifierMap()
    {
        return modifierMap;
    }

    public Modifier getModifier(String modifierKey)
    {
        return modifierMap.getOrDefault(modifierKey, Pair.of(Modifier.EMPTY_MODIFIER, null)).getLeft();
    }

    public ModifierTracker getTrackerForModifier(Modifier modifier)
    {
        return getTrackerForModifier(modifier.getUniqueIdentifier());
    }

    public ModifierTracker getTrackerForModifier(String modifierKey)
    {
        return modifierMap.getOrDefault(modifierKey, Pair.of(Modifier.EMPTY_MODIFIER, null)).getRight();
    }

    public void incrementModifierTracker(ItemStack itemStack, String modifierKey, double increment)
    {
        ModifierTracker tracker = getTrackerForModifier(modifierKey);
        if (tracker != null)
        {
            tracker.incrementCounter(increment);
            setMod(itemStack);
        }
    }

    public void incrementModifierTracker(ItemStack itemStack, Modifier modifier, double increment)
    {
        incrementModifierTracker(itemStack, modifier.getUniqueIdentifier(), increment);
    }

    public void incrementModifierTracker(ItemStack itemStack, Modifier modifier)
    {
        incrementModifierTracker(itemStack, modifier.getUniqueIdentifier(), 1);
    }

    public void incrementModifierTracker(ItemStack itemStack, String modifierKey)
    {
        incrementModifierTracker(itemStack, modifierKey, 1);
    }

    public boolean checkAndIncrementTracker(ItemStack itemStack, Modifier modifier, double increment)
    {
        if (hasModifier(modifier))
        {
            incrementModifierTracker(itemStack, modifier, increment);
            return true;
        }

        return false;
    }

    public boolean checkAndIncrementTracker(ItemStack itemStack, Modifier modifier)
    {
        return checkAndIncrementTracker(itemStack, modifier, 1);
    }

    // Updates the StasisModifiable after every action
    public void setMod(ItemStack itemStack)
    {
        setModifiable(itemStack, this, false);
    }

    // Static methods

    public static StasisModifiable getModifiableFromStack(ItemStack itemStack)
    {
        NBTTagCompound tag = getNBTTag(itemStack);
        StasisModifiable modifiable = new StasisModifiable();
        modifiable.readFromNBT(tag);
        return modifiable;
    }

    public static void setModifiable(ItemStack itemStack, StasisModifiable modifiable, boolean forceWrite)
    {
        NBTTagCompound tag = new NBTTagCompound();
        if (!forceWrite)
        {
            tag = getNBTTag(itemStack);
            modifiable.writeDirtyToNBT(tag);
        }
        else
        {
            modifiable.writeToNBT(tag);
        }

        setNBTTag(itemStack, tag);
    }

    public static NBTTagCompound getNBTTag(ItemStack stack)
    {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        return stack.getTagCompound().getCompoundTag(Constants.NBT.STASIS_MODIFIERS);
    }

    public static void setNBTTag(ItemStack stack, NBTTagCompound tag)
    {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setTag(Constants.NBT.STASIS_MODIFIERS, tag);
    }
}
