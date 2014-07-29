package com.mozido.channels.nextweb.validation;

import com.mozido.channels.nextweb.exception.ChannelErrorMessage;

import java.util.List;

public interface Validator {
    List<ChannelErrorMessage> validate(Object value);
}
