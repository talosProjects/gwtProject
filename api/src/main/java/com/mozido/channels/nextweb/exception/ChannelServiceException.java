package com.mozido.channels.nextweb.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

public class ChannelServiceException extends Exception implements IsSerializable {
    private List<ChannelErrorMessage> channelMessages;
    private List<PlatformErrorData> platformErrors;

    public ChannelServiceException() {
    }

    public ChannelServiceException(PlatformErrorData errorData) {
        setPlatformErrors(new ArrayList<PlatformErrorData>());
        getPlatformErrors().add(errorData);
    }

    public List<ChannelErrorMessage> getChannelMessages() {
        return channelMessages;
    }

    public void setChannelMessages(List<ChannelErrorMessage> channelMessages) {
        this.channelMessages = channelMessages;
    }

    public List<PlatformErrorData> getPlatformErrors() {
        return platformErrors;
    }

    public void setPlatformErrors(List<PlatformErrorData> platformErrors) {
        this.platformErrors = platformErrors;
    }
}
