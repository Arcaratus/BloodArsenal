package arc.bloodarsenal.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileStasisPlate extends TileInventory
{
    private boolean inStasis = false;

    public TileStasisPlate()
    {
        super(1, "stasisPlate");
    }

    @Override
    public void deserialize(NBTTagCompound tag)
    {
        super.deserialize(tag);
        inStasis = tag.getBoolean("inStasis");
    }

    @Override
    public NBTTagCompound serialize(NBTTagCompound tag)
    {
        super.serialize(tag);
        tag.setBoolean("inStasis", inStasis);
        return tag;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean getStasis()
    {
        return inStasis;
    }

    public void setStasis(boolean inStasis)
    {
        this.inStasis = inStasis;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (getStasis())
            return null;

        return super.getCapability(capability, facing);
    }
}
