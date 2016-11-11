package arc.bloodarsenal.item.stasis;

import arc.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStasisShovel extends ItemStasisTool
{
    public ItemStasisShovel()
    {
        super("shovel", 1.5F);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state)
    {
        return state.getBlock() == Blocks.SNOW_LAYER || state.getBlock() == Blocks.SNOW;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemStack = player.getHeldItem(hand);
        if (getActivated(itemStack))
        {
            if (!player.canPlayerEdit(pos.offset(facing), facing, itemStack))
            {
                return EnumActionResult.FAIL;
            }
            else
            {
                IBlockState iblockstate = world.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (facing != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS)
                {
                    IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
                    world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (!world.isRemote)
                    {
                        world.setBlockState(pos, iblockstate1, 11);
                        itemStack.damageItem(1, player);
                    }

                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    return EnumActionResult.PASS;
                }
            }
        }
        else
        {
            return EnumActionResult.PASS;
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemStack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.7, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("ItemStasisShovel");
    }
}
