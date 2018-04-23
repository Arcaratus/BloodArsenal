package arcaratus.bloodarsenal.client.mesh;

import WayofTime.bloodmagic.iface.IActivatable;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionActivatable implements ItemMeshDefinition
{
    private final String name;

    public CustomMeshDefinitionActivatable(String name)
    {
        this.name = name;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack)
    {
        if (stack.getItem() instanceof IActivatable)
            if (((IActivatable) stack.getItem()).getActivated(stack))
                return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, name), "active=true");

        return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, name), "active=false");
    }
}