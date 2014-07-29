package com.mozido.channels.nextweb.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.Node;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzClientSession;

public class CssUtils {
    public static final String CUSTOM_CSS_ID = "custom-css";
    public static final String DEFAULT_THEME = "";

    private static native HeadElement getHeadElement() /*-{
        return $doc.getElementsByTagName("head")[0];
    }-*/;

    public static void loadTheme(String name) {
        MzClientSession.saveTheme(name);
        CssUtils.loadCustomCSS("bootstrap-themes/" + name
                + "-bootstrap.min.css", CssUtils.CUSTOM_CSS_ID);
    }

    public static void loadCustomCSS(String link, String styleClass) {
        /**
         * Remove old style
         */
        for (int i = 0; i < getHeadElement().getChildCount(); i++) {
            Node e = getHeadElement().getChild(i);
            if (e instanceof LinkElement) {
                LinkElement el = (LinkElement) e;
                if (styleClass.equals(el.getClassName())) {
                    String href = el.getHref();
                    if (href != null && href.contains(link)) {
                        // This style already loaded. Skip change procedure
                        return;
                    }
                    getHeadElement().removeChild(el);
                }
            }
        }

        /**
         * Add new style
         */
        LinkElement linkElem = Document.get().createLinkElement();
        linkElem.setRel("stylesheet");
        linkElem.setType("text/css");
        linkElem.setClassName(styleClass);
        linkElem.setHref(link);
        getHeadElement().appendChild(linkElem);
    }
}
