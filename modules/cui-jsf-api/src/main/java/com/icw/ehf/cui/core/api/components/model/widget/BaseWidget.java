package com.icw.ehf.cui.core.api.components.model.widget;

import javax.faces.event.ActionEvent;

import com.icw.ehf.cui.core.api.components.css.ContextState;

import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Abstract base implementation of {@link WidgetModel}.
 */
@ToString
@EqualsAndHashCode
public abstract class BaseWidget implements WidgetModel {

    private static final long serialVersionUID = -3597359970570562302L;

    @Override
    public boolean isDisableCoreAction() {
        return MoreStrings.isEmpty(getCoreAction()) || null != getNotificationBoxValue();
    }

    @Override
    public boolean isRenderPrimaryAction() {
        return !MoreStrings.isEmpty(getPrimaryAction());
    }

    @Override
    public String getTitleIcon() {
        return null;
    }

    @Override
    public boolean isDisablePrimaryAction() {
        return null != getNotificationBoxValue();
    }

    @Override
    public String getCoreAction() {
        return null;
    }

    @Override
    public String getPrimaryAction() {
        return null;
    }

    @Override
    public IDisplayNameProvider<?> getPrimaryActionTitle() {
        return null;
    }

    @Override
    public String getTitleValue() {
        return null;
    }

    @Override
    public boolean isRenderContent() {
        return null == getNotificationBoxValue();
    }

    @Override
    public ContextState getNotificationBoxState() {
        return ContextState.WARNING;
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public boolean isRendered() {
        return true;
    }

    @Override
    public void processAction(ActionEvent actionEvent) {
    }

    @Override
    public void startInitialize() {
    }
}
