package com.mozido.channels.nextweb.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.components.form.bootstrap.MzBootstrapListItem;
import com.mozido.channels.nextweb.history.HistoryCommand;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.history.SplitCode;
import com.mozido.channels.nextweb.model.ui.FGroup;

/**
 * Home page for user in Web mode
 *
 * @author Alexander Manusovich
 */
@SplitCode
@HistoryToken("home")
@FunctionalGroup(value = {FGroup.AUTHENTICATED_USERS})
public class Home extends MzComponent {
    @UiField
    MzBootstrapListItem pagesDemoLink, dialogsDemoLink, otherDemosLink;
    @UiField
    PagesDemo pagesDemo;
    @UiField
    DialogsDemo dialogsDemo;
    @UiField
    OtherDemos otherDemos;

    /**
     * Tab by default
     */
    private Class activeTab;

    /**
     * Views
     */
    @UiTemplate("Home.ui.xml")
    interface WebBinder extends UiBinder<Widget, Home> {
    }

    @Override
    public void onBind() {
        super.onBind();
        registerHistoryEvent(new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                clearTabs();
            }
        });


        registerHistoryEvent(pagesDemoLink, PagesDemo.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                openServicesPage();

                pagesDemo.fireHistoryActions(historyState);
            }
        });
        registerHistoryEvent(dialogsDemoLink, DialogsDemo.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                openCompanyPage();

                dialogsDemo.fireHistoryActions(historyState);
            }
        });

        registerHistoryEvent(otherDemosLink, OtherDemos.class, new HistoryCommand() {
            @Override
            public void onAction(HistoryState historyState) {
                openOthersPage();

                otherDemos.fireHistoryActions(historyState);
            }
        });
    }

    @Override
    public Widget build() {
        UiBinder uiBinder = GWT.create(WebBinder.class);
        return (Widget) uiBinder.createAndBindUi(this);
    }

    private void openServicesPage() {
        clearTabs();
        activeTab = PagesDemo.class;
        pagesDemoLink.setActive(true);
        pagesDemo.setVisible(true);
    }

    private void openCompanyPage() {
        clearTabs();
        activeTab = OtherDemos.class;
        dialogsDemoLink.setActive(true);
        dialogsDemo.setVisible(true);
    }

    private void openOthersPage() {
        clearTabs();
        activeTab = OtherDemos.class;
        otherDemosLink.setActive(true);
        otherDemos.setVisible(true);
    }

    private void clearTabs() {
        if (activeTab == OtherDemos.class || activeTab == null) {
            dialogsDemoLink.setActive(false);
            dialogsDemo.setVisible(false);
        }

        if (activeTab == PagesDemo.class || activeTab == null) {
            pagesDemoLink.setActive(false);
            pagesDemo.setVisible(false);
        }

        if (activeTab == OtherDemos.class || activeTab == null) {
            otherDemosLink.setActive(false);
            otherDemos.setVisible(false);
        }
    }
}
