package com.axibase.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TestText {
    private static final String CLIENT_RESOURCE = "Smack";

    public static void main(String[] args) {
        System.setProperty("smack.debuggerClass", Log4jDebugger.class.getName());
        Scanner inputScanner = new Scanner(System.in);
        ArgumentsManager options = new ArgumentsManager(args);

        String user = options.getOption("user", true);
        String password = options.getOption("password", true);
        String domain = options.getOption("domain", true);
        String host = options.getOption("host", true);
        String port = options.getOption("port", true);
        boolean insecure = options.getBooleanOption("insecure");
        boolean debug = options.getBooleanOption("debug");
        boolean plain = options.getBooleanOption("plain-auth");

        XMPPTCPConnectionConfiguration config = null;
        try {
            XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
            configBuilder.setUsernameAndPassword(user, password)
                    .setXmppDomain(domain)
                    .setResource(CLIENT_RESOURCE)
                    .setHost(host)
                    .setPort(Integer.valueOf(port));
            if (insecure) {
                configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            }
            if (debug) {
                configBuilder.setDebuggerEnabled(true);
            }
            config = configBuilder.build();
        } catch (XmppStringprepException e) {
            Errors.errorExit("Cannot create connection configuration", e);
        }

        if (plain) {
            SASLAuthentication.unBlacklistSASLMechanism("PLAIN");
            SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
        }

        XMPPTCPConnection xmppConnection = new XMPPTCPConnection(config);
        try {
            xmppConnection.connect().login();
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            Errors.errorExit("Cannot establish connection", e);
        }

        EntityBareJid jid = null;
        while (jid == null) {
            System.out.print("Enter user ID to send messages to: ");
            String strJid = inputScanner.nextLine();
            try {
                jid = JidCreate.entityBareFrom(strJid);
            } catch (XmppStringprepException e) {
                Errors.complain("Incorrect user ID", e);
            }
        }

        ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
        Chat chat = chatManager.chatWith(jid);
        try {
            while (true) {
                String messageBody;
                System.out.print("Enter message to send: ");
                try {
                    messageBody = inputScanner.nextLine();
                } catch (NoSuchElementException e) {
                    System.out.println();
                    break;
                }
                Message m = new Message(jid, Message.Type.chat);
                m.setBody(messageBody);
                chat.send(m);
            }
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            Errors.errorExit("Error while sending message", e);
        }
    }
}
