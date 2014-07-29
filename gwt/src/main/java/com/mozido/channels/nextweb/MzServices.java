package com.mozido.channels.nextweb;

import com.google.gwt.core.client.GWT;
import com.mozido.channels.nextweb.services.LoggingService;
import com.mozido.channels.nextweb.services.LoggingServiceAsync;
import com.mozido.channels.nextweb.services.UserService;
import com.mozido.channels.nextweb.services.UserServiceAsync;

/**
 * Services
 *
 * @author Alexander Manusovich
 */
public class MzServices {
    private static UserServiceAsync userService;
    private static LoggingServiceAsync loggingService;
    private static MzReflectionService mzReflectionService;

    static {
        reloadAllServices();
    }

    public static UserServiceAsync getUserService() {
        return userService;
    }

    public static LoggingServiceAsync getLoggingService() {
        return loggingService;
    }

    public static MzReflectionService getMzReflectionService() {
        return mzReflectionService;
    }

    public static void reloadAllServices() {
        userService = GWT.create(UserService.class);
        loggingService = GWT.create(LoggingService.class);
        mzReflectionService = GWT.create(MzReflectionService.class);
    }

}
