package com.axibase.xmpp;

import com.axibase.xmpp.core.Errors;
import com.axibase.xmpp.core.SimpleXmppClient;
import com.axibase.xmpp.core.XmppClientConfig;
import com.axibase.xmpp.core.XmppClientException;
import org.jivesoftware.smack.SmackInitialization;

import java.util.Collection;

public class TestLogin {
    public static void main(String[] args) {
        new SmackInitialization();

        Collection<String> mechanismNameList = SimpleXmppClient.getSaslMechanisms();

        for (String mechanismName : mechanismNameList) {
            XmppClientConfig config = new XmppClientConfig(args);

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
                loginSuccess = false;
            }
            System.out.println("Login with " + mechanismName + (loginSuccess ? " OK" : " FAIL"));
        }
    }
}
