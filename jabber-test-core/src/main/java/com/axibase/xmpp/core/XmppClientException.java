package com.axibase.xmpp.core;

public class XmppClientException extends Exception {
    public XmppClientException() {
    }

    public XmppClientException(String message) {
        super(message);
    }

    public XmppClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmppClientException(Throwable cause) {
        super(cause);
    }

    public XmppClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
