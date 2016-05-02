package arc.bloodarsenal.item.tool;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemBloodInfusedWoodenShovel extends ItemBloodInfusedWoodenTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand);

    public ItemBloodInfusedWoodenShovel()
    {
        super("shovel", 2.0F, EFFECTIVE_ON);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        return state.getMaterial() != Material.iron && state.getMaterial() != Material.anvil && state.getMaterial() != Material.rock ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -4, 0));
        }
        return multimap;
    }
}
