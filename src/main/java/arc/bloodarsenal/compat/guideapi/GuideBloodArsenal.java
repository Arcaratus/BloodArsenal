package arc.bloodarsenal.compat.guideapi;

import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.category.CategoryItemStack;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.compat.guideapi.book.CategoryLifebringer;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nullable;
import java.awt.*;

public class GuideBloodArsenal implements IGuideBook
{
    public static Book guideBook;

    @Nullable
    @Override
    public Book buildBook()
    {
        guideBook = new Book();
        guideBook.setTitle("guide.bloodarsenal.title");
        guideBook.setDisplayName("guide.bloodarsenal.display");
        guideBook.setWelcomeMessage("guide.bloodarsenal.welcome");
        guideBook.setAuthor("guide.bloodarsenal.author");
        guideBook.setRegistryName(new ResourceLocation(BloodArsenal.MOD_ID, "guide"));
        guideBook.setColor(new Color(119, 0, 0));

        return guideBook;
    }

    @Override
    public void handleModel(ItemStack bookStack)
    {
        GuideAPI.setModel(guideBook);
    }

    @Override
    public void handlePost(ItemStack bookStack)
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            String keyBase = "guide.BloodArsenal.category.";
            guideBook.addCategory(new CategoryItemStack(CategoryLifebringer.buildCategory(), keyBase + "lifebringer", new ItemStack(ModItems.BLOOD_INFUSED_IRON_INGOT)));
        }

        GameRegistry.addRecipe(new ShapelessOreRecipe(GuideAPI.getStackFromBook(GuideBloodArsenal.guideBook), Items.BOOK, Items.FLINT_AND_STEEL, OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_WEAK), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence())));
    }
}
