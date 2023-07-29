package de.cuioss.jsf.api.components.model.widget;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Synchronized;
import lombok.ToString;

/**
 * Abstract implementation of the {@link WidgetModel} supporting deferred
 * loading of the content and/or error message.
 *
 * @param <T> type of the content.
 * @author Matthias Walliczek
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDeferredLoadingWidget<T extends Serializable> extends BaseWidget
        implements DashboardWidgetModel {

    private static final long serialVersionUID = -8914809364978152106L;

    @Getter
    protected T content;

    @Getter
    private boolean initialized;

    protected IDisplayNameProvider<?> errorString;

    @Override
    public IDisplayNameProvider<?> getNotificationBoxValue() {
        return errorString;
    }

    /**
     * initialize the content or set the errorString.
     */
    protected abstract void doInit();

    @Override
    @Synchronized
    public void processAction(ActionEvent actionEvent) {
        if (!isInitialized()) {
            doInit();
            initialized = true;
        }
    }
}
