package com.mozido.channels.nextweb.validation.validators;

import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;
import com.mozido.channels.nextweb.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class AmountValidator implements Validator {

    @Override
    public List<ChannelErrorMessage> validate(Object value) {
        if (value == null || ((value instanceof String) && ((String) value).isEmpty())) {
            return makeError(App.constants().vAmountIsNotValid());
        }
        try {
            if (Integer.valueOf((String) value) < 0) {
                return makeError(App.constants().vAmountIsNotValid());
            }
        } catch (NumberFormatException ex) {
            return makeError(App.constants().vAmountIsNotValid());
        }
        return null;
    }

    protected List<ChannelErrorMessage> makeError(String message) {
        ChannelErrorMessage msg = new ChannelErrorMessage();
        msg.setType(ChannelErrorMessageType.VALIDATION_ERROR);
        msg.setMessage(message);
        List<ChannelErrorMessage> result = new ArrayList<ChannelErrorMessage>();
        result.add(msg);
        return result;
    }
}
