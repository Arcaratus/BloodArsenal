package arcaratus.bloodarsenal.data;

import arcaratus.bloodarsenal.common.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
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
    public String getName() {
        return "Testing Blockstates";
    }

    @Override
    protected void registerStatesAndModels()
    {
        Set<Block> remainingBlocks = Registry.BLOCK.stream().filter(b -> BloodArsenal.MOD_ID.equals(Registry.BLOCK.getKey(b).getNamespace()))
                .collect(Collectors.toSet());
        remainingBlocks.forEach(this::simpleBlock);
    }
}
