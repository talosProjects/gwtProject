package com.mozido.channels.nextweb.components;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.ui.FGroup;

/**
 * @author Manusovich Alexander
 */
@HistoryToken("securedPage")
@FunctionalGroup(value = {FGroup.AUTHENTICATED_USERS, FGroup.TEST_SECURED_GROUP},
        ifIsNotAllowedUseInstead = Page404.class)
public class SecuredTestPage extends MzComponent {
    @Override
    public Widget build() {
        return new InlineLabel("");
    }
}
