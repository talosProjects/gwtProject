package com.mozido.channels.nextweb.components.dialog;

public interface MzCloseHandler<T> {
    void onClose(CloseReason<T> action);
}
