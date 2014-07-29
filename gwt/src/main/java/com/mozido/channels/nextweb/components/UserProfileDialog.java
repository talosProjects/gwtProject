package com.mozido.channels.nextweb.components;

import org.gwtbootstrap3.client.ui.Legend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.MzCallBack;
import com.mozido.channels.nextweb.MzClientSession;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.MzServices;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapTextBox;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.history.SplitCode;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.model.ui.FGroup;

@SplitCode
@HistoryToken("userProfileDialog")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class UserProfileDialog extends MzComponent {
    private String userId;
    @UiField
    MzBootstrapTextBox tenantName, roleName, personType, timeZone, timeZoneOffset;
    @UiField
    Legend userName;
    
    public UserProfileDialog() {
        userId = MzClientSession.getUser().getId();
    }

    public UserProfileDialog(String userId) {
        this.userId = userId;
    }

    /**
     * Views
     */
    @UiTemplate("UserProfileDialog.ui.xml")
    interface WebBinder extends UiBinder<Widget, UserProfileDialog> {
    }

    public Widget build() {
        setTitle("User Information");
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    protected void onReset() {
        super.onReset();

        showLoading();
        MzServices.getUserService().readUserInformationById(MzClientSession.getSessionToken(), userId,
                new MzCallBack<UserDTO>(this) {
                    @Override
                    public void onSuccess(final UserDTO userDTO) {
                        hideLoading();
                        userName.setText(userDTO.getFullNameString());
                        tenantName.setFormValue(userDTO.getTenantName());
                        roleName.setFormValue(userDTO.getRoles().toString());
                        personType.setFormValue(userDTO.getPersonType());
                        timeZone.setFormValue(userDTO.getTimezone());
                        timeZoneOffset.setFormValue(userDTO.getTimezoneOffset());
                    }
                }
        );
        
        
        
    }
}
