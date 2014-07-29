package com.mozido.channels.nextweb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.mozido.channels.nextweb.access.AccessRightsDictionary;
import com.mozido.channels.nextweb.components.Home;
import com.mozido.channels.nextweb.components.PagesDemo;
import com.mozido.channels.nextweb.components.shell.Shell;
import com.mozido.channels.nextweb.history.HistoryPath;
import com.mozido.channels.nextweb.utils.StringUtils;

/**
 * Application facade
 *
 * @author Alexander Manusovich
 */
public class App {
    /**
     * Package name for entry point class (all GWT components for that module
     * are living under this package)
     */
    public static final String PACKAGE_ENTRY_POINT =
            StringUtils.getPackageStringForClass(App.class);
    /**
     * Maximum length for mobile phones
     */
    public static final int MAX_PHONE_LENGTH = 12;
    /**
     * Minimum length for mobile phones
     */
    public static final int MIN_PHONE_LENGTH = 7;
    /**
     * Constants which we use in the application
     */
    private static MzConstants constants;
    /**
     * Dictionary with rights for specific users
     */
    private static AccessRightsDictionary rights;
    /**
     * Navigation manager
     */
    private static MzNavigation navigation;


    /**
     * @return Reference for Navigation
     */
    public static MzNavigation navigation() {
        if (navigation == null) {
            navigation = new MzNavigation();
            navigation.setShell(new Shell());

        }
        return navigation;
    }

    /**
     * @return Is that mobile mode?
     */
    public static boolean isMobileVersion() {
        String value = Window.Location.getParameter("mobile");
        return value != null && "true".equals(value);
    }

    /**
     * All string resources which can be localized should be requested
     * by using this method
     *
     * @return Constants
     */
    public static MzConstants constants() {
        if (constants == null) {
            constants = GWT.create(MzConstants.class);
        }
        return constants;
    }

    /**
     * Return URL to home page for current user
     *
     * @return Default path
     */
    public static HistoryPath getHomePage() {
        return HistoryPath.builder()
                .item(Home.class)
                .item(PagesDemo.class)
                .build();
    }

    /**
     * That method should be used if you want check rights for
     * some of functional
     *
     * @return Rights
     */
    public static AccessRightsDictionary rights() {
        if (rights == null) {
            rights = new AccessRightsDictionary();
        }
        return rights;
    }
}
