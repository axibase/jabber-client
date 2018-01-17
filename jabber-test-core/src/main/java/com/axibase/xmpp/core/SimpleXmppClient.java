package com.axibase.xmpp.core;


import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.disco.packet.DiscoverInfo;
import org.jivesoftware.smackx.disco.packet.DiscoverItems;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleXmppClient {
    private static final String CLIENT_RESOURCE = "Smack";
    private static final String DEBUGGER_CLASS_PROPERTY_NAME = "smack.debuggerClass";

    static {
        System.setProperty(DEBUGGER_CLASS_PROPERTY_NAME, Log4jDebugger.class.getName());
    }

    private XmppClientConfig config;
    private XMPPTCPConnection xmppConnection;
    
    public SimpleXmppClient(XmppClientConfig config) throws XmppClientException {
        XMPPTCPConnectionConfiguration connectionConfig;
        try {
            XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
            configBuilder.setUsernameAndPassword(config.getUser(), config.getPassword())
                    .setXmppDomain(config.getDomain())
                    .setResource(CLIENT_RESOURCE)
                    .setHost(config.getHost())
                    .setPort(config.getPort());
            if (config.getInsecure()) {
                configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            }
            configBuilder.setDebuggerEnabled(config.getDebug());
            connectionConfig = configBuilder.build();
        } catch (XmppStringprepException e) {
            throw new XmppClientException("Incorrect user ID", e);
        }

        String auth = config.getAuth();
        if (auth != null) {
            useSingleSaslMechanism(auth);
        }

        this.config = config;

        xmppConnection = new XMPPTCPConnection(connectionConfig);
        xmppConnection.setFromMode(XMPPConnection.FromMode.USER);
    }

    public static Collection<String> getSaslMechanisms() {
        Collection<String> mechanismClassNameList =
                SASLAuthentication.getRegisterdSASLMechanisms().keySet();
        List<String> mechanismNameList = new ArrayList<>();
        for (String mechanismClassName : mechanismClassNameList) {
            String mechanismName;
            try {
                Class<?> classOfMechanism = Class.forName(mechanismClassName);
                SASLMechanism mechanism = (SASLMechanism) classOfMechanism.newInstance();
                mechanismName = mechanism.getName();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw Errors.errorExit("Cannot access to mechanism class " + mechanismClassName);
            }
            mechanismNameList.add(mechanismName);
        }
        return mechanismNameList;
    }

    public static void useSingleSaslMechanism(String mechanism) {
        Collection<String> registeredMechanisms = getSaslMechanisms();
        for (String registeredMechanism : registeredMechanisms) {
            SASLAuthentication.blacklistSASLMechanism(registeredMechanism);
        }
        SASLAuthentication.unBlacklistSASLMechanism(mechanism);
    }

    public void disableRoster() {
        Roster roster = Roster.getInstanceFor(xmppConnection);
        roster.setRosterLoadedAtLogin(false);
    }

    public void login() throws XmppClientException {

        try {
            xmppConnection.connect();
            Debug.info("Successfully connected to host");
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            throw new XmppClientException("Cannot establish connection", e);
        }

        if (!xmppConnection.isConnected()) {
            throw new XmppClientException("Not connected");
        }

        try {
            xmppConnection.login();
            Debug.info("Successfully performed login");
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            throw new XmppClientException("Cannot perform login", e);
        }
    }

    public void disconnect() {
        xmppConnection.disconnect();
    }

    public void handleMessages(MessageHandler mh) {
        ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
        chatManager.addIncomingListener((from, message, chat) ->
                mh.handleMessage(from.toString(), message.getBody(), new UserXmppChat(chat)));
    }

    public void handleReceipts() {
        DeliveryReceiptManager drm = DeliveryReceiptManager.getInstanceFor(xmppConnection);
        drm.addReceiptReceivedListener((fromJid, toJid, receiptId, receipt) ->
                Debug.message("Receipt received from " + fromJid.toString() + " to " + toJid.toString()));
    }

    private Chat getChatRaw(String strJid) throws XmppClientException {
        EntityBareJid jid;
        try {
            jid = JidCreate.entityBareFrom(strJid);
        } catch (XmppStringprepException e) {
            throw new XmppClientException("Incorrect user ID", e);
        }

        ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
        return chatManager.chatWith(jid);
    }

    public SimpleXmppChat getChatWith(String strJid) throws XmppClientException {
        return new UserXmppChat(getChatRaw(strJid));
    }

    public SimpleXmppChat getChatRoom(String strJid, String nickName, String password) throws XmppClientException {
        MultiUserChatManager mucManager = MultiUserChatManager.getInstanceFor(xmppConnection);
        EntityBareJid jid;
        try {
            jid = JidCreate.entityBareFrom(strJid);
        } catch (XmppStringprepException e) {
            throw new XmppClientException("Incorrect room ID", e);
        }
        MultiUserChat muc = mucManager.getMultiUserChat(jid);
        try {
            if (password == null) {
                muc.join(Resourcepart.from(nickName));
            } else {
                muc.join(Resourcepart.from(nickName), password);
            }
        } catch (SmackException.NoResponseException |
                XMPPException.XMPPErrorException |
                SmackException.NotConnectedException |
                MultiUserChatException.NotAMucServiceException |
                InterruptedException |
                XmppStringprepException e) {
            throw new XmppClientException("Cannot join room", e);
        }
        return new RoomXmppChat(muc);
    }

    public List<String> requestItems(String feature) throws XmppClientException {
        List<String> featureServices = new ArrayList<>();
        ServiceDiscoveryManager discoveryManager = ServiceDiscoveryManager.getInstanceFor(xmppConnection);
        DiscoverItems discoverItems;
        try {
            discoverItems = discoveryManager.discoverItems(JidCreate.from(config.getDomain()));
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException
                | SmackException.NotConnectedException | XmppStringprepException e) {
            throw new XmppClientException("Cannot request node items", e);
        }
        for (DiscoverItems.Item item : discoverItems.getItems()) {
            Jid itemId = item.getEntityID();
            DiscoverInfo itemInfo;
            try {
                itemInfo = discoveryManager.discoverInfo(itemId);
            } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException
                    | SmackException.NotConnectedException | InterruptedException e) {
                throw new XmppClientException("Cannot request information for item", e);
            }
            boolean mucService = itemInfo.containsFeature(feature);
            if (mucService) {
                featureServices.add(itemId.toString());
            }
        }
        return featureServices;
    }

    public void setStatus(String status) throws XmppClientException {
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus(status);

        try {
            xmppConnection.sendStanza(presence);
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            throw new XmppClientException("Cannot send status");
        }
    }
}
