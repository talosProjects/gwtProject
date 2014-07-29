package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.dialog.CloseReason;
import com.mozido.channels.nextweb.components.dialog.DialogCloseReason;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.history.HistoryCommand;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.history.SplitCode;
import com.mozido.channels.nextweb.model.ui.FGroup;

@SplitCode
@HistoryToken("eventsDialog")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class EventsDialog extends MzComponent {
    @UiField
    MzBootstrapButton closeButton, event1Button, event2Button;
    @UiField
    InlineLabel eventMessage;

    private String lastEvent;

    /**
     * Views
     */
    @UiTemplate("EventsDialog.ui.xml")
    interface WebBinder extends UiBinder<Widget, EventsDialog> {
    }

    public Widget build() {
        setTitle("Events Dialog Title");
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    protected void onReset() {
        super.onReset();
        eventMessage.setText("");
    }

    @Override
    public void onBind() {
        super.onBind();

        registerHistoryEvent(event1Button, "eventA", new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                lastEvent = "A";
                eventMessage.setText("Event A occurs!");
            }
        });

        registerHistoryEvent(event2Button, "eventB", new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                lastEvent = "B";
                eventMessage.setText("Event B occurs!");
            }
        });
    }

    @UiHandler("closeButton")
    public void handleClick(ClickEvent event) {
        closeWindow(new CloseReason<>(DialogCloseReason.CANCEL, lastEvent));
    }
}
