package com.mozido.channels.nextweb.components;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.MzNavigation;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.ui.FGroup;

/**
 * @author Manusovich Alexander
 */
@HistoryToken("support")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class GeneratedInTheCode extends MzComponent {
    @Override
    public Widget build() {
        String content = "This page has been generated in the code.";

        MzBootstrapButton button = new MzBootstrapButton();
        button.setText("Go to main page");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                MzNavigation.navigateToHomePage();
            }
        });

        FlowPanel panel = new FlowPanel();
        panel.add(new HTMLPanel(content));
        panel.add(button);

        return panel;
    }

    @Override
    protected void onReset() {
        super.onReset();
        if (getUrlParams() != null && getUrlParams().containsKey("x")) {

            InformationDialog dialog = new InformationDialog();
            dialog.setTitle("Alert");
            dialog.setText("Parameter = " + getUrlParams().get("x"));

            App.navigation().openWindow(null, dialog, true);
        }
    }
}
