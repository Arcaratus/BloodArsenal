package arcaratus.bloodarsenal.item.tool;

import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import static arcaratus.bloodarsenal.Constants.Item.AXE_EFFECTIVE_ON;

public class ItemBloodInfusedIronAxe extends ItemBloodInfusedTool.Iron
{
    public ItemBloodInfusedIronAxe()
    {
        super("axe", 4.6F, AXE_EFFECTIVE_ON);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return state.getMaterial() != Material.PLANTS && state.getMaterial() != Material.WOOD && state.getMaterial() != Material.LEAVES ? super.getDestroySpeed(stack, state) : efficiency;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4.2, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -4.2, 0));
        }
        return multimap;
    }
}
