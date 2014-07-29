package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.components.dialog.CloseReason;
import com.mozido.channels.nextweb.components.dialog.DialogCloseReason;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.model.UserDTO;

/**
 * Dialog for notification
 */

public class InformationDialog extends MzComponent {
    @UiField
    MzBootstrapButton closeButton;
    @UiField
    InlineLabel message;
    @UiField
    HTML additionalInformation;
    String text, textExtended;

    /**
     * Views
     */
    @UiTemplate("InformationDialog.ui.xml")
    interface WebBinder extends UiBinder<Widget, InformationDialog> {
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    public void onBind() {
        super.onBind();
    }

    @Override
    protected synchronized void onReset() {
        super.onReset();
        message.setText(text);

        if (textExtended != null) {
            additionalInformation.setHTML(textExtended);
        } else {
            additionalInformation.setVisible(false);
        }
    }

    @Override
    public int windowWidth() {
        return 600;
    }

    @UiHandler("closeButton")
    public void handleClick(ClickEvent event) {
        closeWindow(new CloseReason<UserDTO>(
                DialogCloseReason.CANCEL));

    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAdditionalInformation(String text) {
        this.textExtended = text;
    }
}
