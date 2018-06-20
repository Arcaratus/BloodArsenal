package arcaratus.bloodarsenal.compat.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.page.PageText;
import arcaratus.bloodarsenal.compat.guideapi.BookUtils;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class CategoryLifebringer
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String keyBase = "guide.bloodarsenal.entry.lifebringer.";
        String heading;

        // Intro
        {
            heading = keyBase + "intro";
            List<IPage> pages = new ArrayList<>(BookUtils.textPages(heading));

            BookUtils.putPagesToEntry(entries, heading, pages);
        }

        // Glass Is Dangerous
        {
            heading = keyBase + "glassIsDangerous";
            List<IPage> pages = new ArrayList<>(BookUtils.textPages(heading));
//            pages = BookUtils.addRecipeToPage(pages, new ItemStack(ModItems.bloodInfusedIronSword));
//            pages = BookUtils.addRecipeToPage(pages, new ItemStack(Items.DIAMOND_AXE));

            BookUtils.putPagesToEntry(entries, heading, pages);
        }

        entries.forEach((r, entry) -> entry.pageList.stream().filter(page -> page instanceof PageText).forEach(page -> ((PageText) page).setUnicodeFlag(true)));

        return entries;
    }
}
