package arc.bloodarsenal.network;

import arc.bloodarsenal.item.IProfilable;
import arc.bloodarsenal.util.BloodArsenalUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class ProfilablePacketProcessor implements IMessage, IMessageHandler<ProfilablePacketProcessor, IMessage>
{
    private int slot;
    private int mode;

    public ProfilablePacketProcessor()
    {}

    public ProfilablePacketProcessor(int slot, int mode)
    {
        this.slot = slot;
        this.mode = mode;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(slot);
        buffer.writeInt(mode);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        slot = buffer.readInt();
        mode = buffer.readInt();
    }

    @Override
    public IMessage onMessage(ProfilablePacketProcessor message, MessageContext ctx)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        IProfilable profilable = BloodArsenalUtils.Profilable.getProfilable(itemStack);
        if (profilable != null)
        {
            if (message.slot > -1 && message.slot < profilable.getMaxProfiles(itemStack))
                itemStack = ctx.getServerHandler().player.inventory.getStackInSlot(message.slot);

            if (!itemStack.isEmpty())
                BloodArsenalUtils.Profilable.setProfileIndex(itemStack, message.mode);
        }

        return null;
    }
}
