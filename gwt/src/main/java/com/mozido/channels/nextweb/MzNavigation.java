package com.mozido.channels.nextweb;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.mozido.channels.nextweb.access.FunctionalGroupData;
import com.mozido.channels.nextweb.components.dialog.CloseReason;
import com.mozido.channels.nextweb.components.dialog.MzBootstrapDialog;
import com.mozido.channels.nextweb.components.dialog.MzCloseHandler;
import com.mozido.channels.nextweb.components.dialog.MzDialog;
import com.mozido.channels.nextweb.components.shell.Shell;
import com.mozido.channels.nextweb.history.HistoryItem;
import com.mozido.channels.nextweb.history.HistoryPath;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.MzHistory;
import com.mozido.channels.nextweb.utils.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Navigation manager.
 * All operations related with navigation, pages & windows should go thought this manager
 *
 * @author Alexander Manusovich
 */
public class MzNavigation {
    /**
     * Cache for components. All MzComponent instances go to cache and can be
     * reused in the future. If you don't want this - just implement NotCacheable interface
     */
    private HashMap<String, MzComponent> cache = new HashMap<>();
    /**
     * Active component
     */
    private MzComponent currentComponent;
    /**
     * Last history token which was used
     */
    private String lastHandledHistoryToken;
    /**
     * Main container for all pages
     */
    private Shell shell;
    /**
     * Opened windows
     */
    private Map<String, List<MzDialog>> windows;

    /**
     * Constructor
     */
    public MzNavigation() {
    }

    /**
     * Handler for all history events which came from browser
     *
     * @param event New history token
     */
    public void onHistoryEvent(ValueChangeEvent<String> event) {
        String pageToken = event.getValue();

        /**
         * Don't react to the same history token
         */
        if (lastHandledHistoryToken != null
                && lastHandledHistoryToken.equals(pageToken)) {
            return;
        }
        lastHandledHistoryToken = pageToken;

        /**
         * Close all windows which are not related with this event
         */
        closeOtherWindows(pageToken);

        /**
         * Create iterator for working with history iterator
         */
        HistoryPath history = HistoryPath.parse(pageToken);
        if (history == null) {
            navigateToHomePage();
            return;
        }

        /**
         * Check if we have some of windows with such token
         */
        for (String winKey : getWindows().keySet()) {
            if (pageToken.indexOf(winKey) == 0) {
                for (MzDialog win : getWindows().get(winKey)) {
                    int position = HistoryPath.parse(winKey).getItems().size();
                    win.fireHistoryActions(new HistoryState(history, position - 1));
//                    win.reset();
                }
                return;
            }
        }

        /**
         * Open page
         */
        HistoryState iterator = new HistoryState(history, 0);
        if (iterator.getCurrent() != null && iterator.getCurrent().getId() != null) {
            String id = String.valueOf(iterator.getCurrent().getId());
            /**
             * Looking this component in cache
             */
            String historyName = null;
            if (currentComponent != null) {
                historyName = getHistoryToken(currentComponent.getClass());
            }

            if (currentComponent != null && id.equals(historyName)) {
                /**
                 * Update parameters
                 */
                currentComponent.handleURLParameters(iterator.getCurrent().getParameters());
                /**
                 * If requested component already opened and we have sub-elements (pages > 1) in
                 * history iterator, we have to pass them to component. In another case we shouldn't do anything.
                 */
                waitWhenWillBeDisplayedAndOpenSubPages(currentComponent, iterator);
            } else {
                /**
                 * That is request related with new component and we have to open that
                 */
                openPage(history);
            }
        }
    }

    /**
     * History events can invoke process of creating new windows. And we have to control that
     * process, because there are some situations when new history event open new window, but none closed old one.
     * That is why we manage this and manually close all windows if they were opened for different history token.
     *
     * @param pageToken New history token
     */
    private void closeOtherWindows(String pageToken) {
        Iterator i = getWindows().keySet().iterator();
        while (i.hasNext()) {
            String token = (String) i.next();
            if (!(pageToken.indexOf(token + "/") == 0 || pageToken.equals(token))) {
                List<MzDialog> dialogs = getWindows().get(token);
                if (dialogs != null) {
                    Iterator di = dialogs.iterator();
                    while (di.hasNext()) {
                        ((MzDialog) di.next()).destroy();
                    }
                }
                if (getWindows().containsKey(token)) {
                    i.remove();
                }
            }
        }
    }

    /**
     * Create history token from array of component IDs (there can be class or string) and map with params.
     *
     * @param params Params which should be placed into history token
     * @param path   Array of component IDs
     * @return History token
     */
    public String getHistoryToken(final HashMap<String, String> params,
                                  final Object... path) {
        String historyToken = getHistoryToken(path);

        if (params != null && params.size() > 0) {
            StringBuilder paramsString = new StringBuilder();
            paramsString.append("$");
            boolean first = true;
            for (String key : params.keySet()) {
                if (!first) {
                    paramsString.append(",");
                }
                paramsString
                        .append(key).append(":")
                        .append(params.get(key));
                first = false;
            }
            historyToken += paramsString.toString();
        }

        return historyToken;
    }

    public String getHistoryTokenFromPath(final HistoryPath path) {
        if (path == null || path.getItems() == null || path.getItems().size() == 0) {
            return "";
        }
        return getHistoryTokenFromPath(path, 0, path.getItems().size() - 1);
    }

    public String getHistoryTokenFromPath(final HistoryPath path,
                                          final int startIndex,
                                          final int endIndex) {
        if (path == null || path.getItems() == null || path.getItems().size() == 0) {
            return "";
        }
        String[] tokens = new String[endIndex - startIndex + 1];
        for (int i = startIndex; i <= endIndex; i++) {
            HistoryItem item = path.getItems().get(i);

            if (item.getId() instanceof Class) {
                tokens[i - startIndex] = MzServices.getMzReflectionService()
                        .getHistoryTokenByClass((Class) item.getId());
            } else {
                tokens[i - startIndex] = String.valueOf(item.getId());
            }
            if (item.getParameters() != null && item.getParameters().size() > 0) {
                StringBuilder paramsString = new StringBuilder();

                for (String key : item.getParameters().keySet()) {
                    if (paramsString.length() > 0) {
                        paramsString.append(",");
                    }
                    paramsString
                            .append(key).append(":")
                            .append(item.getParameters().get(key));
                }
                tokens[i - startIndex] += "$" + paramsString.toString();
            }
        }
        return StringUtils.join(tokens, "/");
    }

    /**
     * Create history token from  array of component IDs (class or string itmes)
     *
     * @param path Array of component IDs
     * @return History token
     */
    public String getHistoryToken(final Object... path) {
        String[] pages = convertAllCmpIdsToHistoryTokens(path);

        String historyToken = "";
        if (pages != null && pages.length > 0) {
            historyToken = StringUtils.join(pages, "/");
        }

        return historyToken;
    }

    /**
     * Convert all component IDs (class or strings) to history tokens
     *
     * @param path Array of component IDs
     * @return Array with history tokens
     */
    private String[] convertAllCmpIdsToHistoryTokens(final Object[] path) {
        String[] pages = null;
        if (path != null && path.length > 0) {
            pages = new String[path.length];
            for (int i = 0; i < path.length; i++) {
                if (path[i] instanceof Class) {
                    pages[i] = MzServices.getMzReflectionService()
                            .getHistoryTokenByClass((Class) path[i]);
                } else {
                    pages[i] = String.valueOf(path[i]);
                }
            }
        }
        return pages;
    }

    /**
     * Open page
     */
    public void openPage(final HistoryPath path) {
        final HistoryItem item = path.getItems().get(0);
        /**
         * Try to load component (component will be created if it necessary)
         */
        getComponentByToken(item.getStringId(), new OnComponentReadyAction<MzComponent>() {
            @Override
            public void onReady(final MzComponent componentByClass) {
                currentComponent = componentByClass;

                componentByClass.handleURLParameters(item.getParameters());
                getShell().setContent(componentByClass);

                /**
                 * We should wait when widget will be attached to DOM,
                 * after that we have to open sub page
                 */
                Timer t = new Timer() {
                    @Override
                    public void run() {
                        if (componentByClass.isAttachedToDOM()) {
                            componentByClass.fireHistoryActions(
                                    new HistoryState(path).nextItem());
                        } else {
                            schedule(MzComponent.DELAY_MILLIS);
                        }
                    }
                };
                t.run();
            }

            @Override
            public void cantBeCreated() {
                /**
                 * In case if we can't create component which should be opened, we have to
                 * open default page
                 */
                navigateToHomePage();
            }
        });
    }

    public void openPage(final Class clazz) {
        openPage(HistoryPath.builder().item(clazz).build());
    }

    /**
     * This method loading component by class name. After that create window wrapper for that and
     * display this
     *
     * @param historyState Current history state
     * @param clazz        Class of component
     * @param canClose     Should we display close button in the header of window or not
     * @param closeHandler Close action which should be invoked when window will be closed
     */
    public void openWindow(final HistoryState historyState,
                           final Class clazz,
                           final boolean canClose,
                           final MzCloseHandler closeHandler) {
        getComponentByToken(getHistoryToken(clazz), new OnComponentReadyAction<MzComponent>() {
            @Override
            public void onReady(MzComponent component) {
                /**
                 * For mobile version we are always using glassEnabled = true
                 */
                openWindow(historyState,
                        component,
                        canClose,
                        closeHandler);
            }

            @Override
            public void cantBeCreated() {
                /**
                 * In case if we can't create component which should be opened, we have to
                 * open default page
                 */
                navigateToHomePage();
            }
        });
    }

    /**
     * This method loading component by class name. After that create window wrapper for that and
     * display this
     *
     * @param historyState Current history state
     * @param clazz        Class of component
     */
    public void openWindow(final HistoryState historyState, final Class clazz) {
        openWindow(historyState, clazz, true, null);
    }

    /**
     * Create window wrapper for component and show that
     *
     * @param historyState Current history state
     * @param component    Component which will be placed in the window
     * @param closable     Should we display close button in the header of window or not
     * @param closeHandler Close action which should be invoked when window will be closed
     */
    public void openWindow(final HistoryState historyState,
                           final MzComponent component,
                           final boolean closable,
                           final MzCloseHandler closeHandler) {
        final MzDialog dialog = new MzBootstrapDialog();
        component.setDialog(dialog);

        /**
         * Save point to this dialog in the map token-dialog. We need that point for
         * the case when we will have new history event and if it will be different we
         * have to close all dialogs which were opened for different history token.
         */
        if (!getWindows().containsKey(lastHandledHistoryToken)) {
            getWindows().put(lastHandledHistoryToken, new ArrayList<MzDialog>());
        }
        getWindows().get(lastHandledHistoryToken).add(dialog);
        /**
         * Show window
         */
        dialog.changeTitle(component.getTitle());
        dialog.show(component, closable);

        /**
         * Set close action
         */
        dialog.setCloseHandler(new MzCloseHandler() {
            @Override
            public void onClose(CloseReason action) {
                for (String token : getWindows().keySet()) {
                    Iterator i = getWindows().get(token).iterator();
                    while (i.hasNext()) {
                        MzDialog dialogFromList = (MzDialog) i.next();
                        if (dialogFromList == dialog) {
                            i.remove();
                        }
                    }
                }
                if (closeHandler != null) {
                    closeHandler.onClose(action);
                }
            }
        });

        /**
         * Set history path for component
         */
        if (historyState != null) {
            component.fireHistoryActions(historyState);
        }
    }

    /**
     * Create window wrapper for component and show that
     *
     * @param historyState Current history state
     * @param component    Component which will be placed in the window
     * @param canClose     Should we display close button in the header of window or not
     */
    public void openWindow(final HistoryState historyState,
                           final MzComponent component,
                           final boolean canClose) {
        openWindow(historyState, component, canClose, null);
    }

    /**
     * Create new history event and send it to browser
     *
     * @param params Parameters which should be sent
     * @param path   Path with component IDs
     */
    private void navigateWithParameters(final HashMap<String, String> params,
                                        final Object... path) {
        String historyToken = getHistoryToken(params, path);
        if (historyToken.equalsIgnoreCase(History.getToken())) {
            lastHandledHistoryToken = null;
            MzHistory.fireCurrentHistoryState();
        } else {
            MzHistory.newItem(historyToken, true);
        }
    }

    /**
     * Create new history event and send it to browser
     *
     * @param path Path with component IDs
     */
    public void navigate(final Object... path) {
        navigateWithParameters(null, path);
    }

    public void navigate(final HistoryPath path) {
        String historyToken = getHistoryTokenFromPath(path);
        if (historyToken.equalsIgnoreCase(History.getToken())) {
            lastHandledHistoryToken = null;
            MzHistory.fireCurrentHistoryState();
        } else {
            MzHistory.newItem(historyToken, true);
        }
    }


    /**
     * Navigate to default home page (depends on current mode)
     */
    public static void navigateToHomePage() {
        App.navigation().navigate(App.getHomePage());
    }

    /**
     * Wait when component will be displayed (or don't wait if it is active) and
     * open sub pages
     *
     * @param component Component
     * @param path      Path
     */
    public void waitWhenWillBeDisplayedAndOpenSubPages(
            final MzComponent component, final HistoryState path) {

        if (component.isAttachedToDOM()) {
            component.fireHistoryActions(path);
        } else {
            /**
             * We have to wait when widget will be attached and displayed,
             * after that we can open sub page
             */
            Timer t = new Timer() {
                @Override
                public void run() {
                    if (component.isAttachedToDOM()) {
                        component.fireHistoryActions(path);
                    } else {
                        schedule(MzComponent.DELAY_MILLIS);
                    }
                }
            };
            t.run();
        }
    }

    /**
     * Getting component from cache or if it is not there - make new instance
     *
     * @param token        History token
     * @param onLoadAction Action which should be performed after component will be ready
     * @param <T>          Type of component
     */
    public <T extends MzComponent> void getComponentByToken(
            final String token, final OnComponentReadyAction<T> onLoadAction) {
        if (cache.containsKey(token)) {
            /**
             * Load from cache
             */
            T component = (T) cache.get(token);
            /**
             * Check rights. If we don't have rights to open this component - just
             * ignore request
             */
            Class<? extends MzComponent> clazz = component.getClass();
            if (!MzClientSession.canBeOpened(clazz)) {
                loadAllowedComponent(onLoadAction, clazz);
                return;
            }
            onLoadAction.onReady(component);
        } else {
            /**
             * Create new component
             */
            MzReflectionService mzReflectionService = MzServices.getMzReflectionService();
            Class classByHistoryToken = mzReflectionService.getClassByHistoryToken(token);
            if (classByHistoryToken == null) {
                onLoadAction.cantBeCreated();
            } else {
                /**
                 * Check rights. If we don't have rights to open this component - just
                 * ignore request
                 */
                if (!MzClientSession.canBeOpened(classByHistoryToken)) {
                    loadAllowedComponent(onLoadAction, classByHistoryToken);
                    return;
                }
                String className = classByHistoryToken.getName();
                mzReflectionService.instantiate(className, new OnComponentReadyAction<T>() {
                    @Override
                    public void onReady(T component) {
                        /**
                         * Put component to cache if we can do that (component doesn't implement
                         * NotCacheable interface)
                         */
                        if (!(component instanceof CmpNotCacheable)) {
                            cache.put(token, component);
                        }
                        onLoadAction.onReady(component);
                    }

                    @Override
                    public void cantBeCreated() {
                        onLoadAction.cantBeCreated();
                    }
                });
            }
        }
    }

    /**
     * This method trying to load allowed component instead of requested if we don't have enough access
     *
     * @param clazz        Class of requested component
     * @param onLoadAction Action which should be performed after component will be ready
     * @param <T>          Type of component
     */
    private <T extends MzComponent> void loadAllowedComponent(
            final OnComponentReadyAction<T> onLoadAction,
            final Class<? extends MzComponent> clazz) {
        MzReflectionService mzReflectionService = MzServices.getMzReflectionService();
        FunctionalGroupData functionalData = mzReflectionService.getFunctionalGroupByClass(clazz);

        if (functionalData.ifIsNotAllowedUseInstead() != null) {
            String tokenForNewComponent = mzReflectionService.getHistoryTokenByClass(
                    functionalData.ifIsNotAllowedUseInstead());
            getComponentByToken(tokenForNewComponent, onLoadAction);
        }
    }

    public Map<String, List<MzDialog>> getWindows() {
        if (windows == null) {
            windows = new TreeMap<String, List<MzDialog>>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.length() - o1.length();
                }
            });
        }
        return windows;
    }

    public void setShell(Shell shell) {
        this.shell = shell;
    }

    public Shell getShell() {
        return shell;
    }
}
