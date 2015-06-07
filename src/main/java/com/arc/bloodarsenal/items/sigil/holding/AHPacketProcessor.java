package com.arc.bloodarsenal.items.sigil.holding;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class AHPacketProcessor implements IMessage, IMessageHandler<AHPacketProcessor, IMessage>
{
    private int slot;
    private int mode;

    public AHPacketProcessor() {}

    public AHPacketProcessor(int slot, int mode)
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
    public IMessage onMessage(AHPacketProcessor message, MessageContext ctx)
    {
        ItemStack itemStack = null;

        if (message.slot > -1 && message.slot < 9)
        {
            itemStack = ctx.getServerHandler().playerEntity.inventory.getStackInSlot(message.slot);
        }

        if (itemStack != null)
        {
            SigilAugmentedHolding.cycleSigil(itemStack, message.mode);
        }

        return null;
    }
}
