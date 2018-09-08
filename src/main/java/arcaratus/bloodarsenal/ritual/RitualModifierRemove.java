package arcaratus.bloodarsenal.ritual;

import WayofTime.bloodmagic.BloodMagic;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.item.armour.ItemLivingArmour;
import WayofTime.bloodmagic.livingArmour.*;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.ritual.types.RitualUpgradeRemove;
import WayofTime.bloodmagic.util.helper.ItemHelper;
import WayofTime.bloodmagic.util.helper.NBTHelper;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BALog;
import com.google.common.collect.Iterables;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * This Ritual overrides RitualUpgradeRemove in BloodMagic
 * Uses REFLECTION because the annotation overrides weirdly
 */
//@RitualRegister("upgradeRemove")
public class RitualModifierRemove extends Ritual
{
    public static final String CHECK_RANGE = "fillRange";

    public RitualModifierRemove()
    {
        super("ritualUpgradeRemove", 0, 25000, "ritual." + BloodMagic.MODID + ".upgradeRemoveRitual");
        addBlockRange(CHECK_RANGE, new AreaDescriptor.Rectangle(new BlockPos(0, 1, 0), 1, 2, 1));
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone)
    {
        World world = masterRitualStone.getWorldObj();

        if (world.isRemote)
            return;

        BlockPos pos = masterRitualStone.getBlockPos();

        AreaDescriptor checkRange = getBlockRange(CHECK_RANGE);

        List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, checkRange.getAABB(pos));

        boolean modifierRemove = false;

        for (EntityItem entityItem : itemList)
        {
            boolean removedModifier = false;

            ItemStack itemStack = entityItem.getItem();
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(itemStack);
                if (modifiable != null)
                {
                    @SuppressWarnings("unchecked") HashMap<String, Pair<Modifier, ModifierTracker>> modifierMap = (HashMap<String, Pair<Modifier, ModifierTracker>>) modifiable.getModifierMap();
                    for (Entry<String, Pair<Modifier, ModifierTracker>> entry : modifierMap.entrySet())
                    {
                        Modifier modifier = entry.getValue().getLeft();
                        ModifierTracker tracker = entry.getValue().getRight();
                        String modifierKey = entry.getKey();

                        ItemStack modifierStack = new ItemStack(RegistrarBloodArsenalItems.MODIFIER_TOME);
                        NBTHelper.checkNBT(modifierStack);
                        ModifierHelper.setKey(modifierStack, modifierKey);
                        ModifierHelper.setLevel(modifierStack, tracker.getLevel());
                        ModifierHelper.setReady(modifierStack, tracker.isReadyToUpgrade());
                        modifier.writeSpecialNBT(modifierStack, new ItemStack(itemStack.getTagCompound().getCompoundTag(Constants.NBT.ITEMSTACK)), tracker.getLevel());

                        boolean successful = modifiable.removeModifier(modifier);

                        if (successful)
                        {
                            modifierRemove = true;
                            removedModifier = true;
                            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, modifierStack));
                        }
                    }

                    if (removedModifier)
                    {
                        StasisModifiable.setModifiable(itemStack, modifiable, true);

                        masterRitualStone.setActive(false);

                        world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY() - 1, pos.getZ(), true));
                    }
                }
            }
        }

        if (modifierRemove)
            return;

        List<EntityPlayer> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, checkRange.getAABB(pos));

        for (EntityPlayer player : playerList)
        {
            if (LivingArmour.hasFullSet(player))
            {
                boolean removedUpgrade = false;

                ItemStack chestStack = Iterables.toArray(player.getArmorInventoryList(), ItemStack.class)[2];
                LivingArmour armour = ItemLivingArmour.getLivingArmour(chestStack);
                if (armour != null)
                {
                    @SuppressWarnings("unchecked") HashMap<String, LivingArmourUpgrade> upgradeMap = (HashMap<String, LivingArmourUpgrade>) armour.upgradeMap.clone();

                    for (Entry<String, LivingArmourUpgrade> entry : upgradeMap.entrySet())
                    {
                        LivingArmourUpgrade upgrade = entry.getValue();
                        String upgradeKey = entry.getKey();

                        ItemStack upgradeStack = new ItemStack(RegistrarBloodMagicItems.UPGRADE_TOME);
                        ItemHelper.LivingUpgrades.setKey(upgradeStack, upgradeKey);
                        ItemHelper.LivingUpgrades.setLevel(upgradeStack, upgrade.getUpgradeLevel());

                        boolean successful = armour.removeUpgrade(player, upgrade);

                        if (successful)
                        {
                            removedUpgrade = true;
                            world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, upgradeStack));
                            for (Entry<String, StatTracker> trackerEntry : armour.trackerMap.entrySet())
                            {
                                StatTracker tracker = trackerEntry.getValue();
                                if (tracker != null)
                                    if (tracker.providesUpgrade(upgradeKey))
                                        tracker.resetTracker(); //Resets the tracker if the upgrade corresponding to it was removed.
                            }
                        }
                    }

                    if (removedUpgrade)
                    {
                        ((ItemLivingArmour) chestStack.getItem()).setLivingArmour(chestStack, armour, true);
                        ItemLivingArmour.setLivingArmour(chestStack, armour);
                        armour.recalculateUpgradePoints();

                        masterRitualStone.setActive(false);

                        world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY() - 1, pos.getZ(), true));
                    }
                }
            }
        }
    }

    @Override
    public int getRefreshTime()
    {
        return 1;
    }

    @Override
    public int getRefreshCost()
    {
        return 0;
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components)
    {
        addCornerRunes(components, 1, 0, EnumRuneType.DUSK);
        addCornerRunes(components, 2, 0, EnumRuneType.FIRE);
        addOffsetRunes(components, 1, 2, 0, EnumRuneType.FIRE);
        addCornerRunes(components, 1, 1, EnumRuneType.WATER);
        addParallelRunes(components, 4, 0, EnumRuneType.EARTH);
        addCornerRunes(components, 1, 3, EnumRuneType.WATER);
        addParallelRunes(components, 1, 4, EnumRuneType.AIR);

        for (int i = 0; i < 4; i++)
            addCornerRunes(components, 3, i, EnumRuneType.EARTH);
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualModifierRemove();
    }

    public static void overrideRitual()
    {
        BALog.DEFAULT.info("Overriding the Sound of the Cleansing Soul (upgradeRemoveRitual)");
        BALog.DEFAULT.info("Report any issues about the ritual to Blood Arsenal first, NOT Blood Magic");

        try
        {
            Field fieldRituals = RitualManager.class.getDeclaredField("rituals");
            fieldRituals.setAccessible(true);
            Field fieldRitualsReverse = RitualManager.class.getDeclaredField("ritualsReverse");
            fieldRitualsReverse.setAccessible(true);
            Field fieldSortedRituals = RitualManager.class.getDeclaredField("sortedRituals");
            fieldSortedRituals.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            modifiersField.setInt(fieldRituals, fieldRituals.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            modifiersField.setInt(fieldRitualsReverse, fieldRituals.getModifiers() & ~java.lang.reflect.Modifier.FINAL);

            RitualManager manager = BloodMagic.RITUAL_MANAGER;
            String id = "upgradeRemove";
            Ritual modifierRemoveRitual = new RitualModifierRemove();

            Map<String, Ritual> rituals = (Map<String, Ritual>) fieldRituals.get(manager);
            rituals.put(id, modifierRemoveRitual);
            fieldRituals.set(manager, rituals);

            Map<Ritual, String> ritualsReverse = (Map<Ritual, String>) fieldRitualsReverse.get(manager);
            ritualsReverse.put(modifierRemoveRitual, id);
            fieldRitualsReverse.set(manager, ritualsReverse);

            List<Ritual> sortedRituals = (List<Ritual>) fieldSortedRituals.get(manager);
            for (int i = 0; i < sortedRituals.size(); i++)
            {
                if (sortedRituals.get(i) instanceof RitualUpgradeRemove)
                {
                    sortedRituals.set(i, modifierRemoveRitual);
                    break;
                }
            }

            fieldRituals.setAccessible(false);
            fieldRitualsReverse.setAccessible(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
