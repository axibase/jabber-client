package com.axibase.xmpp.core;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.XmlStringBuilder;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.xhtmlim.XHTMLManager;
import org.jivesoftware.smackx.xhtmlim.XHTMLText;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class RoomXmppChat extends AbstractXmppChat {
    private MultiUserChat muc;

    RoomXmppChat(MultiUserChat muc) {
        this.muc = muc;
    }

    @Override
    protected Message newMessage() {
        return muc.createMessage();
    }

    @Override
    protected void sendMessage(Message m) throws XmppClientException {
        try {
            muc.sendMessage(m);
        } catch (SmackException.NotConnectedException e) {
            throw new XmppClientException("Cannot send message, not connected", e);
        } catch (InterruptedException e) {
            throw Errors.errorExit("Unknown error", e);
        }
    }

    @Override
    public void leave() throws XmppClientException {
        try {
            muc.leave();
        } catch (SmackException.NotConnectedException e) {
            throw new XmppClientException("Cannot leave room, not connected", e);
        } catch (InterruptedException e) {
            throw Errors.errorExit("Unknown error", e);
        }
    }
}
