package com.axibase.xmpp.core;

public interface SimpleXmppChat {
    public void sendText(String text) throws XmppClientException;
    public void leave() throws XmppClientException;
}
