package arcaratus.bloodarsenal.compat.jei.infusion;

import WayofTime.bloodmagic.iface.IActivatable;
import WayofTime.bloodmagic.iface.ISigil;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.compat.jei.BloodArsenalPlugin;
import arcaratus.bloodarsenal.modifier.IModifiableItem;
import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SanguineInfusionCategory implements IRecipeCategory<SanguineInfusionRecipeJEI>
{
    private static final int OUTPUT_SLOT = 0;
    private static final int INPUT_SLOT = 1;

    @Nonnull
    private final IDrawable background = BloodArsenalPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(BloodArsenal.DOMAIN + "textures/gui/sanguine_infusion.png"), 0, 0, 103, 103);
    @Nonnull
    private final ICraftingGridHelper craftingGridHelper;

    public SanguineInfusionCategory()
    {
        craftingGridHelper = BloodArsenalPlugin.jeiHelper.getGuiHelper().createCraftingGridHelper(INPUT_SLOT, OUTPUT_SLOT);
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return Constants.Compat.JEI_CATEGORY_SANGUINE_INFUSION;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return TextHelper.localize("jei.bloodarsenal.recipe.sanguine_infusion");
    }

    @Nonnull
    @Override
    public String getModName()
    {
        return BloodArsenal.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon()
    {
        return null;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {}

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull SanguineInfusionRecipeJEI recipeWrapper, @Nonnull IIngredients ingredients)
    {
        int centerX = 27;
        int centerY = 57;
        int index = 2;

        IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();

        stackGroup.init(0, true, centerX, centerY);
        stackGroup.init(1, false, 77, 8);

        RecipeSanguineInfusion recipe = recipeWrapper.getRecipe();
        List<List<ItemStack>> itemInputs = ingredients.getInputs(ItemStack.class);

        if (recipe.isModifier())
        {
            List<ItemStack> inputs = new LinkedList<>();

            switch (recipe.getModifierKey())
            {
                case Constants.Modifiers.SIGIL:
                {
                    List<ItemStack> sigils = new ArrayList<>();
                    for (ItemStack itemStack : BloodArsenalPlugin.sigils)
                    {
                        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ISigil)
                        {
                            ItemStack copyStack = itemStack.copy();
                            sigils.add(copyStack);
                        }
                    }

                    itemInputs.add(sigils);
                    break;
                }

                case Constants.Modifiers.BAD_POTION:
                {
                    List<ItemStack> potions = new ArrayList<>();
                    for (ItemStack itemStack : BloodArsenalPlugin.badPotions)
                    {
                        if (!itemStack.isEmpty())
                        {
                            ItemStack copyStack = itemStack.copy();
                            potions.add(copyStack);
                        }
                    }

                    itemInputs.add(potions);
                    break;
                }

                case Constants.Modifiers.BENEFICIAL_POTION:
                {
                    List<ItemStack> potions = new ArrayList<>();
                    for (ItemStack itemStack : BloodArsenalPlugin.beneficialPotions)
                    {
                        if (!itemStack.isEmpty())
                        {
                            ItemStack copyStack = itemStack.copy();
                            potions.add(copyStack);
                        }
                    }

                    itemInputs.add(potions);
                    break;
                }
            }

            for (ItemStack itemStack : BloodArsenalPlugin.modifiables)
            {
                if (!itemStack.isEmpty() && itemStack.getItem() instanceof IModifiableItem && itemStack.getItem() instanceof IActivatable)
                {
                    ItemStack copyStack = itemStack.copy();
                    inputs.add(copyStack);
                }
            }

            stackGroup.set(0, inputs);
            stackGroup.set(1, inputs);
        }
        else
        {
            stackGroup.set(0, ingredients.getInputs(ItemStack.class).get(0));
            stackGroup.set(1, ingredients.getOutputs(ItemStack.class).get(0));
            ingredients.getInputs(ItemStack.class).remove(0);
        }

        double angleBetweenEach = 360.0 / ingredients.getInputs(ItemStack.class).size();
        Point point = new Point(centerX, centerY - 35), center = new Point(centerX - 1, centerY);
        int maxLevel = recipe.isModifier() ? recipe.getModifier().getMaxLevel() : 0;

        for (List<ItemStack> inputs : itemInputs)
        {
            if (recipe.isModifier()) // Puts in the items for the different levels
            {
                for (int i = 1; i < maxLevel; i++)
                    for (List<ItemStack> itemStacks : recipe.getItemStackInputs(i))
                        for (ItemStack itemStack : itemStacks)
                            if (BloodArsenalUtils.areStacksEqual(inputs.get(0), itemStack))
                                inputs.add(itemStack);

                for (int i = 0; i < inputs.size(); i++) // This mess here removes duplicate items
                {
                    if (i < inputs.size() - 1)
                    {
                        if (inputs.get(i).hasTagCompound())
                        {
                            break;
                        }
                        else if (inputs.get(i).getCount() == inputs.get(i + 1).getCount())
                        {
                            inputs.remove(i);
                            i--;
                        }
                    }
                }
            }

            stackGroup.init(index, true, point.x, point.y);
            stackGroup.set(index, inputs);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
        }

        if (recipe.isModifier())
        {
            int modifierLevel = -1;
            List<ItemStack> inputStacks = new ArrayList<>();

            Map<Integer, ? extends IGuiIngredient<ItemStack>> ings = stackGroup.getGuiIngredients();

            for (int i = 2; i < ings.size(); i++) // Item inputs
                inputStacks.add(ings.get(i).getDisplayedIngredient());

            // Get the modifier level for the # of items present
            for (int i = maxLevel; i >= 0; i--)
            {
                if (recipe.matches(ItemStack.EMPTY, inputStacks, i))
                {
                    modifierLevel = i;
                    break;
                }
            }

            recipeWrapper.setLevel(modifierLevel);
        }
    }

    private Point rotatePointAbout(Point in, Point about, double degrees)
    {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Point((int) newX, (int) newY);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        return ImmutableList.of();
    }
}
