package com.arc.bloodarsenal.common.tileentity;

import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
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

    PacketHandler()
    {
        this.channels = NetworkRegistry.INSTANCE.newChannel("BloodArsenal", new BATileCodec());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            addClientHandler();
        }
        if (FMLCommonHandler.instance().getSide() == Side.SERVER)
        {
            System.out.println("Server sided~");
            addServerHandler();
        }
    }

    @SideOnly(Side.CLIENT)
    private void addClientHandler()
    {
        FMLEmbeddedChannel clientChannel = this.channels.get(Side.CLIENT);

        String tileCodec = clientChannel.findChannelHandlerNameForType(BATileCodec.class);
        clientChannel.pipeline().addAfter(tileCodec, "TilePortableAltarHandler", new TilePortableAltarMessageHandler());
        clientChannel.pipeline().addAfter(tileCodec, "TileLifeInfuserHandler", new TileLifeInfuserMessageHandler());
        clientChannel.pipeline().addAfter(tileCodec, "TileLPMaterializer", new TileLPMaterializerMessageHandler());
    }

    @SideOnly(Side.SERVER)
    private void addServerHandler()
    {
        FMLEmbeddedChannel serverChannel = this.channels.get(Side.SERVER);

        String messageCodec = serverChannel.findChannelHandlerNameForType(BATileCodec.class);
        serverChannel.pipeline().addAfter(messageCodec, "KeyboardMessageHandler", new KeyboardMessageHandler());
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

    private static class TileLPMaterializerMessageHandler extends SimpleChannelInboundHandler<TileLPMaterializerMessage>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TileLPMaterializerMessage msg) throws Exception
        {
            World world = BloodArsenal.proxy.getClientWorld();
            TileEntity te = world.getTileEntity(msg.x, msg.y, msg.z);

            if (te instanceof TileLPMaterializer)
            {
                TileLPMaterializer tile = (TileLPMaterializer) te;

                tile.handlePacketData(msg.items, msg.fluids, msg.capacity);
            }
        }
    }

    private static class KeyboardMessageHandler extends SimpleChannelInboundHandler<KeyboardMessage>
    {
        public KeyboardMessageHandler()
        {
            System.out.println("I am being created");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, KeyboardMessage msg) throws Exception
        {
            System.out.println("Hmmm");

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

    public static class TileLPMaterializerMessage extends BAMessage
    {
        int x;
        int y;
        int z;

        int[] items;
        int[] fluids;
        int capacity;
    }

    public static class KeyboardMessage extends BAMessage
    {
        byte keyPressed;
    }

    private class ClientToServerCodec extends FMLIndexedMessageToMessageCodec<BAMessage>
    {
        public ClientToServerCodec()
        {
        }

        @Override
        public void encodeInto(ChannelHandlerContext ctx, BAMessage msg, ByteBuf target) throws Exception
        {
            target.writeInt(msg.index);
        }

        @Override
        public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, BAMessage msg)
        {
//            int index = source.readInt();

            System.out.println("Packet is recieved and being decoded");
        }
    }

    private class BATileCodec extends FMLIndexedMessageToMessageCodec<BAMessage>
    {
        public BATileCodec()
        {
            addDiscriminator(0, TilePortableAltarMessage.class);
            addDiscriminator(1, TileLifeInfuserMessage.class);
            addDiscriminator(2, TileLPMaterializerMessage.class);
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

                case 2:
                    target.writeInt(((TileLPMaterializerMessage) msg).x);
                    target.writeInt(((TileLPMaterializerMessage) msg).y);
                    target.writeInt(((TileLPMaterializerMessage) msg).z);

                    target.writeBoolean(((TileLPMaterializerMessage) msg).items != null);

                    if (((TileLPMaterializerMessage) msg).items != null)
                    {
                        int[] items = ((TileLPMaterializerMessage) msg).items;

                        for (int j = 0; j < items.length; j++)
                        {
                            int i = items[j];
                            target.writeInt(i);
                        }
                    }

                    target.writeBoolean(((TileLPMaterializerMessage) msg).fluids != null);

                    if (((TileLPMaterializerMessage) msg).fluids != null)
                    {
                        int[] fluids = ((TileLPMaterializerMessage) msg).fluids;

                        for (int j = 0; j < fluids.length; j++)
                        {
                            int i = fluids[j];
                            target.writeInt(i);
                        }
                    }

                    target.writeInt(((TileLPMaterializerMessage) msg).capacity);

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
                    ((TileLifeInfuserMessage) msg).fluids = new int[4];

                    if (hasFluids2)
                    {
                        for (int i = 0; i < ((TileLifeInfuserMessage) msg).fluids.length; i++)
                        {
                            ((TileLifeInfuserMessage) msg).fluids[i] = dat.readInt();
                        }
                    }

                    ((TileLifeInfuserMessage) msg).capacity = dat.readInt();

                    break;

                case 2:
                    ((TileLPMaterializerMessage) msg).x = dat.readInt();
                    ((TileLPMaterializerMessage) msg).y = dat.readInt();
                    ((TileLPMaterializerMessage) msg).z = dat.readInt();
                    boolean hasStacks3 = dat.readBoolean();

                    ((TileLPMaterializerMessage) msg).items = new int[TileLifeInfuser.sizeInv * 3];

                    if (hasStacks3)
                    {
                        ((TileLPMaterializerMessage) msg).items = new int[TileLifeInfuser.sizeInv * 3];

                        for (int i = 0; i < ((TileLPMaterializerMessage) msg).items.length; i++)
                        {
                            ((TileLPMaterializerMessage) msg).items[i] = dat.readInt();
                        }
                    }

                    boolean hasFluids3 = dat.readBoolean();
                    ((TileLPMaterializerMessage) msg).fluids = new int[4];

                    if (hasFluids3)
                    {
                        for (int i = 0; i < ((TileLPMaterializerMessage) msg).fluids.length; i++)
                        {
                            ((TileLPMaterializerMessage) msg).fluids[i] = dat.readInt();
                        }
                    }

                    ((TileLPMaterializerMessage) msg).capacity = dat.readInt();

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

    public static Packet getPacket(TileLPMaterializer tile)
    {
        TileLPMaterializerMessage msg = new TileLPMaterializerMessage();
        msg.index = 2;
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

    public void sendToServer(Packet message)
    {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
