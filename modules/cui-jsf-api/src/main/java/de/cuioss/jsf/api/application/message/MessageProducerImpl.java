package de.cuioss.jsf.api.application.message;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.application.bundle.CuiResourceBundle;
import lombok.NonNull;
import lombok.Setter;

/**
 * Helper class simplifying the production of {@link FacesMessage}s.
 * <p>
 * It enforces the use of {@link ResourceBundle} by accepting resourceBundle
 * keys only. Therefore it needs the property {@link CuiResourceBundle} to be
 * injected in order to work at all
 * </p>
 * <p>
 * It needs to be session or none scoped in order to be injected into viewScoped
 * / Session Scoped beans.
 * </p>
 *
 * @author Oliver Wolff
 */
public class MessageProducerImpl implements MessageProducer {

    private static final long serialVersionUID = -7009766061728111063L;

    @Setter
    private CuiResourceBundle resourceBundle;

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "messageProducer";

    @Override
    public FacesMessage getMessageFor(final String messageKey, final FacesMessage.Severity severity,
            final Object... parameter) {
        try {
            final var result = MessageFormat.format(this.resourceBundle.getString(messageKey), parameter);
            return new FacesMessage(severity, result, result);
        } catch (final MissingResourceException e) {
            final var missingKey = "missing key : " + messageKey;
            return new FacesMessage(severity, missingKey, missingKey);
        }

    }

    @Override
    public FacesMessage getErrorMessageFor(final String messageKey, final Object... parameter) {
        return getMessageFor(messageKey, FacesMessage.SEVERITY_ERROR, parameter);
    }

    @Override
    public FacesMessage getInfoMessageFor(final String messageKey, final Object... parameter) {
        return getMessageFor(messageKey, FacesMessage.SEVERITY_INFO, parameter);
    }

    @Override
    public void setFacesMessage(final String messagekey, final FacesMessage.Severity severity, final String componentId,
            final Object... parameter) {
        FacesContext.getCurrentInstance().addMessage(componentId, getMessageFor(messagekey, severity, parameter));
    }

    @Override
    public void addMessage(String message, javax.faces.application.FacesMessage.Severity severity, String componentId,
            Object... parameter) {
        final var format = MessageFormat.format(message, parameter);
        FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(severity, format, format));
    }

    @Override
    public void setGlobalMessage(final String messagekey, final FacesMessage.Severity severity,
            final Object... parameter) {
        setFacesMessage(messagekey, severity, null, parameter);
    }

    @Override
    public void addGlobalMessage(@NonNull final String message, @NonNull final FacesMessage.Severity severity,
            final Object... parameter) {
        addMessage(message, severity, null, parameter);
    }

    @Override
    public void setGlobalInfoMessage(final String messagekey, final Object... parameter) {
        setGlobalMessage(messagekey, FacesMessage.SEVERITY_INFO, parameter);
    }

    @Override
    public void setGlobalErrorMessage(final String messagekey, final Object... parameter) {
        setGlobalMessage(messagekey, FacesMessage.SEVERITY_ERROR, parameter);
    }

    @Override
    public void setGlobalWarningMessage(final String messagekey, final Object... parameter) {
        setGlobalMessage(messagekey, FacesMessage.SEVERITY_WARN, parameter);
    }

}
