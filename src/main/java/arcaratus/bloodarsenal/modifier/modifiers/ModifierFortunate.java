package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModifierFortunate extends Modifier
{
    public ModifierFortunate()
    {
        super(Constants.Modifiers.FORTUNATE, Constants.Modifiers.FORTUNATE_COUNTER.length, EnumModifierType.CORE);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player, int level)
    {
        Block block = state.getBlock();
        if (block.getDrops(world, pos, state, level + 1).size() > block.getDrops(world, pos, state, 0).size())
            NewModifiable.incrementModifierTracker(itemStack, this, 1);
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra, int level)
    {
        BloodArsenalUtils.writeNBTEnchantment(itemStack, Enchantments.FORTUNE, level + 1);
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        BloodArsenalUtils.removeNBTEnchantment(itemStack, Enchantments.FORTUNE);
    }
}
