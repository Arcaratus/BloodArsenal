package arcaratus.bloodarsenal.data;

import arcaratus.bloodarsenal.common.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Code partially adapted from Botania
 * https://github.com/Vazkii/Botania
 */
public class GeneratorBlock extends BlockStateProvider
{
    public GeneratorBlock(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, BloodArsenal.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName()
    {
        return "Blood Arsenal Blockstates";
    }

    @Override
    protected void registerStatesAndModels()
    {
        Set<Block> remainingBlocks = Registry.BLOCK.stream().filter(b -> BloodArsenal.MOD_ID.equals(Registry.BLOCK.getKey(b).getNamespace()))
                .collect(Collectors.toSet());

        // Remove manually written models

        // Process special block types
        GeneratorItem.takeAll(remainingBlocks, b -> b instanceof RotatedPillarBlock).forEach(b -> buildRotatedPillars(b, b.getRegistryName()));

        remainingBlocks.forEach(this::simpleBlock);
    }

    private void buildRotatedPillars(Block pillar, ResourceLocation rl)
    {
        ModelFile pillarModel = models().cubeColumn(rl.getPath(),
                BloodArsenal.rl("block/" + rl.getPath()),
                BloodArsenal.rl("block/" + rl.getPath() + "_top"));
        getVariantBuilder(pillar)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X).setModels(new ConfiguredModel(pillarModel, 90, 90, false))
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y).setModels(new ConfiguredModel(pillarModel))
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z).setModels(new ConfiguredModel(pillarModel, 90, 0, false));
    }
}
