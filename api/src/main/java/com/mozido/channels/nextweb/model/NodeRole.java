package com.mozido.channels.nextweb.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Node (Company) roles
 * @author Alexander Manusovich
 */
public enum NodeRole implements IsSerializable {
    ANY("ANY");

    private final String code;

    private NodeRole(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static NodeRole fromString(final String s) {
        for (final NodeRole role : values())
            if (role.code.equals(s))
                return role;

        throw new IllegalStateException(s);
    }
}

