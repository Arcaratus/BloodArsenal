package arcaratus.bloodarsenal.data;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.item.ModItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.*;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static arcaratus.bloodarsenal.common.block.ModBlocks.*;

/**
 * Code partially adapted from Botania
 * https://github.com/Vazkii/Botania
 */
public class GeneratorLootTable implements IDataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    private final DataGenerator generator;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public GeneratorLootTable(DataGenerator generator)
    {
        this.generator = generator;

        for (Block b : Registry.BLOCK)
        {
            ResourceLocation id = Registry.BLOCK.getKey(b);
            if (!BloodArsenal.MOD_ID.equals(id.getNamespace()))
            {
                continue;
            }

            if (b instanceof SlabBlock)
            {
                functionTable.put(b, GeneratorLootTable::genSlab);
            }
        }

        // Silk Touch
        functionTable.put(BLOOD_INFUSED_GLOWSTONE.get(), block -> genSilkDrop(block, ItemLootEntry.builder(ModItems.BLOOD_INFUSED_GLOWSTONE_DUST.get())
                .acceptFunction(SetCount.builder(RandomValueRange.of(2, 4)))
                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))
                .acceptFunction(LimitCount.func_215911_a(IntClamper.func_215843_a(1, 4)))));
    }

    @Nonnull
    @Override
    public String getName()
    {
        return "Blood Arsenal block loot tables";
    }

    @Override
    public void act(DirectoryCache cache) throws IOException
    {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (Block b : Registry.BLOCK)
        {
            ResourceLocation id = Registry.BLOCK.getKey(b);
            if (!BloodArsenal.MOD_ID.equals(id.getNamespace()))
            {
                continue;
            }

            Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, GeneratorLootTable::genRegular);
            tables.put(id, func.apply(b));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet())
        {
            Path path = getPath(generator.getOutputFolder(), e.getKey());
            IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    private static Path getPath(Path root, ResourceLocation id)
    {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder empty(Block b)
    {
        return LootTable.builder();
    }

    private static LootTable.Builder genCopyNbt(Block b, String... tags)
    {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
        CopyNbt.Builder func = CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY);
        for (String tag : tags)
        {
            func = func.replaceOperation(tag, "BlockEntityTag." + tag);
        }
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder())
                .acceptFunction(func);
        return LootTable.builder().addLootPool(pool);
    }

    private static LootTable.Builder genSilkDrop(IItemProvider silkDrop, IItemProvider normalDrop)
    {
        LootEntry.Builder<?> cobbleDrop = ItemLootEntry.builder(normalDrop).acceptCondition(SurvivesExplosion.builder());
        LootEntry.Builder<?> stoneDrop = ItemLootEntry.builder(silkDrop).acceptCondition(SILK_TOUCH);

        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(stoneDrop.alternatively(cobbleDrop)));
    }

    private static LootTable.Builder genSilkDrop(IItemProvider silkDrop, LootEntry.Builder<?> builder)
    {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(silkDrop).acceptCondition(SILK_TOUCH).alternatively(builder)));
    }

    private static LootTable.Builder genSlab(Block b)
    {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b)
                .acceptFunction(SetCount.builder(ConstantRange.of(2))
                .acceptCondition(BlockStateProperty.builder(b).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))))
                .acceptFunction(ExplosionDecay.builder());
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
    }

    private static LootTable.Builder genRegular(Block b)
    {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(pool);
    }
}
