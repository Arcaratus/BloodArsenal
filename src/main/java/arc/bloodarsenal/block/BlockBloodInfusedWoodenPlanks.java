package arc.bloodarsenal.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBloodInfusedWoodenPlanks extends BlockBloodArsenalBase
{
    public BlockBloodInfusedWoodenPlanks(String name)
    {
        super(name, Material.wood);

        setHardness(3.0F);
        setResistance(6.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
