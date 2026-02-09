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
package de.cuioss.jsf.api.components.model.lazyloading;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;

import java.io.Serializable;

/**
 * Interface defining a model for components with lazy loading capabilities.
 * <p>
 * This interface provides the foundation for implementing components that can defer
 * the loading of their content until it's actually needed, improving initial page
 * load performance. It extends {@link ActionListener} to receive notifications
 * when the component should load its content.
 * </p>
 * <p>
 * Components using this model typically render a loading indicator or placeholder
 * until the content is fully loaded. The model also supports displaying notification
 * messages (e.g., errors during loading) instead of the primary content when needed.
 * </p>
 * <p>
 * Key features of this model:
 * </p>
 * <ul>
 *   <li>Initialization state tracking to avoid redundant loading</li>
 *   <li>Support for display of notification messages when loading fails</li>
 *   <li>Control over content rendering based on initialization state</li>
 *   <li>Integration with JSF component lifecycle through ActionListener</li>
 * </ul>
 * <p>
 * Implementations of this interface should consider thread safety for their specific
 * use cases, especially if the model might be accessed from multiple threads.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * public class MyLazyComponent implements LazyLoadingModel {
 *     
 *     private boolean initialized;
 *     private IDisplayNameProvider&lt;?&gt; errorMessage;
 *     private Data content;
 *     
 *     &#64;Override
 *     public boolean isInitialized() {
 *         return initialized;
 *     }
 *     
 *     &#64;Override
 *     public void processAction(ActionEvent actionEvent) {
 *         try {
 *             content = dataService.loadData();
 *             initialized = true;
 *         } catch (Exception e) {
 *             errorMessage = new SimpleDisplayNameProvider("Failed to load data");
 *             initialized = true;
 *         }
 *     }
 *     
 *     &#64;Override
 *     public IDisplayNameProvider&lt;?&gt; getNotificationBoxValue() {
 *         return errorMessage;
 *     }
 *     
 *     // Other method implementations...
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see LazyLoadingThreadModel
 */
public interface LazyLoadingModel extends ActionListener, Serializable {

    /**
     * Returns the notification message to be displayed when content cannot be loaded.
     * <p>
     * This method returns a display name provider for a notification message that
     * should be shown instead of the normal content when loading has failed or another
     * exceptional condition has occurred. If null is returned, no notification will
     * be displayed.
     * </p>
     * <p>
     * The notification box's state (severity) is determined by {@link #getNotificationBoxState()}.
     * </p>
     *
     * @return a display name provider for the notification message, or null if no
     *         notification should be displayed
     * @see #getNotificationBoxState()
     */
    IDisplayNameProvider<?> getNotificationBoxValue();

    /**
     * Returns the contextual state (severity) for the notification box.
     * <p>
     * This method determines the visual styling of the notification box returned by
     * {@link #getNotificationBoxValue()}. The returned state typically indicates
     * the severity or nature of the notification (e.g., warning, error, info).
     * </p>
     * <p>
     * This value is only relevant when {@link #getNotificationBoxValue()} returns a non-null value.
     * </p>
     *
     * @return the contextual state for the notification box (e.g., ContextState.WARNING)
     * @see ContextState
     */
    ContextState getNotificationBoxState();

    /**
     * Determines whether the component's content should be rendered.
     * <p>
     * This method controls the visibility of the component's primary content.
     * If it returns false, the content will not be rendered, which is typically
     * the case when an error notification is being displayed instead.
     * </p>
     * <p>
     * A common implementation pattern is to return the inverse of whether a
     * notification message exists:
     * </p>
     * <pre>
     * &#64;Override
     * public boolean isRenderContent() {
     *     return getNotificationBoxValue() == null;
     * }
     * </pre>
     *
     * @return true if the component's content should be rendered, false otherwise
     */
    boolean isRenderContent();

    /**
     * Indicates whether the component's content has been initialized.
     * <p>
     * When this method returns false, the lazy loading component will trigger the
     * loading process by calling {@link #processAction(ActionEvent)}. Once loading
     * is complete (whether successful or not), this method should return true to
     * prevent repeated loading attempts.
     * </p>
     * <p>
     * Components may display a loading indicator or placeholder while this method
     * returns false.
     * </p>
     *
     * @return true if the content has been initialized, false if it needs to be loaded
     */
    boolean isInitialized();
}
