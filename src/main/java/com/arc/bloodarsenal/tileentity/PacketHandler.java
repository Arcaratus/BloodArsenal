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

        String tileCodec = clientChannel.findChannelHandlerNameForType(TilePortableAltarCodec.class);
        clientChannel.pipeline().addAfter(tileCodec, "TilePortableAltarHandler", new TilePortableAltarMessageHandler());
        clientChannel.pipeline().addAfter(tileCodec, "TileLifeInfuserHandler", new TileLifeInfuserMessageHandler());
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

    private static class TileLifeInfuserMessageHandler extends SimpleChannelInboundHandler<TileLifeInfuserMessage>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TileLifeInfuserMessage msg) throws Exception
        {
            World world = BloodArsenal.proxy.getClientWorld();
            TileEntity te = world.getTileEntity(msg.x, msg.y, msg.z);
            if (te instanceof TileLifeInfuser)
            {
                TileLifeInfuser tile = (TileLifeInfuser) te;

                tile.handlePacketData(msg.items, msg.fluids, msg.capacity);
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

    public static class TileLifeInfuserMessage extends BAMessage
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
            addDiscriminator(1, TileLifeInfuserMessage.class);
        }

        @Override
        public void encodeInto(ChannelHandlerContext ctx, BAMessage msg, ByteBuf target) throws Exception
        {
            target.writeInt(msg.index);

            switch (msg.index)
            {
                case 0:
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

                    break;

                case 1:
                    target.writeInt(((TileLifeInfuserMessage) msg).x);
                    target.writeInt(((TileLifeInfuserMessage) msg).y);
                    target.writeInt(((TileLifeInfuserMessage) msg).z);

                    target.writeBoolean(((TileLifeInfuserMessage) msg).items != null);
                    if (((TileLifeInfuserMessage) msg).items != null)
                    {
                        int[] items = ((TileLifeInfuserMessage) msg).items;
                        for (int j = 0; j < items.length; j++)
                        {
                            int i = items[j];
                            target.writeInt(i);
                        }
                    }

                    target.writeBoolean(((TileLifeInfuserMessage) msg).fluids != null);
                    if (((TileLifeInfuserMessage) msg).fluids != null)
                    {
                        int[] fluids = ((TileLifeInfuserMessage) msg).fluids;
                        for (int j = 0; j < fluids.length; j++)
                        {
                            int i = fluids[j];
                            target.writeInt(i);
                        }
                    }

                    target.writeInt(((TileLifeInfuserMessage) msg).capacity);

                    break;
            }
        }

        @Override
        public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, BAMessage msg)
        {
            int index = dat.readInt();

            switch (index)
            {
                case 0:
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

                    break;

                case 1:
                    ((TileLifeInfuserMessage) msg).x = dat.readInt();
                    ((TileLifeInfuserMessage) msg).y = dat.readInt();
                    ((TileLifeInfuserMessage) msg).z = dat.readInt();
                    boolean hasStacks2 = dat.readBoolean();

                    ((TileLifeInfuserMessage) msg).items = new int[TileLifeInfuser.sizeInv * 3];
                    if (hasStacks2)
                    {
                        ((TileLifeInfuserMessage) msg).items = new int[TileLifeInfuser.sizeInv * 3];
                        for (int i = 0; i < ((TileLifeInfuserMessage) msg).items.length; i++)
                        {
                            ((TileLifeInfuserMessage) msg).items[i] = dat.readInt();
                        }
                    }

                    boolean hasFluids2 = dat.readBoolean();
                    ((TileLifeInfuserMessage) msg).fluids = new int[6];
                    if (hasFluids2)
                    {
                        for (int i = 0; i < ((TileLifeInfuserMessage) msg).fluids.length; i++)
                        {
                            ((TileLifeInfuserMessage) msg).fluids[i] = dat.readInt();
                        }
                    }

                    ((TileLifeInfuserMessage) msg).capacity = dat.readInt();

                    break;
            }
        }
    }

    public static Packet getPacket(TilePortableAltar tile)
    {
        TilePortableAltarMessage msg = new TilePortableAltarMessage();
        msg.index = 0;
        msg.x = tile.xCoord;
        msg.y = tile.yCoord;
        msg.z = tile.zCoord;
        msg.items = tile.buildIntDataList();
        msg.fluids = tile.buildFluidList();
        msg.capacity = tile.getCapacity();

        return INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
    }

    public static Packet getPacket(TileLifeInfuser tile)
    {
        TileLifeInfuserMessage msg = new TileLifeInfuserMessage();
        msg.index = 1;
        msg.x = tile.xCoord;
        msg.y = tile.yCoord;
        msg.z = tile.zCoord;
        msg.items = tile.buildIntDataList();
        msg.fluids = tile.buildFluidList();
        msg.capacity = tile.getCapacity();

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
