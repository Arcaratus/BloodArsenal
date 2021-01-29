package arcaratus.bloodarsenal.common.item.tool;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.potion.ModEffects;
import arcaratus.bloodarsenal.common.util.DamageSourceGlass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import wayoftime.bloodmagic.event.SacrificeKnifeUsedEvent;
import wayoftime.bloodmagic.util.helper.IncenseHelper;
import wayoftime.bloodmagic.util.helper.NBTHelper;
import wayoftime.bloodmagic.util.helper.PlayerHelper;
import wayoftime.bloodmagic.util.helper.PlayerSacrificeHelper;

public class GlassSacrificialDaggerItem extends Item
{
    public GlassSacrificialDaggerItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        if (entityLiving instanceof PlayerEntity && !entityLiving.getEntityWorld().isRemote && PlayerSacrificeHelper.sacrificePlayerHealth((PlayerEntity) entityLiving))
        {
            IncenseHelper.setHasMaxIncense(stack, (PlayerEntity) entityLiving, false);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return IncenseHelper.getHasMaxIncense(stack) || super.hasEffect(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
        {
            return super.onItemRightClick(world, player, hand);
        }
        else if (canUseForSacrifice(stack))
        {
            player.setActiveHand(hand);
            return ActionResult.resultSuccess(stack);
        }
        else
        {
            int lpAdded = ConfigHandler.COMMON.glassSacrificialDaggerLP.get();
            if (!player.abilities.isCreativeMode)
            {
                SacrificeKnifeUsedEvent evt = new SacrificeKnifeUsedEvent(player, true, true, 2, lpAdded);
                if (MinecraftForge.EVENT_BUS.post(evt))
                {
                    return super.onItemRightClick(world, player, hand);
                }

                if (evt.shouldDrainHealth)
                {
                    player.hurtResistantTime = 0;
                    player.attackEntityFrom(DamageSourceGlass.INSTANCE, 0.001F);
                    player.setHealth(Math.max(player.getHealth() - (ConfigHandler.COMMON.glassSacrificialDaggerDamage.get().floatValue() + random.nextInt(2) - 0.002F), 1.0E-4F));

                    if (!world.isRemote)
                    {
                        if (!player.isPotionActive(ModEffects.BLEEDING.get()) && random.nextBoolean())
                        {
                            player.addPotionEffect(new EffectInstance(ModEffects.BLEEDING.get(), 40 + (random.nextInt(4) * 20), random.nextInt(2)));
                        }

                        if (player.getHealth() <= 0.001F)
                        {
                            player.onDeath(DamageSourceGlass.INSTANCE);
                            player.setHealth(0.0F);
                        }
                    }
                }

                if (!evt.shouldFillAltar)
                {
                    return super.onItemRightClick(world, player, hand);
                }

                lpAdded = evt.lpAdded;
            }
            else if (player.isSneaking())
            {
                lpAdded = 2147483647;
            }

            double posX = player.getPosX();
            double posY = player.getPosY();
            double posZ = player.getPosZ();
            world.playSound(player, posX, posY, posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

            for (int l = 0; l < 8; ++l)
            {
                world.addParticle(RedstoneParticleData.REDSTONE_DUST, posX + Math.random() - Math.random(), posY + Math.random() - Math.random(), posZ + Math.random() - Math.random(), 0.0D, 0.0D, 0.0D);
            }

            if (world.isRemote || !PlayerHelper.isFakePlayer(player))
            {
                PlayerSacrificeHelper.findAndFillAltar(world, player, lpAdded, false);
            }

            return super.onItemRightClick(world, player, hand);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!world.isRemote && entity instanceof PlayerEntity)
        {
            setUseForSacrifice(stack, isPlayerPreparedForSacrifice(world, (PlayerEntity) entity));
        }
    }

    public boolean isPlayerPreparedForSacrifice(World world, PlayerEntity player)
    {
        return !world.isRemote && PlayerSacrificeHelper.getPlayerIncense(player) > 0.0D;
    }

    public boolean canUseForSacrifice(ItemStack stack)
    {
        stack = NBTHelper.checkNBT(stack);
        return stack.getTag().getBoolean("sacrifice");
    }

    public void setUseForSacrifice(ItemStack stack, boolean sacrifice)
    {
        stack = NBTHelper.checkNBT(stack);
        stack.getTag().putBoolean("sacrifice", sacrifice);
    }
}
