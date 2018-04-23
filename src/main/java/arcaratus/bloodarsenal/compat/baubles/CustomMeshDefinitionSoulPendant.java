package arcaratus.bloodarsenal.compat.baubles;

import WayofTime.bloodmagic.api.soul.EnumDemonWillType;
import arcaratus.bloodarsenal.BloodArsenal;
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
        if (stack.getItem() == CompatBaubles.SOUL_PENDANT)
        {
            EnumDemonWillType type = ((ItemSoulPendant) stack.getItem()).getCurrentType(stack);
            return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), "type=" + ItemSoulPendant.names[stack.getItemDamage()] + "_" + type.getName().toLowerCase());
        }

        return new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), "type=petty_default");
    }
}