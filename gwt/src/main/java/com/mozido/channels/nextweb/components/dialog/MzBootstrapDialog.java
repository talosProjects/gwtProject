package com.mozido.channels.nextweb.components.dialog;

import com.mozido.channels.nextweb.MzComponent;
import com.mozido.channels.nextweb.history.HistoryState;
import com.mozido.channels.nextweb.history.MzHistory;
import org.gwtbootstrap3.client.shared.event.ModalHiddenEvent;
import org.gwtbootstrap3.client.shared.event.ModalHiddenHandler;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;

/**
 * Implementation of dialog for Web mode
 *
 * @author Alexander Manusovich
 */
public class MzBootstrapDialog implements MzDialog {
    private Modal modal;
    private String title;

    private MzCloseHandler closeHandler;

    private MzComponent component;

    public void show(MzComponent widget) {
        show(widget, false);
    }

    @Override
    public void reset() {
        component.reset();
    }

    @Override
    public void fireHistoryActions(HistoryState historyState) {
        component.fireHistoryActions(historyState);
    }

    public void show(MzComponent component, boolean closable) {
        this.component = component;

        modal = new Modal();
        modal.setFade(true);
        modal.setClosable(closable);
        modal.setBackdrop(ModalBackdrop.STATIC);
        modal.setKeyboard(true);

        if (title != null && !title.isEmpty()) {
            modal.setTitle(title);
        } else {
            modal.setTitle(component.getTitle());
        }

        ModalBody cmp = new ModalBody();
        cmp.add(component);

        modal.add(cmp);
        modal.show();

        modal.addHiddenHandler(new ModalHiddenHandler() {
            @Override
            public void onHidden(ModalHiddenEvent evt) {
                close(new CloseReason(DialogCloseReason.CANCEL));
            }
        });
    }

    @Override
    public void destroy() {
        modal.hide();
        component = null;
    }

    public void close(CloseReason closeReason) {
        if (component.currentHistoryState() == null) {
            destroy();
        } else {
            MzHistory.newItem(component.currentHistoryState()
                    .previousItem().getCurrentURI());
        }

        if (closeHandler != null) {
            closeHandler.onClose(closeReason);
        }
    }

    public void setCloseHandler(MzCloseHandler closeHandler) {
        this.closeHandler = closeHandler;
    }

    @Override
    public void changeTitle(String newTitle) {
        if (modal != null) {
            modal.setTitle(newTitle);
        } else {
            this.title = newTitle;
        }
    }
}
