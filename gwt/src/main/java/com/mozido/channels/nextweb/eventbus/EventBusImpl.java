package com.mozido.channels.nextweb.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alexander Manusovich
 */
public class EventBusImpl implements EventBus {
    private HashMap<Class, ArrayList<EventListener>> listeners;
    private static EventBus instance;

    @Override
    public void addListener(Class eventType, EventListener listener) {
        if (!getListeners().containsKey(eventType)) {
            getListeners().put(eventType, new ArrayList<EventListener>());
        }
        getListeners().get(eventType).add(listener);
    }

    public HashMap<Class, ArrayList<EventListener>> getListeners() {
        if (listeners == null) {
            listeners = new HashMap<>();
        }
        return listeners;
    }

    @Override
    public void removeListener(EventListener listener) {
        for (Class key : listeners.keySet()) {
            if (getListeners().get(key).contains(listener)) {
                getListeners().get(key).remove(listener);
            }
        }
    }

    @Override
    public void fireEvent(BaseEvent e) {
        if (listeners != null) {
            for (Class key : listeners.keySet()) {
                if (key.equals(e.getClass())) {
                    ArrayList<EventListener> listenerArrayList = getListeners().get(key);
                    if (listenerArrayList != null) {
                        List<EventListener> copy = new ArrayList<>(listenerArrayList);
                        for (EventListener listener : copy) {
                            listener.onEvent(e);
                        }
                    }
                }
            }
        }
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBusImpl();
        }
        return instance;
    }
}
