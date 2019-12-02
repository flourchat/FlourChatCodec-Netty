package com.ishland.FlourChat.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketContentCodec {

    private static final Logger logger = LogManager
            .getLogger ("Packet Content Codec");

    public static byte[] encode (List<Object> list) {
        if (PacketContentCodec.logger.isDebugEnabled ())
            PacketContentCodec.logger
                    .debug ("Encoding " + list);
        ByteBuf buffer = Unpooled.buffer ();
        for (Object obj : list) {
            buffer.writeBytes (obj.toString ()
                    .getBytes (Charset.forName ("UTF-8")));
            buffer.writeBytes ("<"
                    .getBytes (Charset.forName ("UTF-8")));
        }
        byte[] result = buffer.array ();
        buffer.release ();
        return result;
    }

    public static List<String> decode (byte[] in) {
        String str = new String (in,
                Charset.forName ("UTF-8"));
        String[] outa = str.split ("<");
        List<String> list = new ArrayList<> ();
        for (String s : outa)
            list.add (s);
        return list;
    }
}
