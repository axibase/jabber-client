package com.axibase.xmpp.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Debug {
    private static final Logger LOGGER = LogManager.getLogger(Debug.class.getName());

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void message(String message) {
        info(message);
        System.out.println(message);
    }

    public static void complain(String message, Throwable error) {
        if (error != null) {
            LOGGER.error(message, error);
            System.err.println(message + ": " + error.getMessage());
        } else {
            System.err.println(message);
        }
    }
}
