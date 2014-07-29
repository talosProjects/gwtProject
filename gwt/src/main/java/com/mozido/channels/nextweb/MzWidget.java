package com.mozido.channels.nextweb;

import com.google.gwt.dom.client.Node;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mozido.channels.nextweb.eventbus.EventBus;
import com.mozido.channels.nextweb.eventbus.EventBusImpl;
import com.mozido.channels.nextweb.eventbus.events.ComponentReadyEvent;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.validation.CanBeValidated;
import com.mozido.channels.nextweb.validation.HasValue;
import com.mozido.channels.nextweb.validation.Validation;
import com.mozido.channels.nextweb.validation.Validator;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

/**
 * MzWidget that is UI with some of logic. Widget can't be presented as page or window. Also you can't create
 * history events and handle them in widget.
 *
 * @author Alexander Manusovich
 */
public abstract class MzWidget extends FlowPanel implements CanBeValidated, HasContainerId {
    /**
     * Each widget or component has own unique ID. That is ID looks like
     * midXXX where XXX any integer number. All sel
     */
    private String containerId = "mz-cid" + Math.round(Math.random() * 0xfffff);
    /**
     * Flag which is present state of created process of widget
     */
    private boolean widgetCreating;
    /**
     * Flag which is says if widget already created or not
     */
    protected boolean widgetCreated;
    /**
     * Flag which is says if widget already initialized or not
     */
    private boolean widgetInitialized;
    /**
     * List of validators which can validate this widget
     */
    private Validator[] validators;

    /**
     * Widgets and components have to define code which is building UI for them
     *
     * @return GWT Widget component
     */
    public abstract Widget build();

    /**
     * Event Bus for notify listeners about state of widget
     */
    private EventBus eventsBus =
            EventBusImpl.getInstance();

    /**
     * Code which is running when widget is loading by GWT
     */
    protected void onLoad() {
        super.onLoad();
        init();
        reset();
    }


    public void init() {
        if (widgetCreating || widgetCreated) {
            return;
        }
        widgetCreating = true;

        HTMLPanel busyPanel = new HTMLPanel("<i style=\"margin-right:1em\" " +
                "class=\"fa fa-rss fa-spin\"></i>Loading...");
        busyPanel.setVisible(false);
        busyPanel.getElement().addClassName("mz-widget-loading");

        Widget widget = build();
        widget.getElement().addClassName("mz-widget-content");

        clear();
        getElement().addClassName("mz-widget");
        getElement().addClassName(containerId);
        add(widget);
        add(busyPanel);

        widgetCreating = false;
        widgetCreated = true;
    }


    protected void showLoading() {
        $("." + containerId + " .mz-widget-content").hide();
        $("." + containerId + " .mz-widget-loading").show();
    }

    protected void hideLoading() {
        $("." + containerId + " .mz-widget-loading").hide();
        $("." + containerId + " .mz-widget-content").show();
    }

    /**
     * When someone wants reset widget or clear it is current state,
     * he has to use reset method.
     */
    public void reset() {
        if (!widgetInitialized) {
            onBind();
            widgetInitialized = true;
        }
        onReset();
    }

    /**
     * Method for initialization components (like js select2). This
     * method will be executed only once after component
     * will be attached & displayed.
     */
    public void onBind() {
    }

    /**
     * Method which should be overridden when you want define some logic after
     * component will be open or reopen again from background.
     */
    protected void onReset() {
        /**
         * Clear all validation messages & errors
         */
        Validation.clear(this);
        /**
         * Notify all possible listeners about the fact that component is ready or being updated
         */
        if (getEventsBus() != null) {
            getEventsBus().fireEvent(new ComponentReadyEvent(this));
        }
    }

    /**
     * Validate this component
     *
     * @return Validation result
     */
    public List<ChannelErrorMessage> validate() {
        List<ChannelErrorMessage> res = null;
        if (this instanceof HasValue
                && getValidators() != null
                && getValidators().length > 0) {
            Object value = ((HasValue) this).getValue();
            for (Validator v : getValidators()) {
                final List<ChannelErrorMessage> messages = v.validate(value);
                if (messages != null) {
                    if (res == null) {
                        res = new ArrayList<>();
                    }
                    res.addAll(messages);
                }
            }
        }
        return res;
    }

    /**
     * Display validaton message for this widget
     *
     * @param messageElement Element which is present message
     */
    @Override
    public void displayValidationMessage(Node messageElement) {
        addStyleName(Validation.CSS_INVALID);
        getElement().getParentNode().insertAfter(messageElement, getElement());
    }

    /**
     * Remove validation messages for that widget
     */
    @Override
    public void clearValidationMessages() {
        removeStyleName(Validation.CSS_INVALID);
        $("." + containerId + " input, div, select").removeClass(Validation.CSS_INVALID);
        $("." + containerId + " .mz-validation-message").remove();
        $("." + containerId + " .mz-validation-message .mz-state-active")
                .removeClass("mz-state-active");
    }

    /**
     * Check if component already presented in DOM
     */
    public boolean isAttachedToDOM() {
        return GQuery.$('.' + containerId).elements().length > 0;
    }

    public void setValidators(Validator... validators) {
        this.validators = validators;
    }

    public Validator[] getValidators() {
        return validators;
    }

    @Override
    public String getContainerId() {
        return containerId;
    }

    public boolean isWidgetInitialized() {
        return widgetInitialized;
    }

    public EventBus getEventsBus() {
        return eventsBus;
    }
}
