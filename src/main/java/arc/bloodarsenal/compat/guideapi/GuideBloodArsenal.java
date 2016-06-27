package arc.bloodarsenal.compat.guideapi;

import WayofTime.bloodmagic.compat.jei.BloodMagicPlugin;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.util.NBTBookTags;
import amerifrance.guideapi.category.CategoryItemStack;
import arc.bloodarsenal.compat.guideapi.book.CategoryLifebringer;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;

public class GuideBloodArsenal
{
    public static Book guideBook;

    public static void initBook()
    {
        guideBook = new Book();
        guideBook.setTitle("guide.BloodArsenal.title");
        guideBook.setDisplayName("guide.BloodArsenal.display");
        guideBook.setWelcomeMessage("guide.BloodArsenal.welcome");
        guideBook.setAuthor("guide.BloodArsenal.author");
        guideBook.setRegistryName("BloodArsenal");
        guideBook.setColor(new Color(119, 0, 0));

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            GuideAPI.setModel(guideBook);
    }

    public static void initCategories()
    {
        String keyBase = "guide.BloodArsenal.category.";
        guideBook.addCategory(new CategoryItemStack(CategoryLifebringer.buildCategory(), keyBase + "lifebringer", new ItemStack(ModItems.bloodInfusedIronIngot)));
    }

    public static void initJEIBlacklist()
    {
        if (Loader.isModLoaded("JEI"))
            BloodMagicPlugin.jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(GuideAPI.guideBook, NBTBookTags.BOOK_TAG, NBTBookTags.CATEGORY_PAGE_TAG, NBTBookTags.CATEGORY_TAG, NBTBookTags.ENTRY_PAGE_TAG, NBTBookTags.ENTRY_TAG, NBTBookTags.KEY_TAG, NBTBookTags.PAGE_TAG);
    }
}
