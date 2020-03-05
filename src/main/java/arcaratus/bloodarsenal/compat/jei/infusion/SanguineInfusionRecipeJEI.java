package arcaratus.bloodarsenal.compat.jei.infusion;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.compat.jei.BloodArsenalPlugin;
import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

public class SanguineInfusionRecipeJEI implements IRecipeWrapper
{
    private RecipeSanguineInfusion recipe;

    private final String[] infoString;
    private int level = 0;

    public SanguineInfusionRecipeJEI(RecipeSanguineInfusion recipe)
    {
        this.recipe = recipe;

        if (recipe.isModifier())
            infoString = new String[] { "jei.bloodarsenal.recipe.required_LP", TextHelper.localize(recipe.getModifier().getUnlocalizedName()), "jei.bloodarsenal.recipe.modifier_level" };
        else
            infoString = new String[] { TextHelper.localize("jei.bloodarsenal.recipe.required_LP", recipe.getLpCost()) };
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients)
    {
        List<List<ItemStack>> expandedInputs = BloodArsenalPlugin.jeiHelper.getStackHelper().expandRecipeItemStackInputs(recipe.getItemStackInputs(0));
        if (!recipe.isModifier())
            expandedInputs.add(0, Lists.newArrayList(recipe.getInfuse()));
        ingredients.setInputLists(VanillaTypes.ITEM, expandedInputs);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    public RecipeSanguineInfusion getRecipe()
    {
        return recipe;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        if (recipe.isModifier())
        {
            minecraft.fontRenderer.drawString(TextHelper.localize(infoString[0], (level + 1) * recipe.getLpCost()), 110 - minecraft.fontRenderer.getStringWidth(TextHelper.localize(infoString[0], (level + 1) * recipe.getLpCost())) / 2, 40, Color.gray.getRGB());
            minecraft.fontRenderer.drawString(infoString[1], 10 - minecraft.fontRenderer.getStringWidth(infoString[1]) / 2, 4, Color.gray.getRGB());
            minecraft.fontRenderer.drawString(TextHelper.localize(infoString[2], level == -1 ? 1 : level + 1), 10 - minecraft.fontRenderer.getStringWidth(TextHelper.localize(infoString[2], level + 1)) / 2, 14, Color.gray.getRGB());
        }
        else
        {
            minecraft.fontRenderer.drawString(infoString[0], 110 - minecraft.fontRenderer.getStringWidth(infoString[0]) / 2, 40, Color.gray.getRGB());
        }
    }
}