package com.arc.bloodarsenal.common.tileentity;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import com.arc.bloodarsenal.common.items.tool.IFillable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileLifeInfuser extends TileEntity implements IInventory, IFluidTank, IFluidHandler
{
    private ItemStack[] inv;
    public static final int sizeInv = 1;
    public int ticksExisted = 0;
    public static int damageLastTick = 0;

    public boolean tookLastTick = true;

    protected FluidStack fluid;
    public int capacity;
    private boolean canBeFilled;
    protected FluidStack fluidInput;

    private int bufferCapacity;

    public FluidTank[] tanks = {new FluidTankRestricted(FluidContainerRegistry.BUCKET_VOLUME * 20, "Life Essence")};

    public TileLifeInfuser()
    {
        this.inv = new ItemStack[1];
        fluid = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        fluidInput = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        this.capacity = FluidContainerRegistry.BUCKET_VOLUME * 20;

        bufferCapacity = FluidContainerRegistry.BUCKET_VOLUME;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList tagList = par1NBTTagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            int slot = tag.getByte("Slot");

            if (slot >= 0 && slot < inv.length)
            {
                inv[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        if (!par1NBTTagCompound.hasKey("Empty"))
        {
            FluidStack fluid = this.fluid.loadFluidStackFromNBT(par1NBTTagCompound);

            if (fluid != null)
            {
                setMainFluid(fluid);
            }

            FluidStack fluidIn = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, par1NBTTagCompound.getInteger("inputAmount"));

            if (fluidIn != null)
            {
                setInputFluid(fluidIn);
            }
        }

        canBeFilled = par1NBTTagCompound.getBoolean("canBeFilled");
        capacity = par1NBTTagCompound.getInteger("capacity");
        bufferCapacity = par1NBTTagCompound.getInteger("bufferCapacity");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < inv.length; i++)
        {
            if (inv[i] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                inv[i].writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        par1NBTTagCompound.setTag("Inventory", itemList);

        if (fluid != null)
        {
            fluid.writeToNBT(par1NBTTagCompound);
        }
        else
        {
            par1NBTTagCompound.setString("Empty", "");
        }

        if (fluidInput != null)
        {
            par1NBTTagCompound.setInteger("inputAmount", fluidInput.amount);
        }

        par1NBTTagCompound.setTag("Inventory", itemList);
        par1NBTTagCompound.setBoolean("canBeFilled", canBeFilled);
        par1NBTTagCompound.setInteger("capacity", capacity);
        par1NBTTagCompound.setInteger("bufferCapacity", bufferCapacity);
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt)
    {
        ItemStack stack = getStackInSlot(slot);

        if (stack != null)
        {
            if (stack.stackSize <= amt)
            {
                setInventorySlotContents(slot, null);
            }
            else
            {
                stack = stack.splitStack(amt);

                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(slot, null);
                }
            }
        }

        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);

        if (stack != null)
        {
            setInventorySlotContents(slot, null);
        }

        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        inv[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return "TileLifeInfuser";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    //Logic for the actual block is under here
    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.getWorldTime() % 10 == 0) {
            int intakeMax = 500;
            int fluidInputted;
            fluidInputted = Math.min(intakeMax, capacity - this.fluid.amount);
            fluidInputted = Math.min(this.fluidInput.amount, fluidInputted);
            this.fluid.amount += fluidInputted;
            this.fluidInput.amount -= fluidInputted;
        }

        if (++ticksExisted % 10 == 0) {
            ItemStack inputStack = inv[0];
            if (inputStack != null) {
                if (inputStack.getItem() instanceof IFillable) {
                    IFillable fillable = (IFillable) inputStack.getItem();
                    int fillAmount = 200;

                    if (fillable.getLPStored(inputStack) < fillable.getMaxLP() && !(fillable.getLPStored(inputStack) + fillAmount > fillable.getMaxLP()) && getFluidAmount() >= fillAmount) {
                        fluid.amount = fluid.amount - fillAmount;
                        fillable.incrementLPStored(inputStack, fillAmount);
                        SpellHelper.sendIndexedParticleToAllAround(worldObj, xCoord, yCoord, zCoord, 20, worldObj.provider.dimensionId, 1, xCoord, yCoord, zCoord);
                    }
                } else if (inputStack.getItemDamage() > 0) {
                    int lpCost = 300;
                    int damage = inputStack.getItemDamage();

                    if (getFluidAmount() >= lpCost) {
                        fluid.amount = fluid.amount - lpCost;
                        inputStack.setItemDamage(Math.max(0, damage - 1));
                    } else {
                        return;
                    }

                    processInput();
                    markDirty();

                    if (damageLastTick != 0 && damageLastTick != damage) {
                        //Insert fancy particles here
                        SpellHelper.sendIndexedParticleToAllAround(worldObj, xCoord, yCoord, zCoord, 20, worldObj.provider.dimensionId, 1, xCoord, yCoord, zCoord);
                        tookLastTick = true;
                    } else {
                        tookLastTick = false;
                    }
                }
            } else {
                tookLastTick = false;
            }

            damageLastTick = inputStack == null ? 0 : inputStack.getItemDamage();
        }

        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        return slot == 0;
    }

    public void sendChatInfoToPlayer(EntityPlayer player)
    {
        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.tile.contains") + " " + this.getFluidAmount() + "LE"));
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.getPacket(this);
    }

    public void handlePacketData(int[] intData, int[] fluidData, int capacity)
    {
        if (intData == null)
        {
            return;
        }

        if (intData.length == 3)
        {
            for (int i = 0; i < 1; i++)
            {
                if (intData[i * 3 + 2] != 0)
                {
                    ItemStack is = new ItemStack(Item.getItemById(intData[i * 3]), intData[i * 3 + 2], intData[i * 3 + 1]);
                    inv[i] = is;
                }
                else
                {
                    inv[i] = null;
                }
            }
        }

        FluidStack flMain = new FluidStack(fluid, fluidData[1]);
        FluidStack flIn = new FluidStack(fluidInput, fluidData[3]);

        this.setMainFluid(flMain);
        this.setInputFluid(flIn);

        this.capacity = capacity;
    }

    public int[] buildIntDataList()
    {
        int[] sortList = new int[3];//3 ints per stack (1 * 3)
        int pos;

        for (ItemStack is : inv)
        {
            pos = 0;

            if (is != null)
            {
                sortList[pos++] = Item.getIdFromItem(is.getItem());
                sortList[pos++] = is.getItemDamage();
                sortList[pos++] = is.stackSize;
            }
            else
            {
                sortList[pos++] = 0;
                sortList[pos++] = 0;
                sortList[pos++] = 0;
            }
        }

        return sortList;
    }

    //Fluid Stuffs
    public FluidTank[] getTanks()
    {
        return this.tanks;
    }

    public int fillMainTank(int amount) //TODO STUFFS
    {
        int filledAmount = Math.min(capacity - fluid.amount, amount);
        fluid.amount += filledAmount;

        return filledAmount;
    }

    public boolean processInput()
    {
        for (int i = 0; i < getTanks().length; i++)
        {
            int c = fluidAmount();

            if ((c > 0) && (getTanks()[i].getFluidAmount() >= fluidAmount()))
            {
                getTanks()[i].drain(fluidAmount(), true);
                return true;
            }
        }
        return false;
    }

    public int fluidAmount()
    {
        return fluid.amount;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        TileEntity tile = this;

        if (resource == null)
        {
            return 0;
        }

        if (resource.getFluid().getID() != (fluidInput).getFluid().getID())
        {
            return 0;
        }

        if (!doFill)
        {
            if (fluidInput == null)
            {
                return Math.min(bufferCapacity, resource.amount);
            }

            if (!fluidInput.isFluidEqual(resource))
            {
                return 0;
            }

            return Math.min(bufferCapacity - fluidInput.amount, resource.amount);
        }

        if (fluidInput == null)
        {
            fluidInput = new FluidStack(resource, resource.amount);

            if (tile != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluidInput, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this, fluidInput.amount));
            }

            return fluidInput.amount;
        }

        if (!fluidInput.isFluidEqual(resource))
        {
            return 0;
        }

        int filled = bufferCapacity - fluidInput.amount;

        if (resource.amount < filled)
        {
            fluidInput.amount += resource.amount;
            filled = resource.amount;
        }
        else
        {
            fluidInput.amount = bufferCapacity;
        }

        if (tile != null)
        {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluidInput, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this, fluidInput.amount));
        }

        return filled;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        //TODO
        if (resource == null)
        {
            return 0;
        }

        resource = resource.copy();
        int totalUsed = 0;
        int used = this.fill(resource, doFill);
        resource.amount -= used;
        totalUsed += used;
        return totalUsed;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain)
    {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return this.fluidInput != null && this.fluid.getFluid().equals(fluidInput.getFluid());
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    public void setMainFluid(FluidStack fluid)
    {
        this.fluid = fluid;
    }

    public void setInputFluid(FluidStack fluid)
    {
        this.fluidInput = fluid;
    }

    public FluidStack getFluid()
    {
        return fluid;
    }

    public int getFluidAmount()
    {
        if (fluid == null)
        {
            return 0;
        }

        return fluid.amount;
    }

    public int getCapacity()
    {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo()
    {
        return new FluidTankInfo(this);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTank compositeTank = new FluidTank(capacity);
        compositeTank.setFluid(fluid);
        return new FluidTankInfo[]{compositeTank.getInfo()};
    }

    public int[] buildFluidList()
    {
        int[] sortList = new int[4];

        if (this.fluid == null)
        {
            sortList[0] = AlchemicalWizardry.lifeEssenceFluid.getID();
            sortList[1] = 0;
        }
        else
        {
            sortList[0] = this.fluid.getFluid().getID();
            sortList[1] = this.fluid.amount;
        }

        if (this.fluidInput == null)
        {
            sortList[2] = AlchemicalWizardry.lifeEssenceFluid.getID();
            sortList[3] = 0;
        }
        else
        {
            sortList[2] = this.fluidInput.getFluid().getID();
            sortList[3] = this.fluidInput.amount;
        }

        return sortList;
    }
}
