package com.ishland.FlourChat.codec_netty.packet;

public class GenericPacket {

    public byte version = 0x00;
    public byte type;
    public byte packetId;
    public byte senderType;
    public short length;
    public byte[] content;
}
