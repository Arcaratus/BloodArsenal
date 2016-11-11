package arc.bloodarsenal.item.stasis;

import arc.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStasisAxe extends ItemStasisTool
{
    public ItemStasisAxe()
    {
        super("axe", 3F);
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, IBlockState state)
    {
        return !getActivated(itemStack) || (state.getMaterial() != Material.PLANTS && state.getMaterial() != Material.WOOD && state.getMaterial() != Material.LEAVES) ? super.getStrVsBlock(itemStack, state) : efficiencyOnProperMaterial;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemStack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 9, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("ItemStasisAxe");
    }
}
