package com.mozido.channels.nextweb.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * User Roles
 * Values have to be the same as roles from phoenix.switch_tenant_role table
 *
 * @author Alexander Manusovich
 */
public enum UserRole implements IsSerializable {
    SUBSCRIBER("GWTWT_SUBSCRIBER"),
    ANY("ANY");

    private final String code;

    private UserRole(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static UserRole fromString(final String s) {
        for (final UserRole role : values())
            if (role.code.equals(s))
                return role;

        throw new IllegalStateException(s);
    }
}
