package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

import java.util.Scanner;

public class TestText {

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

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter user ID to send messages to: ");
        String userId = inputScanner.nextLine();

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendText("Hello");
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        System.out.println("Sending message: OK");
    }
}
