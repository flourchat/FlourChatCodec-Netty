package com.ishland.FlourChat.codec_netty.codec;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ishland.FlourChat.codec_netty.packet.GenericPacket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class GenericPacketCodec extends
        MessageToMessageCodec<BinaryWebSocketFrame, GenericPacket> {

    private static final Logger logger = LogManager
            .getLogger ();

    @Override
    protected void encode (ChannelHandlerContext ctx,
            GenericPacket msg, List<Object> out)
            throws Exception {
        ByteBuf buffer = Unpooled.buffer ();
        buffer.writeByte (msg.version);
        buffer.writeByte (msg.type);
        buffer.writeByte (msg.packetId);
        buffer.writeByte (msg.senderType);
        buffer.writeShort (msg.length);
        buffer.writeZero (2);
        buffer.writeBytes (msg.content);
        if (GenericPacketCodec.logger.isTraceEnabled ())
            GenericPacketCodec.logger.trace ("Encoded "
                    + msg + " into " + buffer.array ());
        out.add (new BinaryWebSocketFrame (buffer));
    }

    @Override
    protected void decode (ChannelHandlerContext ctx,
            BinaryWebSocketFrame msg, List<Object> out)
            throws Exception {
        msg = msg.retain ();
        GenericPacket packet = new GenericPacket ();
        ByteBuf buffer = msg.content ().retain ();
        if (buffer.readByte () != packet.version) {
            // TODO invaild version

            buffer.resetReaderIndex ();
            buffer.release ();
            return;
        }
        packet.type = buffer.readByte ();
        packet.packetId = buffer.readByte ();
        packet.senderType = buffer.readByte ();
        packet.length = buffer.readShort ();
        buffer.readShort ();
        packet.content = buffer.copy ().array ();
        buffer.release ();
        msg.release ();
        if (GenericPacketCodec.logger.isTraceEnabled ())
            GenericPacketCodec.logger.trace ("Decoded "
                    + buffer.array () + " into " + packet);
        out.add (packet);
    }

}
