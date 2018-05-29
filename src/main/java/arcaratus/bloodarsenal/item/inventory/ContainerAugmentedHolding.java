package arcaratus.bloodarsenal.item.inventory;

import WayofTime.bloodmagic.iface.ISigil;
import WayofTime.bloodmagic.item.sigil.ItemSigilHolding;
import arcaratus.bloodarsenal.item.sigil.ItemSigilAugmentedHolding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

import static arcaratus.bloodarsenal.registry.Constants.Misc.PLAYER_INVENTORY_COLUMNS;
import static arcaratus.bloodarsenal.registry.Constants.Misc.PLAYER_INVENTORY_ROWS;

public class ContainerAugmentedHolding extends Container
{
    private final EntityPlayer player;
    public final InventoryAugmentedHolding inventoryHolding;

    public ContainerAugmentedHolding(EntityPlayer player, InventoryAugmentedHolding inventoryHolding)
    {
        this.player = player;
        this.inventoryHolding = inventoryHolding;
        int currentSlotHeldIn = player.inventory.currentItem;

        for (int columnIndex = 0; columnIndex < ItemSigilAugmentedHolding.INVENTORY_SIZE; ++columnIndex)
        {
            addSlotToContainer(new SlotHolding(this, inventoryHolding, player, columnIndex, 8 + columnIndex * 18, 17));
        }

        for (int rowIndex = 0; rowIndex < PLAYER_INVENTORY_ROWS; ++rowIndex)
        {
            for (int columnIndex = 0; columnIndex < PLAYER_INVENTORY_COLUMNS; ++columnIndex)
            {
                addSlotToContainer(new Slot(player.inventory, columnIndex + rowIndex * 9 + 9, 8 + columnIndex * 18, 41 + rowIndex * 18));
            }
        }

        for (int actionBarIndex = 0; actionBarIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarIndex)
        {
            if (actionBarIndex == currentSlotHeldIn)
            {
                addSlotToContainer(new SlotDisabled(player.inventory, actionBarIndex, 8 + actionBarIndex * 18, 99));
            }
            else
            {
                addSlotToContainer(new Slot(player.inventory, actionBarIndex, 8 + actionBarIndex * 18, 99));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if (!entityPlayer.getEntityWorld().isRemote)
        {
            saveInventory(entityPlayer);
        }
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (!player.getEntityWorld().isRemote)
        {
            saveInventory(player);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slotObject = inventorySlots.get(slotIndex);
        int slots = inventorySlots.size();

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if (stack.getItem() instanceof ISigil)
            {
                if (slotIndex < ItemSigilHolding.inventorySize)
                {
                    if (!mergeItemStack(stackInSlot, ItemSigilHolding.inventorySize, slots, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!mergeItemStack(stackInSlot, 0, ItemSigilHolding.inventorySize, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (stack.getItem() instanceof ItemSigilHolding || stack.getItem() instanceof ItemSigilAugmentedHolding)
            {
                if (slotIndex < ItemSigilHolding.inventorySize + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS))
                {
                    if (!mergeItemStack(stackInSlot, ItemSigilHolding.inventorySize + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), inventorySlots.size(), false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!mergeItemStack(stackInSlot, ItemSigilHolding.inventorySize, ItemSigilHolding.inventorySize + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.getCount() == 0)
            {
                slotObject.putStack(ItemStack.EMPTY);
            }
            else
            {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slotObject.onTake(player, stackInSlot);
        }

        return stack;
    }

    public void saveInventory(EntityPlayer entityPlayer)
    {
        inventoryHolding.onGuiSaved(entityPlayer);
    }

    private class SlotHolding extends Slot
    {
        private final EntityPlayer player;
        private ContainerAugmentedHolding containerHolding;

        public SlotHolding(ContainerAugmentedHolding containerHolding, IInventory inventory, EntityPlayer player, int slotIndex, int x, int y)
        {
            super(inventory, slotIndex, x, y);
            this.player = player;
            this.containerHolding = containerHolding;
        }

        @Override
        public void onSlotChanged()
        {
            super.onSlotChanged();

            if (FMLCommonHandler.instance().getEffectiveSide().isServer())
            {
                containerHolding.saveInventory(player);
            }
        }

        @Override
        public boolean isItemValid(ItemStack itemStack)
        {
            return itemStack.getItem() instanceof ISigil && !(itemStack.getItem() instanceof ItemSigilHolding || itemStack.getItem() instanceof ItemSigilAugmentedHolding);
        }
    }

    private class SlotDisabled extends Slot
    {
        public SlotDisabled(IInventory inventory, int slotIndex, int x, int y)
        {
            super(inventory, slotIndex, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack)
        {
            return false;
        }

        @Override
        public boolean canTakeStack(EntityPlayer player)
        {
            return false;
        }
    }
}