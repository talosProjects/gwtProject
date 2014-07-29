package com.mozido.channels.nextweb.components.form.bootstrap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.validation.CanBeValidated;
import com.mozido.channels.nextweb.validation.HasValue;
import com.mozido.channels.nextweb.validation.Validation;
import com.mozido.channels.nextweb.validation.ValidationHelper;
import com.mozido.channels.nextweb.validation.Validator;
import org.gwtbootstrap3.client.ui.Input;

import java.util.List;

/**
 * Form component
 *
 * @author Alexander Manusovich
 */
public class MzBootstrapTextBox extends Input implements CanBeValidated, HasValue {
    private Validator[] validators;
    private Widget autoClick;

    public MzBootstrapTextBox() {
        addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent keyPressEvent) {
                Validation.clear(MzBootstrapTextBox.this);
                if (autoClick != null && KeyCodes.KEY_ENTER ==
                        keyPressEvent.getNativeEvent().getKeyCode()) {
                    MzBootstrapButton.fireClick(autoClick);
                }
            }
        });
    }

    @Override
    public List<ChannelErrorMessage> validate() {
        return ValidationHelper.validate(validators, getFormValue());
    }

    @Override
    public void displayValidationMessage(Node message) {
        addStyleName(Validation.CSS_INVALID);
        getElement().getParentNode().insertAfter(message, getElement());
        getElement().getParentElement().addClassName("has-error");
        getElement().focus();
    }

    @Override
    public void clearValidationMessages() {
        removeStyleName(Validation.CSS_INVALID);
        getElement().getParentElement().removeClassName("has-error");
        int c = getElement().getParentElement().getChildCount();
        for (int i = 0; i < c; i++) {
            Element elem = getElement().getParentElement().getChild(i).cast();
            if (Element.is(elem) && elem.getAttribute("class")
                    .contains(Validation.CSS_VALIDATION_MESSAGE)) {
                elem.removeFromParent();
                break;
            }
        }
    }

    @Override
    public void setValidators(Validator... validators) {
        this.validators = validators;
    }

    /**
     * Types which can accept input box:
     * color date datetime datetime-local email month number range search tel time url week
     */
    public void setType(String type) {
        getElement().setAttribute("type", type);
    }

    /**
     * Automatically click on the following button if user press Enter
     */
    public void autoClickOnEnter(Widget button) {
        autoClick = button;
    }

    public void setPlaceholder(String text) {
        String placeholder = (text != null ? text : "");
        getElement().setPropertyString("placeholder", placeholder);
    }

    @Override
    public Object getValue() {
        return getFormValue();
    }

    @Override
    public void clearValue() {
        setFormValue("");
    }
}
