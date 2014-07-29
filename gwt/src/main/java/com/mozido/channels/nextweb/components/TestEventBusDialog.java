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
import com.mozido.channels.nextweb.eventbus.events.TestTitleChangeEvent;
import com.mozido.channels.nextweb.history.HistoryCommand;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.history.SplitCode;
import com.mozido.channels.nextweb.model.ui.FGroup;

@SplitCode
@HistoryToken("testEventBusDialog")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class TestEventBusDialog extends MzComponent {
    @UiField
    MzBootstrapButton changeTitle;

    /**
     * Views
     */
    @UiTemplate("TestEventBusDialog.ui.xml")
    interface WebBinder extends UiBinder<Widget, TestEventBusDialog> {
    }

    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @UiHandler("changeTitle")
    public void handleThrowExceptionClick(ClickEvent event) {
        String newTitle = "New Title" + Math.round(Math.random() * 100);
        getEventsBus().fireEvent(new TestTitleChangeEvent(newTitle));
    }
}
