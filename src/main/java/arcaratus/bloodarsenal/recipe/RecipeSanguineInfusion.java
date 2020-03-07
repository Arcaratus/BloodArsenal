package arcaratus.bloodarsenal.recipe;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.modifier.ModifierHandler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeSanguineInfusion
{
    @Nonnull
    private final ItemStack output;
    @Nonnull
    private final ItemStack infuse;
    // Contains the Ingredient and the amount required
    @Nonnull
    private final List<Pair<Ingredient, Integer>> inputs = new ArrayList<>();

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

        for (Pair<Object, Integer> pair : inputs)
        {
            Object o = pair.getLeft();
            Integer i = pair.getRight();
            if (o instanceof Item)
                this.inputs.add(p(Ingredient.fromStacks(new ItemStack((Item) o)), i));
            else if (o instanceof Block)
                this.inputs.add(p(Ingredient.fromStacks(new ItemStack((Block) o)), i));
            else if (o instanceof ItemStack)
                this.inputs.add(p(Ingredient.fromStacks(((ItemStack) o).copy()), i));
            else if (o instanceof String)
                this.inputs.add(p(new OreIngredient((String) o), i));
            else if (o instanceof RecipeFilter)
                filter = (RecipeFilter) o;
            else
                throw new IllegalArgumentException("Invalid input: " + o);
        }
    }

    @SafeVarargs
    public RecipeSanguineInfusion(@Nonnegative int lpCost, @Nonnull String modifierKey, @Nonnull Pair<Object, Integer>... inputs)
    {
        this.output = this.infuse = ItemStack.EMPTY;

        this.lpCost = lpCost;
        this.isModifier = true;
        this.modifierKey = modifierKey;
        this.modifier = ModifierHandler.getModifierFromKey(BloodArsenal.MOD_ID + ".modifier." + modifierKey);

        for (Pair<Object, Integer> pair : inputs)
        {
            Object o = pair.getLeft();
            Integer i = pair.getRight();
            if (o instanceof Item)
                this.inputs.add(p(Ingredient.fromStacks(new ItemStack((Item) o)), i));
            else if (o instanceof Block)
                this.inputs.add(p(Ingredient.fromStacks(new ItemStack((Block) o)), i));
            else if (o instanceof ItemStack)
                this.inputs.add(p(Ingredient.fromStacks(((ItemStack) o).copy()), i));
            else if (o instanceof String)
                this.inputs.add(p(new OreIngredient((String) o), i));
            else if (o instanceof RecipeFilter)
                filter = (RecipeFilter) o;
            else
                throw new IllegalArgumentException("Invalid input");
        }
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

    public List<Pair<Ingredient, Integer>> getInputs()
    {
        return getInputsForLevel(-1);
    }

    public List<Pair<Ingredient, Integer>> getInputsForLevel(int modifierLevel)
    {
        if (modifierLevel <= 0)
            return inputs;

        List<Pair<Ingredient, Integer>> inputsForLevel = new ArrayList<>();
        for (Pair<Ingredient, Integer> entry : inputs)
            inputsForLevel.add(p(entry.getKey(), entry.getValue() * (modifierLevel + 1) * getLevelMultiplier()));

        return inputsForLevel;
    }

    public List<List<ItemStack>> getItemStackInputs(int modifierLevel)
    {
        List<List<ItemStack>> stackSet = new ArrayList<>();
        for (Pair<Ingredient, Integer> entry : getInputsForLevel(modifierLevel))
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
        List<Pair<Ingredient, Integer>> ingredientsMap = getInputsForLevel(modifierLevel);

        boolean filterExists = filter != null;
        for (Pair<Ingredient, Integer> entry : ingredientsMap)
        {
            boolean foundIngredient = false;

            for (ItemStack input : dummyList)
            {
                Ingredient ingredient = entry.getKey();
                if (ingredient.apply(input) && input.getCount() >= entry.getValue())
                {
                    foundIngredient = true;
                    dummyList.remove(input);
                    break;
                }

                if (filterExists && filter.matches(input))
                {
                    dummyList.remove(input);
                    break;
                }
            }

            if (!foundIngredient)
                return false;
        }

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

    public Pair<Ingredient, Integer> p(Ingredient ingredient, int i)
    {
        return Pair.of(ingredient, i);
    }
}
