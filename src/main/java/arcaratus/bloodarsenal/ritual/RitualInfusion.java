package arcaratus.bloodarsenal.ritual;

import WayofTime.bloodmagic.altar.IBloodAltar;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.util.helper.NBTHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.block.BlockStasisPlate;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.collect.Sets;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@RitualRegister("infusion")
public class RitualInfusion extends RitualBloodArsenal
{
    private static final Set<BlockPos> STASIS_PLATE_POS = Sets.newHashSet(new BlockPos(1, 1, 3), new BlockPos(-1, 1, 3), new BlockPos(1, 1, -3), new BlockPos(-1, 1, -3), new BlockPos(3, 1, 1), new BlockPos(3, 1, -1), new BlockPos(-3, 1, 1), new BlockPos(-3, 1, -1));

    private int craftingTimer;
    private boolean isCrafting;
    private int modifierLevel; // Here to maintain the same crafting even if inputs are somehow changed
    private boolean trackerFlag;

    private RecipeSanguineInfusion recipe;
    private ItemStack wildStack;
    private NBTTagCompound specialNBT;

    public RitualInfusion()
    {
        super("infusion", 0, ConfigHandler.rituals.infusionRitualActivationCost, 1, ConfigHandler.rituals.infusionRitualRefreshCost);

        craftingTimer = 0;
        isCrafting = false;
        modifierLevel = 0;
        trackerFlag = false;

        recipe = null;
        wildStack = ItemStack.EMPTY;
        specialNBT = null;
    }

    @Override
    public boolean activateRitual(IMasterRitualStone masterRitualStone, EntityPlayer player, World world, SoulNetwork network)
    {
        BlockPos pos = masterRitualStone.getBlockPos();
        if (!checkStructure(world, pos))
        {
            BloodArsenalUtils.sendPlayerMessage(player, "chat.bloodarsenal.ritual.configuration", true);
            return false;
        }

        IInventory altarInv = (IInventory) world.getTileEntity(pos.add(0, 1, 0));

        if (altarInv.isEmpty())
        {
            BloodArsenalUtils.sendPlayerMessage(player, "chat.bloodarsenal.ritual.altar_no_item", true);
            return false;
        }

        List<ItemStack> inputStacks = getItemStackInputs(world, pos);
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

        System.out.println("EL: " + inputStacks);

        ItemStack input = altarInv.getStackInSlot(0);
        recipe = SanguineInfusionRecipeRegistry.getRecipeFromInputs(input, inputStacks); // Checks if the inputs match a recipe

        if (recipe == null)
        {
            BloodArsenalUtils.sendPlayerMessage(player, "chat.bloodarsenal.ritual.recipe_error", true);
            return false;
        }

        if (recipe.getFilter() != null && !recipe.getFilter().matches(wildStack) && (wildClass == null || wildStack.isEmpty() || !wildClass.isInstance(wildStack.getItem()))) // Check special classes and special itemstack
        {
            BloodArsenalUtils.sendPlayerMessage(player, "chat.bloodarsenal.ritual.recipe_error", true);
            return false;
        }

        if (recipe.getModifier() != Modifier.EMPTY_MODIFIER && input.getItem() instanceof IModifiableItem) // Modifier recipes
        {
            StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(input);

            String modifierKey = recipe.getModifier().getUniqueIdentifier();
            Modifier modifier = ModifierHandler.getModifierFromKey(modifierKey);
            ModifierTracker trackerOnItem = modifiable.getTrackerForModifier(modifierKey);

            if (!modifiable.hasModifier(modifierKey))
            {
                modifierLevel = 0; // Set the level to 0 in the case it doesn't have the modifier already
            }
            else
            {
                modifierLevel = trackerOnItem.getLevel() + 1; // Reduce the level down to the appropriate one
                trackerFlag = true; // Has a tracker to be upgraded
            }

            if (!modifiable.canApplyModifier(modifier, modifierLevel))
            {
                BloodArsenalUtils.sendPlayerMessage(player, TextHelper.localizeEffect("chat.bloodarsenal.ritual.modifier_incompatible", TextHelper.localize(modifier.getUnlocalizedName()), modifierLevel, input.getDisplayName()), true);
                return false;
            }

            Modifier modifierOnItem = modifiable.getModifier(modifierKey);

            if (modifierOnItem.getSpecialNBT(input) != null)
                specialNBT = modifierOnItem.getSpecialNBT(input);

            if (specialNBT != null)
            {
                ItemStack specificStack = new ItemStack(specialNBT.getCompoundTag(Constants.NBT.ITEMSTACK));
                if (!recipe.matchesWithSpecificity(wildStack, specificStack))
                {
                    BloodArsenalUtils.sendPlayerMessage(player, TextHelper.localizeEffect("chat.bloodarsenal.ritual.input_specific_error", specificStack.getDisplayName()), true);
                    return false;
                }
            }
        }

        return true;
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

            IInventory altarInv = (IInventory) world.getTileEntity(pos.add(0, 1, 0));
            ItemStack input = altarInv.getStackInSlot(0);

            if (recipe != null && !altarInv.isEmpty())
            {
                if (isCrafting && recipe.getModifier() == Modifier.EMPTY_MODIFIER) // Non-modifier recipes
                {
                    tickCrafting(world, pos, network);

                    // Finished crafting
                    if (craftingTimer >= recipe.getLpCost() / getRefreshCost())
                    {
                        shrinkItemStackInputs(world, pos, constructItemStackList(recipe, inputStacks, 0), ItemStack.EMPTY);
                        altarInv.setInventorySlotContents(0, recipe.getOutput());

                        world.spawnEntity(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY(), masterRitualStone.getBlockPos().getZ(), true));
                        endRitual(world, pos, masterRitualStone);
                    }
                }
                else if (recipe.getModifier() != Modifier.EMPTY_MODIFIER && input.getItem() instanceof IModifiableItem) // Modifier recipes
                {
                    StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(input);

                    String modifierKey = recipe.getModifier().getUniqueIdentifier();
                    Modifier modifier = ModifierHandler.getModifierFromKey(modifierKey);
                    ModifierTracker trackerOnItem = modifiable.getTrackerForModifier(modifierKey);
                    int level = getMaxModifierLevel(recipe, modifier.getMaxLevel(), inputStacks);

                    if (isCrafting && modifierLevel <= level) // Checks currently crafting modifier recipe and that modifierLevel hasn't changed
                    {
                        tickCrafting(world, pos, network);

                        // Finished crafting
                        if (craftingTimer >= recipe.getLpCost() * (modifierLevel + 1) / getRefreshCost())
                        {
                            modifiable.applyModifier(ModifierHelper.getModifierAndTracker(modifierKey, modifierLevel));
                            if (trackerFlag)
                                trackerOnItem.onModifierUpgraded(); // Upgrades the existing modifier

                            ItemStack copyStack = input.copy();
                            NBTHelper.checkNBT(copyStack);
                            modifier.removeSpecialNBT(copyStack); // Needed here in order to reset NBT data
                            modifier.writeSpecialNBT(copyStack, wildStack, modifierLevel);
                            modifiable.setMod(copyStack); // Updates the Stasis Modifiable

                            shrinkItemStackInputs(world, pos, constructItemStackList(recipe, inputStacks, modifierLevel), wildStack);
                            altarInv.setInventorySlotContents(0, copyStack);

                            world.spawnEntity(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY() + 1, masterRitualStone.getBlockPos().getZ(), true));
                            world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 3.5, pos.getZ() + 0.5, 0, false);
                            endRitual(world, pos, masterRitualStone);
                        }
                    }
                    else if (!isCrafting) // Start crafting process for modifier recipes
                    {
                        setStasisPlates(world, stasisPlates, true);
                        isCrafting = true;
                    }
                    else
                    {
                        endRitual(world, pos, masterRitualStone);
                    }
                }
                else if (ItemStack.areItemsEqual(recipe.getInfuse(), input)) // Start crafting process for non-modifier recipes
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

    private void tickCrafting(World world, BlockPos pos, SoulNetwork network)
    {
        if (!isCrafting)
            return;

        craftingTimer++;
        if (world.getWorldTime() % 4 == 0)
        {
            network.syphon(SoulTicket.block(world, pos, ConfigHandler.rituals.infusionRitualRefreshCost));
            if (world instanceof WorldServer)
            {
                WorldServer server = (WorldServer) world;
                server.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 5, 0.2, 0, 0.2, 0.2);
                server.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 5, 0.2, 0, 0.2, 0.1);
            }
        }
    }

    private void endRitual(World world, BlockPos pos, IMasterRitualStone mrs)
    {
        world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, false);
        List<TileStasisPlate> stasisPlates = getStasisPlates(world, pos);
        setStasisPlates(world, stasisPlates, false);
        isCrafting = false;
        craftingTimer = 0;
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

    // Get the max modifier level for the # of items present
    private int getMaxModifierLevel(RecipeSanguineInfusion recipe, int maxLevel, List<ItemStack> inputStacks)
    {
        for (int i = maxLevel; i >= 0; i--)
        {
            if (recipe.matches(ItemStack.EMPTY, inputStacks, i))
            {
                return i;
            }
        }

        return 0;
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

    // Provides the List<ItemStack> that will be consumed given the ingredients, inputs, and level of recipe
    private List<ItemStack> constructItemStackList(RecipeSanguineInfusion recipe, List<ItemStack> inputs, int level)
    {
        List<Pair<Ingredient, Integer>> ingredients = recipe.getInputsForLevel(level);
        List<ItemStack> dummyList = new ArrayList<>();

        for (Pair<Ingredient, Integer> entry : ingredients)
        {
            Ingredient ingredient = entry.getKey();
            int count = entry.getValue();

            for (ItemStack input : inputs)
            {
                if (ingredient.apply(input) && input.getCount() >= count)
                {
                    dummyList.add(ItemHandlerHelper.copyStackWithSize(input, count));
                    inputs.remove(input); // Handles duplicate-kind ItemStacks
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
                    if (!extraStack.isEmpty() && ItemStack.areItemsEqual(extraStack, plateStack))
                    {
                        plateStack.shrink(1);
                        plate.setInventorySlotContents(0, plateStack);
                    }

                    for (ItemStack recipeInput : recipeInputs)
                    {
                        if (ItemStack.areItemsEqual(recipeInput, plateStack))
                        {
                            plateStack.shrink(recipeInput.getCount());
                            plate.setInventorySlotContents(0, plateStack);
                            recipeInputs.remove(recipeInput);
                            break;
                        }
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
