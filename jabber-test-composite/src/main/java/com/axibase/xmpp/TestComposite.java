package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

public class TestComposite {

    public static void main(String[] args) {

        XmppClientConfig config = new XmppClientConfig(args);

        basicSend(config);
        sendWithReceiptRequest(config);
        loginWithoutRoster(config);
        sendWithStatus(config);
        sendSelf(config);
        nonExistentSend(config);
    }

    private static void announce(String title) {
        Debug.message("------------------------");
        Debug.message(title + '\n');
    }

    private static void basicSend(XmppClientConfig config) {
        String message = "Basic send";
        announce(message);

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
        Debug.message("Login: OK");

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendText("Hello: " + message);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        Debug.message("Sending message: OK");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }

    private static void sendWithReceiptRequest(XmppClientConfig config) {
        String message = "Send with receipt request";
        announce(message);

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
        Debug.message("Login: OK");

        client.handleReceipts();

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
            chat.enableCheck();
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendText("Hello: " + message);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        Debug.message("Sending message: OK");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }

    private static void loginWithoutRoster(XmppClientConfig config) {
        String message = "Login without loading roster";
        announce(message);

        String userId = config.getTo();

        SimpleXmppClient client;
        try {
            client = new SimpleXmppClient(config);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot initialize XMPP client", e);
        }

        client.disableRoster();

        try {
            client.login();
        } catch (XmppClientException e) {
            throw Errors.errorExit("Login failed", e);
        }
        Debug.message("Login: OK");

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendText("Hello: " + message);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        Debug.message("Sending message: OK");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }

    private static void sendWithStatus(XmppClientConfig config) {
        String message = "Set status and send";
        announce(message);

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
        Debug.message("Login: OK");

        try {
            client.setStatus("Now online");
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to set status", e);
        }
        Debug.message("Set status: OK");

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendText("Hello: " + message);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        Debug.message("Sending message: OK");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }

    private static void sendSelf(XmppClientConfig config) {
        String message = "Send to itself";
        announce(message);

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
        Debug.message("Login: OK");

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with itself", e);
        }

        client.handleMessages((from, messageText, chatInstance) -> Debug.message("Received message: OK"));

        try {
            chat.sendText("Hello: " + message);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        Debug.message("Sending message: OK");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }

    private static void nonExistentSend(XmppClientConfig config) {
        String message = "Send to non-existent user";
        announce(message);

        String userId = "do@not.exist";

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
        Debug.message("Login: OK");

        SimpleXmppChat chat;
        try {
            chat = client.getChatWith(userId);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Cannot start chat with selected user", e);
        }

        try {
            chat.sendText("Hello: " + message);
        } catch (XmppClientException e) {
            throw Errors.errorExit("Failed to send message", e);
        }
        Debug.message("Sending message: OK");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }
}
