package arc.bloodarsenal.util.handler;

import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if (event.getModID().equals(BloodArsenal.MOD_ID))
            ConfigHandler.syncConfig();
    }

    @SubscribeEvent
    public void onDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (ConfigHandler.doGlassShardsDrop && ModItems.glassShard != null)
        {
            Block block = event.getState().getBlock();
            if (event.getDrops().isEmpty() && block == Blocks.GLASS && event.getHarvester().getHeldItemMainhand().getItem() == Items.FLINT)
            {
                int quantity = MathHelper.clamp_int(1 + event.getWorld().rand.nextInt(2) + event.getWorld().rand.nextInt(event.getFortuneLevel() + 1), 0, 3);

                event.getDrops().add(new ItemStack(ModItems.glassShard, quantity));
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event)
    {
        if (event.getEntity().worldObj.isRemote || ModItems.bloodInfusedStick == null)
            return;

        if (event.getSource().getEntity() instanceof EntityPlayer && !PlayerHelper.isFakePlayer((EntityPlayer) event.getSource().getEntity()))
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

            if (player.getName().equals("Arcaratus") && player.getHeldItemMainhand().getItem() == ModItems.bloodInfusedStick && player.getHeldItemMainhand().hasTagCompound() && player.getHeldItemMainhand().getTagCompound().hasKey("living"))
            {
                event.getEntity().worldObj.spawnEntityInWorld(new EntityLightningBolt(event.getEntity().worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, true));
                event.getEntityLiving().setHealth(0);
            }
        }
    }
}
