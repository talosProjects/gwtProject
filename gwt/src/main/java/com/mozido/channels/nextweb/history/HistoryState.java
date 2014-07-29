package com.mozido.channels.nextweb.history;

import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzServices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Iterator for access to history path elements
 *
 * @author Alexander Manusovich
 */
public class HistoryState {
    private HistoryPath path;
    private int currentIndex = -1;
    private Map<String, Integer> possibleHistoryActions;

    public HistoryState(HistoryPath path) {
        this.path = path;
    }

    public HistoryState(HistoryPath path, int currentIndex) {
        this.path = path;
        this.currentIndex = currentIndex;
    }

    public HistoryItem getCurrent() {
        if (currentIndex != -1) {
            return getPath().getItems().get(currentIndex);
        } else {
            return null;
        }
    }

    public Object getCurrentObjectId() {
        String stringId = getCurrent().getStringId();
        Class eventClass = MzServices.getMzReflectionService()
                .getClassByHistoryToken(stringId);

        if (eventClass != null) {
            return eventClass;
        } else {
            return stringId;
        }
    }

    public HistoryPath getPath() {
        return path;
    }


    public HistoryState nextItem() {
        possibleHistoryActions = null;
        currentIndex++;
        return this;
    }

    public HistoryState previousItem() {
        possibleHistoryActions = null;
        currentIndex--;
        return this;
    }

    public String getCurrentURI() {
        return App.navigation().getHistoryTokenFromPath(
                getPath(), 0, currentIndex);
    }

    public Map<String, Integer> getPossibleHistoryActions() {
        if (possibleHistoryActions != null) {
            return possibleHistoryActions;
        }

        possibleHistoryActions = new HashMap<String, Integer>();
        possibleHistoryActions.put("", currentIndex);

        /**
         * Generate all possible actions from this point
         */
        int startIndex = currentIndex + 1;
        for (int i = startIndex; i < getPath().getItems().size(); i++) {
            String historyToken = getPath().getItems().get(i).getStringId();
            if (possibleHistoryActions != null && possibleHistoryActions.size() > 0) {
                Set<String> currentSet = new HashSet<String>(possibleHistoryActions.keySet());
                for (String action : currentSet) {
                    String key;
                    if (action.isEmpty()) {
                        key = action;
                    } else {
                        key = action + "/";
                    }
                    possibleHistoryActions.put(key + historyToken, i);
                }
            } else {
                possibleHistoryActions.put(historyToken, i);
            }
        }

        return possibleHistoryActions;
    }
}
