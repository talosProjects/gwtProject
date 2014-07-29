package com.mozido.channels.nextweb.history;

/**
 * @author Alexander Manusovich
 */
public interface HistoryCommand {
    void onAction(HistoryState historyState);
}
