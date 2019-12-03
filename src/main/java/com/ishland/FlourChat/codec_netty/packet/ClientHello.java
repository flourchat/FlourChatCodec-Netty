package com.ishland.FlourChat.codec_netty.packet;

public class ClientHello {

    public static byte PACKET_ID = 0x01;
    public static byte SENDER_TYPE = Type.CLIENT;

    public String message;

    public ClientHello (String message) {
        this.message = message;
    }

    @Override
    public String toString () {
        return "ClientHello{message=" + this.message + "}";
    }
}
