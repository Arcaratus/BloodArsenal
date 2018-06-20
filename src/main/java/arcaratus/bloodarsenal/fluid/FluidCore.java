package arcaratus.bloodarsenal.fluid;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidCore extends Fluid
{
    public FluidCore(String fluidName)
    {
        super(fluidName, new ResourceLocation(BloodArsenal.MOD_ID, "blocks/" + fluidName + "_still"), new ResourceLocation(BloodArsenal.MOD_ID, "blocks/" + fluidName + "_flowing"));
    }

    @Override
    public String getLocalizedName(FluidStack fluidStack)
    {
        return TextHelper.localize("tile.bloodarsenal.fluid." + fluidName + ".name");
    }
}
