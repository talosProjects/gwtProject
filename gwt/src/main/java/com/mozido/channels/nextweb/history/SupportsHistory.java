package com.mozido.channels.nextweb.history;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author Alexander Manusovich
 */
public interface SupportsHistory {
    void setHistoryToken(String navigate);

    String getHistoryToken();

    public HandlerRegistration addClickHandler(ClickHandler handler);

    void setVisible(boolean visible);
}
