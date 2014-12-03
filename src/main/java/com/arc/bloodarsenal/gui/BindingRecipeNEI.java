package com.arc.bloodarsenal.gui;

import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRecipe;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;

public class BindingRecipeNEI extends TemplateRecipeHandler
{
    public class CachedBindingRecipe extends CachedRecipe
    {
        PositionedStack input;
        PositionedStack output;

        public CachedBindingRecipe(BindingRecipe recipe)
        {
            input = new PositionedStack(recipe.requiredItem, 38, 2, false);
            output = new PositionedStack(recipe.outputItem, 132, 32, false);
        }

        @Override
        public PositionedStack getIngredient()
        {
            return input;
        }

        @Override
        public PositionedStack getResult()
        {
            return output;
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("alchemicalwizardry.binding"))
        {

        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for(BindingRecipe recipe: BindingRegistry.bindingRecipes)
        {
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.outputItem, result))
            {
                if (recipe != null && recipe.outputItem != null) arecipes.add(new CachedBindingRecipe(recipe));
            }
        }
    }
}
