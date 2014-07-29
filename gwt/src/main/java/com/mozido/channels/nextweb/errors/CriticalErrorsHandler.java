package com.mozido.channels.nextweb.errors;

import com.mozido.channels.nextweb.MzClientSession;
import com.mozido.channels.nextweb.exception.PlatformErrorData;

import java.util.List;

/**
 * This handler store functional for handling errors which are critical and we need
 * special action for such errors - like invalid session token and etc
 *
 * @author Alexander Manusovich
 */
public class CriticalErrorsHandler {
    public static final String INVALID_TOKEN1 = "MSS1136";
    public static final String INVALID_TOKEN2 = "AUTH004";

    /**
     * Check all list and if we have to something critical - handle that
     *
     * @param errors List of errors
     */
    public static void handle(final List<PlatformErrorData> errors) {
        if (errors != null) {
            for (PlatformErrorData data : errors) {
                if (INVALID_TOKEN1.equalsIgnoreCase(data.getCode())
                        || INVALID_TOKEN2.equalsIgnoreCase(data.getCode())) {
                    MzClientSession.logout();
                }
            }
        }
    }
}
