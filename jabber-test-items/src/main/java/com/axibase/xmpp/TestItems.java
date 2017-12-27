package com.axibase.xmpp;

import com.axibase.xmpp.core.*;

import java.util.List;

public class TestItems {
    private static final String MUC_FEATURE = "http://jabber.org/protocol/muc";

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
            List<String> mucServices = client.requestItems(MUC_FEATURE);
            System.out.println("Request service items: OK");
            System.out.print("MUC services:");
            for (String service : mucServices) {
                System.out.print(" " + service);
            }
            System.out.println();
        } catch (XmppClientException e) {
            Debug.complain("Cannot request service items", e);
        }
    }
}
