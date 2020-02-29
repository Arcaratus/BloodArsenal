package arcaratus.bloodarsenal.compat.hwyla.provider;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class DataProviderStasisPlate implements IWailaDataProvider
{
    public static final IWailaDataProvider INSTANCE = new DataProviderStasisPlate();

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        if (!config.getConfig(Constants.Compat.WAILA_CONFIG_STASIS_PLATE))
            return currenttip;

        if (accessor.getNBTData().getBoolean("inStasis"))
            currenttip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.stasis"));

        return currenttip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        TileStasisPlate stasisPlate = (TileStasisPlate) te;
        tag.setBoolean("inStasis", stasisPlate.getStasis());

        return tag;
    }
}
