package arc.bloodarsenal.compat.baubles;

import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import arc.bloodarsenal.BloodArsenal;
import baubles.api.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class ItemBauble extends Item implements IBauble
{
    private final BaubleType baubleType;

    public ItemBauble(String name, BaubleType baubleType)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);

        this.baubleType = baubleType;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return super.onItemRightClick(itemStack, world, player, hand);

        if (!world.isRemote)
        {
            IInventory baubles = BaublesApi.getBaubles(player);

            for (int i = 0; i < baubles.getSizeInventory(); ++i)
            {
                if (baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, stack))
                {
                    baubles.setInventorySlotContents(i, stack.copy());
                    if (!player.capabilities.isCreativeMode)
                    {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }

                    this.onEquipped(stack, player);
                    break;
                }
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
        if (!player.getEntityWorld().isRemote)
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
