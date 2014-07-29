package com.mozido.channels.nextweb.history;

/**
 * @author Alexander Manusovich
 */
public class HistoryActionData {
    private HistoryCommand[] actions;

    public HistoryActionData(HistoryCommand[] actions) {
        this.actions = actions;
    }

    public HistoryCommand[] getActions() {
        return actions;
    }
}
