package com.mozido.channels.nextweb.validation.validators;

import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;
import com.mozido.channels.nextweb.validation.HasValue;
import com.mozido.channels.nextweb.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class SameValueValidator implements Validator {
    private HasValue anotherComponent;

    public SameValueValidator(HasValue anotherComponent) {
        this.anotherComponent = anotherComponent;
    }

    @Override
    public List<ChannelErrorMessage> validate(Object value) {
        if (value == null || !value.equals(anotherComponent.getValue())) {
            ChannelErrorMessage msg = new ChannelErrorMessage();
            msg.setType(ChannelErrorMessageType.VALIDATION_ERROR);
            msg.setMessage(App.constants().vIsNotTheSameValue());

            List<ChannelErrorMessage> result = new ArrayList<ChannelErrorMessage>();
            result.add(msg);
            return result;
        }
        return null;
    }
}
