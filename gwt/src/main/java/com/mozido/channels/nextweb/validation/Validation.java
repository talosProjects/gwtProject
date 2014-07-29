package com.mozido.channels.nextweb.validation;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;

import java.util.List;

public class Validation {
    public static final String CSS_INVALID = "mz-validation-invalid-state";
    public static final String CSS_VALIDATION_MESSAGE = "mz-validation-message";

    private boolean valid = true;

    public static Validation clear(CanBeValidated container) {
        Validation validation = new Validation();
        container.clearValidationMessages();
        return validation;
    }

    public Validation validate(CanBeValidated... fields) {
        if (fields != null) {
            for (CanBeValidated field : fields) {
                validateField(field);
            }
        }
        return this;
    }

    private void validateField(CanBeValidated field) {
        List<ChannelErrorMessage> messages = field.validate();
        if (messages != null) {
            valid = false;
            for (ChannelErrorMessage message : messages) {
                Element container = DOM.createDiv();
                container.setClassName(CSS_VALIDATION_MESSAGE + " help-block");

                final ChannelErrorMessageType type = message.getType();
                if (type == null || type == ChannelErrorMessageType.VALIDATION_ERROR) {
                    container.setInnerText(message.getMessage());
                } else if (type == ChannelErrorMessageType.PLATFORM_ERROR) {
                    String msg = message.getMessage();
                    if (msg == null || msg.isEmpty()) {
                        msg = message.getPlatformMessage();
                    }

                    String codePart = "";
                    if (message.getCode() != null && !message.getCode().isEmpty()) {
                        codePart = "<b>(" + message.getCode().toUpperCase() + ")</b>&#160;";
                    }
                    container.setInnerHTML("<div class='alert alert-danger'>"
                            + codePart + msg + "</div>");

                }

                field.displayValidationMessage(container);
            }
        } else {
            field.clearValidationMessages();
        }
    }

    public boolean isValid() {
        return valid;
    }
}
