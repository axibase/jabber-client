package com.axibase.xmpp.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.debugger.AbstractDebugger;

import java.io.Reader;
import java.io.Writer;

public class Log4jDebugger extends AbstractDebugger {

    private static final Logger LOGGER = LogManager.getLogger(Log4jDebugger.class.getName());

    public Log4jDebugger(XMPPConnection connection, Writer writer, Reader reader) {
        super(connection, writer, reader);
    }

    @Override
    protected void log(String logMessage) {
        LOGGER.info(logMessage);
    }

    @Override
    protected void log(String logMessage, Throwable throwable) {
        LOGGER.error(logMessage, throwable);
    }
}
