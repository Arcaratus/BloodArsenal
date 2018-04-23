package arcaratus.bloodarsenal.modifier;

import WayofTime.bloodmagic.util.ChatUtil;
import WayofTime.bloodmagic.util.Utils;
import amerifrance.guideapi.api.util.TextHelper;
import arcaratus.bloodarsenal.modifier.modifiers.ModifierShadowTool;
import arcaratus.bloodarsenal.registry.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class StasisModifiable implements IModifiable
{
    public HashMap<String, ModifierTracker> trackerMap = new HashMap<>();
    public HashMap<String, Modifier> modifierMap = new HashMap<>();

    public static Map<UUID, StasisModifiable> modifiableMap = new HashMap<>();

    public boolean hasModifier(Class<? extends Modifier> clazz)
    {
        for (Modifier modifier : modifierMap.values())
            if (modifier.getClass() == clazz)
                return true;

        return false;
    }

    public Modifier getModifier(Class<? extends Modifier> clazz)
    {
        for (Modifier modifier : modifierMap.values())
            if (modifier.getClass() == clazz)
                return modifier;

        return Modifier.EMPTY_MODIFIER;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers()
    {
        Multimap<String, AttributeModifier> attributeMap = HashMultimap.create();

        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue();
            if (modifier == null)
                continue;

            attributeMap.putAll(modifier.getAttributeModifiers());
        }

        return attributeMap;
    }

    @Override
    public boolean canApplyModifier(Modifier modifier)
    {
        if (!ModifierHandler.isModifierCompatible(modifierMap, modifier))
            return false;

        for (EnumModifierType modType : EnumModifierType.values()) // Checks for max modifiers for modifier type
        {
            int count = 0;
            for (Modifier mod : modifierMap.values())
                if (mod.getType() == modType)
                    count++;

            if (count > modType.getMax())
                return false;
        }

        String key = modifier.getUniqueIdentifier();
        if (modifierMap.containsKey(key))
        {
            int nextLevel = modifier.getLevel();
            int currentLevel = modifierMap.get(key).getLevel();

            if (nextLevel > currentLevel)
                return true;
        }
        else
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean markModifierReady(ItemStack itemStack, EntityPlayer player, Modifier modifier)
    {
        if (modifier == Modifier.EMPTY_MODIFIER)
            return false;

        String key = modifier.getUniqueIdentifier();
        if (modifierMap.containsKey(key))
        {
            int nextLevel = modifier.getLevel();
            int currentLevel = modifierMap.get(key).getLevel();

            if (!modifierMap.get(key).readyForUpgrade() && nextLevel > currentLevel)
            {
                modifierMap.get(key).setReadyForUpgrade(true);
                String name = modifierMap.get(key).hasAltName() ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
                ChatUtil.sendChat(player, TextHelper.localizeEffect("chat.bloodarsenal.modifierReady", name, modifier.getLevel()));
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean applyModifier(Modifier modifier)
    {
        String key = modifier.getUniqueIdentifier();
        if (modifierMap.containsKey(key))
        {
            int nextLevel = modifier.getLevel();
            int currentLevel = modifierMap.get(key).getLevel();

            if (nextLevel > currentLevel)
            {
                modifier.setReadyForUpgrade(false);
                modifierMap.put(key, modifier);
                for (ModifierTracker tracker : trackerMap.values())
                    tracker.onModifierAdded(modifier);

                return true;
            }
        }
        else
        {
            modifier.setReadyForUpgrade(false);
            modifierMap.put(key, modifier);
            for (ModifierTracker tracker : trackerMap.values())
                tracker.onModifierAdded(modifier);

            return true;
        }

        return false;
    }

    @Override
    public boolean removeModifier(Modifier modifier)
    {
        String key = modifier.getUniqueIdentifier();
        if (modifierMap.containsKey(key))
        {
            modifierMap.remove(key);
            return true;
        }

        return false;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot)
    {
        if (world.isRemote)
            return;

        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue();
            if (modifier == null)
                continue;

            modifier.onUpdate(itemStack, world, entity, itemSlot);
        }

        for (Entry<String, ModifierTracker> entry : trackerMap.entrySet())
        {
            ModifierTracker tracker = entry.getValue();
            if (tracker == null)
                continue;

            if (tracker.onTick(this) && entity instanceof EntityPlayer)
                markModifierReady(itemStack, (EntityPlayer) entity, tracker.getNextModifier(modifierMap));
        }
    }

    // Only for ModifierShadowTool
    public void onSpecialUpdate(ItemStack itemStack, World world, Entity entity)
    {
        if (world.isRemote)
            return;

        for (Entry<String, ModifierTracker> entry : trackerMap.entrySet())
        {
            ModifierTracker tracker = entry.getValue();
            if (tracker == null)
                continue;

            if (tracker.getModifier() instanceof ModifierShadowTool && tracker.onTick(this) && entity instanceof EntityPlayer)
                markModifierReady(itemStack, (EntityPlayer) entity, tracker.getNextModifier(modifierMap));
        }
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker)
    {
        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue();
            if (modifier == null)
                continue;

            modifier.hitEntity(itemStack, target, attacker);
        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player)
    {
        if (world.isRemote)
            return;

        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue();
            if (modifier == null)
                continue;

            modifier.onBlockDestroyed(itemStack, world, state, pos, player);
        }
    }

    @Override
    public void onRelease(ItemStack itemStack, World world, EntityPlayer player, int charge)
    {
        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue();
            if (modifier == null || modifier.getType() != EnumModifierType.ABILITY)
                continue;

            modifier.onRelease(itemStack, world, player, charge);
        }
    }

    @Override
    public void onRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            Modifier modifier = entry.getValue();
            if (modifier == null || modifier.getType() != EnumModifierType.ABILITY)
                continue;

            modifier.onRightClick(itemStack, world, player);
        }
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
                boolean readyForUpgrade = modTag.getBoolean(Constants.NBT.READY_TO_UPGRADE);
                NBTTagCompound nbtTag = modTag.getCompoundTag(Constants.NBT.MODIFIER);
                Modifier modifier = ModifierHandler.generateModifierFromKey(key, level, readyForUpgrade, nbtTag);
                if (modifier != null)
                    modifierMap.put(key, modifier);
                else
                    modTags.removeTag(i);
            }
        }

        for (ModifierTracker tracker : ModifierHandler.trackers)
        {
            try
            {
                String key = tracker.getUniqueIdentifier();
                NBTTagCompound trackerTag = tag.getCompoundTag(key);
                if (!trackerTag.hasNoTags())
                    tracker.readFromNBT(trackerTag);
                trackerMap.put(key, tracker);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag, boolean forceWrite)
    {
        NBTTagList tags = new NBTTagList();
        for (Entry<String, Modifier> entry : modifierMap.entrySet())
        {
            NBTTagCompound modifierTag = new NBTTagCompound();

            Modifier modifier = entry.getValue();
            NBTTagCompound nbtTag = new NBTTagCompound();
            modifier.writeToNBT(nbtTag);

            modifierTag.setString(Constants.NBT.KEY, modifier.getUniqueIdentifier());
            modifierTag.setInteger(Constants.NBT.LEVEL, modifier.getLevel());
            modifierTag.setBoolean(Constants.NBT.READY_TO_UPGRADE, modifier.readyForUpgrade());
            modifierTag.setTag(Constants.NBT.MODIFIER, nbtTag);

            tags.appendTag(modifierTag);
        }

        tag.setTag(Constants.NBT.MODIFIERS, tags);

        for (Entry<String, ModifierTracker> entry : trackerMap.entrySet())
        {
            ModifierTracker tracker = entry.getValue();
            if (tracker == null)
                continue;

            String key = tracker.getUniqueIdentifier();
            if (forceWrite || tracker.isDirty())
            {
                NBTTagCompound trackerTag = new NBTTagCompound();
                tracker.writeToNBT(trackerTag);
                tag.setTag(key, trackerTag);
                tracker.resetDirty();
            }
        }
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

    // Static methods
    public static StasisModifiable getModFromNBT(ItemStack itemStack)
    {
        NBTTagCompound tag = getNBTTag(itemStack);
        StasisModifiable modifiable = new StasisModifiable();
        modifiable.readFromNBT(tag);
        return modifiable;
    }

    public static void setStasisModifiable(ItemStack itemStack, StasisModifiable modifiable, boolean forceWrite)
    {
        NBTTagCompound tag = new NBTTagCompound();
        if (!forceWrite)
        {
            tag = getNBTTag(itemStack);
            modifiable.writeDirtyToNBT(tag);
        }
        else
        {
            modifiable.writeDirtyToNBT(tag);
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

    public static Modifier getModifier(String uniqueIdentifier, ItemStack itemStack)
    {
        if (!hasModifiable(itemStack))
            setStasisModifiable(itemStack, getModFromNBT(itemStack));

        StasisModifiable modifiable = getStasisModifiable(itemStack);
        for (Entry<String, Modifier> entry : modifiable.modifierMap.entrySet())
            if (entry.getKey().equals(uniqueIdentifier))
                return entry.getValue();

        return null;
    }

    public static Modifier getModifierFromNBT(String uniqueIdentifier, ItemStack itemStack)
    {
        StasisModifiable modifiable = getModFromNBT(itemStack);
        for (Entry<String, Modifier> entry : modifiable.modifierMap.entrySet())
            if (entry.getKey().equals(uniqueIdentifier))
                return entry.getValue();

        return null;
    }

    public static boolean hasModifiable(ItemStack itemStack)
    {
        UUID uuid = Utils.getUUID(itemStack);
        return uuid != null && modifiableMap.containsKey(uuid);
    }

    public static StasisModifiable getStasisModifiable(ItemStack itemStack)
    {
        return modifiableMap.getOrDefault(Utils.getUUID(itemStack), null);
    }

    public static void setStasisModifiable(ItemStack itemStack, StasisModifiable modifiable)
    {
        if (!Utils.hasUUID(itemStack))
            Utils.setUUID(itemStack);

        modifiableMap.put(Utils.getUUID(itemStack), modifiable);
    }

    public static boolean hasModifier(String id, ItemStack itemStack)
    {
        if (!hasModifiable(itemStack))
            setStasisModifiable(itemStack, getModFromNBT(itemStack));

        return getStasisModifiable(itemStack).modifierMap.containsKey(id);
    }

    // This method is horribly horrendous as all hell
    public static void invokeModMethod(ItemStack itemStack, Method method, Object... args)
    {
        if (!StasisModifiable.hasModifiable(itemStack))
            StasisModifiable.setStasisModifiable(itemStack, StasisModifiable.getModFromNBT(itemStack));

        StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);

        try
        {
            method.invoke(modifiable, args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        StasisModifiable.setStasisModifiable(itemStack, modifiable, false);
    }
}
