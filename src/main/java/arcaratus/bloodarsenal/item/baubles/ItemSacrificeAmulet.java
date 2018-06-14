package arcaratus.bloodarsenal.item.baubles;

import WayofTime.bloodmagic.altar.IAltarManipulator;
import WayofTime.bloodmagic.altar.IBloodAltar;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.iface.IItemLPContainer;
import WayofTime.bloodmagic.util.Constants;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.ConfigHandler;
import baubles.api.BaubleType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemSacrificeAmulet extends ItemBauble implements IAltarManipulator, IItemLPContainer, IVariantProvider
{
    public final int CAPACITY = 10000; // Max LP storage

    public ItemSacrificeAmulet(String name)
    {
        super(name, BaubleType.AMULET);

        setHasSubtypes(true);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (event.getEntity().getEntityWorld().isRemote)
            return;

        if (event.getSource().getImmediateSource() instanceof EntityPlayer && !PlayerHelper.isFakePlayer((EntityPlayer) event.getSource().getImmediateSource()))
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();

            ItemStack baubleStack = BaubleUtils.getBaubleStackInPlayer(player, this);

            if (baubleStack.getItem() instanceof ItemSacrificeAmulet)
            {
                ItemSacrificeAmulet amulet = (ItemSacrificeAmulet) baubleStack.getItem();

                boolean shouldSyphon = amulet.getStoredLP(baubleStack) < amulet.CAPACITY;
                float damageDone = event.getEntityLiving().getHealth() < event.getAmount() ? event.getAmount() - event.getEntityLiving().getHealth() : event.getAmount();
                int totalLP = (int) Math.round(damageDone * ConfigHandler.values.sacrificeAmuletMultiplier);

                if (shouldSyphon)
                    ItemHelper.LPContainer.addLPToItem(baubleStack, totalLP, amulet.CAPACITY);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return super.onItemRightClick(world, player, hand);

        if (world.isRemote)
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        RayTraceResult rayTrace = rayTrace(world, player, false);

        if (rayTrace == null)
        {
            return super.onItemRightClick(world, player, EnumHand.MAIN_HAND);
        }
        else
        {
            if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                TileEntity tile = world.getTileEntity(rayTrace.getBlockPos());

                if (!(tile instanceof IBloodAltar))
                    return super.onItemRightClick(world, player, EnumHand.MAIN_HAND);

                ItemHelper.LPContainer.tryAndFillAltar((IBloodAltar) tile, stack, world, rayTrace.getBlockPos());
            }
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag)
    {
        if (!stack.hasTagCompound())
            return;

        list.add(TextHelper.localize("tooltip.bloodarsenal.self_sacrifice_amulet.desc"));
        list.add(TextHelper.localizeEffect("tooltip.bloodarsenal.stored", getStoredLP(stack)));

        super.addInformation(stack, world, list, flag);
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
        if (getStoredLP(itemstack) > CAPACITY)
            setStoredLP(itemstack, CAPACITY);
    }

    // IFillable

    @Override
    public int getCapacity()
    {
        return this.CAPACITY;
    }

    @Override
    public int getStoredLP(ItemStack stack)
    {
        return !stack.isEmpty() ? NBTHelper.checkNBT(stack).getTagCompound().getInteger(Constants.NBT.STORED_LP) : 0;
    }

    @Override
    public void setStoredLP(ItemStack stack, int lp)
    {
        if (!stack.isEmpty())
            NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.STORED_LP, lp);
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
