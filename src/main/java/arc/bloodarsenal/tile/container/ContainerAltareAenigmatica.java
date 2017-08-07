package arc.bloodarsenal.tile.container;

import WayofTime.bloodmagic.api.orb.IBloodOrb;
import arc.bloodarsenal.tile.TileAltareAenigmatica;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import static arc.bloodarsenal.registry.Constants.Misc.PLAYER_INVENTORY_COLUMNS;
import static arc.bloodarsenal.registry.Constants.Misc.PLAYER_INVENTORY_ROWS;

public class ContainerAltareAenigmatica extends Container
{
    private final IInventory tileAltareAenigmatica;

    public ContainerAltareAenigmatica(InventoryPlayer inventoryPlayer, IInventory tileAltareAenigmatica)
    {
        this.tileAltareAenigmatica = tileAltareAenigmatica;

        for (int rowIndex = 0; rowIndex < 3; ++rowIndex)
        {
            for (int columnIndex = 0; columnIndex < 3; ++columnIndex)
            {
                addSlotToContainer(new Slot(tileAltareAenigmatica, columnIndex + rowIndex * 3, 8 + columnIndex * 18, 16 + rowIndex * 18));
            }
        }

        addSlotToContainer(new SlotOrb(tileAltareAenigmatica, TileAltareAenigmatica.ORB_SLOT, 116, 52));

        for (int rowIndex = 0; rowIndex < PLAYER_INVENTORY_ROWS; ++rowIndex)
        {
            for (int columnIndex = 0; columnIndex < PLAYER_INVENTORY_COLUMNS; ++columnIndex)
            {
                addSlotToContainer(new Slot(inventoryPlayer, columnIndex + rowIndex * 9 + 9, 8 + columnIndex * 18, 81 + rowIndex * 18));
            }
        }

        for (int actionBarIndex = 0; actionBarIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarIndex)
        {
            addSlotToContainer(new Slot(inventoryPlayer, actionBarIndex, 8 + actionBarIndex * 18, 139));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();

            if (itemstack1 == null)
            {
                return null;
            }

            itemstack = itemstack1.copy();

            if (index > TileAltareAenigmatica.ORB_SLOT)
            {
                if (itemstack1.getItem() instanceof IBloodOrb)
                {
                    if (!this.mergeItemStack(itemstack1, TileAltareAenigmatica.ORB_SLOT, TileAltareAenigmatica.ORB_SLOT + 1, false)) //TODO: Add alchemy tools to list
                    {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemstack1, 0, TileAltareAenigmatica.ORB_SLOT, false))
                {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, TileAltareAenigmatica.ORB_SLOT + 1, TileAltareAenigmatica.ORB_SLOT + 37, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileAltareAenigmatica.isUseableByPlayer(playerIn);
    }

    private class SlotOrb extends Slot
    {
        public SlotOrb(IInventory inventory, int slotIndex, int x, int y)
        {
            super(inventory, slotIndex, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack)
        {
            return itemStack != null && itemStack.getItem() instanceof IBloodOrb;
        }
    }
}
