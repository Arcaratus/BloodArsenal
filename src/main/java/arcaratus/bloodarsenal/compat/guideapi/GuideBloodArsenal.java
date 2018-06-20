package arcaratus.bloodarsenal.compat.guideapi;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import amerifrance.guideapi.api.*;
import amerifrance.guideapi.api.impl.Book;
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
    public static final Book GUIDE_BOOK = new Book();

    @Nullable
    @Override
    public Book buildBook()
    {
        GUIDE_BOOK.setTitle("guide.bloodarsenal.title");
        GUIDE_BOOK.setDisplayName("guide.bloodarsenal.display");
        GUIDE_BOOK.setWelcomeMessage("guide.bloodarsenal.welcome");
        GUIDE_BOOK.setAuthor("guide.bloodarsenal.author");
        GUIDE_BOOK.setRegistryName(new ResourceLocation(BloodArsenal.MOD_ID, "guide"));
        GUIDE_BOOK.setColor(new Color(119, 0, 0));

        return GUIDE_BOOK;
    }

//    @Override
//    public void handleModel(ItemStack bookStack)
//    {
//        GuideAPI.setModel(GUIDE_BOOK);
//    }

    @Override
    public void handlePost(ItemStack bookStack)
    {
        String keyBase = "guide.bloodarsenal.category.";
        GUIDE_BOOK.addCategory(new CategoryItemStack(CategoryLifebringer.buildCategory(), keyBase + "lifebringer", EnumBaseTypes.BLOOD_INFUSED_IRON_INGOT.getStack()));
    }

    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack)
    {
        return new ShapelessOreRecipe(new ResourceLocation(BloodArsenal.MOD_ID, "guide"), GuideAPI.getStackFromBook(GUIDE_BOOK), Items.BOOK, Items.FLINT_AND_STEEL, RegistrarBloodMagicItems.BLOOD_ORB, FluidUtil.getFilledBucket(new FluidStack(BlockLifeEssence.getLifeEssence(), 0))).setRegistryName("bloodarsenal_guide");
    }
}
