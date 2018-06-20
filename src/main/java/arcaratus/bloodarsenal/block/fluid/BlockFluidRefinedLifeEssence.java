package arcaratus.bloodarsenal.block.fluid;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidRefinedLifeEssence extends BlockFluidClassic
{
    public BlockFluidRefinedLifeEssence(String name)
    {
        super(RegistrarBloodArsenalBlocks.FLUID_REFINED_LIFE_ESSENCE, Material.WATER); // Har har har superfluid

        setUnlocalizedName(BloodArsenal.MOD_ID + ".fluid." + name);
        setRegistryName(name);
        RegistrarBloodArsenalBlocks.FLUID_REFINED_LIFE_ESSENCE.setBlock(this);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos blockPos)
    {
        return !world.getBlockState(blockPos).getMaterial().isLiquid() && super.canDisplace(world, blockPos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos blockPos)
    {
        return !world.getBlockState(blockPos).getMaterial().isLiquid() && super.displaceIfPossible(world, blockPos);
    }
}
