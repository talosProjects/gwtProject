package com.mozido.channels.nextweb.access;

import com.mozido.channels.nextweb.model.NodeRole;
import com.mozido.channels.nextweb.model.UserRole;
import com.mozido.channels.nextweb.model.ui.FGroup;

/**
 * Dictionary with rights for project
 *
 * @author Alexander Manusovich
 */
public class AccessRightsDictionary extends BaseAccessRightsDictionary {
    public AccessRightsDictionary() {
        super();

        addRight(NodeRole.ANY, UserRole.SUBSCRIBER, FGroup.BALANCE);
    }
}
