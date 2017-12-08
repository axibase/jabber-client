package com.axibase.xmpp.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Errors {
    private static final Logger LOGGER = LogManager.getLogger(Errors.class.getName());

    public static void complain(String message, Throwable error) {
        if (error != null) {
            LOGGER.error("Error occurred", error);
            System.err.println(message + ": " + error.getMessage());
        } else {
            System.err.println(message);
        }
    }

    public static RuntimeException errorExit(String message) {
        return errorExit(message, null);
    }

    public static RuntimeException errorExit(String message, Throwable error) {
        complain(message, error);
        System.exit(1);
        return new RuntimeException();
    }
}
