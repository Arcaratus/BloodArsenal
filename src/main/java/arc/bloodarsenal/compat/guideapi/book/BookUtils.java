package arc.bloodarsenal.compat.guideapi.book;

import WayofTime.bloodmagic.api.registry.AltarRecipeRegistry.AltarRecipe;
import WayofTime.bloodmagic.compat.guideapi.page.PageAltarRecipe;
import WayofTime.bloodmagic.util.helper.RecipeHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.page.PageIRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public class BookUtils
{
    private static final int MAX_LENGTH = 270;

    public static List<IPage> textPages(String heading, int number)
    {
        return PageHelper.pagesForLongText(TextHelper.localizeEffect(heading + ".info." + number), MAX_LENGTH);
    }

    public static List<IPage> textPages(String heading)
    {
        return textPages(heading, 1);
    }

    public static Map<ResourceLocation, EntryAbstract> putPagesToEntry(Map<ResourceLocation, EntryAbstract> entry, String heading, List<IPage> pages)
    {
        entry.put(new ResourceLocation(heading), new EntryText(pages, TextHelper.localize(heading)));
        return entry;
    }

    public static List<IPage> addRecipeToPage(List<IPage> pages, ItemStack recipeStack)
    {
        IRecipe recipe = RecipeHelper.getRecipeForOutput(recipeStack);
        if (recipe != null)
            pages.add(new PageIRecipe(recipe));

        return pages;
    }

    public static List<IPage> addAltarRecipeToPage(List<IPage> pages, ItemStack recipeStack)
    {
        AltarRecipe altarRecipe = RecipeHelper.getAltarRecipeForOutput(recipeStack);
        if (altarRecipe != null)
            pages.add(new PageAltarRecipe(altarRecipe));

        return pages;
    }

//    public static List<IPage> addFurnaceRecipeToPage(List<IPage> pages, ItemStack recipeStack)
//    {
//        TartaricForgeRecipe forgeRecipe = RecipeHelper.getForgeRecipeForOutput(recipeStack);
//        if (forgeRecipe != null)
//            pages.add(new );
//
//        return pages;
//    }
}
