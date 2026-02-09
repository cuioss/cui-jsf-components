/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingModel;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.PostAddToViewEvent;

/**
 * Data model interface for widget components within the patient overview UI.
 * <p>
 * This interface defines the contract for data models that drive widget components
 * in the CUI JSF component library. Widgets are self-contained UI components that
 * display focused content with standardized interaction patterns, commonly used on
 * dashboards and overview pages.
 * </p>
 * <p>
 * The model extends {@link LazyLoadingModel} to support asynchronous data loading
 * patterns, which helps optimize the loading of multiple widgets on a page.
 * </p>
 * <p>
 * Key features supported by this model:
 * </p>
 * <ul>
 *   <li>Widget identification and rendering control</li>
 *   <li>Configurable title and icon display</li>
 *   <li>Primary and core action navigation support</li>
 *   <li>State-based action enablement</li>
 *   <li>Lazy initialization capability</li>
 * </ul>
 * <p>
 * Implementation classes should consider thread safety for their specific use cases.
 * See {@link BaseWidget} for a standard implementation basis.
 * </p>
 *
 * @author Matthias Walliczek
 * @since 1.0
 * @see BaseWidget
 * @see LazyLoadingModel
 * @see DashboardWidgetModel
 */
public interface WidgetModel extends LazyLoadingModel {

    /**
     * Returns the localized title of the widget.
     * <p>
     * This provider is used to generate the localized title text displayed in the
     * widget header. If {@link #getTitleValue()} returns a non-null value, that 
     * value takes precedence over this provider.
     * </p>
     *
     * @return the display name provider for the widget's title, never null
     */
    IDisplayNameProvider<?> getTitle();

    /**
     * Returns the icon identifier for the widget's title area.
     * <p>
     * This icon is typically displayed alongside the title in the widget header.
     * The returned value should be a valid icon identifier recognized by the
     * component's rendering system.
     * </p>
     *
     * @return the name/identifier of the icon, or null if no icon should be displayed
     */
    String getTitleIcon();

    /**
     * Returns a direct title string for the widget.
     * <p>
     * This method allows specifying a literal title string that takes precedence
     * over the localized title provided by {@link #getTitle()}. This is useful for
     * dynamic titles that aren't based on a fixed resource key.
     * </p>
     *
     * @return the direct title value, or null to use the localized title from {@link #getTitle()}
     */
    String getTitleValue();

    /**
     * Returns the navigation outcome for the widget's core action.
     * <p>
     * The core action typically represents the main functionality of the widget,
     * such as navigating to a detail view. This outcome will be used for navigation
     * when the core action is activated.
     * </p>
     *
     * @return the JSF navigation outcome for the core action, or null if no action is available
     */
    String getCoreAction();

    /**
     * Returns the navigation outcome for the widget's primary action.
     * <p>
     * The primary action represents an additional, prominent action available on the
     * widget, typically displayed as a button or link. This outcome will be used for
     * navigation when the primary action is activated.
     * </p>
     *
     * @return the JSF navigation outcome for the primary action, or null if no action is available
     */
    String getPrimaryAction();

    /**
     * Returns the localized title for the primary action.
     * <p>
     * This provider is used to generate the localized text displayed for the
     * widget's primary action button or link.
     * </p>
     *
     * @return the display name provider for the primary action's title, or null if not applicable
     */
    IDisplayNameProvider<?> getPrimaryActionTitle();

    /**
     * Determines whether the core action should be disabled.
     * <p>
     * When true, the core action will be rendered but not interactive. This is typically
     * used when an action is contextually unavailable but should still be visible.
     * </p>
     *
     * @return true if the core action should be disabled, false otherwise
     */
    boolean isDisableCoreAction();

    /**
     * Determines whether the primary action should be rendered.
     * <p>
     * Controls the visibility of the primary action button or link. When false,
     * the primary action will not be displayed at all.
     * </p>
     *
     * @return true if the primary action should be rendered, false otherwise
     */
    boolean isRenderPrimaryAction();

    /**
     * Determines whether the primary action should be disabled.
     * <p>
     * When true, the primary action will be rendered but not interactive. This is typically
     * used when an action is contextually unavailable but should still be visible.
     * </p>
     *
     * @return true if the primary action should be disabled, false otherwise
     */
    boolean isDisablePrimaryAction();

    /**
     * Returns a unique identifier for this widget.
     * <p>
     * This ID is used for sorting and identifying the widget within collections.
     * It should be unique within the scope of widgets displayed together.
     * </p>
     *
     * @return the unique identifier string for this widget, never null
     */
    String getId();

    /**
     * Determines whether the widget should be rendered at all.
     * <p>
     * Controls the overall visibility of the widget. When false, the widget
     * will not be displayed on the page.
     * </p>
     *
     * @return true if the widget should be rendered, false otherwise
     */
    boolean isRendered();

    /**
     * Initiates asynchronous initialization of the widget.
     * <p>
     * This method is typically called during the {@link PostAddToViewEvent} phase
     * to begin loading the widget's data. The implementation should return immediately
     * and perform any time-consuming operations asynchronously.
     * </p>
     * <p>
     * While initialization is in progress, the widget may display a loading indicator.
     * </p>
     */
    void startInitialize();
}
