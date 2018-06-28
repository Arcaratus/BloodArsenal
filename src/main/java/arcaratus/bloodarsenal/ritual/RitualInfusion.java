package arcaratus.bloodarsenal.ritual;

import WayofTime.bloodmagic.altar.IBloodAltar;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.util.helper.NBTHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.block.BlockStasisPlate;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.*;
import java.util.function.Consumer;

public class RitualInfusion extends RitualBloodArsenal
{
    private static final Set<BlockPos> STASIS_PLATE_POS = Sets.newHashSet(new BlockPos(1, 1, 3), new BlockPos(-1, 1, 3), new BlockPos(1, 1, -3), new BlockPos(-1, 1, -3), new BlockPos(3, 1, 1), new BlockPos(3, 1, -1), new BlockPos(-3, 1, 1), new BlockPos(-3, 1, -1));

    private int craftingTimer;
    private boolean isCrafting;

    public RitualInfusion()
    {
        super("infusion", 0, ConfigHandler.rituals.infusionRitualActivationCost, 1, ConfigHandler.rituals.infusionRitualRefreshCost);

        craftingTimer = 0;
        isCrafting = false;
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone, World world, SoulNetwork network)
    {
        BlockPos pos = masterRitualStone.getBlockPos();

        if (world.isRemote)
            return;

        if (checkStructure(world, pos))
        {
            List<ItemStack> inputStacks = getItemStackInputs(world, pos);
            List<TileStasisPlate> stasisPlates = getStasisPlates(world, pos);
            ItemStack wildStack = ItemStack.EMPTY;
            Class wildClass = null;

            // Remove wildcard item inputs from inputStacks
            first:
            for (ItemStack itemStack : inputStacks)
            {
                for (Class clazz : SanguineInfusionRecipeRegistry.getBlacklistedClasses())
                {
                    if (clazz.isInstance(itemStack.getItem()))
                    {
                        wildStack = itemStack;
                        wildClass = clazz;
                        inputStacks.remove(itemStack);
                        break first;
                    }
                }
            }

            IInventory altarInv = (IInventory) world.getTileEntity(pos.add(0, 1, 0));
            ItemStack input = altarInv.getStackInSlot(0);
            RecipeSanguineInfusion recipe = SanguineInfusionRecipeRegistry.getRecipeFromInputs(input, inputStacks); // Checks if the inputs match a recipe

            if (recipe != null && !altarInv.isEmpty())
            {
                if (recipe.getFilter() != null && !recipe.getFilter().matches(wildStack) && (wildClass == null || wildStack.isEmpty() || !wildClass.isInstance(wildStack.getItem()))) // Check special classes and special itemstack
                {
                    endRitual(world, pos, masterRitualStone);
                    return;
                }

                if (recipe.getModifier() != Modifier.EMPTY_MODIFIER && input.getItem() instanceof IModifiableItem)
                {
                    StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(input);

                    String modifierKey = recipe.getModifier().getUniqueIdentifier();
                    Modifier modifier = ModifierHandler.getModifierFromKey(modifierKey);
                    Modifier modifierOnItem = modifiable.getModifier(modifierKey);
                    ModifierTracker trackerOnItem = modifiable.getTrackerForModifier(modifierKey);

                    if (!modifiable.canApplyModifier(modifier))
                    {
                        endRitual(world, pos, masterRitualStone);
                        return;
                    }

                    int level = -1;

                    // Get the modifier level for the # of items present
                    for (int i = modifier.getMaxLevel(); i >= 0; i--)
                    {
                        if (recipe.matches(ItemStack.EMPTY, inputStacks, i))
                        {
                            level = i;
                            break;
                        }
                    }

                    NBTTagCompound specialNBT = null;
                    boolean trackerFlag = false;
                    if (!modifiable.hasModifier(modifierKey) && level >= 0)
                    {
                        level = 0;
                    }
                    else if (trackerOnItem != null && trackerOnItem.isReadyToUpgrade() && level > trackerOnItem.getLevel())
                    {
                        if (modifierOnItem.getSpecialNBT(input) != null)
                            specialNBT = modifierOnItem.getSpecialNBT(input);

                        level = trackerOnItem.getLevel() + 1;
                        trackerFlag = true;
                    }
                    else
                    {
                        endRitual(world, pos, masterRitualStone);
                        return;
                    }

                    // Just assume this works
                    if (specialNBT != null && !recipe.matchesWithSpecificity(wildStack, new ItemStack(specialNBT.getCompoundTag(Constants.NBT.ITEMSTACK))))
                    {
                        endRitual(world, pos, masterRitualStone);
                        return;
                    }

                    tickCrafting(network);

                    if (craftingTimer == recipe.getLpCost() * (level + 1) / getRefreshCost())
                    {
                        modifiable.applyModifier(ModifierHelper.getModifierAndTracker(modifierKey, level));
                        if (trackerFlag)
                            trackerOnItem.onModifierUpgraded();
                        ItemStack copyStack = input.copy();
                        NBTHelper.checkNBT(copyStack);
                        modifier.removeSpecialNBT(copyStack); // Needed here in order to reset NBT data
                        modifier.writeSpecialNBT(copyStack, wildStack, level);
                        StasisModifiable.setModifiable(copyStack, modifiable, false);

                        shrinkItemStackInputs(world, pos, constructItemStackList(recipe.getInputs(), inputStacks), wildStack);
                        altarInv.setInventorySlotContents(0, copyStack);

                        world.spawnEntity(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY() + 1, masterRitualStone.getBlockPos().getZ(), true));
                        endRitual(world, pos, masterRitualStone);
                        return;
                    }

                    if (!isCrafting)
                    {
                        setStasisPlates(world, stasisPlates, true);
                        isCrafting = true;
                    }
                }
                else if (isCrafting)
                {
                    tickCrafting(network);

                    if (craftingTimer == recipe.getLpCost() / getRefreshCost())
                    {
                        shrinkItemStackInputs(world, pos, constructItemStackList(recipe.getInputs(), inputStacks), ItemStack.EMPTY);
                        altarInv.setInventorySlotContents(0, recipe.getOutput());

                        world.spawnEntity(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY(), masterRitualStone.getBlockPos().getZ(), true));
                        endRitual(world, pos, masterRitualStone);
                    }
                }
                else if (ItemStack.areItemsEqual(recipe.getInfuse(), input))
                {
                    setStasisPlates(world, stasisPlates, true);
                    isCrafting = true;
                }
            }
            else
            {
                endRitual(world, pos, masterRitualStone);
            }
        }
        else
        {
            endRitual(world, pos, masterRitualStone);
        }
    }

    private void tickCrafting(SoulNetwork network)
    {
        if (!isCrafting)
            return;

        craftingTimer++;
        network.syphon(getRefreshCost());
    }

    private void endRitual(World world, BlockPos pos, IMasterRitualStone mrs)
    {
        List<TileStasisPlate> stasisPlates = getStasisPlates(world, pos);
        setStasisPlates(world, stasisPlates, false);
        mrs.setActive(false);
    }

    private boolean checkStructure(World world, BlockPos pos)
    {
        if (!(world.getTileEntity(pos.add(0, 1, 0)) instanceof IBloodAltar))
            return false;

        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
            if (!(world.getBlockState(pos.add(stasisPlatePos)).getBlock() instanceof BlockStasisPlate))
                return false;

        return true;
    }

    private List<TileStasisPlate> getStasisPlates(World world, BlockPos pos)
    {
        List<TileStasisPlate> stasisPlates = new ArrayList<>();
        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
            if (world.getTileEntity(pos.add(stasisPlatePos)) instanceof TileStasisPlate)
                stasisPlates.add((TileStasisPlate) world.getTileEntity(pos.add(stasisPlatePos)));

        return stasisPlates;
    }

    private void setStasisPlates(World world, List<TileStasisPlate> stasisPlates, boolean stasis)
    {
        for (TileStasisPlate plate : stasisPlates)
        {
            plate.setStasis(stasis);
            world.notifyBlockUpdate(plate.getPos(), world.getBlockState(plate.getPos()), world.getBlockState(plate.getPos()), 3);
        }
    }

    private List<ItemStack> getItemStackInputs(World world, BlockPos pos)
    {
        List<ItemStack> stackList = new ArrayList<>();
        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
        {
            BlockPos actualPos = pos.add(stasisPlatePos);
            if (world.getTileEntity(actualPos) instanceof TileStasisPlate)
            {
                TileStasisPlate plate = (TileStasisPlate) world.getTileEntity(actualPos);
                if (!plate.getStackInSlot(0).isEmpty())
                    stackList.add(plate.getStackInSlot(0));
            }
        }

        return stackList;
    }

    private List<ItemStack> constructItemStackList(ImmutableMap<Ingredient, Integer> ingredients, List<ItemStack> inputs)
    {
        List<ItemStack> dummyList = new ArrayList<>();

        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet())
        {
            for (ItemStack input : inputs)
            {
                Ingredient ingredient = entry.getKey();
                if (ingredient.apply(input) && input.getCount() >= entry.getValue())
                {
                    dummyList.add(ItemHandlerHelper.copyStackWithSize(input, entry.getValue()));
                    break;
                }
            }
        }

        return dummyList;
    }

    private void shrinkItemStackInputs(World world, BlockPos pos, List<ItemStack> recipeInputs, ItemStack extraStack)
    {
        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
        {
            BlockPos platePosition = pos.add(stasisPlatePos);
            if (world.getTileEntity(platePosition) instanceof TileStasisPlate)
            {
                TileStasisPlate plate = (TileStasisPlate) world.getTileEntity(platePosition);
                ItemStack plateStack = plate.getStackInSlot(0);
                if (!plateStack.isEmpty())
                {
                    for (ItemStack recipeInput : recipeInputs)
                    {
                        if (ItemStack.areItemsEqual(recipeInput, plateStack))
                        {
                            plateStack.shrink(recipeInput.getCount());
                            plate.setInventorySlotContents(0, plateStack);
                        }
                    }

                    if (!extraStack.isEmpty() && ItemStack.areItemsEqual(extraStack, plateStack))
                    {
                        plateStack.shrink(1);
                        plate.setInventorySlotContents(0, plateStack);
                    }
                }
            }
        }
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components)
    {
        addCornerRunes(components, 1, -1, EnumRuneType.WATER);
        addCornerRunes(components, 2, -1, EnumRuneType.FIRE);
        addParallelRunes(components, 3, -1, EnumRuneType.EARTH);
        addOffsetRunes(components, 1, 4, -1, EnumRuneType.WATER);
        addParallelRunes(components, 5, -1, EnumRuneType.AIR);
        addParallelRunes(components, 2, 0, EnumRuneType.DUSK);
        addOffsetRunes(components, 1, 3, 0, EnumRuneType.FIRE);
        addCornerRunes(components, 3, 0, EnumRuneType.DUSK);
        addParallelRunes(components, 4, 0, EnumRuneType.DUSK);
        addCornerRunes(components, 4, 0, EnumRuneType.WATER);
        addOffsetRunes(components, 2, 5, 0, EnumRuneType.BLANK);
        addOffsetRunes(components, 3, 5, 0, EnumRuneType.BLANK);
        addOffsetRunes(components, 4, 5, 0, EnumRuneType.BLANK);
        addCornerRunes(components, 4, 1, EnumRuneType.EARTH);
        addOffsetRunes(components, 2, 5, 1, EnumRuneType.EARTH);
        addCornerRunes(components, 4, 2, EnumRuneType.FIRE);
        addOffsetRunes(components, 2, 5, 2, EnumRuneType.FIRE);
        addCornerRunes(components, 4, 3, EnumRuneType.EARTH);
        addOffsetRunes(components, 2, 5, 3, EnumRuneType.EARTH);
        addOffsetRunes(components, 1, 5, 4, EnumRuneType.WATER);
        addOffsetRunes(components, 3, 4, 4, EnumRuneType.WATER);
        addParallelRunes(components, 4, 5, EnumRuneType.AIR);
        addCornerRunes(components, 3, 5, EnumRuneType.AIR);
        addOffsetRunes(components, 2, 3, 5, EnumRuneType.DUSK);
        addParallelRunes(components, 3, 6, EnumRuneType.DUSK);
        addOffsetRunes(components, 1, 3, 6, EnumRuneType.DUSK);
        addCornerRunes(components, 2, 6, EnumRuneType.DUSK);
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualInfusion();
    }
}
