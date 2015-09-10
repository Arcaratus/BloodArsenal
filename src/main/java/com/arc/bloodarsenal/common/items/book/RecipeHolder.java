package com.arc.bloodarsenal.common.items.book;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public class RecipeHolder
{
    private static List recipeList;

    public static IRecipe glassSacrificialDagger;
    public static IRecipe glassDaggerOfSacrifice;

    public static void init()
    {
        recipeList = CraftingManager.getInstance().getRecipeList();
    }

    private static IRecipe getRecipeForItemStack(ItemStack stack)
    {
        for (Object obj : recipeList)
        {
            IRecipe recipe = (IRecipe) obj;
            if (recipe.getRecipeOutput() != null && stack.isItemEqual(recipe.getRecipeOutput()))
            {
                return recipe;
            }
        }

        return null;
    }

    private static AltarRecipe getAltarRecipeForItemStack(ItemStack stack)
    {
        for (AltarRecipe recipe : AltarRecipeRegistry.altarRecipes)
        {
            if (recipe.getResult() != null && stack.isItemEqual(recipe.getResult()))
            {
                return recipe;
            }
        }

        return null;
    }
}
