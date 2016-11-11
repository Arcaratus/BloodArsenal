package arc.bloodarsenal.client.mesh;

import arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;

public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
{
    public final Fluid fluid;
    public final ModelResourceLocation location;

    public FluidStateMapper(Fluid fluid)
    {
        this.fluid = fluid;
        this.location = new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "FluidBlock"), fluid.getName());
    }

    @Nonnull
    @Override
    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
    {
        return location;
    }

    @Nonnull
    @Override
    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
    {
        return location;
    }
}