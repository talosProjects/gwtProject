package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzClientSession;
import com.mozido.channels.nextweb.MzWidget;
import com.mozido.channels.nextweb.errors.MzNotificationManager;
import com.mozido.channels.nextweb.eventbus.EventListener;
import com.mozido.channels.nextweb.eventbus.events.TestTitleChangeEvent;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.utils.CssUtils;

/**
 * Navigation widget
 * 
 * @author Alexander Manusovich
 */
public class Navigation extends MzWidget {
    @UiField
    Anchor ameliaTheme, ceruleanTheme, cosmoTheme, cyborgTheme,
            darklyTheme, flatlyTheme, lumenTheme, simplexTheme, spacelabTheme,
            superheroTheme, unitedTheme, yetiTheme, userName, userInfo, userList,  logout;

    @UiField
    InlineLabel title;
    @UiField
    Anchor api;

    private boolean authenticated;

    /**
     * Views
     */
    @UiTemplate("Navigation.ui.xml")
    interface WebBinder extends UiBinder<Widget, Navigation> {
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    @Override
    public void onBind() {
        super.onBind();
        userInfo.setVisible(authenticated);
        userList.setVisible(authenticated);
        userName.setVisible(authenticated);
        
        logout.setVisible(authenticated);

        getEventsBus().addListener(TestTitleChangeEvent.class, new EventListener<TestTitleChangeEvent>() {
            @Override
            public void onEvent(TestTitleChangeEvent e) {
                title.setText(e.getNewTitle());
            }
        });
    }

    @Override
    protected void onReset() {
        super.onReset();
        if (authenticated) {
            UserDTO user = MzClientSession.getUser();
            userName.setText(user.getFirstName() + " " + user.getLastName());
            userInfo.setText("viewInfo");
            userList.setText("userList");
        }
    }

    @UiHandler({"ameliaTheme", "ceruleanTheme", "cosmoTheme", "cyborgTheme", "darklyTheme", "flatlyTheme",
            "lumenTheme", "simplexTheme", "spacelabTheme", "superheroTheme", "unitedTheme", "yetiTheme"})
    public void handleClick(ClickEvent event) {
        String theme = ((Anchor) event.getSource()).getText();
        CssUtils.loadTheme(theme);
    }


    @UiHandler("userName")
    public void handleUserNameClick(ClickEvent event) {
        /**
         * Just for example we are passing user id to user profile dialog.
         */
        String user = MzClientSession.getUser().getId();
        App.navigation().openWindow(null, new UserProfileDialog(user), true);
    }

    @UiHandler("userInfo")
    public void handleUserInfoClick(ClickEvent event) {
        /**
         * Just for example we are passing user id to user profile dialog.
         */
        String user = MzClientSession.getUser().getId();
        App.navigation().openWindow(null, new UserProfileDialog(user), true);
        //MzNotificationManager.displayError(MzClientSession.getUser().toString());
    }
 

    @UiHandler("userList")
    public void handleUserListClick(ClickEvent event) {
        /**
         * Just for example we are passing user id to user profile dialog.
         */
        UserDTO user = MzClientSession.getUser();
        
        String search = "Supervisor Role";
        boolean containsSupervisor = false;
        for(String str: user.getRoles()) {
            if(str.trim().contains(search))
            	containsSupervisor = true;
        }
        
        if(containsSupervisor) {
        	MzNotificationManager.displayError("Información de los usuarios dentro de está empresa\n"+user.getCompanyUsers());	
        } else {
        	MzNotificationManager.displayError("El usuario no tiene el rol supervisor");
        }
        
    }
    
    
    
    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @UiHandler({"logout"})
    public void handleLogoutClick(ClickEvent event) {
        MzClientSession.logout();
    }

    @UiHandler("api")
    public void handleAPIClick(ClickEvent event) {
        Window.Location.assign("api/index.html");
    }
}
