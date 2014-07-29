package com.mozido.channels.nextweb.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PlatformErrorData implements IsSerializable {
    private String code;
    private String description;
    private String message;

    public PlatformErrorData() {
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}