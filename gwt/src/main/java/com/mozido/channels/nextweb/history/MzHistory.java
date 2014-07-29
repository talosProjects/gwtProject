package com.mozido.channels.nextweb.history;

import com.google.gwt.user.client.History;

/**
 * @author Alexander Manusovich
 */
public class MzHistory {
    public static void newItem(String token) {
        History.newItem(token);
    }

    public static void newItem(String token, boolean issueEvent) {
        History.newItem(token, issueEvent);
    }

    public static void fireCurrentHistoryState() {
        History.fireCurrentHistoryState();
    }

    public static void back() {
        History.back();
    }
}
