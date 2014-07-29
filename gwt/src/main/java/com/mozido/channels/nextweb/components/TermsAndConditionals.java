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

@HistoryToken("terms")
public class TermsAndConditionals extends MzComponent {

    /**
     * Views
     */
    @UiTemplate("TermsAndConditionals.ui.xml")
    interface WebBinder extends UiBinder<Widget, TermsAndConditionals> {
    }

    @Override
    public Widget build() {
        setTitle("Terms and Conditionals");

        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }
}