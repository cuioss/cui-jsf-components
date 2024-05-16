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
package de.cuioss.jsf.api.application.message;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Provider;

import lombok.NonNull;

/**
 * Helper class simplifying the production of {@link FacesMessage}s.
 * <p>
 * The concrete implementation {@link MessageProducerBean} is
 * {@link RequestScoped}, therefore for injecting it into {@link ViewScoped} or
 * {@link SessionScoped} beans you must use a {@link Provider}.
 * </p>
 */
public interface MessageProducer extends Serializable {

    /**
     * Producer method for simplifying creation of facesMessages.
     *
     * @param messageKey must no be null FacesMessage.Severity severity
     * @param severity   The Severity level of the Message, must not be null.
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     * @return the created facesMessage
     */
    FacesMessage getMessageFor(String messageKey, FacesMessage.Severity severity, Object... parameter);

    /**
     * Convenience Method for
     * {@link #getMessageFor(String, jakarta.faces.application.FacesMessage.Severity, Object...)}
     * that sets in addition the Messages into the {@link FacesContext}
     *
     * @param messagekey  must no be null FacesMessage.Severity severity
     * @param severity    The Severity level of the Message, must not be null.
     * @param componentId identifying the component, may be null.
     * @param parameter   Ellipses of Object Parameter for MessageFormat
     */
    void setFacesMessage(String messagekey, FacesMessage.Severity severity, String componentId, Object... parameter);

    /**
     * Convenience Method for setting Faces messages without resource bundle
     * resolving
     *
     * @param message     must not be null
     * @param severity    must no be null FacesMessage.Severity severity
     * @param componentId identifying the component, may be null.
     * @param parameter   optional Ellipses of Object Parameter for MessageFormat
     */
    void addMessage(@NonNull final String message, @NonNull final FacesMessage.Severity severity,
            final String componentId, final Object... parameter);

    /**
     * Convenience Method for
     * {@link #getMessageFor(String, jakarta.faces.application.FacesMessage.Severity, Object...)}
     * with {@link FacesMessage#SEVERITY_ERROR}
     *
     * @param messageKey must no be null FacesMessage.Severity severity
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     * @return the created facesMessage
     */
    default FacesMessage getErrorMessageFor(String messageKey, Object... parameter) {
        return getMessageFor(messageKey, FacesMessage.SEVERITY_ERROR, parameter);
    }

    /**
     * Convenience Method for
     * {@link #getMessageFor(String, jakarta.faces.application.FacesMessage.Severity, Object...)}
     * with {@link FacesMessage#SEVERITY_INFO}
     *
     * @param messageKey must no be null FacesMessage.Severity severity
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     * @return the created facesMessage
     */
    default FacesMessage getInfoMessageFor(String messageKey, Object... parameter) {
        return getErrorMessageFor(messageKey, FacesMessage.SEVERITY_INFO, parameter);
    }

    /**
     * Convenience Method for setting global Faces messages
     *
     * @param messagekey must no be null FacesMessage.Severity severity
     * @param severity   The Severity level of the Message, must not be null.
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    default void setGlobalMessage(String messagekey, FacesMessage.Severity severity, Object... parameter) {
        setFacesMessage(messagekey, severity, null, parameter);
    }

    /**
     * Convenience Method for setting global Faces messages without resource bundle
     * resolving
     *
     * @param message   must not be null
     * @param severity  must no be null FacesMessage.Severity severity
     * @param parameter optional Ellipses of Object Parameter for MessageFormat
     */
    default void addGlobalMessage(String message, FacesMessage.Severity severity, Object... parameter) {
        addMessage(message, severity, null, parameter);
    }

    /**
     * Convenience Method for setting global info Faces messages
     *
     * @param messagekey must no be null
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    default void setGlobalInfoMessage(String messagekey, Object... parameter) {
        setGlobalMessage(messagekey, FacesMessage.SEVERITY_INFO, parameter);
    }

    /**
     * Convenience Method for setting global error Faces messages
     *
     * @param messagekey must no be null
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    default void setGlobalErrorMessage(String messagekey, Object... parameter) {
        setGlobalMessage(messagekey, FacesMessage.SEVERITY_ERROR, parameter);
    }

    /**
     * Convenience Method for setting global warning Faces messages
     *
     * @param messagekey must no be null
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    default void setGlobalWarningMessage(String messagekey, Object... parameter) {
        setGlobalMessage(messagekey, FacesMessage.SEVERITY_WARN, parameter);
    }

}
