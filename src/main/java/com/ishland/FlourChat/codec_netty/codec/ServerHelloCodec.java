package com.ishland.FlourChat.codec_netty.codec;

import java.util.ArrayList;
import java.util.List;

import com.ishland.FlourChat.codec_netty.packet.GenericPacket;
import com.ishland.FlourChat.codec_netty.packet.ServerHello;
import com.ishland.FlourChat.utils.PacketContentCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class ServerHelloCodec extends
        MessageToMessageCodec<GenericPacket, ServerHello> {

    @Override
    protected void encode (ChannelHandlerContext ctx,
            ServerHello msg, List<Object> out)
            throws Exception {
        GenericPacket output = new GenericPacket ();
        output.type = ServerHello.PACKET_ID;
        output.senderType = ServerHello.SENDER_TYPE;
        ByteBuf buffer = Unpooled.buffer ();
        List<Object> list = new ArrayList<> ();
        list.add (msg.message);
        buffer.writeBytes (
                PacketContentCodec.encode (list));
        buffer.resetReaderIndex ();
        output.content = buffer.array ();
        buffer.release ();
        out.add (output);
    }

    @Override
    protected void decode (ChannelHandlerContext ctx,
            GenericPacket msg, List<Object> out)
            throws Exception {
        if (msg.type != ServerHello.PACKET_ID)
            return;
        List<String> list = PacketContentCodec
                .decode (msg.content);
        if (list.size () > 1)
            return;
        ServerHello output = new ServerHello (list.get (0));
        out.add (output);
    }

}
