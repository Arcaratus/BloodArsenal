package arcaratus.bloodarsenal.compat.jei.infusion;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SanguineInfusionWrapper implements IRecipeWrapper
{
    private final List<List<ItemStack>> input;
    private final ItemStack output;

    private int lpCost;

    private final RecipeSanguineInfusion recipe;
    private final String[] infoString;

    private boolean isModifier = false;

    private int level = 0;

    @SuppressWarnings("unchecked")
    public SanguineInfusionWrapper(RecipeSanguineInfusion recipe)
    {
        List<List<ItemStack>> itemInputs = new ArrayList<>();
        if (recipe.getInfuse() != ItemStack.EMPTY)
            itemInputs.add(Collections.singletonList(recipe.getInfuse()));

        List<ItemStack> extraInputs = new ArrayList<>();
        getItemStacks(recipe, itemInputs, extraInputs);

        input = itemInputs;
        output = recipe.getOutput();
        lpCost = recipe.getLpCost();
        this.recipe = recipe;
        isModifier = recipe.isModifier();

        if (recipe.isModifier())
            infoString = new String[] { "jei.bloodarsenal.recipe.requiredLP", TextHelper.localize(recipe.getModifier().getUnlocalizedName()), "jei.bloodarsenal.recipe.modifierLevel" };
        else
            infoString = new String[] { TextHelper.localize("jei.bloodarsenal.recipe.requiredLP", lpCost) };

    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInputLists(ItemStack.class, input);
        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        if (isModifier)
        {
            minecraft.fontRenderer.drawString(TextHelper.localize(infoString[0], (level + 1) * lpCost), 110 - minecraft.fontRenderer.getStringWidth(TextHelper.localize(infoString[0], (level + 1) * lpCost)) / 2, 40, Color.gray.getRGB());
            minecraft.fontRenderer.drawString(infoString[1], 10 - minecraft.fontRenderer.getStringWidth(infoString[1]) / 2, 4, Color.gray.getRGB());
            minecraft.fontRenderer.drawString(TextHelper.localize(infoString[2], level + 1), 10 - minecraft.fontRenderer.getStringWidth(TextHelper.localize(infoString[2], level + 1)) / 2, 14, Color.gray.getRGB());
        }
        else
        {
            minecraft.fontRenderer.drawString(infoString[0], 110 - minecraft.fontRenderer.getStringWidth(infoString[0]) / 2, 40, Color.gray.getRGB());
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        return ImmutableList.of();
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton)
    {
        return false;
    }

    public RecipeSanguineInfusion getRecipe()
    {
        return recipe;
    }

    public void setInfoData(int level)
    {
        this.level = level;
    }

    private static void getItemStacks(RecipeSanguineInfusion recipe, List<List<ItemStack>> itemInputs, List<ItemStack> extraInputs)
    {
        for (Object o : recipe.getInputs())
        {
            if (o instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) o;
                itemInputs.add(Collections.singletonList(new ItemStack(itemStack.getItem(), itemStack.getCount(), itemStack.getMetadata())));
            }
            else if (o instanceof String)
            {
                itemInputs.add(OreDictionary.getOres((String) o));
            }
        }
    }
}
