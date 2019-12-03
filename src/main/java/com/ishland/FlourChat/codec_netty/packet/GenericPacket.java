package com.ishland.FlourChat.codec_netty.packet;

public class GenericPacket {

    public byte version = 0x00;
    public byte type;
    public byte packetId;
    public byte senderType;
    public short length;
    public byte[] content;

    @Override
    public String toString () {
        return "GenericPacket{" + "version=" + this.version
                + ",type=" + this.type + ",packetId="
                + this.packetId + ",senderType="
                + this.senderType + ",length=" + this.length
                + ",content=" + this.content + "}";
    }
}
