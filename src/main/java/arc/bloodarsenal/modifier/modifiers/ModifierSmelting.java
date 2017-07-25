package arc.bloodarsenal.modifier.modifiers;

import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.registry.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ModifierSmelting extends Modifier
{
    public ModifierSmelting(int level)
    {
        super(Constants.Modifiers.SMELTING, Constants.Modifiers.SMELTING_COUNTER.length, level, EnumModifierType.CORE);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player)
    {
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
        if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
            return;

        ItemStack blockStack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
        ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(blockStack);
        if (resultStack != null)
        {
            if (!(getLevel() > 0 || (getLevel() == 0 && resultStack.getItem() instanceof ItemBlock)))
                resultStack = null;
            world.setBlockToAir(pos);
            Block.spawnAsEntity(world, pos, resultStack);
            ModifierTracker.getTracker(this).incrementCounter(StasisModifiable.getStasisModifiable(itemStack), 1);
        }
        else
        {
            world.setBlockToAir(pos);
            state.getBlock().dropBlockAsItem(world, pos, state, 0);
        }
    }
}
