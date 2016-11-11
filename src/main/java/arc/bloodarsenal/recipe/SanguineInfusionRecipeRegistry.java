package arc.bloodarsenal.recipe;

import arc.bloodarsenal.modifier.Modifier;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SanguineInfusionRecipeRegistry
{
    private static List<RecipeSanguineInfusion> infusionRecipes = new ArrayList<>();

    private static List<Class> blacklistedClasses = new ArrayList<>();

    public static void registerSanguineInfusionRecipe(ItemStack output, int lpCost, ItemStack infuse, Object... inputs)
    {
        RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(output, lpCost, infuse, inputs);
        infusionRecipes.add(recipe);
    }

    public static void registerModificationRecipe(int lpCost, Modifier modifier, int levelMultiplier, Object... inputs)
    {
        RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(lpCost, modifier, inputs).setLevelMultiplier(levelMultiplier);
        infusionRecipes.add(recipe);
    }

    public static void registerModificationRecipe(int lpCost, Modifier modifier, int levelMultiplier, Class specialClass, Object... inputs)
    {
        RecipeSanguineInfusion recipe = new RecipeSanguineInfusion(lpCost, modifier, inputs).setLevelMultiplier(levelMultiplier).setSpecial();
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
    public static RecipeSanguineInfusion getRecipeFromInputs(List<ItemStack> inputs, List<ItemStack> extraInputs)
    {
        for (RecipeSanguineInfusion recipe : infusionRecipes)
            if (recipe.matches(inputs, extraInputs))
                return recipe;

        return null;
    }
}
