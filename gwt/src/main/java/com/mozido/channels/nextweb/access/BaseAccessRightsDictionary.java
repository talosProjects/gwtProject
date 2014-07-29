package com.mozido.channels.nextweb.access;

import com.mozido.channels.nextweb.model.NodeRole;
import com.mozido.channels.nextweb.model.UserKind;
import com.mozido.channels.nextweb.model.UserRole;
import com.mozido.channels.nextweb.model.ui.FGroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dictionary for storing rights
 *
 * @author Alexander Manusovich
 */
public abstract class BaseAccessRightsDictionary {
    /**
     * Map which is define which rights have different types of users
     */
    private final Map<UserKind, Set<FGroup>> accessMap = new HashMap<UserKind, Set<FGroup>>();

    /**
     * @param userKinds      Type of the user
     * @param componentGroup Functional
     * @return Check if user has rights to this functional
     */
    public boolean hasAccess(List<UserKind> userKinds, FGroup componentGroup) {
        Set<FGroup> componentGroupSet = new HashSet<FGroup>();
        for (UserKind userKind : userKinds) {
            if (accessMap.containsKey(userKind)) {
                componentGroupSet.addAll(accessMap.get(userKind));
            }
        }
        return (componentGroupSet.contains(componentGroup));
    }

    /**
     * Add right to the functional for specific type of the users and node
     *
     * @param nodeRole        Node Type
     * @param genericUserRole User Role
     * @param componentGroups Functional in the application 
     */
    protected void addRight(NodeRole nodeRole, UserRole genericUserRole, FGroup... componentGroups) {
        Set<FGroup> componentGroupSet = new HashSet<FGroup>(Arrays.asList(componentGroups));
        accessMap.put(new UserKind(nodeRole, genericUserRole), componentGroupSet);
    }
}

