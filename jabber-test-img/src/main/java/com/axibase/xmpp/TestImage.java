package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

public class TestImage {
    public static void main(String[] args) {
        XmppClientConfig config = new XmppClientConfig(args);
        String userId = config.getTo();

        SimpleXmppClient client;
        try {
            client = new SimpleXmppClient(config);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot initialize XMPP client", e);
        }

        try {
            client.login();
        } catch (XmppClientException e) {
            throw Errors.errorExit("Login failed", e);
        }
        System.out.println("Login: OK");

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendImage("xmpp.png");
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot send image", e);
        }
        System.out.println("Sending message: OK");
    }
}
