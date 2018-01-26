package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

public class TestEcho {
    public static void main(String[] args) {
        XmppClientConfig config = new XmppClientConfig(args);

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

        try {
            client.setStatus("Hello");
        } catch (XmppClientException e) {
            e.printStackTrace();
        }

        client.handleMessages((from, message, chat) -> {
            Debug.message("Received message from " + from + ": " + message);
            try {
                chat.sendText(message);
                System.out.println("Sending message to " + from + ": OK");
            } catch (XmppClientException e) {
                Debug.complain("Failed to send message", e);
            }
        });

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
