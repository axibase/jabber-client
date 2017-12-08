package com.axibase.xmpp.core;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class RoomXmppChat implements SimpleXmppChat {
    MultiUserChat muc;

    public RoomXmppChat(MultiUserChat muc) {
        this.muc = muc;
    }

    @Override
    public void sendText(String text) throws XmppClientException {
        try {
            muc.sendMessage("Hello");
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
