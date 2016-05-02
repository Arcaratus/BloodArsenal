package arc.bloodarsenal.item.tool;

import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemBloodInfusedIronPickaxe extends ItemBloodInfusedTool.Iron
{
    public ItemBloodInfusedIronPickaxe()
    {
        super("pickaxe", 1F, PICKAXE_EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn == Blocks.OBSIDIAN ? this.toolMaterial.getHarvestLevel() == 3
                : (blockIn != Blocks.DIAMOND_BLOCK && blockIn != Blocks.DIAMOND_ORE ? (blockIn != Blocks.EMERALD_ORE && blockIn != Blocks.EMERALD_BLOCK ? (blockIn != Blocks.GOLD_ORE && blockIn != Blocks.GOLD_ORE ? (blockIn != Blocks.IRON_BLOCK && blockIn != Blocks.IRON_ORE ? (blockIn != Blocks.LAPIS_BLOCK && blockIn != Blocks.LAPIS_ORE ? (blockIn != Blocks.REDSTONE_ORE && blockIn != Blocks.LIT_REDSTONE_ORE ? (blockIn.getMaterial() == Material.ROCK || (blockIn.getMaterial() == Material.IRON || blockIn.getMaterial() == Material.ANVIL)) : this.toolMaterial.getHarvestLevel() >= 2)
                : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        return state.getMaterial() != Material.IRON && state.getMaterial() != Material.ANVIL && state.getMaterial() != Material.ROCK ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
        }
        return multimap;
    }
}
