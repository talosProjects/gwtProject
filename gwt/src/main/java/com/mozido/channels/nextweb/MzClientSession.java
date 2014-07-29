package com.mozido.channels.nextweb;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mozido.channels.nextweb.access.FunctionalGroupData;
import com.mozido.channels.nextweb.model.SessionDTO;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.model.UserKind;
import com.mozido.channels.nextweb.model.ui.FGroup;
import com.mozido.channels.nextweb.utils.CssUtils;
import com.mozido.channels.nextweb.utils.glass.Glass;

import java.util.ArrayList;
import java.util.List;

/**
 * Instance of this class represents user session
 *
 * @author Alexander Manusovich
 */
public class MzClientSession {
    /**
     * Cookies which are used by application for user identification
     */
    private static final String COOKIES_MZ_USER = "MZ_USER";
    private static final String COOKIES_MZ_PROGRAM = "MZ_PROGRAM";
    private static final String COOKIES_MZ_REMEMBER_USER = "MZ_REMEMBER_USER";
    private static final String COOKIES_MZ_SAVED_USER = "MZ_SAVED_USER";
    private static final String COOKIES_MZ_TOKEN = "MZ_TOKEN";
    private static final String COOKIES_MZ_THEME = "MZ_THEME";

    /**
     * Information about user
     */
    private static UserDTO user;
    /**
     * Structure with all combinations - node type - user type for
     * quick access to rights
     */
    private static List<UserKind> userKinds;


    public static SessionDTO getSession() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getSession();
    }

    public static UserDTO getUser() {
        return user;
    }

    public static void setUser(final String login, final UserDTO user,
                               final Boolean remember) {
        Cookies.setCookie(COOKIES_MZ_USER, login);
        Cookies.setCookie(COOKIES_MZ_PROGRAM, user.getTenantName());
        Cookies.setCookie(COOKIES_MZ_TOKEN, user.getSession().getToken());
        if (remember != null) {
            Cookies.setCookie(COOKIES_MZ_REMEMBER_USER, String.valueOf(remember));

            if (remember) {
                Cookies.setCookie(COOKIES_MZ_SAVED_USER, login);
            } else {
                Cookies.setCookie(COOKIES_MZ_SAVED_USER, null);
            }
        }
        MzClientSession.user = user;

        /**
         * Get all kinds
         */
        userKinds = new ArrayList<>();
    }

    /**
     * Check if user has rights to use functional
     */
    public static boolean hasRights(FGroup... functionalList) {
        for (FGroup functional : functionalList) {
            if (functional == FGroup.AUTHENTICATED_USERS) {
                if (getUser() == null) {
                    return false;
                }
            } else if (!App.rights().hasAccess(userKinds, functional)) {
                return false;
            }
        }
        return true;
    }

    public static void logout() {
        Glass.show();
        Cookies.setCookie(COOKIES_MZ_USER, null);
        Cookies.setCookie(COOKIES_MZ_PROGRAM, null);
        Cookies.setCookie(COOKIES_MZ_TOKEN, null);

        MzServices.getUserService()
                .logout(getSession(),
                        new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                reload();
                            }

                            @Override
                            public void onSuccess(Void aVoid) {
                                reload();
                            }
                        }
                );
    }

    public static void reload() {
        reloadJS();
    }

    /**
     * Check if this component can be opened by current user
     *
     * @param clazz Class for component
     * @return result
     */
    public static boolean canBeOpened(Class clazz) {
        MzReflectionService mzReflectionService = MzServices.getMzReflectionService();
        FunctionalGroupData functionalData = mzReflectionService.getFunctionalGroupByClass(clazz);
        return functionalData == null || MzClientSession.hasRights(functionalData.value());
    }


    public static native void reloadJS() /*-{
        $wnd.location.reload();
    }-*/;

    public static String getSessionToken() {
        return Cookies.getCookie(MzClientSession.COOKIES_MZ_TOKEN);
    }

    public static String getLogin() {
        return Cookies.getCookie(MzClientSession.COOKIES_MZ_USER);
    }

    public static boolean isRememberUserSupported() {
        String value = Cookies.getCookie(MzClientSession.COOKIES_MZ_REMEMBER_USER);
        return value != null && !value.isEmpty() && Boolean.valueOf(value);
    }

    public static String getSavedUser() {
        String s = Cookies.getCookie(COOKIES_MZ_SAVED_USER);
        if (s == null || s.isEmpty() || s.equalsIgnoreCase("null")) {
            return "";
        }
        return s;
    }

    public static void saveTheme(final String theme) {
        Cookies.setCookie(COOKIES_MZ_THEME, theme);
    }

    public static String getSavedTheme() {
        String s = Cookies.getCookie(COOKIES_MZ_THEME);
        if (s == null || s.isEmpty() || s.equalsIgnoreCase("null")) {
            return CssUtils.DEFAULT_THEME;
        }
        return s;
    }


}
