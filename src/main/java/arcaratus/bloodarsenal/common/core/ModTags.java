package arcaratus.bloodarsenal.common.core;

import arcaratus.bloodarsenal.common.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.Function;

/**
 * Code partially adapted from Botania
 * https://github.com/Vazkii/Botania
 */
public final class ModTags
{
    private static <T> ITag.INamedTag<T> getOrRegister(List<? extends ITag.INamedTag<T>> list, Function<ResourceLocation, ITag.INamedTag<T>> register, ResourceLocation loc)
    {
        for (ITag.INamedTag<T> existing : list)
        {
            if (existing.getName().equals(loc))
            {
                return existing;
            }
        }

        return register.apply(loc);
    }

    public static class Items
    {
        public static final ITag.INamedTag<Item> INGOTS_BLOOD_INFUSED_IRON = forgeTag("ingots/blood_infused_iron");

        public static final ITag.INamedTag<Item> BLOCKS_BLOOD_INFUSED_IRON = forgeTag("storage_blocks/blood_infused_iron");

        private static ITag.INamedTag<Item> tag(String name)
        {
            return ItemTags.makeWrapperTag(BloodArsenal.rl(name).toString());
        }

        private static ITag.INamedTag<Item> forgeTag(String name)
        {
            return getOrRegister(ItemTags.getAllTags(), loc -> ItemTags.makeWrapperTag(loc.toString()), new ResourceLocation("forge", name));
        }
    }

    public static class Blocks
    {
        public static final ITag.INamedTag<Block> BLOCKS_BLOOD_INFUSED_IRON = forgeTag("storage_blocks/blood_infused_iron");

        private static ITag.INamedTag<Block> tag(String name)
        {
            return BlockTags.makeWrapperTag(BloodArsenal.rl(name).toString());
        }

        private static ITag.INamedTag<Block> forgeTag(String name)
        {
            return getOrRegister(BlockTags.getAllTags(), loc -> BlockTags.makeWrapperTag(loc.toString()), new ResourceLocation("forge", name));
        }
    }

    public static class Entities
    {
        private static ITag.INamedTag<EntityType<?>> tag(String name)
        {
            return EntityTypeTags.getTagById(BloodArsenal.rl(name).toString());
        }
    }
}
