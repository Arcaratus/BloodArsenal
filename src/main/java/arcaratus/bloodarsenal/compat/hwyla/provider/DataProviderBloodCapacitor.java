package arcaratus.bloodarsenal.compat.hwyla.provider;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileBloodCapacitor;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class DataProviderBloodCapacitor implements IWailaDataProvider
{
    public static final IWailaDataProvider INSTANCE = new DataProviderBloodCapacitor();

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        if (!config.getConfig(Constants.Compat.WAILA_CONFIG_BLOOD_CAPACITOR) && !config.getConfig("capability.energyinfo"))
            return currenttip;

        currenttip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.energy", accessor.getNBTData().getInteger("Energy")));

        return currenttip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        TileBloodCapacitor capacitor = (TileBloodCapacitor) te;
        tag.setInteger("Energy", capacitor.getEnergyStored());
        return tag;
    }
}
