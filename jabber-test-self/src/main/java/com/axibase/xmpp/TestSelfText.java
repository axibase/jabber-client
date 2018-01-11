package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

public class TestSelfText {

    public static void main(String[] args) {
        XmppClientConfig config = new XmppClientConfig(args);
        String userId = config.getUser() + "@" + config.getDomain();

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

        client.handleMessages((from, message, chat1)
                -> System.out.println("Receiving message: OK"));

        try {
            System.out.println("Sending message to itself, ID is " + userId);
            chat.sendText("Hello");
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        System.out.println("Sending message: OK");
    }
}
