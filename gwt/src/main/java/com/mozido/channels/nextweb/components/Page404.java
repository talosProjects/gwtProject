package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.history.HistoryToken;

/**
 * Terms And Conditionals
 */

@HistoryToken("404")
public class Page404 extends MzComponent {

    /**
     * Views
     */
    @UiTemplate("Page404.ui.xml")
    interface WebBinder extends UiBinder<Widget, Page404> {
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }
}