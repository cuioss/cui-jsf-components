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
package de.cuioss.jsf.api.components.model.result_content;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultObject;
import jakarta.faces.application.FacesMessage;

/**
 * Interface for handling the display of messages and controlling content rendering
 * in response to operation results.
 * <p>
 * This interface provides a unified approach for handling user feedback mechanisms 
 * in response to operations, particularly those encapsulated in {@link ResultObject}s.
 * It offers multiple options for displaying feedback (notification boxes, faces messages,
 * or sticky messages) and allows controlling whether the main content should be rendered.
 * </p>
 * <p>
 * The controller typically works in conjunction with {@link ResultErrorHandler} to
 * provide a standardized way of presenting operation outcomes to users, with appropriate
 * visual styling based on the nature of the result.
 * </p>
 * <p>
 * Key features:
 * </p>
 * <ul>
 *   <li>Support for notification boxes attached to specific components</li>
 *   <li>Support for global JSF faces messages</li>
 *   <li>Support for sticky messages that persist across user interactions</li>
 *   <li>Control over content rendering based on operation results</li>
 * </ul>
 * <p>
 * Implementation classes may vary in how and where they display these messages,
 * providing flexibility for different UI layouts and component structures.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 * @see ResultErrorHandler
 * @see ResultObject
 * @see NotificationBoxMessage
 */
public interface ErrorController {

    /**
     * Adds a notification message to be displayed in a notification box.
     * <p>
     * Notification boxes typically appear close to the component they
     * relate to and provide context-specific feedback.
     * The provided state determines the appearance of the notification box.
     * </p>
     * <p>
     * If the value is null, no notification box will be rendered.
     * </p>
     *
     * @param value the message to be displayed in the notification box, or null to
     *             hide the notification box
     * @param state the contextual state determining the visual styling of the notification
     *             box (e.g., warning, error, info)
     */
    void addNotificationBox(IDisplayNameProvider<?> value, ContextState state);

    /**
     * Adds a global JSF faces message.
     * <p>
     * Global faces messages are typically displayed at a designated area of the page
     * (often at the top) and are not tied to a specific component. They follow the
     * standard JSF message severity system.
     * </p>
     * <p>
     * These messages are managed by the JSF framework and follow its lifecycle for
     * display and clearing.
     * </p>
     *
     * @param value the message text to be displayed
     * @param severity the JSF message severity level (e.g., INFO, WARN, ERROR)
     */
    void addGlobalFacesMessage(IDisplayNameProvider<?> value, FacesMessage.Severity severity);

    /**
     * Adds a sticky message that persists across user interactions.
     * <p>
     * Sticky messages are designed to remain visible until explicitly dismissed by
     * the user or the application. They are useful for important information that
     * should not be missed.
     * </p>
     * <p>
     * The implementation determines the exact behavior and appearance of sticky messages,
     * but they typically appear as prominent notifications.
     * </p>
     *
     * @param value the message text to be displayed
     * @param state the contextual state determining the visual styling of the message
     */
    void addStickyMessage(IDisplayNameProvider<?> value, ContextState state);

    /**
     * Controls whether the associated content should be rendered.
     * <p>
     * This method allows components to conditionally render their main content
     * based on the result of operations. For example, in error scenarios, it may
     * be appropriate to hide the main content and only display error messages.
     * </p>
     * <p>
     * The exact definition of "content" depends on the implementing component, but
     * it typically refers to the primary functional elements rather than messages
     * or notifications.
     * </p>
     *
     * @param renderContent true if the content should be rendered, false if it should be hidden
     */
    void setRenderContent(boolean renderContent);
}
