package com.axibase.xmpp.core;

public interface SimpleXmppChat {
    void sendText(String text) throws XmppClientException;
    void sendImage(String srcPath) throws XmppClientException;
    void leave() throws XmppClientException;
}
