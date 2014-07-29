/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mozido.channels.nextweb;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.mozido.channels.nextweb.components.Authentication;
import com.mozido.channels.nextweb.errors.MzNotificationManager;
import com.mozido.channels.nextweb.history.MzHistory;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.utils.CssUtils;
import com.mozido.channels.nextweb.utils.glass.Glass;

/**
 * Entry point for application
 *
 * @author Alexander Manusovich
 */
public class MzEntryPoint implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        CssUtils.loadTheme(MzClientSession.getSavedTheme());

        registerUnhandledExceptions();
        Glass.create(App.constants().cPleaseWait());
        RootLayoutPanel.get().add(App.navigation().getShell());

        /**
         * Try to load current user from cookies
         */
        final String login = MzClientSession.getLogin();
        String token = MzClientSession.getSessionToken();

        /**
         * Register handler which will handle all history events
         */
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
                App.navigation().onHistoryEvent(stringValueChangeEvent);
            }
        });

        /**
         * Try to load user by using existing token from the cookies
         */
        if (token != null
                && !token.isEmpty()
                && !"null".equalsIgnoreCase(token)) {
            Glass.show();
            /**
             * Read information about user by token, program ID and Account Id.
             */
            MzServices.getUserService().
                    readUserInformation(token, login, new AsyncCallback<UserDTO>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Glass.hide();
                            App.navigation().navigate(Authentication.class);
                        }

                        @Override
                        public void onSuccess(UserDTO user) {
                            Glass.hide();
                            MzClientSession.setUser(login, user, null);
                            MzHistory.fireCurrentHistoryState();
                        }
                    });
        } else {
            App.navigation().navigate(Authentication.class);
        }
    }

    /**
     * Method register special handler for exceptions which can't
     * be (or doesn't have handlers) in the code
     */
    private void registerUnhandledExceptions() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                Throwable uw = unwrap(e);
                AsyncCallback<StackTraceElement[]> deobfuscateTraceCallBack =
                        new AsyncCallback<StackTraceElement[]>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                MzNotificationManager.displayError(
                                        "Client Failure",
                                        "Can't get additional information");
                            }

                            @Override
                            public void onSuccess(StackTraceElement[] trace) {
                                MzNotificationManager.displayStackTrace(trace);
                            }
                        };
                /**
                 * Trying deobfuscate stacktrace
                 */
                MzServices.getLoggingService().
                        logClientException(uw.getStackTrace(), GWT.getPermutationStrongName(),
                                deobfuscateTraceCallBack);
            }

            public Throwable unwrap(Throwable e) {
                if (e instanceof UmbrellaException) {
                    UmbrellaException ue = (UmbrellaException) e;
                    if (ue.getCauses().size() == 1) {
                        return unwrap(ue.getCauses().iterator().next());
                    }
                }
                return e;
            }
        });
    }
}