package arc.bloodarsenal.client.mesh;

import WayofTime.bloodmagic.api.soul.EnumDemonWillType;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.compat.baubles.ItemSoulPendant;
import arc.bloodarsenal.registry.ModItems;
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
        if (stack != null && stack.getItem() == ModItems.soulPendant)
        {
            EnumDemonWillType type = ((ItemSoulPendant) stack.getItem()).getCurrentType(stack);
            return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), "type=" + ItemSoulPendant.names[stack.getItemDamage()] + "_" + type.getName().toLowerCase());
        }

        return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), "type=petty_default");
    }
}