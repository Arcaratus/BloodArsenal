package arcaratus.bloodarsenal.recipe;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class SanguineInfusionRecipeRegistry
{
    private static List<RecipeSanguineInfusion> infusionRecipes = new ArrayList<>();
    private static List<Class> blacklistedClasses = new ArrayList<>();

    @SafeVarargs
    public static void registerSanguineInfusionRecipe(ItemStack output, int lpCost, ItemStack infuse, Pair<Object, Integer>... inputs)
    {
        RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(output, lpCost, infuse, inputs);
        infusionRecipes.add(recipe);
    }

    @SafeVarargs
    public static void registerModificationRecipe(int lpCost, String modifierKey, int levelMultiplier, Pair<Object, Integer>... inputs)
    {
        RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(lpCost, modifierKey, inputs).setLevelMultiplier(levelMultiplier);
        infusionRecipes.add(recipe);
    }

    /**
     * For recipes with "wildcard" ItemStacks with varying NBT data (i.e. Potions, Sigils, etc.)
     */
    @SafeVarargs
    public static void registerModificationRecipe(int lpCost, String modifierKey, int levelMultiplier, Class specialClass, Pair<Object, Integer>... inputs)
    {
        RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(lpCost, modifierKey, inputs).setLevelMultiplier(levelMultiplier);
        infusionRecipes.add(recipe);
        blacklistedClasses.add(specialClass);
    }

    public static List<RecipeSanguineInfusion> getInfusionRecipes()
    {
        return infusionRecipes;
    }

    public static List<Class> getBlacklistedClasses()
    {
        return blacklistedClasses;
    }

    // Complexity of O(n^3)
    public static RecipeSanguineInfusion getRecipeFromInputs(ItemStack infuseStack, List<ItemStack> inputs)
    {
        for (RecipeSanguineInfusion recipe : infusionRecipes)
            if (recipe.matches(infuseStack, inputs))
                return recipe;

        return null;
    }
}
