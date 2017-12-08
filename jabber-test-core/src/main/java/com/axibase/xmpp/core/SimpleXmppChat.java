package com.axibase.xmpp.core;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.Jid;

public class SimpleXmppChat {
    private Chat peerChat;
    private Jid peerJid;

    SimpleXmppChat(Chat peerChat, Jid peerJid) {
        this.peerChat = peerChat;
        this.peerJid = peerJid;
    }

    public void sendText(String text) throws XmppClientException {
        Message m = new Message(peerJid, Message.Type.chat);
        m.setBody(text);
        try {
            peerChat.send(m);
        } catch (SmackException.NotConnectedException e) {
            throw new XmppClientException("Cannot send message, not connected", e);
        } catch (InterruptedException e) {
            throw Errors.errorExit("Unknown error", e);
        }
    }
}
