package arc.bloodarsenal.ritual;

import WayofTime.bloodmagic.api.livingArmour.LivingArmourUpgrade;
import WayofTime.bloodmagic.api.livingArmour.StatTracker;
import WayofTime.bloodmagic.api.ritual.*;
import WayofTime.bloodmagic.api.util.helper.ItemHelper.LivingUpgrades;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.item.armour.ItemLivingArmour;
import WayofTime.bloodmagic.livingArmour.LivingArmour;
import WayofTime.bloodmagic.registry.ModItems;
import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.registry.Constants;
import com.google.common.collect.Iterables;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.Map.Entry;

/**
 * This Ritual overrides RitualUpgradeRemove in BloodMagic
 */
public class RitualModifierRemove extends Ritual
{
    public static final String CHECK_RANGE = "fillRange";

    public RitualModifierRemove()
    {
        super("ritualUpgradeRemove", 0, 25000, "ritual." + WayofTime.bloodmagic.api.Constants.Mod.MODID + ".upgradeRemoveRitual");
        addBlockRange(CHECK_RANGE, new AreaDescriptor.Rectangle(new BlockPos(0, 1, 0), 1, 2, 1));
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone)
    {
        World world = masterRitualStone.getWorldObj();

        if (world.isRemote)
        {
            return;
        }

        BlockPos pos = masterRitualStone.getBlockPos();

        AreaDescriptor checkRange = getBlockRange(CHECK_RANGE);

        List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, checkRange.getAABB(pos));

        boolean modifierRemove = false;

        for (EntityItem entityItem : itemList)
        {
            boolean removedModifier = false;

            ItemStack itemStack = entityItem.getEntityItem();
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);
                if (modifiable != null)
                {
                    @SuppressWarnings("unchecked") HashMap<String, Modifier> modifierMap = (HashMap<String, Modifier>) modifiable.modifierMap.clone();
                    for (Entry<String, Modifier> entry : modifierMap.entrySet())
                    {
                        Modifier modifier = entry.getValue();
                        String modifierKey = entry.getKey();

                        ItemStack modifierStack = new ItemStack(arc.bloodarsenal.registry.ModItems.MODIFIER_TOME);
                        NBTHelper.checkNBT(modifierStack);
                        ModifierHelper.setKey(modifierStack, modifierKey);
                        ModifierHelper.setLevel(modifierStack, modifier.getLevel());
                        ModifierHelper.setReady(modifierStack, modifier.readyForUpgrade());
                        modifier.writeSpecialNBT(modifierStack, new ItemStack(itemStack.getTagCompound().getCompoundTag(Constants.NBT.ITEMSTACK)));

                        boolean successful = modifiable.removeModifier(modifier);

                        if (successful)
                        {
                            modifierRemove = true;
                            removedModifier = true;
                            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, modifierStack));
                            for (Entry<String, ModifierTracker> trackerEntry : modifiable.trackerMap.entrySet())
                            {
                                ModifierTracker tracker = trackerEntry.getValue();
                                if (tracker != null && tracker.providesModifier(modifierKey))
                                {
                                    tracker.resetTracker();
                                }
                            }
                        }
                    }

                    if (removedModifier)
                    {
                        StasisModifiable.setStasisModifiable(itemStack, modifiable, true);
                        StasisModifiable.setStasisModifiable(itemStack, modifiable);

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

                        ItemStack upgradeStack = new ItemStack(ModItems.UPGRADE_TOME);
                        LivingUpgrades.setKey(upgradeStack, upgradeKey);
                        LivingUpgrades.setLevel(upgradeStack, upgrade.getUpgradeLevel());

                        boolean successful = armour.removeUpgrade(player, upgrade);

                        if (successful)
                        {
                            removedUpgrade = true;
                            world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, upgradeStack));
                            for (Entry<String, StatTracker> trackerEntry : armour.trackerMap.entrySet())
                            {
                                StatTracker tracker = trackerEntry.getValue();
                                if (tracker != null)
                                {
                                    if (tracker.providesUpgrade(upgradeKey))
                                    {
                                        tracker.resetTracker(); //Resets the tracker if the upgrade corresponding to it was removed.
                                    }
                                }
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
    public ArrayList<RitualComponent> getComponents()
    {
        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();

        this.addCornerRunes(components, 1, 0, EnumRuneType.DUSK);
        this.addCornerRunes(components, 2, 0, EnumRuneType.FIRE);
        this.addOffsetRunes(components, 1, 2, 0, EnumRuneType.FIRE);
        this.addCornerRunes(components, 1, 1, EnumRuneType.WATER);
        this.addParallelRunes(components, 4, 0, EnumRuneType.EARTH);
        this.addCornerRunes(components, 1, 3, EnumRuneType.WATER);
        this.addParallelRunes(components, 1, 4, EnumRuneType.AIR);

        for (int i = 0; i < 4; i++)
        {
            this.addCornerRunes(components, 3, i, EnumRuneType.EARTH);
        }

        return components;
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualModifierRemove();
    }
}
