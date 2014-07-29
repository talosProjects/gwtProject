package com.mozido.channels.util.ws;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Manusovich Alexander
 */
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {
    public static final SOAPLoggingHandler instance = new SOAPLoggingHandler();
    private static final Logger logger = Logger.getLogger("com.mozido.gwt");

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount",
                    "" + indent);
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            return input;
        }
    }

    public boolean handleMessage(final SOAPMessageContext c) {
        SOAPMessage msg = c.getMessage();
        boolean request = (Boolean) c.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            String s = prettyFormat(new String(out.toByteArray()), 2);
            if (request) {
                logger.log(Level.INFO, "SOAP REQUEST:\n" + s);
            } else {
                logger.log(Level.INFO, "SOAP RESPONSE:\n" + s);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, msg.toString());
        }
        return true;
    }

    public boolean handleFault(final SOAPMessageContext c) {
        SOAPMessage msg = c.getMessage();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            String s = prettyFormat(new String(out.toByteArray()), 2);
            logger.log(Level.SEVERE, "SOAP RESPONSE FAULT:\n" + s);
        } catch (Exception e) {
            logger.log(Level.SEVERE, msg.toString());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return true;
    }

    public void close(final MessageContext c) {
    }

    public Set getHeaders() {
        return null;
    }
}