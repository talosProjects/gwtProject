package com.mozido.channels.nextweb.errors;

import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.components.InformationDialog;

/**
 * Manager for displaying errors
 *
 * @author Alexander Manusovich
 */
public class MzNotificationManager {
    /**
     * Error manager has own dialog which can be used for error displaying
     */
    public static InformationDialog dialog;

    /**
     * Display simple text about error
     *
     * @param text Text
     */
    public static void displayError(final String text) {
        displayError(text, null);
    }

    /**
     * Display text about error and also additional information
     * about that error in the special block after main text
     *
     * @param text                  Main text about error
     * @param additionalInformation Additional text
     */
    public static void displayError(final String text,
                                    final String additionalInformation) {
        if (dialog == null) {
            dialog = new InformationDialog();
        }
        dialog.setText(text);
        if (additionalInformation != null) {
            dialog.setAdditionalInformation(additionalInformation);
        }
        App.navigation().openWindow(null, dialog, true);
    }

    /**
     * Display error with stack trace
     *
     * @param trace Stack trace for error
     */
    public static void displayStackTrace(final StackTraceElement[] trace) {
        String exception = "";
        for (StackTraceElement s : trace) {
            String line = s.getClassName() + "."
                    + s.getMethodName() + ":" + s.getLineNumber();
            if (line.contains(App.PACKAGE_ENTRY_POINT)) {
                line = "<span style='color:blue'>" + line + "</span>";
            }
            line = line.replace(".", ". ");
            exception = exception + line + "<br/>";
        }
        MzNotificationManager.displayError("Client Failure", exception);
    }

}
