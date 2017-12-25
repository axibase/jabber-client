package com.axibase.xmpp;

import com.axibase.xmpp.core.*;
import org.jivesoftware.smack.SmackInitialization;

import java.util.Collection;
import java.util.Collections;

public class TestLogin {
    public static void main(String[] args) {
        new SmackInitialization();

        XmppClientConfig config = new XmppClientConfig(args);
        Collection<String> mechanismNameList = SimpleXmppClient.getSaslMechanisms();

        Debug.message("Will connect to host=" + config.getHost() + " port=" + config.getPort());
        Debug.message("password=" + String.join("", Collections.nCopies(config.getPassword().length(), "*")));
        Debug.message("user=" + config.getUser());
        Debug.message("domain=" + config.getDomain());
        Debug.message("insecure " + (config.getInsecure() ? "enabled" : "disabled"));
        Debug.message("debug.log " + (config.getDebug() ? "enabled" : "disabled"));

        for (String mechanismName : mechanismNameList) {
            SimpleXmppClient client;
            try {
                client = new SimpleXmppClient(config);
            } catch (XmppClientException e) {
                throw Errors.errorExit("Cannot initialize XMPP client", e);
            }
            SimpleXmppClient.useSingleSaslMechanism(mechanismName);

            boolean loginSuccess = true;
            try {
                client.login();
                client.disconnect();
            } catch (XmppClientException e) {
                Debug.complain("Authentication with " + mechanismName + " mechanism failed", e);
                loginSuccess = false;
            }
            System.out.println("Login with " + mechanismName + (loginSuccess ? " OK" : " FAIL"));
        }
    }
}
