package com.mozido.channels.nextweb.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

public class CredentialsDTO extends BaseDTO {

    private String login;

    private String password;

    public CredentialsDTO() {
    }

    @ApiModelProperty(value = "unique identifier", required = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @ApiModelProperty(value = "user's password", required = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
