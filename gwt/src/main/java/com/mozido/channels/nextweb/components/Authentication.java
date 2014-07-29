package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzClientSession;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.MzNavigation;
import com.mozido.channels.nextweb.MzServices;
import com.mozido.channels.nextweb.components.dialog.CloseReason;
import com.mozido.channels.nextweb.components.dialog.DialogCloseReason;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapTextBox;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.CredentialsDTO;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.MzCallBack;
import com.mozido.channels.nextweb.utils.glass.Glass;
import com.mozido.channels.nextweb.validation.Validation;
import com.mozido.channels.nextweb.validation.Validators;

/**
 * Login widget
 */

@HistoryToken("authentication")
public class Authentication extends MzComponent {
    @UiField
    MzBootstrapTextBox login, password;
    @UiField
    MzBootstrapButton submit, register;
    @UiField
    CheckBox rememberMe;

    /**
     * Views
     */
    @UiTemplate("Authentication.ui.xml")
    interface WebBinder extends UiBinder<Widget, Authentication> {
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    public void onBind() {
        super.onBind();

        login.setValidators(Validators.getNotNullValidator());
        password.setValidators(Validators.getNotNullValidator());

        login.autoClickOnEnter(submit);
        password.autoClickOnEnter(submit);

        login.getElement().focus();
    }

    @Override
    protected void onReset() {
        super.onReset();

        rememberMe.setValue(MzClientSession.isRememberUserSupported());
        if (MzClientSession.isRememberUserSupported()) {
            login.setFormValue(MzClientSession.getSavedUser());
            password.getElement().focus();
        } else {
            login.clearValue();
            login.getElement().focus();
        }
    }

    @UiHandler("submit")
    public void onSignIn(ClickEvent event) {
        if (Validation.clear(this)
                .validate(login)
                .validate(password)
                .isValid()) {

            CredentialsDTO credentials = new CredentialsDTO();
            credentials.setLogin(login.getFormValue());
            credentials.setPassword(password.getFormValue());

            Glass.show();
            MzServices.getUserService()
                    .login(credentials, new MzCallBack<UserDTO>(this) {
                        @Override
                        public void onSuccess(UserDTO userDTO) {
                            Glass.hide();
                            successfulAuthentication(new CloseReason<>(
                                    DialogCloseReason.PROCESS_FINISHED, userDTO));
                        }
                    });
        }
    }

    @UiHandler("register")
    public void onRegister(ClickEvent event) {
        App.navigation().navigate(Registration.class);
    }


    private void successfulAuthentication(CloseReason<UserDTO> action) {
        final UserDTO user = action.getData();
        MzClientSession.setUser(login.getFormValue(), user, rememberMe.getValue());
        MzNavigation.navigateToHomePage();
    }
}