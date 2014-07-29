package com.mozido.channels.nextweb.components.form.bootstrap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.history.SupportsHistory;
import org.gwtbootstrap3.client.ui.Button;

/**
 * Form component
 *
 * @author Alexander Manusovich
 */
public class MzBootstrapButton extends Button implements SupportsHistory {
    private String historyToken;

    public void setHistoryToken(String navigate) {
        this.historyToken = navigate;
    }

    public String getHistoryToken() {
        return historyToken;
    }

    public static void fireClick(Widget button) {
        button.fireEvent(new GwtEvent<ClickHandler>() {
            @Override
            public com.google.gwt.event.shared.GwtEvent.Type<ClickHandler> getAssociatedType() {
                return ClickEvent.getType();
            }

            @Override
            protected void dispatch(ClickHandler handler) {
                handler.onClick(null);
            }
        });
    }

}