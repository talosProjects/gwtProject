package com.mozido.channels.nextweb.validation;

/**
 * Interface marks components which can have a value
 *
 * @author Alexander Manusovich
 */
public interface HasValue {
    Object getValue();

    /**
     * Remove value and update component
     */
    void clearValue();
}
