package com.axibase.xmpp.core;

public class Errors {
    public static RuntimeException errorExit(String message) {
        return errorExit(message, null);
    }

    public static RuntimeException errorExit(String message, Throwable error) {
        Debug.complain(message, error);
        System.exit(1);
        return new RuntimeException();
    }
}
