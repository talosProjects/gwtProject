/*
 * Copyright 2010 Google Inc.
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
package com.mozido.channels.nextweb.components.shell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.MzComponent;

/**
 * Application shell for MzEntryPoint sample.
 */
public class Shell extends ResizeComposite {
    /**
     * Views
     */
    @UiTemplate("Shell.ui.xml")
    interface WebBinder extends UiBinder<Widget, Shell> {
    }

    @UiTemplate("ShellMobile.ui.xml")
    interface MobileBinder extends UiBinder<Widget, Shell> {
    }

    /**
     * The panel that holds the content.
     */
    @UiField
    FlowPanel contentPanel;

    @UiField
    ScrollPanel scrollPanel;

    /**
     * The current {@link com.mozido.channels.nextweb.MzComponent} being displayed.
     */
    private MzComponent content;

    /**
     * Construct the {@link Shell}.
     */
    public Shell() {
        // Initialize the ui binder.
        UiBinder uiBinder;
        if (App.isMobileVersion()) {
            uiBinder = GWT.create(MobileBinder.class);
        } else {
            uiBinder = GWT.create(WebBinder.class);
        }
        initWidget((Widget) uiBinder.createAndBindUi(this));

        // Default to no content.
        contentPanel.ensureDebugId("contentPanel");
        scrollPanel.getElement().setId("contentScroll");
        setContent(null);
    }

    /**
     * Set the content to display.
     *
     * @param content the content
     */
    public void setContent(final MzComponent content) {
        this.content = content;

        if (content == null) {
            contentPanel.clear();
        }

        showContent();
    }

    private void showContent() {
        if (content == null) {
            return;
        }
        contentPanel.clear();
        contentPanel.add(content);
    }
}
