package arcaratus.bloodarsenal.block;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.util.IComplexVariantProvider;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBloodInfusedWoodenFenceGate extends BlockFenceGate implements IComplexVariantProvider, IBABlock
{
    public BlockBloodInfusedWoodenFenceGate(String name)
    {
        super(BlockPlanks.EnumType.DARK_OAK);

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
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
    public IProperty[] getIgnoredProperties()
    {
        return new IProperty[] {POWERED};
    }
}
