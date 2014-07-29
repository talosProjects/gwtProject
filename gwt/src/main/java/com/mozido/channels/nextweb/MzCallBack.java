package com.mozido.channels.nextweb;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.mozido.channels.nextweb.errors.CriticalErrorsHandler;
import com.mozido.channels.nextweb.errors.MzNotificationManager;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;
import com.mozido.channels.nextweb.exception.ChannelServiceException;
import com.mozido.channels.nextweb.utils.glass.Glass;

import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

/**
 * Common callback action
 *
 * @Author Alex Manusovich
 */
public abstract class MzCallBack<C> implements AsyncCallback<C> {
    private HasContainerId component;

    protected MzCallBack(HasContainerId component) {
        this.component = component;
    }

    @Override
    public void onFailure(Throwable throwable) {
        Glass.hide();
        if (throwable instanceof StatusCodeException) {
            /**
             *  Indicates that an RPC response was returned with an invalid HTTP status code.
             *  This exception will be presented via AsyncCallback.onFailure(Throwable) if the HTTP response from
             *  the server does not have a 200 status code.
             */
            MzClientSession.logout();
            return;
        }
        if (throwable instanceof ChannelServiceException) {
            ChannelServiceException channelServiceException = (ChannelServiceException) throwable;

            if (channelServiceException.getPlatformErrors() != null
                    && channelServiceException.getPlatformErrors().size() > 0) {
                CriticalErrorsHandler.handle(channelServiceException.getPlatformErrors());
            }

            displayWarnings(channelServiceException.getChannelMessages(), component);
        } else {
            MzNotificationManager.displayError("Remote Call Failure", throwable.getMessage());
        }
    }

    /**
     * Code for handling validation and common error messages from the background
     *
     * @param messages  Messages (type & text)
     * @param component Component where these messages will be displayed
     */
    public static void displayWarnings(final List<ChannelErrorMessage> messages,
                                       final HasContainerId component) {
        if (messages != null && !messages.isEmpty()) {
            for (ChannelErrorMessage vmsg : messages) {

                String debugMsg = "";
                Element message = DOM.createDiv();
                message.setClassName("mz-validation-message help-block");
                final ChannelErrorMessageType type = vmsg.getType();
                if (type == null || type == ChannelErrorMessageType.VALIDATION_ERROR) {
                    message.setInnerText(vmsg.getMessage());
                    debugMsg = debugMsg + vmsg.getMessage();
                } else if (type == ChannelErrorMessageType.PLATFORM_ERROR) {

                    String msg = vmsg.getMessage();
                    if (msg == null || msg.isEmpty()) {
                        msg = vmsg.getPlatformMessage();
                    }


                    String codePart = "";
                    if (vmsg.getCode() != null && !vmsg.getCode().isEmpty()) {
                        codePart = "<b>(" + vmsg.getCode().toUpperCase() + ")</b>&#160;";
                    }
                    message.setInnerHTML("<div class='alert alert-danger'>" + codePart + msg + "</div>");
                }

                /**
                 * If we don't have field value, we should display this in common section
                 */
                Element box = null;
                final Element[] elements = $("." + component.getContainerId()
                        + " .validationResults").elements();
                if (elements.length > 0) {
                    box = elements[0];
                }
                if (box != null) {
                    box.appendChild(message);
                } else {
                    /**
                     * If we can't find .validationResults block in this component - just display
                     * error in the dialog
                     */
                    MzNotificationManager.displayError(debugMsg);
                }
            }
        }
    }


}
