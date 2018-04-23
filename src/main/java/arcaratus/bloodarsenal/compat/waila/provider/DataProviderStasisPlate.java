package arcaratus.bloodarsenal.compat.waila.provider;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.block.BlockStasisPlate;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class DataProviderStasisPlate implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        if (accessor.getBlock() instanceof BlockStasisPlate && accessor.getTileEntity() instanceof TileStasisPlate)
        {
            NBTTagCompound tag = accessor.getNBTData();
            boolean stasis = tag.getBoolean("inStasis");
            currenttip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.stasis", stasis));
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        if (te != null)
            te.writeToNBT(tag);
        return tag;
    }
}
