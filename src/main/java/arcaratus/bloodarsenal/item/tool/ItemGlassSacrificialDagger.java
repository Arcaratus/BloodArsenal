package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.event.SacrificeKnifeUsedEvent;
import WayofTime.bloodmagic.util.Constants;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenal;
import arcaratus.bloodarsenal.util.DamageSourceGlass;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemGlassSacrificialDagger extends Item implements IMeshProvider
{
    public ItemGlassSacrificialDagger(String name)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.addAll(Arrays.asList(TextHelper.cutLongString(TextHelper.localizeEffect("tooltip.bloodarsenal.glass_sacrificial_dagger.desc"))));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer && !entityLiving.getEntityWorld().isRemote)
            if (PlayerSacrificeHelper.sacrificePlayerHealth((EntityPlayer) entityLiving))
                IncenseHelper.setHasMaxIncense(stack, (EntityPlayer) entityLiving, false);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player) || player.capabilities.isCreativeMode)
            return super.onItemRightClick(world, player, hand);

        if (canUseForSacrifice(stack))
        {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        int lpAdded = ConfigHandler.values.glassSacrificialDaggerLP;

        SacrificeKnifeUsedEvent evt = new SacrificeKnifeUsedEvent(player, true, true, 2, lpAdded);
        if (MinecraftForge.EVENT_BUS.post(evt))
            return super.onItemRightClick(world, player, hand);

        if (evt.shouldDrainHealth)
        {
            player.hurtResistantTime = 0;
            float nextHitDmg = (float)(ConfigHandler.values.glassSacrificialDaggerHealth + itemRand.nextInt(3)
            if (Math.ceil(player.getHealth() - nextHitDmg) <= 0)
            {
                player.attackEntityFrom(DamageSourceGlass.INSTANCE, Float.MAX_VALUE);
            } else
            {
                player.attackEntityFrom(DamageSourceGlass.INSTANCE, 0.001F);
                float damageAmount = net.minecraftforge.common.ForgeHooks.onLivingDamage(player, damageSrc, nextHitDmg);
                player.getCombatTracker().trackDamage(damageSrc, player.getHealth(), damageAmount);
                player.setHealth(Math.max(player.getHealth() - nextHitDmg), 0.001F));
                
                if (!player.isPotionActive(RegistrarBloodArsenal.BLEEDING) && itemRand.nextBoolean())
                    player.addPotionEffect(new PotionEffect(RegistrarBloodArsenal.BLEEDING, 40 + (itemRand.nextInt(4) * 20), itemRand.nextInt(2)));
            }
        }

        if (!evt.shouldFillAltar)
            return super.onItemRightClick(world, player, hand);

        lpAdded = evt.lpAdded;

        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

        for (int l = 0; l < 8; ++l)
            world.spawnParticle(EnumParticleTypes.REDSTONE, posX + Math.random() - Math.random(), posY + Math.random() - Math.random(), posZ + Math.random() - Math.random(), 0, 0, 0);

        if (!world.isRemote && PlayerHelper.isFakePlayer(player) || player.isPotionActive(PlayerSacrificeHelper.soulFrayId))
            return super.onItemRightClick(world, player, hand);

        PlayerSacrificeHelper.findAndFillAltar(world, player, lpAdded, false);

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        if (!world.isRemote && entity instanceof EntityPlayer)
        {
            boolean prepared = isPlayerPreparedForSacrifice(world, (EntityPlayer) entity);
            setUseForSacrifice(stack, prepared);
            if (IncenseHelper.getHasMaxIncense(stack) && !prepared)
                IncenseHelper.setHasMaxIncense(stack, (EntityPlayer) entity, false);

            if (prepared)
            {
                boolean isMax = IncenseHelper.getMaxIncense((EntityPlayer) entity) == IncenseHelper.getCurrentIncense((EntityPlayer) entity);
                IncenseHelper.setHasMaxIncense(stack, (EntityPlayer) entity, isMax);
            }
        }
    }

    public boolean isPlayerPreparedForSacrifice(World world, EntityPlayer player)
    {
        return !world.isRemote && (PlayerSacrificeHelper.getPlayerIncense(player) > 0);
    }

    public boolean canUseForSacrifice(ItemStack stack)
    {
        stack = NBTHelper.checkNBT(stack);
        return stack.getTagCompound().getBoolean(Constants.NBT.SACRIFICE);
    }

    public void setUseForSacrifice(ItemStack stack, boolean sacrifice)
    {
        stack = NBTHelper.checkNBT(stack);
        stack.getTagCompound().setBoolean(Constants.NBT.SACRIFICE, sacrifice);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return stack -> new ModelResourceLocation(getRegistryName(), canUseForSacrifice(stack) ? "type=ceremonial" : "type=normal");
    }

    @Override
    public void gatherVariants(Consumer<String> variants)
    {
        variants.accept("type=normal");
        variants.accept("type=ceremonial");
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return IncenseHelper.getHasMaxIncense(stack) || super.hasEffect(stack);
    }
}
