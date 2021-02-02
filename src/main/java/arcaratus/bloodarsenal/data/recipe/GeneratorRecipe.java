package arcaratus.bloodarsenal.data.recipe;

import arcaratus.bloodarsenal.common.block.ModBlocks;
import arcaratus.bloodarsenal.common.core.ModTags;
import arcaratus.bloodarsenal.common.item.ModItems;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

/**
 * Code partially adapted from Botania
 * https://github.com/Vazkii/Botania
 */
public class GeneratorRecipe extends RecipeProvider
{
    public GeneratorRecipe(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        registerMain(consumer);
        registerTools(consumer);
    }

    private void registerMain(Consumer<IFinishedRecipe> consumer)
    {

    }

    private void registerTools(Consumer<IFinishedRecipe> consumer)
    {
        registerToolSetRecipes(consumer, Ingredient.fromStacks(new ItemStack(ModBlocks.BLOOD_INFUSED_PLANKS.get())), Ingredient.fromItems(ModItems.BLOOD_INFUSED_STICK.get()),
                hasItem(ModTags.Items.INGOTS_BLOOD_INFUSED_IRON), ModItems.BLOOD_INFUSED_WOODEN_SWORD.get(), ModItems.BLOOD_INFUSED_WOODEN_PICKAXE.get(),
                ModItems.BLOOD_INFUSED_WOODEN_AXE.get(), ModItems.BLOOD_INFUSED_WOODEN_SHOVEL.get());
    }

    private void registerToolSetRecipes(Consumer<IFinishedRecipe> consumer, Ingredient item, Ingredient stick, ICriterionInstance criterion, IItemProvider sword, IItemProvider pickaxe, IItemProvider axe, IItemProvider shovel)
    {
        ShapedRecipeBuilder.shapedRecipe(pickaxe)
                .key('S', item)
                .key('T', stick)
                .patternLine("SSS")
                .patternLine(" T ")
                .patternLine(" T ")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(shovel)
                .key('S', item)
                .key('T', stick)
                .patternLine("S")
                .patternLine("T")
                .patternLine("T")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(axe)
                .key('S', item)
                .key('T', stick)
                .patternLine("SS")
                .patternLine("TS")
                .patternLine("T ")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(sword)
                .key('S', item)
                .key('T', stick)
                .patternLine("S")
                .patternLine("S")
                .patternLine("T")
                .addCriterion("has_item", criterion)
                .build(consumer);

    }
}
