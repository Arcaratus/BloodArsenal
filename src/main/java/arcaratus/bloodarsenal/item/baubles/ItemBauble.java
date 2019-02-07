package arcaratus.bloodarsenal.item.baubles;

import WayofTime.bloodmagic.util.helper.PlayerHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import baubles.api.*;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public abstract class ItemBauble extends Item implements IBauble
{
    // Apparently baubles doesn't unequip on death, which causes attribute modifiers to get weird/desync on respawn
    // See Baubles#236
    // Do it for our baubles if they're going to drop
    // TODO there is still some weirdness going on when dying/returning in the End, figure that out
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent evt)
    {
        if (!evt.getEntityLiving().world.isRemote && evt.getEntityLiving() instanceof EntityPlayer && !evt.getEntityLiving().world.getGameRules().getBoolean("keepInventory") && !((EntityPlayer) evt.getEntityLiving()).isSpectator())
        {
            IItemHandler inv = BaublesApi.getBaublesHandler((EntityPlayer) evt.getEntityLiving());
            for (int i = 0; i < inv.getSlots(); i++)
            {
                ItemStack stack = inv.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem().getRegistryName().getNamespace().equals(BloodArsenal.MOD_ID))
                {
                    ((ItemBauble) stack.getItem()).onUnequipped(stack, evt.getEntityLiving());
                }
            }
        }
    }

    private final BaubleType baubleType;

    public ItemBauble(String name, BaubleType baubleType)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);

        this.baubleType = baubleType;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return super.onItemRightClick(world, player, hand);

        ItemStack toEquip = stack.copy();
        toEquip.setCount(1);

        if (canEquip(toEquip, player) && !world.isRemote)
        {
            IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);

            for (int i = 0; i < baubles.getSlots(); i++)
            {
                if (baubles.isItemValidForSlot(i, toEquip, player))
                {
                    ItemStack stackInSlot = baubles.getStackInSlot(i);
                    if (stackInSlot.isEmpty() || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, player))
                    {
                        baubles.setStackInSlot(i, ItemStack.EMPTY);

                        baubles.setStackInSlot(i, toEquip);
                        ((IBauble) toEquip.getItem()).onEquipped(toEquip, player);

                        stack.shrink(1);

                        if (!stackInSlot.isEmpty())
                        {
                            ((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, player);

                            if (stack.isEmpty())
                                return ActionResult.newResult(EnumActionResult.SUCCESS, stackInSlot);
                            else
                                ItemHandlerHelper.giveItemToPlayer(player, stackInSlot);
                        }

                        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                    }
                }
            }
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack)
    {
        return baubleType;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {
        if (player != null && !player.getEntityWorld().isRemote)
        {
            player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.1F, 1.3F);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }
}
