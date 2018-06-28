package arcaratus.bloodarsenal.recipe;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.modifier.ModifierHandler;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreIngredient;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeSanguineInfusion
{
    @Nonnull
    private final ItemStack output;
    @Nonnull
    private final ItemStack infuse;
    // Contains the Ingredient and the amount required
    @Nonnull
    private final ImmutableMap<Ingredient, Integer> inputs;

    @Nonnegative
    private final int lpCost;

    private boolean isModifier = false;
    private String modifierKey = "";
    private Modifier modifier = Modifier.EMPTY_MODIFIER;

    // The multiplier used per level (think of the r term in a geometric series)
    private int levelMultiplier = 1;

    private RecipeFilter filter = null;

    @SafeVarargs
    public RecipeSanguineInfusion(@Nonnull ItemStack output, @Nonnegative int lpCost, @Nonnull ItemStack infuse, @Nonnull Pair<Object, Integer>... inputs)
    {
        this.output = output.copy();
        this.infuse = infuse.copy();

        this.lpCost = lpCost;

        ImmutableMap.Builder<Ingredient, Integer> inputsToMap = ImmutableMap.builder();
        for (Pair<Object, Integer> pair : inputs)
        {
            Object o = pair.getLeft();
            Integer i = pair.getRight();
            if (o instanceof Item)
                inputsToMap.put(Ingredient.fromStacks(new ItemStack((Item) o)), i);
            else if (o instanceof Block)
                inputsToMap.put(Ingredient.fromStacks(new ItemStack((Block) o)), i);
            else if (o instanceof ItemStack)
                inputsToMap.put(Ingredient.fromStacks(((ItemStack) o).copy()), i);
            else if (o instanceof String)
                inputsToMap.put(new OreIngredient((String) o), i);
            else if (o instanceof RecipeFilter)
                filter = (RecipeFilter) o;
            else
                throw new IllegalArgumentException("Invalid input: " + o);
        }

        this.inputs = inputsToMap.build();
    }

    @SafeVarargs
    public RecipeSanguineInfusion(@Nonnegative int lpCost, @Nonnull String modifierKey, @Nonnull Pair<Object, Integer>... inputs)
    {
        this.output = this.infuse = ItemStack.EMPTY;

        this.lpCost = lpCost;
        this.isModifier = true;
        this.modifierKey = modifierKey;
        this.modifier = ModifierHandler.getModifierFromKey(BloodArsenal.MOD_ID + ".modifier." + modifierKey);

        ImmutableMap.Builder<Ingredient, Integer> inputsToMap = ImmutableMap.builder();
        for (Pair<Object, Integer> pair : inputs)
        {
            Object o = pair.getLeft();
            Integer i = pair.getRight();
            if (o instanceof Item)
                inputsToMap.put(Ingredient.fromStacks(new ItemStack((Item) o)), i);
            else if (o instanceof Block)
                inputsToMap.put(Ingredient.fromStacks(new ItemStack((Block) o)), i);
            else if (o instanceof ItemStack)
                inputsToMap.put(Ingredient.fromStacks(((ItemStack) o).copy()), i);
            else if (o instanceof String)
                inputsToMap.put(new OreIngredient((String) o), i);
            else if (o instanceof RecipeFilter)
                filter = (RecipeFilter) o;
            else
                throw new IllegalArgumentException("Invalid input");
        }

        this.inputs = inputsToMap.build();
    }

    public RecipeSanguineInfusion setLevelMultiplier(int levelMultiplier)
    {
        this.levelMultiplier = levelMultiplier;
        return this;
    }

    public ItemStack getInfuse()
    {
        return infuse;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public int getLpCost()
    {
        return lpCost;
    }

    public boolean isModifier()
    {
        return isModifier;
    }

    public String getModifierKey()
    {
        return modifierKey;
    }

    public Modifier getModifier()
    {
        return modifier;
    }

    public int getLevelMultiplier()
    {
        return levelMultiplier;
    }

    public RecipeFilter getFilter()
    {
        return filter;
    }

    public ImmutableMap<Ingredient, Integer> getInputs()
    {
        return getInputsForLevel(-1);
    }

    public ImmutableMap<Ingredient, Integer> getInputsForLevel(int modifierLevel)
    {
        if (modifierLevel <= 0)
            return inputs;

        ImmutableMap.Builder<Ingredient, Integer> builder = ImmutableMap.builder();
        for (Map.Entry<Ingredient, Integer> entry : inputs.entrySet())
            builder.put(entry.getKey(), entry.getValue() * (modifierLevel + 1) * getLevelMultiplier());
        return builder.build();
    }

    public List<List<ItemStack>> getItemStackInputs(int modifierLevel)
    {
        List<List<ItemStack>> stackSet = new ArrayList<>();
        for (Map.Entry<Ingredient, Integer> entry : getInputsForLevel(modifierLevel).entrySet())
        {
            List<ItemStack> actualStacks = new ArrayList<>();
            for (ItemStack itemStack : entry.getKey().getMatchingStacks())
                actualStacks.add(ItemHandlerHelper.copyStackWithSize(itemStack, entry.getValue()));

            stackSet.add(actualStacks);
        }

        return stackSet;
    }

    public boolean matches(ItemStack infuseStack, List<ItemStack> itemStackInputs)
    {
        return matches(infuseStack, itemStackInputs, -1);
    }

    // Complexity of O(n^2)
    public boolean matches(ItemStack infuseStack, List<ItemStack> itemStackInputs, int modifierLevel)
    {
        if (!(infuse.isEmpty() || ItemStack.areItemsEqual(infuse, infuseStack)))
            return false;

        List<ItemStack> dummyList = itemStackInputs.stream().map(ItemStack::copy).collect(Collectors.toList());
        ImmutableMap<Ingredient, Integer> ingredientsMap = getInputsForLevel(modifierLevel);

        boolean foundFilter = filter == null;
        for (Map.Entry<Ingredient, Integer> entry : ingredientsMap.entrySet())
        {
            boolean foundIngredient = false;

            // TODO fix an error here, not working for Potion Recipes
            for (Iterator<ItemStack> iterator = dummyList.iterator(); iterator.hasNext();)
            {
                ItemStack input = iterator.next();
                Ingredient ingredient = entry.getKey();
                if (ingredient.apply(input) && input.getCount() >= entry.getValue())
                {
                    foundIngredient = true;
                    iterator.remove();
                    break;
                }

                if (!foundFilter && filter.matches(input))
                {
                    foundFilter = true;
                    iterator.remove();
                }
            }

            if (!foundIngredient)
                return false;
        }

        if (!foundFilter)
            return false;

        for (ItemStack input : dummyList)
            if (!input.isEmpty())
                return false;

        return dummyList.isEmpty();
    }

    /**
     * Compares the itemStack with the compareStack
     * Then applies the filter if present
     */
    public boolean matchesWithSpecificity(ItemStack itemStack, ItemStack compareStack)
    {
        if (!PotionUtils.getEffectsFromStack(itemStack).isEmpty() && !PotionUtils.getEffectsFromStack(compareStack).isEmpty())
        {
            Potion potion = PotionUtils.getEffectsFromStack(itemStack).get(0).getPotion();
            Potion comparePotion = PotionUtils.getEffectsFromStack(compareStack).get(0).getPotion();

            return potion == comparePotion && (filter == null || filter.matches(itemStack)) && ItemStack.areItemsEqual(itemStack, compareStack);
        }
        else
        {
            return (filter == null || filter.matches(itemStack)) && ItemStack.areItemsEqual(itemStack, compareStack);
        }
    }
}
