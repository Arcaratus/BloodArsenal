package com.arc.bloodarsenal.tileentity;

import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.EnumMap;

public enum PacketHandler
{
    INSTANCE;

    private EnumMap<Side, FMLEmbeddedChannel> channels;

    private PacketHandler()
    {
        // request a channel pair for IronChest from the network registry
        // Add the IronChestCodec as a member of both channel pipelines
        this.channels = NetworkRegistry.INSTANCE.newChannel("BloodArsenal", new TilePortableAltarCodec());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            addClientHandler();
        }
    }

    @SideOnly(Side.CLIENT)
    private void addClientHandler()
    {
        FMLEmbeddedChannel clientChannel = this.channels.get(Side.CLIENT);

        String tileAltarCodec = clientChannel.findChannelHandlerNameForType(TilePortableAltarCodec.class);
        clientChannel.pipeline().addAfter(tileAltarCodec, "TlePortableAltarHandler", new TilePortableAltarMessageHandler());
    }

    private static class TilePortableAltarMessageHandler extends SimpleChannelInboundHandler<TilePortableAltarMessage>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TilePortableAltarMessage msg) throws Exception
        {
            World world = BloodArsenal.proxy.getClientWorld();
            TileEntity te = world.getTileEntity(msg.x, msg.y, msg.z);
            if (te instanceof TilePortableAltar)
            {
                TilePortableAltar altar = (TilePortableAltar) te;

                altar.handlePacketData(msg.items, msg.fluids, msg.capacity);
            }
        }
    }

    public static class BAMessage
    {
        int index;
    }

    public static class TilePortableAltarMessage extends BAMessage
    {
        int x;
        int y;
        int z;

        int[] items;
        int[] fluids;
        int capacity;
    }

    private class TilePortableAltarCodec extends FMLIndexedMessageToMessageCodec<BAMessage>
    {
        public TilePortableAltarCodec()
        {
            addDiscriminator(0, TilePortableAltarMessage.class);
        }

        @Override
        public void encodeInto(ChannelHandlerContext ctx, BAMessage msg, ByteBuf target) throws Exception
        {
            target.writeInt(msg.index);

            target.writeInt(((TilePortableAltarMessage) msg).x);
            target.writeInt(((TilePortableAltarMessage) msg).y);
            target.writeInt(((TilePortableAltarMessage) msg).z);

            target.writeBoolean(((TilePortableAltarMessage) msg).items != null);
            if (((TilePortableAltarMessage) msg).items != null)
            {
                int[] items = ((TilePortableAltarMessage) msg).items;
                for (int j = 0; j < items.length; j++)
                {
                    int i = items[j];
                    target.writeInt(i);
                }
            }

            target.writeBoolean(((TilePortableAltarMessage) msg).fluids != null);
            if (((TilePortableAltarMessage) msg).fluids != null)
            {
                int[] fluids = ((TilePortableAltarMessage) msg).fluids;
                for (int j = 0; j < fluids.length; j++)
                {
                    int i = fluids[j];
                    target.writeInt(i);
                }
            }

            target.writeInt(((TilePortableAltarMessage) msg).capacity);
        }

        @Override
        public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, BAMessage msg)
        {
            int index = dat.readInt();

            ((TilePortableAltarMessage) msg).x = dat.readInt();
            ((TilePortableAltarMessage) msg).y = dat.readInt();
            ((TilePortableAltarMessage) msg).z = dat.readInt();
            boolean hasStacks = dat.readBoolean();

            ((TilePortableAltarMessage) msg).items = new int[TilePortableAltar.sizeInv * 3];
            if (hasStacks)
            {
                ((TilePortableAltarMessage) msg).items = new int[TilePortableAltar.sizeInv * 3];
                for (int i = 0; i < ((TilePortableAltarMessage) msg).items.length; i++)
                {
                    ((TilePortableAltarMessage) msg).items[i] = dat.readInt();
                }
            }

            boolean hasFluids = dat.readBoolean();
            ((TilePortableAltarMessage) msg).fluids = new int[6];
            if (hasFluids)
            {
                for (int i = 0; i < ((TilePortableAltarMessage) msg).fluids.length; i++)
                {
                    ((TilePortableAltarMessage) msg).fluids[i] = dat.readInt();
                }
            }

            ((TilePortableAltarMessage) msg).capacity = dat.readInt();
        }
    }

    public static Packet getPacket(TilePortableAltar tileAltar)
    {
        TilePortableAltarMessage msg = new TilePortableAltarMessage();
        msg.index = 0;
        msg.x = tileAltar.xCoord;
        msg.y = tileAltar.yCoord;
        msg.z = tileAltar.zCoord;
        msg.items = tileAltar.buildIntDataList();
        msg.fluids = tileAltar.buildFluidList();
        msg.capacity = tileAltar.getCapacity();

        return INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
    }

    public void sendTo(Packet message, EntityPlayerMP player)
    {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToAll(Packet message)
    {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToAllAround(Packet message, NetworkRegistry.TargetPoint point)
    {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }
}
