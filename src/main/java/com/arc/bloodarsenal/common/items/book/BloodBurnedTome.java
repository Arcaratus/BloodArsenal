package com.arc.bloodarsenal.common.items.book;

import WayofTime.alchemicalWizardry.api.items.ShapelessBloodOrbRecipe;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.BookBuilder;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class BloodBurnedTome
{
    public static Book burnedTome;
    public static List<CategoryAbstract> categories = new ArrayList();
    private final static String R_LOC = "BloodArsenal:book/images/";

    public static void registerTome()
    {
        BookBuilder bookBuilder = new BookBuilder();
        bookBuilder.setCategories(categories).setUnlocBookTitle("guide.bloodArsenal.book.title").setUnlocWelcomeMessage("guide.bloodArsenal.book.welcomeMessage").setUnlocDisplayName("guide.bloodArsenal.book.name").setAuthor("An Arc Mage").setItemTexture("BloodArsenal:textures/items/burned_tome");
        burnedTome = bookBuilder.build();
        GuideRegistry.registerBook(burnedTome);
        GameRegistry.addRecipe(new ShapelessBloodOrbRecipe(GuideRegistry.getItemStackForBook(burnedTome), ModItems.glass_shard, new ItemStack(WayofTime.alchemicalWizardry.ModItems.apprenticeBloodOrb, 1, OreDictionary.WILDCARD_VALUE)));
    }
}
