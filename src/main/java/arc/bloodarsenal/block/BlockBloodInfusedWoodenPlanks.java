package arc.bloodarsenal.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBloodInfusedWoodenPlanks extends BlockBloodArsenalBase
{
    public BlockBloodInfusedWoodenPlanks(String name)
    {
        super(name, Material.wood);

        setHardness(3.0F);
        setResistance(4.0F);
        setSoundType(SoundType.WOOD);
    }
}
