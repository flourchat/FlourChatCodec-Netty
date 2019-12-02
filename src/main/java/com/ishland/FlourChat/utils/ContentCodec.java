package com.ishland.FlourChat.utils;

import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Deprecated
public class ContentCodec {

    private static final Logger logger = LogManager
            .getLogger ("Packet Handler");

    public static ByteBuf arrayToBuf (byte[] in) {
        ByteBuf out = Unpooled.buffer ();
        out.writeBytes (in);
        return out;
    }

    @Nullable
    public static String decodeString (
            @NonNull ByteBuf in) {
        byte Identifier = in.readByte ();
        if (Identifier != 0x00) {
            ContentCodec.logger.warn (
                    "Invaild identifier of String: Expected 0x00 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 1);
            return null;
        }
        in.readByte ();
        short length = in.readShort ();
        String result = in.toString (in.readerIndex (),
                length, Charset.forName ("UTF-8"));
        in.readerIndex (in.readerIndex () + length);
        in.release ();
        return result;
    }

    public static byte[] encodeString (
            @NonNull String str) {
        ByteBuf out = Unpooled.buffer ();
        // Identifier
        out.writeByte (0x00);
        out.writeByte (0x00);
        // Length
        out.writeShort (str.length ());
        // Content
        out.writeBytes (
                str.getBytes (Charset.forName ("UTF-8")));
        out.resetReaderIndex ();
        byte[] result = out.array ();
        out.release ();
        return result;
    }

    @Nullable
    public static Short decodeInt16 (@NonNull ByteBuf in) {
        // Identifier
        byte Identifier = in.readByte ();
        if (Identifier != 0x01) {
            ContentCodec.logger.warn (
                    "Invaild identifier of Int16: Expected 0x01 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 1);
            return null;
        }
        byte AnotherIdentifier = in.readByte ();
        if (AnotherIdentifier != 0x00) {
            ContentCodec.logger.warn (
                    "Invaild identifier of Int16: Expected 0x00 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 2);
            return null;
        }
        return Short.valueOf (in.readShort ());
    }

    public static byte[] encodeInt16 (@NonNull Short num) {
        ByteBuf out = Unpooled.buffer ();
        // Identifier
        out.writeByte (0x01);
        out.writeByte (0x00);
        // Content
        out.writeShort (num.shortValue ());
        out.resetReaderIndex ();
        byte[] result = out.array ();
        out.release ();
        return result;
    }

    @Nullable
    public static Integer decodeInt32 (
            @NonNull ByteBuf in) {
        // Identifier
        byte Identifier = in.readByte ();
        if (Identifier != 0x01) {
            ContentCodec.logger.warn (
                    "Invaild identifier of Int32: Expected 0x01 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 1);
            return null;
        }
        byte AnotherIdentifier = in.readByte ();
        if (AnotherIdentifier != 0x01) {
            ContentCodec.logger.warn (
                    "Invaild identifier of Int32: Expected 0x01 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 2);
            return null;
        }
        return Integer.valueOf (in.readInt ());
    }

    public static byte[] encodeInt32 (
            @NonNull Integer num) {
        ByteBuf out = Unpooled.buffer ();
        // Identifier
        out.writeByte (0x01);
        out.writeByte (0x02);
        // Content
        out.writeInt (num.intValue ());
        out.resetReaderIndex ();
        byte[] result = out.array ();
        out.release ();
        return result;
    }

    public static Long decodeInt64 (@NonNull ByteBuf in) {
        // Identifier
        byte Identifier = in.readByte ();
        if (Identifier != 0x01) {
            ContentCodec.logger.warn (
                    "Invaild identifier of Int64: Expected 0x01 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 1);
            return null;
        }
        byte AnotherIdentifier = in.readByte ();
        if (AnotherIdentifier != 0x02) {
            ContentCodec.logger.warn (
                    "Invaild identifier of Int64: Expected 0x02 but got "
                            + String.valueOf (Identifier));
            in.readerIndex (in.readerIndex () - 2);
            return null;
        }

        return Long.valueOf (in.readLong ());
    }

    public static byte[] encodeInt64 (@NonNull Long num) {
        ByteBuf out = Unpooled.buffer ();
        // Identifier
        out.writeByte (0x01);
        out.writeByte (0x02);
        // Content
        out.writeLong (num.longValue ());
        out.resetReaderIndex ();
        byte[] result = out.array ();
        out.release ();
        return result;
    }
}
