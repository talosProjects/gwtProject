package com.mozido.channels.nextweb.components.dialog;

/**
 * The type of reason why we are closing dialog
 *
 * @author Alexander Manusovich
 */
public enum DialogCloseReason {
    /**
     * Use that if process which is presented in the dialog has been
     * finished and we don't need this dialog anymore
     */
    PROCESS_FINISHED,
    /**
     * Use that when user wants cancel process which is presented in dialog
     */
    CANCEL
}
