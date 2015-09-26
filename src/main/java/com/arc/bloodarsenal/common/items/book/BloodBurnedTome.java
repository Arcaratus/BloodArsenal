package com.arc.bloodarsenal.common.items.book;

import WayofTime.alchemicalWizardry.api.items.ShapelessBloodOrbRecipe;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.BookBuilder;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryUniText;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageUnlocImage;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class BloodBurnedTome
{
    public static Book burnedTome;
    public static List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
    private final static String R_LOC = "BloodArsenal:book/images/";

    public static void registerTome()
    {
        registerLifebringer();

        BookBuilder bookBuilder = new BookBuilder();
        bookBuilder.setCategories(categories).setUnlocBookTitle("guide.bloodarsenal.book.title").setUnlocWelcomeMessage("guide.bloodarsenal.book.welcomeMessage").setUnlocDisplayName("guide.bloodarsenal.book.name").setAuthor("-An Arc Mage").setItemTexture("BloodArsenal:burned_tome");
        burnedTome = bookBuilder.build();
        GuideRegistry.registerBook(burnedTome);
        GameRegistry.addRecipe(new ShapelessBloodOrbRecipe(GuideRegistry.getItemStackForBook(burnedTome), ModItems.glass_shard, Items.writable_book, new ItemStack(WayofTime.alchemicalWizardry.ModItems.apprenticeBloodOrb, 1, OreDictionary.WILDCARD_VALUE)));
        RecipeHolder.init();
    }

    private static void registerLifebringer()
    {
        List<EntryAbstract> lifeBringerEntries = new ArrayList<EntryAbstract>();

        {
            //A Different Backstory
            ArrayList<IPage> backstoryPages = new ArrayList<IPage>();
            makePage(lifeBringerEntries, backstoryPages, "backstory");
        }

        {
            //Glass Is Dangerous
            ArrayList<IPage> glass = new ArrayList<IPage>();
            makePage(lifeBringerEntries, glass, "glass");
        }

        categories.add(new CategoryItemStack(lifeBringerEntries, "guide.bloodarsenal.category.lifeBringer", new ItemStack(ModItems.bound_bow)));
    }

    private static void makePage(List<EntryAbstract> entries, ArrayList<IPage> pages, String name, Object... others)
    {
        pages.addAll(PageHelper.pagesForLongText(replaceColors(StatCollector.translateToLocal("guide.bloodarsenal.entry." + name))));

        if (others != null)
        {
            for (Object code : others)
            {
                if (code instanceof IPage)
                {
                    pages.add((IPage) code);
                }

                if (code instanceof Object[])
                {
                    Object[] objects = (Object[]) code;
                    for (Object object : objects)
                    {
                        pages.add((IPage) object);
                    }
                }
            }
        }
        entries.add(new EntryUniText(pages, "guide.bloodarsenal.entryTitles." + name));
    }

    private static Object[] s(String pageName)
    {
        if (pageName != null)
        {
            return PageHelper.pagesForLongText(replaceColors(StatCollector.translateToLocal("guide.bloodarsenal.entry." + pageName))).toArray();
        }

        return null;
    }

    private static Object[] r(IRecipe... recipes)
    {
        if (recipes != null)
        {
            ArrayList<PageIRecipe> pageIRecipes = new ArrayList<PageIRecipe>();
            for (IRecipe recipe : recipes)
            {
                pageIRecipes.add(new PageIRecipe(recipe));
            }
            return pageIRecipes.toArray();
        }

        return null;
    }

    private static Object[] i(String... names)
    {
        if (names != null)
        {
            ArrayList<PageUnlocImage> images = new ArrayList<PageUnlocImage>();
            for (String name : names)
            {
                images.add(new PageUnlocImage(replaceColors(StatCollector.translateToLocal("guide.bloodarsenal.caption." + name)), new ResourceLocation(R_LOC + name + ".png"), true));
            }

            return images.toArray();
        }

        return null;
    }

    protected static String replaceColors(String text)
    {
        if (text != null)
        {
            return text.replace("@BLD@", EnumChatFormatting.BOLD.toString())
                       .replace("@UND@", EnumChatFormatting.UNDERLINE.toString())
                       .replace("@ITC@", EnumChatFormatting.ITALIC.toString())

                       .replace("@AQA@", EnumChatFormatting.AQUA.toString())
                       .replace("@GLD@", EnumChatFormatting.GOLD.toString())
                       .replace("@DRED@", EnumChatFormatting.DARK_RED.toString())
                       .replace("@RED@", EnumChatFormatting.RED.toString())
                       .replace("@LPRP@", EnumChatFormatting.LIGHT_PURPLE.toString())
                       .replace("@PRP@", EnumChatFormatting.DARK_PURPLE.toString())
                       .replace("@GRN@", EnumChatFormatting.GREEN.toString())
                       .replace("@DGRN@", EnumChatFormatting.DARK_GREEN.toString())
                       .replace("@BLU@", EnumChatFormatting.BLUE.toString())
                       .replace("@DBLU@", EnumChatFormatting.DARK_BLUE.toString())
                       .replace("@GRY@", EnumChatFormatting.GRAY.toString())
                       .replace("@DGRY@", EnumChatFormatting.DARK_GRAY.toString())
                       .replace("@RST@", EnumChatFormatting.RESET.toString())
                       .replace("@END@", EnumChatFormatting.BLACK.toString());
        }

        return null;
    }
}
