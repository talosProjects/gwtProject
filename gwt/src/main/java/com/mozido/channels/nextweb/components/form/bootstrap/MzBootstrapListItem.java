package com.mozido.channels.nextweb.components.form.bootstrap;

import com.mozido.channels.nextweb.history.SupportsHistory;
import org.gwtbootstrap3.client.ui.ListItem;

/**
 * Form component
 *
 * @author Alexander Manusovich
 */
public class MzBootstrapListItem extends ListItem implements SupportsHistory {
    private String historyToken;

    public void setHistoryToken(String navigate) {
        this.historyToken = navigate;
    }

    public String getHistoryToken() {
        return historyToken;
    }
}