package arc.bloodarsenal.ritual;

import WayofTime.bloodmagic.api.altar.IBloodAltar;
import WayofTime.bloodmagic.api.ritual.*;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import arc.bloodarsenal.block.BlockStasisPlate;
import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.recipe.RecipeSanguineInfusion;
import arc.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.tile.TileStasisPlate;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualInfusion extends RitualBloodArsenal
{
    private BlockPos[] stasisPlatePositions = new BlockPos[] { new BlockPos(1, 1, 3), new BlockPos(-1, 1, 3), new BlockPos(1, 1, -3), new BlockPos(-1, 1, -3), new BlockPos(3, 1, 1), new BlockPos(3, 1, -1), new BlockPos(-3, 1, 1), new BlockPos(-3, 1, -1) };

    private int craftingTimer;
    private boolean isCrafting;

    private final int CONSTANT_OF_INFUSION = 100;

    public RitualInfusion()
    {
        super("infusion", 0, 100, 1, 0);

        craftingTimer = 0;
        isCrafting = false;
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone)
    {
        World world = masterRitualStone.getWorldObj();

        if (world.isRemote)
            return;

        BlockPos pos = masterRitualStone.getBlockPos();

        if (checkStructure(world, pos))
        {
            List<ItemStack> inputStacks = getItemStackInputs(world, pos);
            List<TileStasisPlate> stasisPlates = getStasisPlates(world, pos);
            ItemStack wildStack = null;
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

            RecipeSanguineInfusion recipe = SanguineInfusionRecipeRegistry.getRecipeFromInputs(inputStacks); // Checks if items match
            IInventory altarInv = (IInventory) world.getTileEntity(pos.add(0, 1, 0));
            ItemStack input = altarInv.getStackInSlot(0);

            if (recipe != null && altarInv != null)
            {
                if (recipe.isSpecial() && (wildClass == null || wildStack == null || !wildClass.isInstance(wildStack.getItem()))) // Check special classes and special itemstack
                {
                    endRitual(world, pos, masterRitualStone);
                    return;
                }

                if (recipe.getFilter() != null && !recipe.getFilter().matches(wildStack)) // Check specific item filters
                {
                    endRitual(world, pos, masterRitualStone);
                    return;
                }

                if (recipe.isModifier())
                {
                    if (input != null && input.getItem() instanceof IModifiableItem)
                    {
                        StasisModifiable modifiable = StasisModifiable.getModFromNBT(input);
                        if (modifiable == null)
                        {
                            endRitual(world, pos, masterRitualStone);
                            return;
                        }

                        Modifier modifier = recipe.getModifier();
                        Modifier originalModifier = modifiable.getModifier(modifier.getClass());

                        int modifierLevel = -1;

                        // Get the modifier level for the # of items present
                        for (int i = recipe.getModifier().getMaxLevel() - 1; i >= 0; i--)
                        {
                            if (recipe.matches(inputStacks, i))
                            {
                                modifierLevel = i;
                                break;
                            }
                        }

                        NBTTagCompound specialNBT = null;
                        if (originalModifier == Modifier.EMPTY_MODIFIER && modifierLevel >= 0)
                        {
                            modifierLevel = 0;
                        }
                        else if (originalModifier.readyForUpgrade() && modifierLevel > originalModifier.getLevel())
                        {
                            if (originalModifier.getSpecialNBT(input) != null)
                            {
                                specialNBT = originalModifier.getSpecialNBT(input);
                            }

                            modifierLevel = originalModifier.getLevel() + 1;
                        }
                        else
                        {
                            endRitual(world, pos, masterRitualStone);
                            return;
                        }

                        if (specialNBT != null && !recipe.matchesWithSpecificity(wildStack, ItemStack.loadItemStackFromNBT(specialNBT.getCompoundTag(Constants.NBT.ITEMSTACK))))
                        {
                            endRitual(world, pos, masterRitualStone);
                            return;
                        }

                        modifier.setLevel(modifierLevel);

                        if (!modifiable.canApplyModifier(modifier))
                        {
                            endRitual(world, pos, masterRitualStone);
                            return;
                        }

                        tickCrafting(masterRitualStone);

                        if (craftingTimer == recipe.getLpCost() * (modifierLevel + 1) / CONSTANT_OF_INFUSION && modifiable.applyModifier(modifier))
                        {
                            ItemStack copyStack = input.copy();
                            NBTHelper.checkNBT(copyStack);
                            modifier.removeSpecialNBT(copyStack); // Needed here in order to reset NBT data
                            modifier.writeSpecialNBT(copyStack, wildStack);
                            if (!StasisModifiable.hasModifiable(copyStack))
                                StasisModifiable.setStasisModifiable(copyStack, StasisModifiable.getModFromNBT(copyStack));
                            StasisModifiable.setStasisModifiable(copyStack, modifiable);

                            shrinkItemStackInputs(world, pos, recipe.getInputsForLevel(modifierLevel), wildStack);
                            altarInv.setInventorySlotContents(0, copyStack);

                            world.spawnEntityInWorld(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY() + 1, masterRitualStone.getBlockPos().getZ(), true));
                            endRitual(world, pos, masterRitualStone);
                            return;
                        }

                        if (!isCrafting)
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
                else if (isCrafting)
                {
                    tickCrafting(masterRitualStone);

                    if (craftingTimer == recipe.getLpCost() / CONSTANT_OF_INFUSION)
                    {
                        shrinkItemStackInputs(world, pos, recipe.getItemStackInputs(), null);
                        altarInv.setInventorySlotContents(0, recipe.getOutput());

                        world.spawnEntityInWorld(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY(), masterRitualStone.getBlockPos().getZ(), true));
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

    private void tickCrafting(IMasterRitualStone mrs)
    {
        if (!isCrafting)
            return;

        craftingTimer++;
        NetworkHelper.getSoulNetwork(mrs.getOwner()).syphon(CONSTANT_OF_INFUSION);
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

        for (BlockPos stasisPlatePos : stasisPlatePositions)
            if (!(world.getBlockState(pos.add(stasisPlatePos)).getBlock() instanceof BlockStasisPlate))
                return false;

        return true;
    }

    private List<TileStasisPlate> getStasisPlates(World world, BlockPos pos)
    {
        List<TileStasisPlate> stasisPlates = new ArrayList<>();
        for (BlockPos stasisPlatePos : stasisPlatePositions)
            if (world.getTileEntity(pos.add(stasisPlatePos)) instanceof TileStasisPlate)
                stasisPlates.add((TileStasisPlate) world.getTileEntity(pos.add(stasisPlatePos)));

        return stasisPlates;
    }

    private void setStasisPlates(World world, List<TileStasisPlate> stasisPlates, boolean stasis)
    {
        for (TileStasisPlate plate : stasisPlates)
        {
            if (plate.getStasis())
            {
                plate.setStasis(stasis);
                world.notifyBlockUpdate(plate.getPos(), world.getBlockState(plate.getPos()), world.getBlockState(plate.getPos()), 3);
            }
        }
    }

    private List<ItemStack> getItemStackInputs(World world, BlockPos pos)
    {
        List<ItemStack> stackList = new ArrayList<>();
        BlockPos actualPos;
        for (BlockPos stasisPlatePos : stasisPlatePositions)
        {
            actualPos = pos.add(stasisPlatePos);
            if (world.getTileEntity(actualPos) instanceof TileStasisPlate)
            {
                TileStasisPlate plate = (TileStasisPlate) world.getTileEntity(actualPos);
                if (plate.getStackInSlot(0) != null)
                    stackList.add(plate.getStackInSlot(0));
            }
        }

        return stackList;
    }

    private void shrinkItemStackInputs(World world, BlockPos pos, List<ItemStack> recipeInputs, ItemStack extraStack)
    {
        for (BlockPos stasisPlatePos : stasisPlatePositions)
        {
            BlockPos platePosition = pos.add(stasisPlatePos);
            if (world.getTileEntity(platePosition) instanceof TileStasisPlate)
            {
                TileStasisPlate plate = (TileStasisPlate) world.getTileEntity(platePosition);
                ItemStack plateStack = plate.getStackInSlot(0);
                if (plate != null)
                {
                    for (ItemStack recipeInput : recipeInputs)
                    {
                        if (ItemStack.areItemsEqual(recipeInput, plateStack))
                        {
                            plateStack.stackSize -= recipeInput.stackSize;
                            plate.setInventorySlotContents(0, plateStack);
                        }
                    }

                    if (extraStack != null && ItemStack.areItemsEqual(extraStack, plateStack))
                    {
                        plateStack.stackSize -= 1;
                        plate.setInventorySlotContents(0, plateStack);
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<RitualComponent> addComponents(ArrayList<RitualComponent> components)
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
        return components;
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualInfusion();
    }
}
