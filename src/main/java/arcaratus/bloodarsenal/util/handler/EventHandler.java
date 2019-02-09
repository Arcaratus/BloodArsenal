package arcaratus.bloodarsenal.util.handler;

import WayofTime.bloodmagic.item.types.ComponentTypes;
import WayofTime.bloodmagic.util.helper.PlayerHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.item.tool.ItemBoundStick;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import arcaratus.bloodarsenal.util.DamageSourceBleeding;
import arcaratus.bloodarsenal.util.DamageSourceGlass;
import net.minecraft.block.Block;
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
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EventHandler
{
    @SubscribeEvent
    public void onDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (ConfigHandler.misc.doGlassShardsDrop)
        {
            Block block = event.getState().getBlock();
            if (block == Blocks.GLASS && event.getHarvester() != null && event.getDrops() != null && event.getDrops().isEmpty() && event.getHarvester().getHeldItemMainhand().getItem() == Items.FLINT)
            {
                int quantity = MathHelper.clamp(1 + event.getWorld().rand.nextInt(2) + event.getWorld().rand.nextInt(event.getFortuneLevel() + 1), 0, 3);

                event.getDrops().add(EnumBaseTypes.GLASS_SHARD.getStack(quantity));
            }
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        if (ConfigHandler.misc.glassDeathLessDrops && !(event.getEntityLiving() instanceof EntityPlayer))
        {
            DamageSource source = event.getSource();
            Random random = new Random();

            if (source instanceof DamageSourceGlass || source instanceof DamageSourceBleeding)
            {
                if (random.nextBoolean())
                    event.getDrops().clear();
                else if (event.getDrops().size() > 0)
                    event.getDrops().remove(random.nextInt(event.getDrops().size()));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHurt(LivingHurtEvent event)
    {
        if (event.getEntity().getEntityWorld().isRemote ||  event.getSource().getImmediateSource() == event.getEntity())
            return;

        if (event.getSource().getImmediateSource() instanceof EntityPlayer && !PlayerHelper.isFakePlayer((EntityPlayer) event.getSource().getImmediateSource()))
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();

            if (player.getName().equals("Arcaratus") && player.getHeldItemMainhand().getItem() == EnumBaseTypes.BLOOD_INFUSED_STICK.getStack().getItem() && player.getHeldItemMainhand().hasTagCompound() && player.getHeldItemMainhand().getTagCompound().hasKey("living"))
            {
                event.getEntity().getEntityWorld().addWeatherEffect(new EntityLightningBolt(event.getEntity().getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, false));
                event.getEntityLiving().setHealth(0);
            }

            if (player.getHeldItemMainhand().getItem() instanceof ItemBoundStick)
            {
                float wouldBeHealth = event.getEntityLiving().getHealth() - event.getAmount();

                if (wouldBeHealth <= 0)
                {
                    event.getEntityLiving().setHealth(1);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLightningStrike(EntityStruckByLightningEvent event)
    {
        World world = event.getLightning().getEntityWorld();
        if (world.isRemote)
            return;

        if (event.getEntity() instanceof EntityItem)
        {
            EntityItem entityItem = (EntityItem) event.getEntity();
            ItemStack itemStack = entityItem.getItem();
            if (itemStack.getItem() == ComponentTypes.REAGENT_BINDING.getStack().getItem())
            {
                ItemStack glowstone, redstone, gunpowder, iron, gold;
                glowstone = redstone = gunpowder = iron = gold = ItemStack.EMPTY;
                for (EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, entityItem.getEntityBoundingBox().grow(1)))
                {
                    if (!item.getItem().isEmpty() && item.getItem().getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) glowstone = item.getItem();
                    else if (!item.getItem().isEmpty() && item.getItem().getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)) redstone = item.getItem();
                    else if (!item.getItem().isEmpty() && item.getItem().getItem() == Items.GUNPOWDER) gunpowder = item.getItem();
                    else if (!item.getItem().isEmpty() && item.getItem().getItem() == Item.getItemFromBlock(Blocks.IRON_BLOCK)) iron = item.getItem();
                    else if (!item.getItem().isEmpty() && item.getItem().getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK)) gold = item.getItem();
                }

                if (!glowstone.isEmpty() && !redstone.isEmpty() && !gunpowder.isEmpty() && !iron.isEmpty() && !gold.isEmpty())
                {
                    EntityItem entItem = new EntityItem(world, entityItem.posX + 0.5, entityItem.posY + 0.5, entityItem.posZ + 0.5, EnumBaseTypes.REAGENT_LIGHTNING.getStack());
                    entItem.setDefaultPickupDelay();
                    entItem.setNoDespawn();
                    world.spawnEntity(entItem);
                }
            }
            else if (itemStack.getItem() == EnumBaseTypes.REAGENT_LIGHTNING.getStack().getItem())
            {
                event.setCanceled(true);
            }
        }
    }
}
