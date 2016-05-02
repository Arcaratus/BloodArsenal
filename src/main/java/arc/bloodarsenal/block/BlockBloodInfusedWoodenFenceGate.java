package arc.bloodarsenal.block;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.util.IComplexVariantProvider;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockBloodInfusedWoodenFenceGate extends BlockFenceGate implements IComplexVariantProvider
{
    public BlockBloodInfusedWoodenFenceGate(String name)
    {
        super(BlockPlanks.EnumType.DARK_OAK);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(3.0F);
        setResistance(6.0F);
        setHarvestLevel("axe", 0);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }

    @Override
    public IProperty[] getIgnoredProperties()
    {
        return new IProperty[] {POWERED};
    }
}
