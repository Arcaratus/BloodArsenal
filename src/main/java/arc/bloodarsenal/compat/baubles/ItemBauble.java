package arc.bloodarsenal.compat.baubles;

import arc.bloodarsenal.BloodArsenal;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class ItemBauble extends Item implements IBauble
{
    public ItemBauble(String name)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote)
        {
            InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);

            for (int i = 0; i < baubles.getSizeInventory(); ++i)
            {
                if (baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, itemStack))
                {
                    baubles.setInventorySlotContents(i, itemStack.copy());
                    if (!player.capabilities.isCreativeMode)
                    {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }

                    this.onEquipped(itemStack, player);
                    break;
                }
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
    {
    }

    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {
        if (!player.worldObj.isRemote)
        {
            player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.1F, 1.3F);
        }
    }

    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {
    }

    public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }

    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
    {
        return true;
    }
}
