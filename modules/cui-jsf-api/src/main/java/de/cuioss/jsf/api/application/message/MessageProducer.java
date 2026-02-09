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
package de.cuioss.jsf.api.application.message;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Provider;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Service interface for simplifying the creation and management of {@link FacesMessage}s
 * in JSF applications.
 * <p>
 * This interface provides methods to:
 * <ul>
 *   <li>Create messages from resource bundle keys with parameter substitution</li>
 *   <li>Add messages to specific components or as global messages</li>
 *   <li>Set messages with different severity levels (INFO, WARN, ERROR)</li>
 * </ul>
 * 
 * <p>
 * The concrete implementation {@link MessageProducerBean} is
 * {@link RequestScoped}, therefore for injecting it into {@link ViewScoped} or
 * {@link SessionScoped} beans you must use a {@link Provider}.
 * 
 * <p>
 * Implementations of this interface are expected to be thread-safe.
 * 
 * @author Matthias Walliczek
 * @since 1.0
 */
public interface MessageProducer extends Serializable {

    /**
     * Creates a {@link FacesMessage} using a message key resolved from a resource bundle.
     * <p>
     * The message key is used to look up the message template in the resource bundle,
     * and the parameters are applied using {@link java.text.MessageFormat}.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param severity   The severity level of the message, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @return The created {@link FacesMessage}
     * @throws NullPointerException if messageKey or severity is null
     */
    FacesMessage getMessageFor(String messageKey, FacesMessage.Severity severity, Object... parameter);

    /**
     * Creates a {@link FacesMessage} and adds it to the {@link FacesContext}.
     * <p>
     * The message key is used to look up the message template in the resource bundle,
     * the parameters are applied using {@link java.text.MessageFormat}, and the resulting
     * message is added to the {@link FacesContext} for the specified component.
     * </p>
     *
     * @param messageKey  The key to look up in the resource bundle, must not be {@code null}
     * @param severity    The severity level of the message, must not be {@code null}
     * @param componentId The ID of the component to associate the message with, or {@code null} for a global message
     * @param parameter   Optional parameters for message formatting
     * @throws NullPointerException if messageKey or severity is null
     */
    void setFacesMessage(String messageKey, FacesMessage.Severity severity, String componentId, Object... parameter);

    /**
     * Adds a direct message to the {@link FacesContext} without resource bundle resolution.
     * <p>
     * This method takes a direct message string rather than a resource bundle key,
     * applies the parameters using {@link java.text.MessageFormat} if provided,
     * and adds the resulting message to the {@link FacesContext} for the specified component.
     * </p>
     *
     * @param message     The message text, must not be {@code null}
     * @param severity    The severity level of the message, must not be {@code null}
     * @param componentId The ID of the component to associate the message with, or {@code null} for a global message
     * @param parameter   Optional parameters for message formatting
     * @throws NullPointerException if message or severity is null
     */
    void addMessage(@NonNull final String message, @NonNull final FacesMessage.Severity severity,
            final String componentId, final Object... parameter);

    /**
     * Creates an error message using a message key from the resource bundle.
     * <p>
     * Convenience method for {@link #getMessageFor(String, FacesMessage.Severity, Object...)}
     * that uses {@link FacesMessage#SEVERITY_ERROR} as the severity level.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @return The created error {@link FacesMessage}
     * @throws NullPointerException if messageKey is null
     */
    default FacesMessage getErrorMessageFor(String messageKey, Object... parameter) {
        return getMessageFor(messageKey, FacesMessage.SEVERITY_ERROR, parameter);
    }

    /**
     * Creates an info message using a message key from the resource bundle.
     * <p>
     * Convenience method for {@link #getMessageFor(String, FacesMessage.Severity, Object...)}
     * that uses {@link FacesMessage#SEVERITY_INFO} as the severity level.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @return The created info {@link FacesMessage}
     * @throws NullPointerException if messageKey is null
     */
    default FacesMessage getInfoMessageFor(String messageKey, Object... parameter) {
        return getErrorMessageFor(messageKey, FacesMessage.SEVERITY_INFO, parameter);
    }

    /**
     * Creates a global message using a message key from the resource bundle.
     * <p>
     * Convenience method for {@link #setFacesMessage(String, FacesMessage.Severity, String, Object...)}
     * that uses {@code null} as the component ID to create a global message.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param severity   The severity level of the message, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @throws NullPointerException if messageKey or severity is null
     */
    default void setGlobalMessage(String messageKey, FacesMessage.Severity severity, Object... parameter) {
        setFacesMessage(messageKey, severity, null, parameter);
    }

    /**
     * Adds a direct message as a global message without resource bundle resolution.
     * <p>
     * Convenience method for {@link #addMessage(String, FacesMessage.Severity, String, Object...)}
     * that uses {@code null} as the component ID to create a global message.
     * </p>
     *
     * @param message   The message text, must not be {@code null}
     * @param severity  The severity level of the message, must not be {@code null}
     * @param parameter Optional parameters for message formatting
     * @throws NullPointerException if message or severity is null
     */
    default void addGlobalMessage(String message, FacesMessage.Severity severity, Object... parameter) {
        addMessage(message, severity, null, parameter);
    }

    /**
     * Creates a global info message using a message key from the resource bundle.
     * <p>
     * Convenience method for {@link #setGlobalMessage(String, FacesMessage.Severity, Object...)}
     * that uses {@link FacesMessage#SEVERITY_INFO} as the severity level.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @throws NullPointerException if messageKey is null
     */
    default void setGlobalInfoMessage(String messageKey, Object... parameter) {
        setGlobalMessage(messageKey, FacesMessage.SEVERITY_INFO, parameter);
    }

    /**
     * Creates a global error message using a message key from the resource bundle.
     * <p>
     * Convenience method for {@link #setGlobalMessage(String, FacesMessage.Severity, Object...)}
     * that uses {@link FacesMessage#SEVERITY_ERROR} as the severity level.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @throws NullPointerException if messageKey is null
     */
    default void setGlobalErrorMessage(String messageKey, Object... parameter) {
        setGlobalMessage(messageKey, FacesMessage.SEVERITY_ERROR, parameter);
    }

    /**
     * Creates a global warning message using a message key from the resource bundle.
     * <p>
     * Convenience method for {@link #setGlobalMessage(String, FacesMessage.Severity, Object...)}
     * that uses {@link FacesMessage#SEVERITY_WARN} as the severity level.
     * </p>
     *
     * @param messageKey The key to look up in the resource bundle, must not be {@code null}
     * @param parameter  Optional parameters for message formatting
     * @throws NullPointerException if messageKey is null
     */
    default void setGlobalWarningMessage(String messageKey, Object... parameter) {
        setGlobalMessage(messageKey, FacesMessage.SEVERITY_WARN, parameter);
    }
}
