package arcaratus.bloodarsenal.client.mesh;

import WayofTime.bloodmagic.soul.EnumDemonWillType;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.baubles.ItemSoulPendant;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionSoulPendant implements ItemMeshDefinition
{
    private final String name;

    public CustomMeshDefinitionSoulPendant(String name)
    {
        this.name = name;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack)
    {
        if (!stack.isEmpty() && stack.getItem() == RegistrarBloodArsenalItems.SOUL_PENDANT)
        {
            EnumDemonWillType type = ((ItemSoulPendant) stack.getItem()).getCurrentType(stack);
            return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, name), "type=" + ItemSoulPendant.names[stack.getItemDamage()] + "_" + type.getName().toLowerCase());
        }

        return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, name), "type=petty_default");
    }
}