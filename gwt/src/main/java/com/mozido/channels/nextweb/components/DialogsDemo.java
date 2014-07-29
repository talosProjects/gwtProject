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
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.ui.FGroup;

/**
 * @author Alexander Manusovich
 */
@HistoryToken("dialogs")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class DialogsDemo extends MzComponent {
    @UiField
    MzBootstrapButton windowButton;
    @UiField
    MzBootstrapButton ownInstanceButton;
    @UiField
    MzBootstrapButton noHistoryButton;

    /**
     * Views
     */
    @UiTemplate("DialogsDemo.ui.xml")
    interface WebBinder extends UiBinder<Widget, DialogsDemo> {
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    public void onBind() {
        super.onBind();

        registerHistoryEvent(windowButton, TestDialog.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                App.navigation().openWindow(historyState, TestDialog.class);
            }
        });

        registerHistoryEvent(ownInstanceButton, "customTestDialog", new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                TestDialog testDialog = new TestDialog();
                App.navigation().openWindow(historyState, testDialog, true);
            }
        });
    }

    @UiHandler("noHistoryButton")
    public void handleClick(ClickEvent event) {
        App.navigation().openWindow(null, TestDialog.class);
    }
}
