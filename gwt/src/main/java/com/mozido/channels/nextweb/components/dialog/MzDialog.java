package com.mozido.channels.nextweb.components.dialog;

import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.history.HistoryState;

/**
 * Contract for dialog which can present component
 *
 * @author Alexander Manusovich
 */
public interface MzDialog {
    /**
     * Default window width
     */
    static final int DEFAULT_WINDOW_WIDTH = 500;

    /**
     * @param component    Component which should be wrapped
     * @param canClose     It this flag is set, we are displaying close button
     */
    void show(MzComponent component, boolean canClose);

    /**
     */
    void reset();

    /**
     */
    void fireHistoryActions(HistoryState historyState);

    /**
     * @param closeReason Reason why we wants close dialog
     */
    void close(CloseReason closeReason);

    /**
     */
    void destroy();

    /**
     * @param closeHandler Handler which should be used when dialog will be closed
     */
    void setCloseHandler(MzCloseHandler closeHandler);

    /**
     * Change title
     */
    void changeTitle(final String newTitle);
}
