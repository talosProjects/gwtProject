package com.mozido.channels.nextweb.eventbus;

/**
 * @author Alexander Manusovich
 */
public interface EventListener<T> {
    void onEvent(T e);
}
