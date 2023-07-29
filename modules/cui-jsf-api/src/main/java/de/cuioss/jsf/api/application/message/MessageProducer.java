package de.cuioss.jsf.api.application.message;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.NonNull;

/**
 * Helper class simplifying the production of {@link FacesMessage}s.
 * <p>
 * It needs to be session or none scoped in order to be injected into viewScoped
 * / Session Scoped beans.
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
     * {@link #getMessageFor(String, javax.faces.application.FacesMessage.Severity, Object...)}
     * with {@link FacesMessage#SEVERITY_ERROR}
     *
     * @param messageKey must no be null FacesMessage.Severity severity
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     * @return the created facesMessage
     */
    FacesMessage getErrorMessageFor(String messageKey, Object... parameter);

    /**
     * Convenience Method for
     * {@link #getMessageFor(String, javax.faces.application.FacesMessage.Severity, Object...)}
     * with {@link FacesMessage#SEVERITY_INFO}
     *
     * @param messageKey must no be null FacesMessage.Severity severity
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     * @return the created facesMessage
     */
    FacesMessage getInfoMessageFor(String messageKey, Object... parameter);

    /**
     * Convenience Method for
     * {@link #getMessageFor(String, javax.faces.application.FacesMessage.Severity, Object...)}
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
     * Convenience Method for setting global Faces messages
     *
     * @param messagekey must no be null FacesMessage.Severity severity
     * @param severity   The Severity level of the Message, must not be null.
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    void setGlobalMessage(String messagekey, FacesMessage.Severity severity, Object... parameter);

    /**
     * Convenience Method for setting global Faces messages without resource bundle
     * resolving
     *
     * @param message   must not be null
     * @param severity  must no be null FacesMessage.Severity severity
     * @param parameter optional Ellipses of Object Parameter for MessageFormat
     */
    void addGlobalMessage(String message, FacesMessage.Severity severity, Object... parameter);

    /**
     * Convenience Method for setting global info Faces messages
     *
     * @param messagekey must no be null
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    void setGlobalInfoMessage(String messagekey, Object... parameter);

    /**
     * Convenience Method for setting global error Faces messages
     *
     * @param messagekey must no be null
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    void setGlobalErrorMessage(String messagekey, Object... parameter);

    /**
     * Convenience Method for setting global warning Faces messages
     *
     * @param messagekey must no be null
     * @param parameter  Ellipses of Object Parameter for MessageFormat
     */
    void setGlobalWarningMessage(String messagekey, Object... parameter);

}
