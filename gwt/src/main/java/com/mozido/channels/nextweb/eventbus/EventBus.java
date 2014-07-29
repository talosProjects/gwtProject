package com.mozido.channels.nextweb.eventbus;


/**
 * That event bus we need only for Test mode. Test listeners listen when components is ready and can be used.
 *
 * @author Alexander Manusovich
 */
public interface EventBus {
    void addListener(Class eventType, EventListener listener);

    void removeListener(EventListener listener);

    void fireEvent(BaseEvent e);
}
