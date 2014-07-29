package com.mozido.channels.util;

import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelErrorMessageType;
import com.mozido.channels.nextweb.exception.ChannelServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manusovich Alexander
 */
public class Exceptions {
    public static ChannelServiceException makeServerException(Exception exception) throws ChannelServiceException {
        final ChannelServiceException channelServiceException = new ChannelServiceException();
        List<ChannelErrorMessage> messages = new ArrayList<ChannelErrorMessage>();

        ChannelErrorMessage msg = new ChannelErrorMessage();
        msg.setMessage(exception.getMessage());
        msg.setPlatformMessage(exception.getMessage());
        msg.setType(ChannelErrorMessageType.PLATFORM_ERROR);
        messages.add(msg);

        channelServiceException.setChannelMessages(messages);
        return channelServiceException;
    }
}
