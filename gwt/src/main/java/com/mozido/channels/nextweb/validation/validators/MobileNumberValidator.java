package com.mozido.channels.nextweb.validation.validators;

import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;
import com.mozido.channels.nextweb.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class MobileNumberValidator implements Validator {
    @Override
    public List<ChannelErrorMessage> validate(Object value) {
        if (value == null
                || (!(value instanceof String))
                || ((String) value).isEmpty()
                || ((String) value).length() < App.MIN_PHONE_LENGTH
                || ((String) value).length() > App.MAX_PHONE_LENGTH
                || !((String) value).matches("^[0-9,;]+$")) {
            ChannelErrorMessage msg = new ChannelErrorMessage();
            msg.setType(ChannelErrorMessageType.VALIDATION_ERROR);
            msg.setMessage(App.constants().vNumberIsNotValid());
            List<ChannelErrorMessage> result = new ArrayList<ChannelErrorMessage>();
            result.add(msg);
            return result;
        }
        return null;
    }
}
