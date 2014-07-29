package com.mozido.channels.nextweb.utils;

import com.google.gwt.i18n.client.NumberFormat;
import com.mozido.channels.nextweb.model.MoneyDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StringUtils {
    public static String join(String[] strings, String separator) {
        if (strings.length > 1) {
            StringBuilder result = new StringBuilder();
            for (String s : strings) {
                if (result.length() != 0) {
                    result.append(separator);
                }
                result.append(s);
            }
            return result.toString();
        } else if (strings.length == 1) {
            return strings[0];
        }

        return "";
    }

    public static HashMap<String, String> splitParams(String paramsString) {
        HashMap<String, String> result = new HashMap<String, String>();
        String[] params = paramsString.split(",");
        for (String param : params) {
            if (param.indexOf("=") > 0) {
                String key = param.substring(0, param.indexOf('='));
                String value = param.substring(param.indexOf('=') + 1);
                result.put(key, value);
            }
        }
        return result;
    }

    public static String getFormattedAmount(MoneyDTO moneyDTO) {
        NumberFormat numberFormat = NumberFormat.getCurrencyFormat();
        return numberFormat.format(((double) moneyDTO.getValue()) / 100.0);
    }

    public static String getPackageStringForClass(Class clazz) {
        return clazz.getName().substring(0, clazz.getName().lastIndexOf('.'));
    }

    public static String[] splitHistoryString(String pageToken) {
        List<String> result = new ArrayList<String>();
        for (String s : pageToken.split("/")) {
            if (s != null && !s.isEmpty()) {
                result.add(s);
            }
        }
        return result.toArray(new String[1]);
    }

}
