package com.mozido.channels.nextweb;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.mozido.channels.nextweb.components.InformationDialog;
import com.mozido.channels.nextweb.components.dialog.CloseReason;
import com.mozido.channels.nextweb.components.dialog.MzDialog;
import com.mozido.channels.nextweb.history.HistoryActionData;
import com.mozido.channels.nextweb.history.HistoryCommand;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.MzHistory;
import com.mozido.channels.nextweb.history.SupportsHistory;

import java.util.HashMap;
import java.util.Map;

/**
 * MzComponent can present page or window. You can use history actions in components as well.
 * Also components implements IsReflectionSupported interface which is means that you can
 * create this component via reflection when you have only Class reference.
 *
 * @author Alexander Manusovich
 */
public abstract class MzComponent extends MzWidget implements CmpSupportsReflection {
    /**
     * Some operations with UI requires delays (before now and the moment when UI will be attached to DOM).
     * For that reason we are using Timers with delays = DELAY_MILLIS
     */
    public static final int DELAY_MILLIS = 10;
    /**
     * Default win
     */
    /**
     * When you want open this component as window, we need create special dialog witch will
     * contain all window behaviour and contains this component. This dialog will be created only
     * when you have to open component in new window.
     */
    private MzDialog dialog;
    /**
     * Dialog title
     */
    private String dialogTitle = getClass().getSimpleName();
    /**
     * Each component has own url. URL shows hierarchy and place where is component there
     */
    private HistoryState historyState;
    /**
     * To support browser history we have to use GWT History. That means that all our events for
     * open pages or windows should be presented there as well. We are using special actions
     * for that. When you register action for component, special historyToken is generating for that
     * component and when you click on that component instead of executing action we are invoking history event
     * in GWT History. After that we are handling this even and execute action which was register.
     */
    private HashMap<String, HistoryActionData> registeredHistoryActions;
    /**
     * Parameters which have been passed through URL to this component
     */
    private HashMap<String, String> urlParams;

    /**
     * Constructor
     */
    public MzComponent() {
        registeredHistoryActions = new HashMap<>();
    }

    /**
     * Registration of history event
     *
     * @param handlers Handlers list. There handlers will be used when this event returns
     *                 back to component
     * @return History token for that action
     */
    protected String registerHistoryEvent(final HistoryCommand... handlers) {
        return addHistoryAction(null, handlers);
    }

    /**
     * Registration of history event
     *
     * @param component Component which will invoke that action
     * @param url       Path/url for that action.
     * @param handlers  Handlers list. There handlers will be used when this event returns
     *                  back to component
     * @return History token for that action
     */
    protected void registerHistoryEvent(final SupportsHistory component,
                                        final Object url,
                                        final HistoryCommand... handlers) {
        if (url != null && url instanceof Class) {
            boolean visible = MzClientSession.canBeOpened((Class) url);
            component.setVisible(visible);
        }

        component.setHistoryToken(addHistoryAction(new Object[]{url}, handlers));
        component.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (historyState == null) {
                    InformationDialog dialog = new InformationDialog();
                    dialog.setTitle("Invalid State Alert");
                    dialog.setText("You can't use history actions in this component. " +
                            "One of root components is not supports history.");
                    App.navigation().openWindow(null, dialog, true);
                } else {
                    String root = historyState.getCurrentURI();
                    if (root != null && root.length() > 0) {
                        root = root + "/";
                    }
                    MzHistory.newItem(root + component.getHistoryToken());

                }
            }
        });
    }

    /**
     * Add history action to supported list
     *
     * @param url      Path/url for that action.
     * @param handlers Handlers list. There handlers will be used when this event returns
     *                 back to component
     * @return History token for that action
     */
    private String addHistoryAction(final Object[] url,
                                    final HistoryCommand... handlers) {
        String historyToken = App.navigation().getHistoryToken(null, url);
        registeredHistoryActions.put(historyToken, new HistoryActionData(handlers));
        return historyToken;
    }

    /**
     * Handle history event and execute all actions which is registered
     * for that URL
     */
    public void fireHistoryActions(final HistoryState path) {
        historyState = path;

        Map<String, Integer> possibleActions = historyState.getPossibleHistoryActions();
        if (possibleActions != null && possibleActions.size() > 0) {
            for (String action : possibleActions.keySet()) {
                /** Check all records for this sub string */
                for (String key : registeredHistoryActions.keySet()) {
                    if (action.equalsIgnoreCase(key)) {
                        HistoryActionData info = registeredHistoryActions.get(action);
                        if (info != null && info.getActions() != null) {
                            for (HistoryCommand cmd : info.getActions()) {

                                int index = possibleActions.get(action);
                                HistoryState newIterator =
                                        new HistoryState(historyState.getPath(), index);

                                cmd.onAction(newIterator);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        if (isAttachedToDOM()) {
            super.reset();
        } else {
            /**
             * We have to wait when widget will be attached and displayed,
             * after that we can execute reset
             */
            Timer t = new Timer() {
                @Override
                public void run() {
                    if (isAttachedToDOM()) {
                        MzComponent.super.reset();
                    } else {
                        schedule(DELAY_MILLIS);
                    }
                }
            };
            t.schedule(DELAY_MILLIS);
        }
    }

    /**
     * @param closeReason Reason why we need close this window
     */
    public void closeWindow(CloseReason closeReason) {
        if (dialog != null) {
            dialog.close(closeReason);
        }
    }

    /**
     * @return Default window width
     */
    public int windowWidth() {
        return MzDialog.DEFAULT_WINDOW_WIDTH;
    }

    /**
     * When someone pass parameters via URL they are setting by this method
     *
     * @param params Parameters which are came in from browser URL
     */
    public void handleURLParameters(HashMap<String, String> params) {
        this.urlParams = params;
    }

    public void setDialog(MzDialog dialog) {
        this.dialog = dialog;
    }

    public HistoryState currentHistoryState() {
        return historyState;
    }

    public HashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setTitle(String title) {
        this.dialogTitle = title;
        if (dialog != null) {
            dialog.changeTitle(title);
        }
    }

    public String getTitle() {
        return dialogTitle;
    }
}