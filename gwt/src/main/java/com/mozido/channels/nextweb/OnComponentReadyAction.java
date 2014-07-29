package com.mozido.channels.nextweb;

/**
 * Initialization process of component is not synchronised. That is why we have special action - on ready.
 * When component will be created onReady method will be invoked. If we can't create component (for example
 * current user doesn't have access to that) - cantBeCreated method will be invoked.
 *
 * @author Alexander Manusovich
 */
public interface OnComponentReadyAction<T extends MzComponent> {
    void onReady(T component);

    /**
     * Handler for situations when we can't build component (wrong id or doesn't
     * have enough rights for that)
     */
    void cantBeCreated();
}
