package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.dialog.CloseReason;
import com.mozido.channels.nextweb.components.dialog.DialogCloseReason;
import com.mozido.channels.nextweb.components.dialog.MzCloseHandler;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.history.HistoryCommand;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.history.SplitCode;
import com.mozido.channels.nextweb.model.ui.FGroup;

@SplitCode
@HistoryToken("testDialog")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class TestDialog extends MzComponent {
    @UiField
    MzBootstrapButton clientException, dialog2Button, closeButton;
    @UiField
    InlineLabel lastAction;

    /**
     * Views
     */
    @UiTemplate("TestDialog.ui.xml")
    interface WebBinder extends UiBinder<Widget, TestDialog> {
    }

    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    public void onBind() {
        super.onBind();
        setTitle("Dialog Title");

        registerHistoryEvent(dialog2Button, EventsDialog.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                App.navigation().openWindow(historyState, EventsDialog.class, true, new MzCloseHandler() {
                    @Override
                    public void onClose(CloseReason action) {
                        lastAction.setText("Last action in events dialog: " + action.getData());
                    }
                });
            }
        });
    }

    @Override
    protected void onReset() {
        super.onReset();
        lastAction.setText("");
    }

    @UiHandler("clientException")
    public void handleThrowExceptionClick(ClickEvent event) {
        throw new RuntimeException("Test exception from client");
    }


    @UiHandler("closeButton")
    public void handleClick(ClickEvent event) {
        closeWindow(
                new CloseReason(DialogCloseReason.CANCEL));
    }
}
