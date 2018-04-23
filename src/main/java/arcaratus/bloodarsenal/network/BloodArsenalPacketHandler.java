package arcaratus.bloodarsenal.network;

import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BloodArsenalPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(BloodArsenal.MOD_ID);

    public static void init()
    {
        INSTANCE.registerMessage(SigilAugmentedHoldingPacketProcessor.class, SigilAugmentedHoldingPacketProcessor.class, 0, Side.SERVER);
        INSTANCE.registerMessage(ProfilablePacketProcessor.class, ProfilablePacketProcessor.class, 1, Side.SERVER);
    }

    public static void sendToAllAround(IMessage message, TileEntity te, int range)
    {
        INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(te.getWorld().provider.getDimension(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), range));
    }

    public static void sendToAllAround(IMessage message, TileEntity te)
    {
        sendToAllAround(message, te, 64);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player)
    {
        INSTANCE.sendTo(message, player);
    }
}
