package com.axibase.xmpp.core;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.Jid;

class UserXmppChat extends AbstractXmppChat {
    private Chat peerChat;
    private Jid peerJid;

    UserXmppChat(Chat peerChat, Jid peerJid) {
        this.peerChat = peerChat;
        this.peerJid = peerJid;
    }

    @Override
    protected Message newMessage() {
        return new Message(peerJid, Message.Type.chat);
    }

    @Override
    protected void sendMessage(Message m) throws XmppClientException {
        try {
            peerChat.send(m);
        } catch (SmackException.NotConnectedException e) {
            throw new XmppClientException("Cannot send message, not connected", e);
        } catch (InterruptedException e) {
            throw Errors.errorExit("Unknown error", e);
        }
    }
}
