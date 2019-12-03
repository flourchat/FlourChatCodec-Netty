package com.ishland.FlourChat.codec_netty.codec;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ishland.FlourChat.codec_netty.packet.ClientHello;
import com.ishland.FlourChat.codec_netty.packet.GenericPacket;
import com.ishland.FlourChat.utils.PacketContentCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class ClientHelloCodec extends
        MessageToMessageCodec<GenericPacket, ClientHello> {

    private static final Logger logger = LogManager
            .getLogger ();

    @Override
    protected void encode (ChannelHandlerContext ctx,
            ClientHello msg, List<Object> out)
            throws Exception {
        GenericPacket output = new GenericPacket ();
        output.type = ClientHello.PACKET_ID;
        output.senderType = ClientHello.SENDER_TYPE;
        ByteBuf buffer = Unpooled.buffer ();
        List<Object> list = new ArrayList<> ();
        list.add (msg.message);
        buffer.writeBytes (
                PacketContentCodec.encode (list));
        buffer.resetReaderIndex ();
        output.content = buffer.array ();
        buffer.release ();
        if (ClientHelloCodec.logger.isTraceEnabled ())
            ClientHelloCodec.logger.trace (
                    "Encoded " + msg + " into " + output);
        out.add (output);
    }

    @Override
    protected void decode (ChannelHandlerContext ctx,
            GenericPacket msg, List<Object> out)
            throws Exception {
        if (msg.type != ClientHello.PACKET_ID)
            return;
        List<String> list = PacketContentCodec
                .decode (msg.content);
        if (list.size () > 1)
            return;
        ClientHello output = new ClientHello (list.get (0));
        if (ClientHelloCodec.logger.isTraceEnabled ())
            ClientHelloCodec.logger.trace (
                    "Decoded " + msg + " into " + output);
        out.add (output);
    }

}
