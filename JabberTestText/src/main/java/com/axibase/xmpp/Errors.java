package com.axibase.xmpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Errors {
    private static final Logger LOGGER = LogManager.getLogger(TestText.class.getName());

    public static void report(Throwable error) {
        LOGGER.error("Error occurred", error);
    }

    public static void complain(String message, Throwable error) {
        if (error != null) {
            LOGGER.error("Error occurred", error);
            System.err.println(message + ": " + error.getMessage());
        } else {
            System.err.println(message);
        }
    }

    public static void errorExit(String message) {
        errorExit(message, null);
    }

    public static void errorExit(String message, Throwable error) {
        complain(message, error);
        System.exit(1);
    }
}
