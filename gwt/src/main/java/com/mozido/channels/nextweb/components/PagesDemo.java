package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.history.HistoryCommand;
import com.mozido.channels.nextweb.history.HistoryPath;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.ui.FGroup;

import java.util.HashMap;

/**
 * @author Alexander Manusovich
 */
@HistoryToken("pages")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class PagesDemo extends MzComponent {
    @UiField
    MzBootstrapButton generatedInTheCode, generatedInTheCodeNoHistory,
            passParameters, authorizationCheck, authorizationCheck2;

    /**
     * Views
     */
    @UiTemplate("PagesDemo.ui.xml")
    interface WebBinder extends UiBinder<Widget, PagesDemo> {
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    public void onBind() {
        super.onBind();

        registerHistoryEvent(generatedInTheCode, GeneratedInTheCode.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                App.navigation().openPage(GeneratedInTheCode.class);
            }
        });
        registerHistoryEvent(authorizationCheck, SecuredTestPage.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                App.navigation().openPage(SecuredTestPage.class);
            }
        });
    }

    @UiHandler("generatedInTheCodeNoHistory")
    public void handleGeneratedNoHistoryClick(ClickEvent event) {
        App.navigation().navigate(GeneratedInTheCode.class);
    }

    @UiHandler("authorizationCheck2")
    public void handleAuthorizationCheck2Click(ClickEvent event) {
        App.navigation().navigate(SecuredTestPage.class);
    }

    @UiHandler("passParameters")
    public void handlePassParametersClick(ClickEvent event) {
        HistoryPath hp = HistoryPath.builder()
                .item(GeneratedInTheCode.class).parameter("x", "parameter value from url")
                .build();

        App.navigation().navigate(hp);
    }

}
