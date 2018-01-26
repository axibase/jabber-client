package com.axibase.xmpp.core;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.XmlStringBuilder;
import org.jivesoftware.smackx.receipts.DeliveryReceiptRequest;
import org.jivesoftware.smackx.xhtmlim.XHTMLManager;
import org.jivesoftware.smackx.xhtmlim.XHTMLText;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

abstract class AbstractXmppChat implements SimpleXmppChat {
    private boolean doCheck = false;
    protected abstract Message newMessage();
    protected abstract void sendMessage(Message m) throws XmppClientException;

    @Override
    public void enableCheck() {
        doCheck = true;
    }

    @Override
    public void sendText(String text) throws XmppClientException {
        Message m = newMessage();
        m.setBody(text);
        if (doCheck) {
            DeliveryReceiptRequest.addTo(m);
        }
        sendMessage(m);
    }

    @Override
    public void sendImage(String srcPath) throws XmppClientException {
        File srcFile = new File(srcPath);
        Message m = newMessage();
        m.setBody(srcFile.getName());

        XHTMLText xhtmlText = new XHTMLText(null, null);

        Class<XHTMLText> cls = XHTMLText.class;
        Field textField;
        try {
            textField = cls.getDeclaredField("text");
            textField.setAccessible(true);

            XmlStringBuilder text = (XmlStringBuilder) textField.get(xhtmlText);

            text.halfOpenElement("img");
            text.optAttribute("alt", "img");
            text.optAttribute("src", ImageHelper.loadAsDataUrl(srcPath));
            text.closeEmptyElement();

            xhtmlText.appendBrTag();
            xhtmlText.append("Some string");
            xhtmlText.appendBrTag();
            xhtmlText.appendOpenEmTag();
            xhtmlText.append("Emphasis");
            xhtmlText.appendCloseEmTag();
            xhtmlText.appendBrTag();
            xhtmlText.appendOpenCodeTag();
            xhtmlText.append("Code");
            xhtmlText.appendCloseCodeTag();
            xhtmlText.appendBrTag();
            xhtmlText.appendOpenOrderedListTag(null);
            xhtmlText.appendLineItemTag(null);
            xhtmlText.append("Item1");
            xhtmlText.appendCloseLineItemTag();
            xhtmlText.appendLineItemTag(null);
            xhtmlText.append("Item2");
            xhtmlText.appendCloseLineItemTag();
            xhtmlText.appendCloseOrderedListTag();
            xhtmlText.appendBrTag();
            text.append(" <table>\n" +
                    "  <tr>\n" +
                    "    <th>Firstname</th>\n" +
                    "    <th>Lastname</th>\n" +
                    "    <th>Age</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Jill</td>\n" +
                    "    <td>Smith</td>\n" +
                    "    <td>50</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Eve</td>\n" +
                    "    <td>Jackson</td>\n" +
                    "    <td>94</td>\n" +
                    "  </tr>\n" +
                    "</table> ");
            xhtmlText.appendBrTag();
            text.append("<a href=\"https://google.com\">Go to google</a>");


            xhtmlText.appendCloseBodyTag();

            XHTMLManager.addBody(m, xhtmlText);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new XmppClientException("Private field error", e);
        } catch (IOException e) {
            throw new XmppClientException("Error reading image", e);
        }

        sendMessage(m);
    }

    @Override
    public void leave() throws XmppClientException {
    }
}
