package arc.bloodarsenal.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBloodInfusedIron extends BlockBloodArsenalBase
{
    public BlockBloodInfusedIron(String name)
    {
        super(name, Material.iron);

        setHardness(7.5F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
        return true;
    }
}
