package com.ishland.FlourChat.codec_netty.packet;

public class ServerHello {

    public static byte PACKET_ID = 0x01;
    public static byte SENDER_TYPE = Type.SERVER;

    public String message;

    public ServerHello (String message) {
        this.message = message;
    }
}
