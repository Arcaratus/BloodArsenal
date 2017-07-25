package arc.bloodarsenal.network;

import arc.bloodarsenal.item.sigil.ItemSigilAugmentedHolding;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class SigilAugmentedHoldingPacketProcessor implements IMessage, IMessageHandler<SigilAugmentedHoldingPacketProcessor, IMessage>
{
    private int slot;
    private int mode;

    public SigilAugmentedHoldingPacketProcessor()
    {
    }

    public SigilAugmentedHoldingPacketProcessor(int slot, int mode)
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
    public IMessage onMessage(SigilAugmentedHoldingPacketProcessor message, MessageContext ctx)
    {
        ItemStack itemStack = null;

        if (message.slot > -1 && message.slot < ItemSigilAugmentedHolding.INVENTORY_SIZE)
            itemStack = ctx.getServerHandler().playerEntity.inventory.getStackInSlot(message.slot);

        if (itemStack != null)
            ItemSigilAugmentedHolding.cycleToNextSigil(itemStack, message.mode);

        return null;
    }
}