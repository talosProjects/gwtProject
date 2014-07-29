package com.mozido.channels.nextweb.utils.glass;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Simple glass panel
 *
 * @author Manusovich Alexander
 */
public class GlassPanel extends PopupPanel {
    private int opacity;
    private String color;

    public GlassPanel(boolean autoHide,
                      boolean modal,
                      String color,
                      int opacity) {
        super(autoHide, modal);
        this.opacity = opacity;
        this.color = color;
    }

    @Override
    public void center() {
        super.center();
        addGlassStyle();
    }

    private void addGlassStyle() {
        String opacityString;
        if (opacity <= 0) {
            opacity = 0;
            opacityString = "0";
        } else if (opacity > 99) {
            opacity = 100;
            opacityString = "1";
        } else {
            opacityString = "0." + opacity;
        }

        if (getGlassElement() != null) {
            Style glassStyle = getGlassElement().getStyle();
            glassStyle.setProperty("width", "100%");
            glassStyle.setProperty("height", "100%");
            glassStyle.setProperty("backgroundColor", color);
            glassStyle.setProperty("opacity", opacityString);
            glassStyle.setProperty("mozOpacity", opacityString);
            glassStyle.setProperty("filter", " alpha(opacity=" + opacity + ")");
        }
    }

    @Override
    public void show() {
        super.show();
        addGlassStyle();
    }
}