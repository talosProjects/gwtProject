package com.mozido.channels.nextweb.utils.glass;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTML;
import com.mozido.channels.nextweb.App;

public class Glass {
    private static GlassPanel glassPanel;

    public static void create(String waitMessage) {
        final String html;
        if (!App.isMobileVersion()) {
            glassPanel = new GlassPanel(false, true, "#000", 45);
            html = "<i style=\"color:silver;font-size:2em\" class=\"fa fa-rss fa-spin\"></i>";
        } else {
            glassPanel = new GlassPanel(false, true, "#000", 0);
            html = "<i style=\"color:#222222;font-size:2em\" class=\"fa fa-rss fa-spin\"></i>";
        }
        glassPanel.setGlassEnabled(true);
        glassPanel.add(new HTML(html));
    }

    public static native void scrollToTop() /*-{
        $wnd.scrollTo(0, 0);
    }-*/;

    public static void show() {
        if (App.isMobileVersion()) {
            scrollToTop();
        }
        if (glassPanel != null) {
            Scheduler.get().scheduleDeferred(new Command() {
                public void execute() {
                    glassPanel.center();
                }
            });
        }
    }

    public static void hide() {
        if (glassPanel != null) {
            Scheduler.get().scheduleDeferred(new Command() {
                public void execute() {
                    glassPanel.hide();
                }
            });
        }
    }

}
