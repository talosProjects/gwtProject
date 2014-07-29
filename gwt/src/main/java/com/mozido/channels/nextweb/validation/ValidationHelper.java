package com.mozido.channels.nextweb.validation;

import com.mozido.channels.nextweb.exception.ChannelErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Manusovich
 */
public class ValidationHelper {
    public static List<ChannelErrorMessage> validate(Validator[] validators, Object valueToCheck) {
        List<ChannelErrorMessage> res = null;
        if (validators != null) {
            for (Validator v : validators) {
                final List<ChannelErrorMessage> messages = v.validate(valueToCheck);
                if (messages != null) {
                    if (res == null) {
                        res = new ArrayList<>();
                    }
                    res.addAll(messages);
                }
            }
        }
        return res;
    }
}
