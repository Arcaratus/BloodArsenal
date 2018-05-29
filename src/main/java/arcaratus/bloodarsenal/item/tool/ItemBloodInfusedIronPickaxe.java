package arcaratus.bloodarsenal.item.tool;

import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import static arcaratus.bloodarsenal.registry.Constants.Item.PICKAXE_EFFECTIVE_ON;

public class ItemBloodInfusedIronPickaxe extends ItemBloodInfusedTool.Iron
{
    public ItemBloodInfusedIronPickaxe()
    {
        super("pickaxe", 1.5F, PICKAXE_EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn == Blocks.OBSIDIAN ? toolMaterial.getHarvestLevel() == 3
                : (blockIn != Blocks.DIAMOND_BLOCK && blockIn != Blocks.DIAMOND_ORE ? (blockIn != Blocks.EMERALD_ORE && blockIn != Blocks.EMERALD_BLOCK ? (blockIn != Blocks.GOLD_ORE && blockIn != Blocks.GOLD_ORE ? (blockIn != Blocks.IRON_BLOCK && blockIn != Blocks.IRON_ORE ? (blockIn != Blocks.LAPIS_BLOCK && blockIn != Blocks.LAPIS_ORE ? (blockIn != Blocks.REDSTONE_ORE && blockIn != Blocks.LIT_REDSTONE_ORE ? (blockIn.getMaterial() == Material.ROCK || (blockIn.getMaterial() == Material.IRON || blockIn.getMaterial() == Material.ANVIL)) : toolMaterial.getHarvestLevel() >= 2)
                : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return state.getMaterial() != Material.IRON && state.getMaterial() != Material.ANVIL && state.getMaterial() != Material.ROCK ? super.getDestroySpeed(stack, state) : efficiency;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
        }
        return multimap;
    }
}
