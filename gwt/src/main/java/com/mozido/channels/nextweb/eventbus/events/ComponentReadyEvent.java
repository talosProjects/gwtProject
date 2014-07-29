package com.mozido.channels.nextweb.eventbus.events;

import com.mozido.channels.nextweb.MzWidget;
import com.mozido.channels.nextweb.eventbus.BaseEvent;

/**
 * @author Alexander Manusovich
 */
public class ComponentReadyEvent extends BaseEvent {
    private MzWidget component;

    public ComponentReadyEvent(MzWidget component) {
        this.component = component;
    }

    public MzWidget getComponent() {
        return component;
    }

    public void setComponent(MzWidget component) {
        this.component = component;
    }
}
