package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzClientSession;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.MzNavigation;
import com.mozido.channels.nextweb.MzServices;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapTextBox;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.MzCallBack;
import com.mozido.channels.nextweb.utils.glass.Glass;

/**
 * Registration
 */

@HistoryToken("registration")
public class Registration extends MzComponent {
    @UiField
    Anchor termsLink;
    @UiField
    MzBootstrapButton register;
    @UiField
    MzBootstrapTextBox firstName, phone, password, passwordConfirmation,
            pin, pinConfirmation, lastName, email;

    /**
     * Views
     */
    @UiTemplate("Registration.ui.xml")
    interface WebBinder extends UiBinder<Widget, Registration> {
    }


    @Override
    protected void onReset() {
        super.onReset();
        firstName.clearValue();
        lastName.clearValue();
        phone.clearValue();
        email.clearValue();
        password.clearValue();
        passwordConfirmation.clearValue();
        pin.clearValue();
        pinConfirmation.clearValue();
        firstName.getElement().focus();
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @UiHandler("register")
    public void handleRegisterClick(ClickEvent event) {
        Glass.show();
        MzServices.getUserService().register(
                firstName.getFormValue(),
                lastName.getFormValue(),
                password.getFormValue(),
                pin.getFormValue(),
                phone.getFormValue(),
                email.getFormValue(), new MzCallBack<UserDTO>(this) {
                    @Override
                    public void onSuccess(UserDTO userDTO) {
                        Glass.hide();
                        MzClientSession.setUser(phone.getFormValue(), userDTO, true);
                        MzNavigation.navigateToHomePage();
                    }
                }
        );
    }

    @UiHandler("termsLink")
    public void handleClick(ClickEvent event) {
        App.navigation().openWindow(currentHistoryState(), TermsAndConditionals.class);
    }
}
