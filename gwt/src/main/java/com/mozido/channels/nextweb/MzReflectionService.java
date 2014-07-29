package com.mozido.channels.nextweb;

import com.mozido.channels.nextweb.access.FunctionalGroupData;

public interface MzReflectionService {
    <T extends MzComponent> void instantiate(String className, OnComponentReadyAction<T> onLoadAction);

    String getHistoryTokenByClass(final Class clazz);

    Class getClassByHistoryToken(final String token);

    FunctionalGroupData getFunctionalGroupByClass(final Class clazz);
}
