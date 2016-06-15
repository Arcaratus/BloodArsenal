package arc.bloodarsenal.client.mesh;

import WayofTime.bloodmagic.api.iface.IActivatable;
import arc.bloodarsenal.BloodArsenal;
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
                return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), "active=true");

        return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), "active=false");
    }
}