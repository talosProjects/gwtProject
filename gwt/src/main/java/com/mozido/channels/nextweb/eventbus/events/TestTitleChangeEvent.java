package com.mozido.channels.nextweb.eventbus.events;

import com.mozido.channels.nextweb.eventbus.BaseEvent;

/**
 * @author Alexander Manusovich
 */
public class TestTitleChangeEvent extends BaseEvent {
    private String newTitle;

    public TestTitleChangeEvent(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }
}
