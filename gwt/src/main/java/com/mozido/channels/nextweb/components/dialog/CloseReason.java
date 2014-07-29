package com.mozido.channels.nextweb.components.dialog;

/**
 * That class contains information about the reason which is explain why
 * user wants close dialog. You have to define type of the reason and also you
 * can add some data which can be important to the actor who initially opened this
 * dialog.
 *
 * @param <T> type of the data
 */
public class CloseReason<T> {
    /**
     * Type of the reason
     */
    private DialogCloseReason type;
    /**
     * Data for the actor who initially opened this dialog
     */
    private T data;

    /**
     * You have to specify at least type of the reason
     */
    public CloseReason(DialogCloseReason type) {
        this.type = type;
    }

    public CloseReason(DialogCloseReason type, T data) {
        this.type = type;
        this.data = data;
    }

    public DialogCloseReason getType() {
        return type;
    }

    public void setType(DialogCloseReason type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
