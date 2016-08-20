package arc.bloodarsenal.util.handler;

import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.item.ItemComponent;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.registry.ModItems;
import arc.bloodarsenal.util.DamageSourceBleeding;
import arc.bloodarsenal.util.DamageSourceGlass;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

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
            if (block != null && block == Blocks.GLASS && event.getHarvester() != null && event.getDrops() != null && event.getDrops().isEmpty() && event.getHarvester().getHeldItemMainhand() != null && event.getHarvester().getHeldItemMainhand().getItem() == Items.FLINT)
            {
                int quantity = MathHelper.clamp_int(1 + event.getWorld().rand.nextInt(2) + event.getWorld().rand.nextInt(event.getFortuneLevel() + 1), 0, 3);

                event.getDrops().add(new ItemStack(ModItems.glassShard, quantity));
            }
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        EntityLivingBase attackedEntity = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity entity = source.getEntity();
        Random random = new Random();

        if (source instanceof DamageSourceGlass || source instanceof DamageSourceBleeding)
        {
            if (random.nextBoolean())
                event.getDrops().clear();
            else if (event.getDrops().size() > 0)
                event.getDrops().remove(random.nextInt(event.getDrops().size()));
        }

    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event)
    {
        if (event.getEntity().worldObj.isRemote || ModItems.bloodInfusedStick == null || event.getSource().getEntity() == event.getEntity())
            return;

        if (event.getSource().getEntity() instanceof EntityPlayer && !PlayerHelper.isFakePlayer((EntityPlayer) event.getSource().getEntity()))
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

            if (player.getName().equals("Arcaratus") && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == ModItems.bloodInfusedStick && player.getHeldItemMainhand().hasTagCompound() && player.getHeldItemMainhand().getTagCompound().hasKey("living"))
            {
                event.getEntity().worldObj.addWeatherEffect(new EntityLightningBolt(event.getEntity().worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, false));
                event.getEntityLiving().setHealth(0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLightningStrike(EntityStruckByLightningEvent event)
    {
        World world = event.getLightning().worldObj;
        if (world.isRemote || event.getEntity() == null)
            return;

        if (event.getEntity() instanceof EntityItem)
        {
            EntityItem entityItem = (EntityItem) event.getEntity();
            ItemStack itemStack = entityItem.getEntityItem();
            if (itemStack.getItem() == ItemComponent.getStack(ItemComponent.REAGENT_BINDING).getItem())
            {
                ItemStack glowstone, redstone, gunpowder, iron, gold;
                glowstone = redstone = gunpowder = iron = gold = null;
                for (EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, entityItem.getEntityBoundingBox()))
                    if (item != null && item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) glowstone = item.getEntityItem();
                    else if (item != null && item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)) redstone = item.getEntityItem();
                    else if (item != null && item.getEntityItem().getItem() == Items.GUNPOWDER) gunpowder = item.getEntityItem();
                    else if (item != null && item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.IRON_BLOCK)) iron = item.getEntityItem();
                    else if (item != null && item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK)) gold = item.getEntityItem();

                if (glowstone != null && redstone != null && gunpowder != null && iron != null && gold != null)
                    world.spawnEntityInWorld(new EntityItem(world, entityItem.posX, entityItem.posY + 0.5, entityItem.posZ, new ItemStack(ModItems.reagentLightning)));
            }
        }
    }
}
