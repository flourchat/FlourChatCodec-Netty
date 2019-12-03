package com.ishland.FlourChat.utils;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageCodec;

public class Register {

    private static final Logger logger = LogManager
            .getLogger ("FlourChat codec register");

    @SuppressWarnings ({
            "rawtypes", "deprecation"
    })
    public static void register (ChannelPipeline ch) {
        Register.logger.debug ("Registering codecs...");
        Reflections reflection = new Reflections ("");
        Set<Class<? extends MessageToMessageCodec>> codecs = reflection
                .getSubTypesOf (
                        MessageToMessageCodec.class);
        for (Class<? extends MessageToMessageCodec> codec : codecs)
            try {
                ch.addLast (codec.getName (),
                        codec.newInstance ());
                Register.logger.debug ("Registered "
                        + codec.getName () + "...");
            } catch (Exception e) {
                Register.logger.warn ("Could not register "
                        + codec.getName ()
                        + ", packet processor is imcomplete now",
                        e);
            }
    }
}
