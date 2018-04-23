package arcaratus.bloodarsenal.item.stasis;

import arcaratus.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStasisPickaxe extends ItemStasisTool
{
    public ItemStasisPickaxe()
    {
        super("pickaxe", 1F);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn == Blocks.OBSIDIAN ? toolMaterial.getHarvestLevel() == 3
                : (blockIn != Blocks.DIAMOND_BLOCK && blockIn != Blocks.DIAMOND_ORE ? (blockIn != Blocks.EMERALD_ORE && blockIn != Blocks.EMERALD_BLOCK ? (blockIn != Blocks.GOLD_ORE && blockIn != Blocks.GOLD_ORE ? (blockIn != Blocks.IRON_BLOCK && blockIn != Blocks.IRON_ORE ? (blockIn != Blocks.LAPIS_BLOCK && blockIn != Blocks.LAPIS_ORE ? (blockIn != Blocks.REDSTONE_ORE && blockIn != Blocks.LIT_REDSTONE_ORE ? (blockIn.getMaterial() == Material.ROCK || (blockIn.getMaterial() == Material.IRON || blockIn.getMaterial() == Material.ANVIL)) : toolMaterial.getHarvestLevel() >= 2)
                : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2);
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, IBlockState state)
    {
        return !getActivated(itemStack) || (state.getMaterial() != Material.IRON && state.getMaterial() != Material.ANVIL && state.getMaterial() != Material.ROCK) ? super.getStrVsBlock(itemStack, state) : efficiencyOnProperMaterial;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemStack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.6, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("ItemStasisPickaxe");
    }
}
