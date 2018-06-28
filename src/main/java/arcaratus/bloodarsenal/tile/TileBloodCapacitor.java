package arcaratus.bloodarsenal.tile;

import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.energy.EnergyStorage;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileBloodCapacitor extends TileInventory implements ITickable
{
    private EnergyStorage energyStorage = new EnergyStorage(ConfigHandler.values.bloodCapacitorStorage);

    public int amountRecv = ConfigHandler.values.bloodCapacitorTransfer;
    public int amountSend = ConfigHandler.values.bloodCapacitorTransfer;

    public TileBloodCapacitor()
    {
        super(2, "blood_capacitor");
    }

    @Override
    public void update()
    {
        if (!world.isBlockPowered(pos))
            transferEnergy();
    }

    protected void transferEnergy()
    {
        for (int i = 0; i < 6 && energyStorage.getEnergyStored() > 0; i++)
        {
            energyStorage.modifyEnergyStored(-BloodArsenalUtils.insertEnergyIntoAdjacentEnergyReceiver(this, EnumFacing.VALUES[i], Math.min(amountSend, energyStorage.getEnergyStored()), false));
        }
    }

    public final void setEnergyStored(int quantity)
    {
        energyStorage.setEnergyStored(quantity);
    }

    @Override
    public void deserialize(NBTTagCompound nbt)
    {
        super.deserialize(nbt);
        energyStorage.readFromNBT(nbt);

        amountRecv = nbt.getInteger("Recv");
        amountSend = nbt.getInteger("Send");
    }

    @Override
    public NBTTagCompound serialize(NBTTagCompound nbt)
    {
        super.serialize(nbt);

        energyStorage.writeToNBT(nbt);
        nbt.setInteger("Recv", amountRecv);
        nbt.setInteger("Send", amountSend);
        return nbt;
    }

    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    public int getEnergyStored()
    {
        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergyStored()
    {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing from)
    {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, from);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, final EnumFacing from)
    {
        if (capability == CapabilityEnergy.ENERGY)
        {
            return CapabilityEnergy.ENERGY.cast(new IEnergyStorage()
            {
                @Override
                public int receiveEnergy(int maxReceive, boolean simulate)
                {
                    return TileBloodCapacitor.this.receiveEnergy(maxReceive, simulate);
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate)
                {
                    return 0;
                }

                @Override
                public int getEnergyStored()
                {
                    return TileBloodCapacitor.this.getEnergyStored();
                }

                @Override
                public int getMaxEnergyStored()
                {
                    return TileBloodCapacitor.this.getMaxEnergyStored();
                }

                @Override
                public boolean canExtract()
                {
                    return false;
                }

                @Override
                public boolean canReceive()
                {
                    return true;
                }
            });
        }
        return super.getCapability(capability, from);
    }
}
