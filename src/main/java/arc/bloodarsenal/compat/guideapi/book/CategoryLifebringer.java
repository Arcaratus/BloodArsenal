package arc.bloodarsenal.compat.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryLifebringer
{
    public static Map<ResourceLocation, EntryAbstract> buildCategory()
    {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String keyBase = "guide." + BloodArsenal.MOD_ID + ".entry.lifebringer.";
        String heading;

        {
            List<IPage> pages = new ArrayList<>();
            heading = keyBase + "intro";
            pages.addAll(BookUtils.textPages(heading));

            entries = BookUtils.putPagesToEntry(entries, heading, pages);
        }
        {
            List<IPage> pages = new ArrayList<>();
            heading = keyBase + "glassIsDangerous";
            pages.addAll(BookUtils.textPages(heading));
//            pages = BookUtils.addRecipeToPage(pages, new ItemStack(ModItems.bloodInfusedIronSword));
//            pages = BookUtils.addRecipeToPage(pages, new ItemStack(Items.DIAMOND_AXE));

            entries = BookUtils.putPagesToEntry(entries, heading, pages);
        }

        return entries;
    }
}
