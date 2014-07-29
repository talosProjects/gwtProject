package com.mozido.channels.nextweb.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alexander Manusovich
 */
public class HistoryPathBuilder {
    private List<HistoryItem> items;
    private HistoryItem active;

    public HistoryPathBuilder item(Object id) {
        if (active != null) {
            getItems().add(active);
        }
        active = new HistoryItem();
        active.setId(id);
        return this;
    }

    public HistoryPathBuilder parameter(String key, String value) {
        if (active == null) {
            throw new IllegalStateException("You have to add item first of all");
        }
        if (active.getParameters() == null) {
            active.setParameters(new HashMap<String, String>());
        }
        active.getParameters().put(key, value);
        return this;
    }

    public HistoryPath build() {
        if (active != null) {
            getItems().add(active);
        }
        if (items == null || items.size() == 0) {
            throw new IllegalStateException("You have to add at least one item to build path");
        }

        HistoryPath path = new HistoryPath();
        path.setItems(items);
        return path;
    }

    private List<HistoryItem> getItems() {
        if (items == null) {
            items = new ArrayList<HistoryItem>();
        }
        return items;
    }
}
