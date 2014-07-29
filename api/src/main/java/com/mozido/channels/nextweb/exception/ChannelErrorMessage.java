package com.mozido.channels.nextweb.exception;

import com.mozido.channels.nextweb.model.BaseDTO;

public class ChannelErrorMessage extends BaseDTO {
    private ChannelErrorMessageType type;
    private String code;
    private String foreignCode;
    private String message;
    private String platformMessage;

    public ChannelErrorMessage() {
    }

    public ChannelErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChannelErrorMessageType getType() {
        return type;
    }

    public void setType(ChannelErrorMessageType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getForeignCode() {
        return foreignCode;
    }

    public void setForeignCode(String foreignCode) {
        this.foreignCode = foreignCode;
    }

    public String getPlatformMessage() {
        return platformMessage;
    }

    public void setPlatformMessage(String platformMessage) {
        this.platformMessage = platformMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelErrorMessage message1 = (ChannelErrorMessage) o;

        if (code != null ? !code.equals(message1.code) : message1.code != null) return false;
        if (foreignCode != null ? !foreignCode.equals(message1.foreignCode) : message1.foreignCode != null)
            return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        if (platformMessage != null ? !platformMessage.equals(message1.platformMessage) : message1.platformMessage != null)
            return false;
        if (type != message1.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (foreignCode != null ? foreignCode.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (platformMessage != null ? platformMessage.hashCode() : 0);
        return result;
    }
}



