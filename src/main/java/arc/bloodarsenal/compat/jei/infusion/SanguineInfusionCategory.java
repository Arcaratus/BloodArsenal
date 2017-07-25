package arc.bloodarsenal.compat.jei.infusion;

import WayofTime.bloodmagic.api.iface.IActivatable;
import WayofTime.bloodmagic.api.iface.ISigil;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.compat.jei.BloodArsenalPlugin;
import arc.bloodarsenal.modifier.IModifiableItem;
import arc.bloodarsenal.modifier.modifiers.*;
import arc.bloodarsenal.recipe.RecipeSanguineInfusion;
import arc.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SanguineInfusionCategory implements IRecipeCategory<SanguineInfusionWrapper>
{
    public static final String UID = BloodArsenal.MOD_ID + ":sanguineInfusion";
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;

    public SanguineInfusionCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(103, 103);
        localizedName = I18n.format("jei.bloodarsenal.recipe.sanguineInfusion");
        overlay = guiHelper.createDrawable(new ResourceLocation("bloodarsenal", "textures/gui/sanguine_infusion.png"), 0, 0, 103, 103);
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return localizedName;
    }

    @Nonnull
    @Override
    public String getModName()
    {
        return BloodArsenal.MOD_ID;
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
    public void drawExtras(@Nonnull Minecraft minecraft)
    {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        overlay.draw(minecraft);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull SanguineInfusionWrapper recipeWrapper, @Nonnull IIngredients ingredients)
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

            if (recipe.getModifier() instanceof ModifierSigil)
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
            }
            else if (recipe.getModifier() instanceof ModifierBadPotion)
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
            }
            else if (recipe.getModifier() instanceof ModifierBeneficialPotion)
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

        for (List<ItemStack> inputs : itemInputs)
        {
            if (recipe.isModifier()) // Puts in the items for the different levels
            {
                for (int i = 1; i < recipe.getModifier().getMaxLevel(); i++)
                    for (ItemStack itemStack : recipe.getInputsForLevel(i))
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
            for (int i = recipe.getModifier().getMaxLevel() - 1; i >= 0; i--)
            {
                if (recipe.matches(inputStacks, i))
                {
                    modifierLevel = i;
                    break;
                }
            }

            recipeWrapper.setInfoData(modifierLevel);
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
