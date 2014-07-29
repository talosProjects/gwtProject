package com.mozido.channels.nextweb.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alexander Manusovich
 */
public class HistoryPath {
    private List<HistoryItem> items;

    public HistoryPath() {
    }

    public List<HistoryItem> getItems() {
        return items;
    }

    public void setItems(List<HistoryItem> items) {
        this.items = items;
    }

    public static HistoryPathBuilder builder() {
        return new HistoryPathBuilder();
    }

    public static HistoryPath parse(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        HistoryPath historyPath = new HistoryPath();
        historyPath.setItems(new ArrayList<HistoryItem>());
        String[] tokens = path.split("/");

        for (String token : tokens) {
            HistoryItem item = new HistoryItem();
            if (token.contains("$")) {
                /**
                 * Extract parameters
                 */
                int valueDivider = token.indexOf('$');
                String[] params = token.substring(valueDivider + 1).split(",");
                if (params.length > 0) {
                    item.setParameters(new HashMap<String, String>());
                    for (String param : params) {
                        int divider = param.indexOf(':');
                        String key = param.substring(0, divider);
                        String value = param.substring(divider + 1);
                        item.getParameters().put(key, value);
                    }
                }
                item.setId(token.substring(0, valueDivider));
            } else {
                item.setId(token);
            }
            historyPath.getItems().add(item);
        }
        return historyPath;
    }
}
