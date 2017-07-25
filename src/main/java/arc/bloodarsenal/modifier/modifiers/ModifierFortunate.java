package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModifierFortunate extends Modifier
{
    public ModifierFortunate(int level)
    {
        super(Constants.Modifiers.FORTUNATE, Constants.Modifiers.FORTUNATE_COUNTER.length, level, EnumModifierType.CORE);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player)
    {
        Block block = state.getBlock();
        if (block.getDrops(world, pos, state, getLevel() + 1).size() > block.getDrops(world, pos, state, 0).size())
            ModifierTracker.getTracker(this).incrementCounter(StasisModifiable.getStasisModifiable(itemStack), 1);
    }

    @Override
    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra)
    {
        BloodArsenalUtils.writeNBTEnchantment(itemStack, Enchantments.FORTUNE, getLevel() + 1);
    }

    @Override
    public void removeSpecialNBT(ItemStack itemStack)
    {
        BloodArsenalUtils.removeNBTEnchantment(itemStack, Enchantments.FORTUNE);
    }
}
