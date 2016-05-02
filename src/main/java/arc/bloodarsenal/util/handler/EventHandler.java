package arc.bloodarsenal.util.handler;

import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
    @SubscribeEvent
    public void onDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (ConfigHandler.doGlassShardsDrop && ModItems.glassShard != null)
        {
            Block block = event.getState().getBlock();
            if ((event.getDrops().isEmpty() && block == Blocks.glass || block == Blocks.stained_glass) && event.getHarvester().getHeldItemMainhand().getItem() == Items.flint)
            {
                int quantity = MathHelper.clamp_int(1 + event.getWorld().rand.nextInt(2) + event.getWorld().rand.nextInt(event.getFortuneLevel() + 1), 0, 3);

                event.getDrops().add(new ItemStack(ModItems.glassShard, quantity));
            }
        }
    }
}
