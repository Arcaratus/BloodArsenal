package com.arc.bloodarsenal.tileentity;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import org.lwjgl.Sys;

public class TileLPMaterializer extends TileEntity implements IInventory, IFluidTank, IFluidHandler
{
    private ItemStack[] inv;

    public boolean isActive;

    protected FluidStack fluid;
    public int capacity;
    private boolean canBeFilled;
    protected FluidStack fluidOutput;

    private int drainRate;
    private int bufferCapacity;

    public TileLPMaterializer()
    {
        this.inv = new ItemStack[1];
        fluid = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        fluidOutput = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        capacity = FluidContainerRegistry.BUCKET_VOLUME * 20;

        bufferCapacity = FluidContainerRegistry.BUCKET_VOLUME;
        drainRate = 0;
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

            FluidStack fluidOut = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, par1NBTTagCompound.getInteger("outputAmount"));

            if (fluidOut != null)
            {
                setOutputFluid(fluidOut);
            }
        }

        isActive = par1NBTTagCompound.getBoolean("isActive");
        canBeFilled = par1NBTTagCompound.getBoolean("canBeFilled");
        capacity = par1NBTTagCompound.getInteger("capacity");

        bufferCapacity = par1NBTTagCompound.getInteger("bufferCapacity");
        drainRate = par1NBTTagCompound.getInteger("drainRate");
        readCustomNBT(par1NBTTagCompound);
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

        if (fluid != null)
        {
            fluid.writeToNBT(par1NBTTagCompound);
        }
        else
        {
            par1NBTTagCompound.setString("Empty", "");
        }

        if (fluidOutput != null)
        {
            par1NBTTagCompound.setInteger("outputAmount", fluidOutput.amount);
        }

        par1NBTTagCompound.setTag("Inventory", itemList);
        par1NBTTagCompound.setBoolean("isActive", isActive);
        par1NBTTagCompound.setBoolean("canBeFilled", canBeFilled);
        par1NBTTagCompound.setInteger("capacity", capacity);

        par1NBTTagCompound.setInteger("drainRate", drainRate);
        par1NBTTagCompound.setInteger("bufferCapacity", bufferCapacity);
        writeCustomNBT(par1NBTTagCompound);
    }

    public void readCustomNBT(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

        if (nbttaglist.tagCount() > 0)
        {
            NBTTagCompound tagList = nbttaglist.getCompoundTagAt(0);
            inv[0] = ItemStack.loadItemStackFromNBT(tagList);
        }
    }

    public void writeCustomNBT(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagList nbttaglist = new NBTTagList();
        NBTTagCompound tagList = new NBTTagCompound();

        if (inv[0] != null)
        {
            tagList.setByte("Slot", (byte) 0);
            inv[0].writeToNBT(tagList);
            nbttaglist.appendTag(tagList);
        }
        par1NBTTagCompound.setTag("Inventory", nbttaglist);
    }

    public int fillMainTank(int amount) //TODO
    {
        int filledAmount = Math.min(capacity - fluid.amount, amount);
        fluid.amount += filledAmount;

        return filledAmount;
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
        return "TileLPMaterializer";
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

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        return slot == 0;
    }

    public void sendChatInfoToPlayer(EntityPlayer player)
    {
        player.addChatMessage(new ChatComponentText("Contains: " + this.getFluidAmount() + "LP"));
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

        FluidStack flMain = new FluidStack(fluidData[0], fluidData[1]);
        FluidStack flIn = new FluidStack(fluidData[2], fluidData[3]);

        this.setMainFluid(flMain);
        this.setOutputFluid(flIn);

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

    //Actual Stuffs
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        materialize();

        if (worldObj.getWorldTime() % 50 == 0)
        {
            int syphonMax = 500;
            int fluidOutputted;
            fluidOutputted = Math.min(syphonMax, this.bufferCapacity - this.fluidOutput.amount);
            fluidOutputted = Math.min(this.fluid.amount, fluidOutputted);
            this.fluidOutput.amount += fluidOutputted;
            this.fluid.amount -= fluidOutputted;
        }

        if (worldObj != null)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void materialize()
    {
        ItemStack orb = this.getStackInSlot(0);

        if (orb == null)
        {
            return;
        }

        if (fluid.amount <= capacity - 100)
        {
            if (!SoulNetworkHandler.canSyphonFromOnlyNetwork(orb, 150))
            {
                SoulNetworkHandler.causeNauseaToPlayer(orb);
                return;
            }

            if (!SoulNetworkHandler.syphonFromNetworkWhileInContainer(orb, 150))
            {
                return;
            }

            if (worldObj.getWorldTime() % 4 == 0)
            {
                SpellHelper.sendIndexedParticleToAllAround(worldObj, xCoord, yCoord, zCoord, 20, worldObj.provider.dimensionId, 1, xCoord, yCoord, zCoord);
            }

            fluid.amount += 100;
        }
    }

    private IFluidHandler[] checkForTanks(TileLPMaterializer tile)
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (ForgeDirection forgeDirection : directions)
        {
            IFluidHandler[] fluidHandlers;
            int x = tile.xCoord;
            int y = tile.yCoord;
            int z = tile.zCoord;

            switch (forgeDirection)
            {
                case NORTH:
                    worldObj.getTileEntity(x, y, z);
                    break;
            }
        }

        return null;
    }

    //Fluid Stuffs
    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        return 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null)
        {
            return null;
        }

        if (!resource.isFluidEqual(fluidOutput))
        {
            return null;
        }

        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if (fluidOutput == null)
        {
            return null;
        }

        int drained = maxDrain;

        if (fluidOutput.amount < drained)
        {
            drained = fluidOutput.amount;
        }

        FluidStack stack = new FluidStack(fluidOutput, drained);

        if (doDrain)
        {
            fluidOutput.amount -= drained;

            if (fluidOutput.amount <= 0)
            {
                fluidOutput = null;
            }

            if (this != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluidOutput, this.worldObj, this.xCoord, this.yCoord, this.zCoord, this));
            }
        }

        if (fluidOutput == null)
        {
            fluidOutput = new FluidStack(AlchemicalWizardry.lifeEssenceFluid, 0);
        }

        if (worldObj != null)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        return stack;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain)
    {
        return this.drain(maxEmpty, doDrain);
    }
    
    public void setMainFluid(FluidStack fluid)
    {
        this.fluid = fluid;
    }

    public void setOutputFluid(FluidStack fluid)
    {
        this.fluidOutput = fluid;
    }

    @Override
    public FluidStack getFluid()
    {
        return fluid;
    }

    @Override
    public int getFluidAmount()
    {
        if (fluid == null)
        {
            return 0;
        }

        return fluid.amount;
    }

    @Override
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

    public int getBufferCapacity()
    {
        return bufferCapacity;
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
            sortList[0] = this.fluid.fluidID;
            sortList[1] = this.fluid.amount;
        }

        if (this.fluidOutput == null)
        {
            sortList[2] = AlchemicalWizardry.lifeEssenceFluid.getID();
            sortList[3] = 0;
        }
        else
        {
            sortList[2] = this.fluidOutput.fluidID;
            sortList[3] = this.fluidOutput.amount;
        }

        return sortList;
    }
}
