package arcaratus.bloodarsenal.compat.guideapi;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import amerifrance.guideapi.api.*;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.category.CategoryItemStack;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.compat.guideapi.book.CategoryLifebringer;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

@GuideBook(priority = EventPriority.HIGHEST)
public class GuideBloodArsenal implements IGuideBook
{
    public static Book GUIDE_BOOK;

    @Nullable
    @Override
    public Book buildBook()
    {
        BookBinder binder = new BookBinder(new ResourceLocation(BloodArsenal.MOD_ID, "guide"));
        binder.setGuideTitle("guide.bloodarsenal.title");
        binder.setItemName("guide.bloodarsenal.display");
        binder.setHeader("guide.bloodarsenal.welcome");
        binder.setAuthor("guide.bloodarsenal.author");
        binder.setColor(new Color(119, 0, 0));
        binder.addCategory(new CategoryItemStack(CategoryLifebringer.buildCategory(), "guide.bloodarsenal.category.lifebringer", EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack()));
        return GUIDE_BOOK = binder.build();
    }

    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack)
    {
        return new ShapelessOreRecipe(new ResourceLocation(BloodArsenal.MOD_ID, "guide"), GuideAPI.getStackFromBook(GUIDE_BOOK), Items.BOOK, Items.FLINT_AND_STEEL, RegistrarBloodMagicItems.BLOOD_ORB, FluidUtil.getFilledBucket(new FluidStack(BlockLifeEssence.getLifeEssence(), 0))).setRegistryName("bloodarsenal_guide");
    }
}
