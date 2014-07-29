package com.mozido.channels.nextweb.model;

/**
 * Kind of user, his roles important for access rights
 *
 * @author Alexander Manusovich
 */
public class UserKind extends BaseDTO {
    private NodeRole nodeRole;
    private UserRole genericUserRole;

    public UserKind() {
    }

    public UserKind(final NodeRole nodeRole,
                    final UserRole genericUserRole) {
        this.genericUserRole = genericUserRole;
        this.nodeRole = nodeRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserKind userKind = (UserKind) o;

        if (genericUserRole != userKind.genericUserRole) return false;
        return nodeRole == userKind.nodeRole;
    }

    @Override
    public int hashCode() {
        int result = nodeRole != null ? nodeRole.hashCode() : 0;
        result = 31 * result + (genericUserRole != null ? genericUserRole.hashCode() : 0);
        return result;
    }
}

