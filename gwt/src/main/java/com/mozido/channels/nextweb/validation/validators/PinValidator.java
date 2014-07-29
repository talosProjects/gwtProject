package com.mozido.channels.nextweb.validation.validators;

import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;
import com.mozido.channels.nextweb.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class PinValidator implements Validator {
    @Override
    public List<ChannelErrorMessage> validate(Object value) {
        if (value == null
                || ((value instanceof String) && ((String) value).isEmpty())
                || ((value instanceof String) && ((String) value).length() != 4)) {
            ChannelErrorMessage msg = new ChannelErrorMessage();
            msg.setType(ChannelErrorMessageType.VALIDATION_ERROR);
            msg.setMessage(App.constants().vPinInvalid());

            List<ChannelErrorMessage> result = new ArrayList<ChannelErrorMessage>();
            result.add(msg);
            return result;
        }
        return null;
    }
}
