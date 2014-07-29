package com.mozido.channels.nextweb.access;

import com.mozido.channels.nextweb.model.ui.FGroup;

/**
 * Just wrapper for annotation
 *
 * @author Alexander Manusovich
 */
public class FunctionalGroupData {
    private FGroup[] groups;
    private Class ifIsNotAllowedUseInstead;

    public FunctionalGroupData(final FGroup[] groups,
                               final Class ifIsNotAllowedUseInstead) {
        this.groups = groups;
        this.ifIsNotAllowedUseInstead = ifIsNotAllowedUseInstead;
    }

    public FGroup[] value() {
        return groups;
    }

    public Class ifIsNotAllowedUseInstead() {
        return ifIsNotAllowedUseInstead;
    }
}
