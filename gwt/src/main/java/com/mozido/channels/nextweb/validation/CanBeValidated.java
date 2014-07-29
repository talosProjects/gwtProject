package com.mozido.channels.nextweb.validation;

import com.google.gwt.dom.client.Node;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;

import java.util.List;

public interface CanBeValidated {
    void setValidators(Validator... validators);

    List<ChannelErrorMessage> validate();

    void displayValidationMessage(Node message);

    void clearValidationMessages();
}
