/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.ActionEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * Abstract base implementation of {@link WidgetModel}.
 * <p>
 * This class provides a foundation for widget implementation by offering sensible
 * defaults for many of the {@link WidgetModel} methods. Extending this class
 * significantly reduces the amount of code required to create a new widget type.
 * </p>
 * <p>
 * Key behaviors provided:
 * </p>
 * <ul>
 *   <li>Automatic action disabling when no action is defined</li>
 *   <li>Conditional rendering of primary actions</li>
 *   <li>Default implementation for rendering decisions</li>
 *   <li>Default notification box configuration</li>
 * </ul>
 * <p>
 * Concrete implementations must at minimum override:
 * </p>
 * <ul>
 *   <li>{@link #getTitle()} to provide the widget title</li>
 *   <li>{@link #getId()} to provide a unique widget identifier</li>
 * </ul>
 * <p>
 * This class is thread-safe if subclasses maintain thread safety guarantees.
 * </p>
 * 
 * @author Matthias Walliczek
 * @since 1.0
 * @see WidgetModel
 * @see DashboardWidgetModel
 */
@ToString
@EqualsAndHashCode
public abstract class BaseWidget implements WidgetModel {

    @Serial
    private static final long serialVersionUID = -3597359970570562302L;

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation disables the core action if no action is defined or
     * if there is a notification message to be displayed.
     * </p>
     * 
     * @return true if the core action should be disabled, false otherwise
     */
    @Override
    public boolean isDisableCoreAction() {
        return MoreStrings.isEmpty(getCoreAction()) || null != getNotificationBoxValue();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation renders the primary action only if a primary action
     * outcome is defined.
     * </p>
     * 
     * @return true if the primary action should be rendered, false otherwise
     */
    @Override
    public boolean isRenderPrimaryAction() {
        return !MoreStrings.isEmpty(getPrimaryAction());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns null, indicating no icon should be displayed.
     * Override this method to provide a specific icon.
     * </p>
     * 
     * @return null, indicating no icon
     */
    @Override
    public String getTitleIcon() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation disables the primary action if there is a notification
     * message to be displayed.
     * </p>
     * 
     * @return true if the primary action should be disabled, false otherwise
     */
    @Override
    public boolean isDisablePrimaryAction() {
        return null != getNotificationBoxValue();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns null, indicating no core action.
     * Override this method to provide a specific action outcome.
     * </p>
     * 
     * @return null, indicating no action
     */
    @Override
    public String getCoreAction() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns null, indicating no primary action.
     * Override this method to provide a specific action outcome.
     * </p>
     * 
     * @return null, indicating no action
     */
    @Override
    public String getPrimaryAction() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns null.
     * Override this method to provide a specific title for the primary action.
     * </p>
     * 
     * @return null
     */
    @Override
    public IDisplayNameProvider<?> getPrimaryActionTitle() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns null, deferring to the localized title from
     * {@link #getTitle()}. Override this method to provide a direct title value.
     * </p>
     * 
     * @return null
     */
    @Override
    public String getTitleValue() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation renders content only if there is no notification
     * message to be displayed.
     * </p>
     * 
     * @return true if widget content should be rendered, false otherwise
     */
    @Override
    public boolean isRenderContent() {
        return null == getNotificationBoxValue();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns WARNING as the notification box state.
     * Override this method to provide a different state based on your needs.
     * </p>
     * 
     * @return ContextState.WARNING
     */
    @Override
    public ContextState getNotificationBoxState() {
        return ContextState.WARNING;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns true, indicating the widget is always initialized.
     * Override this method for widgets that require asynchronous initialization.
     * </p>
     * 
     * @return true
     */
    @Override
    public boolean isInitialized() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation returns true, indicating the widget is always rendered.
     * Override this method to conditionally render the widget.
     * </p>
     * 
     * @return true
     */
    @Override
    public boolean isRendered() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation does nothing.
     * Override this method to handle action events triggered by the widget.
     * </p>
     * 
     * @param actionEvent the action event to process
     */
    @Override
    public void processAction(ActionEvent actionEvent) {
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation does nothing.
     * Override this method to implement asynchronous initialization.
     * </p>
     */
    @Override
    public void startInitialize() {
    }
}
