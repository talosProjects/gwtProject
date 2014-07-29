package com.mozido.channels.nextweb;

/**
 * For some functional (like Validation) we have to have unique component IDs, because in another case
 * we will not be able find correct component on the page. Specially for that we have this contract. It is
 * means that component has to implement the method which is return component ID.
 *
 * @author Alexander Manusovich
 */
public interface HasContainerId {
    /**
     * @return Container ID which should be used for component
     */
    String getContainerId();
}
