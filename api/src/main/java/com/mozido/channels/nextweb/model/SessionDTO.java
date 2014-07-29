package com.mozido.channels.nextweb.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @Author Alex Manusovich
 */
@ApiModel(value = "Moteaf session bean")
public class SessionDTO extends BaseDTO {
    private String token;

    public SessionDTO() {
    }

    public SessionDTO(final String token) {
        this.token = token;
    }

    @ApiModelProperty(value = "session token")
    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
