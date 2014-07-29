package com.mozido.channels.nextweb.history;

import com.mozido.channels.nextweb.MzServices;

import java.util.HashMap;

/**
 * @author Alexander Manusovich
 */
public class HistoryItem {
    private Object id;
    private HashMap<String, String> parameters;

    public HistoryItem() {
    }

    public Object getId() {
        return id;
    }

    public String getStringId() {
        if (getId() == null) {
            return null;
        }
        if (getId() instanceof Class) {
            return MzServices.getMzReflectionService()
                    .getHistoryTokenByClass((Class) getId());
        } else {
            return String.valueOf(getId());
        }
    }

    public void setId(Object id) {
        this.id = id;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }
}
