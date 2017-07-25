package arc.bloodarsenal.recipe;

import arc.bloodarsenal.modifier.Modifier;
import arc.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeSanguineInfusion
{
    private final ItemStack output;
    private final ItemStack infuse;
    private final ImmutableList<Object> inputs;

    private final int lpCost;

    private boolean isModifier = false;
    private boolean isSpecial = false;
    private Modifier modifier = Modifier.EMPTY_MODIFIER;

    private int levelMultiplier = 1;

    private RecipeFilter filter = null;

    public RecipeSanguineInfusion(ItemStack output, int lpCost, ItemStack infuse, Object... inputs)
    {
        this.output = output;
        this.infuse = infuse;

        this.lpCost = lpCost;

        ImmutableList.Builder<Object> inputsToSet = ImmutableList.builder();
        for (Object o : inputs)
        {
            if (o instanceof Item)
                inputsToSet.add(new ItemStack((Item) o));
            else if (o instanceof Block)
                inputsToSet.add(new ItemStack((Block) o));
            else if (o instanceof String || o instanceof ItemStack)
                inputsToSet.add(o);
            else if (o instanceof RecipeFilter)
                filter = (RecipeFilter) o;
            else
                throw new IllegalArgumentException("Invalid input");
        }

        this.inputs = inputsToSet.build();
    }

    public RecipeSanguineInfusion(int lpCost, Modifier modifier, Object... inputs)
    {
        this.output = this.infuse = null;

        this.lpCost = lpCost;
        this.isModifier = true;
        this.modifier = modifier;

        ImmutableList.Builder<Object> inputsToSet = ImmutableList.builder();
        for (Object o : inputs)
        {
            if (o instanceof Item)
                inputsToSet.add(new ItemStack((Item) o));
            else if (o instanceof Block)
                inputsToSet.add(new ItemStack((Block) o));
            else if (o instanceof String || o instanceof ItemStack)
                inputsToSet.add(o);
            else if (o instanceof RecipeFilter)
                filter = (RecipeFilter) o;
            else
                throw new IllegalArgumentException("Invalid input");
        }

        this.inputs = inputsToSet.build();
    }

    public RecipeSanguineInfusion setSpecial()
    {
        isSpecial = true;
        return this;
    }

    public RecipeSanguineInfusion setLevelMultiplier(int levelMultiplier)
    {
        this.levelMultiplier = levelMultiplier;
        return this;
    }

    public List<Object> getInputs()
    {
        return inputs;
    }

    public List<ItemStack> getItemStackInputs()
    {
        List<ItemStack> stackSet = new ArrayList<>();
        for (Object o : inputs)
            if (o instanceof ItemStack)
                stackSet.add((ItemStack) o);
            else if (o instanceof String)
                stackSet.add(BloodArsenalUtils.resolveObject(o));

        return stackSet;
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

    public boolean isSpecial()
    {
        return isSpecial;
    }

    public Modifier getModifier()
    {
        return modifier;
    }

    public int getLevelMultiplier()
    {
        return levelMultiplier;
    }

    public List<ItemStack> getInputsForLevel(int modifierLevel)
    {
        if (modifierLevel < 0)
            return getItemStackInputs();

        List<ItemStack> inputs = new ArrayList<>();

        for (ItemStack itemStack : getItemStackInputs())
        {
            ItemStack dummyStack = itemStack.copy();
            dummyStack.stackSize = itemStack.stackSize * (modifierLevel + 1) * getLevelMultiplier();
            inputs.add(dummyStack);
        }

        return inputs;
    }

    public RecipeFilter getFilter()
    {
        return filter;
    }

    public boolean matches(List<ItemStack> itemStackInputs)
    {
        return matches(itemStackInputs, -1);
    }

    // This has complexity of O(n^2)
    public boolean matches(List<ItemStack> itemStackInputs, int modifierLevel)
    {
        List<ItemStack> dummyList = new ArrayList<>();
        dummyList.addAll(itemStackInputs);

        for (ItemStack ingredient : getInputsForLevel(modifierLevel))
        {
            boolean foundIngredient = false;

            for (ItemStack input : dummyList)
            {
                if (BloodArsenalUtils.areStacksEqual(ingredient, input))
                {
                    if (ingredient != null && ingredient.hasTagCompound() && !ItemStack.areItemStackTagsEqual(ingredient, input))
                        continue;

                    if (input.stackSize >= ingredient.stackSize)
                    {
                        foundIngredient = true;
                        dummyList.remove(input);
                        break;
                    }
                }
            }

            if (!foundIngredient)
                return false;
        }

        for (ItemStack input : dummyList)
            if (input != null)
                return false;

        return true;
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
