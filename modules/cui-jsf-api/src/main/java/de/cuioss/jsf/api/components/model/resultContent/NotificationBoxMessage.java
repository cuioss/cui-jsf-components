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
package de.cuioss.jsf.api.components.model.resultContent;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

/**
 * Immutable value object representing a notification message with its associated contextual state.
 * <p>
 * This class encapsulates the two key aspects of a notification:
 * </p>
 * <ul>
 *   <li>The message content, represented by an {@link IDisplayNameProvider}</li>
 *   <li>The contextual state (severity), represented by a {@link ContextState}</li>
 * </ul>
 * <p>
 * It serves as a data transfer object between components and services that need to
 * communicate notification information. Using this class instead of separate parameters
 * helps maintain consistency and makes notification handling more cohesive.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Create a warning notification
 * var message = new NotificationBoxMessage(
 *     new SimpleDisplayNameProvider("Data validation failed"),
 *     ContextState.WARNING
 * );
 * 
 * // Pass it to a component
 * notificationComponent.setMessage(message);
 * </pre>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ErrorController
 * @see ContextState
 * @see IDisplayNameProvider
 */
@Value
public class NotificationBoxMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 170134856997455422L;

    /**
     * The notification message content.
     * <p>
     * This provider is responsible for generating the text displayed in the notification.
     * Using a display name provider allows for localized or dynamically generated messages.
     * </p>
     */
    private IDisplayNameProvider notificationBoxValue;

    /**
     * The contextual state of the notification.
     * <p>
     * Determines the visual styling and semantic meaning of the notification.
     * Common states include SUCCESS, INFO, WARNING, and DANGER.
     * </p>
     * 
     * @see ContextState
     */
    private ContextState notificationBoxState;
}
