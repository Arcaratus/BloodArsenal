package arcaratus.bloodarsenal.compat.jei.infusion;

import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class SanguineInfusionHandler implements IRecipeHandler<RecipeSanguineInfusion>
{
    @Nonnull
    @Override
    public Class<RecipeSanguineInfusion> getRecipeClass()
    {
        return RecipeSanguineInfusion.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull RecipeSanguineInfusion recipe)
    {
        return SanguineInfusionCategory.UID;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull RecipeSanguineInfusion recipe)
    {
        return new SanguineInfusionWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull RecipeSanguineInfusion recipe)
    {
        return true;
    }
}
