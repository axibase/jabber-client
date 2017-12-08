package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

public class TestRoom {
    public static void main(String[] args) {
        XmppClientConfig config = new XmppClientConfig(args);

        String roomId = config.getTo();
        String nickName = config.getNick();
        String roomPassword = config.getRoomPassword();

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
            chat = client.getChatRoom(roomId, nickName, roomPassword);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot join the room", e);
        }
        System.out.println("Join room: OK");

        try {
            chat.sendText("Hello");
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        System.out.println("Sending message: OK");

        try {
            chat.leave();
        } catch (XmppClientException e) {
            e.printStackTrace();
        }
        System.out.println("Leave room: OK");
    }
}
