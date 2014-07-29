package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzCallBack;
import com.mozido.channels.nextweb.MzClientSession;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.MzServices;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapButton;
import com.mozido.channels.nextweb.eventbus.EventListener;
import com.mozido.channels.nextweb.eventbus.events.ComponentReadyEvent;
import com.mozido.channels.nextweb.eventbus.events.TestTitleChangeEvent;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.model.ui.FGroup;
import com.mozido.channels.nextweb.utils.glass.Glass;

/**
 * @author Alexander Manusovich
 */
@HistoryToken("otherStuff")
@FunctionalGroup(FGroup.AUTHENTICATED_USERS)
public class OtherDemos extends MzComponent {
    @UiField
    MzBootstrapButton testButton, eventBusTestButton;
    @UiField
    ListBox testList;
    @UiField
    ListBox eventsList;
    @UiField
    MzBootstrapButton loading1Button;
    @UiField
    MzBootstrapButton loading2Button;

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    /**
     * Views
     */
    @UiTemplate("OtherDemos.ui.xml")
    interface WebBinder extends UiBinder<Widget, OtherDemos> {
    }

    @Override
    public void onBind() {
        super.onBind();
        getEventsBus().addListener(ComponentReadyEvent.class,
                new EventListener<ComponentReadyEvent>() {
                    @Override
                    public void onEvent(ComponentReadyEvent e) {
                        eventsList.addItem("Component " + e.getComponent()
                                .getClass().getSimpleName() + " is ready");
                    }
                }
        );
        getEventsBus().addListener(TestTitleChangeEvent.class, new EventListener<TestTitleChangeEvent>() {
            @Override
            public void onEvent(TestTitleChangeEvent e) {
                eventsList.addItem("Title has been changed " + e.getNewTitle());
            }
        });
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @UiHandler("testButton")
    public void handleClick(ClickEvent event) {
        Glass.show();
        MzServices.getUserService().readUserInformation(MzClientSession.getSessionToken(),
                MzClientSession.getLogin(), new MzCallBack<UserDTO>(this) {
                    @Override
                    public void onSuccess(UserDTO userDTO) {
                        Glass.hide();
                        testList.addItem("Server callback: " + userDTO.getFirstName() + " " + userDTO.getLastName());
                    }
                }
        );
    }

    @UiHandler("eventBusTestButton")
    public void handleEventBusTestClick(ClickEvent event) {
        App.navigation().openWindow(null, TestEventBusDialog.class);
    }

    @UiHandler("loading1Button")
    public void handleLoading1Click(ClickEvent event) {
        Glass.show();
        Timer t = new Timer() {
            @Override
            public void run() {
                cancel();
                Glass.hide();
            }
        };
        t.schedule(3000);
    }

    @UiHandler("loading2Button")
    public void handleLoading2Click(ClickEvent event) {
        showLoading();
        Timer t = new Timer() {
            @Override
            public void run() {
                hideLoading();
                Glass.hide();
            }
        };
        t.schedule(3000);
    }
}
