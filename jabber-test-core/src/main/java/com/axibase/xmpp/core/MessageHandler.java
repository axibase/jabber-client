package com.axibase.xmpp.core;

public interface MessageHandler {
    void handleMessage(String from, String message, SimpleXmppChat chat);
}
