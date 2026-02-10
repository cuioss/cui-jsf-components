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

import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.ERROR;
import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.INFO;
import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.WARN;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.common.accessor.ConverterAccessor;
import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultObject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Specialized decorator for {@link MessageProducer} that handles {@link IDisplayNameProvider} objects
 * and {@link ResultObject} instances, displaying and logging their content appropriately.
 * <p>
 * This class provides methods to convert {@link ResultObject} instances into user-visible
 * messages with appropriate severity levels. It also handles the conversion of {@link IDisplayNameProvider}
 * objects to displayable strings using the JSF converter mechanism.
 * </p>
 * <p>
 * The class automatically logs exceptions contained in {@link ResultObject} instances with
 * the appropriate log level based on the result state.
 * </p>
 * <p>
 * This class is not thread-safe. It is designed to be used within a single request context.
 * </p>
 *
 * @author Matthias Walliczek
 * @since 1.0
 */
@RequestScoped
@EqualsAndHashCode(exclude = {"messageProducer"})
@ToString(exclude = {"messageProducer"})
public class DisplayNameMessageProducer implements Serializable {

    private static final CuiLogger LOGGER = new CuiLogger(DisplayNameMessageProducer.class);

    /**
     * A dummy component used for converter operations when no actual component is available.
     */
    private static final UIComponent DUMMY = new DummyComponent();

    @Serial
    private static final long serialVersionUID = 3367432860164772689L;

    /**
     * Error message format string used when no suitable converter can be found for a class.
     */
    private static final String NO_FITTING_CONVERTER_REGISTERED = "No fitting converter for [%s] class registered.";

    /**
     * The underlying message producer used to create and add faces messages.
     */
    private final MessageProducer messageProducer;

    /**
     * Constructs a new DisplayNameMessageProducer with the specified message producer.
     *
     * @param messageProducer the message producer to delegate to, must not be null
     */
    @Inject
    public DisplayNameMessageProducer(final MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    /**
     * Creates and adds a global faces message using the detail information from the provided
     * {@link ResultObject}. Also logs any exceptions with the appropriate log level
     * based on the result state.
     * <p>
     * The severity of the message is determined by the state of the {@link ResultObject}:
     * <ul>
     *   <li>ERROR state → SEVERITY_ERROR</li>
     *   <li>WARNING state → SEVERITY_WARN</li>
     *   <li>INFO state → SEVERITY_INFO</li>
     *   <li>VALID state → SEVERITY_INFO</li>
     * </ul>
     *
     * @param requestResultObject The result object containing the message details and state,
     *                            must not be {@code null}
     * @throws NullPointerException if requestResultObject is null
     */
    @SuppressWarnings("squid:S3655")
    public void showAsGlobalMessageAndLog(final ResultObject<?> requestResultObject) {
        FacesMessage.Severity severity = switch (requestResultObject.getState()) {
            case ERROR -> {
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    LOGGER.error(requestResultObject.getResultDetail().get().getCause().get(), ERROR.SILENT_ERROR);
                }
                yield FacesMessage.SEVERITY_ERROR;
            }
            case WARNING -> {
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    LOGGER.warn(requestResultObject.getResultDetail().get().getCause().get(), WARN.SILENT_ERROR);
                }
                yield FacesMessage.SEVERITY_WARN;
            }
            case INFO -> {
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    LOGGER.info(requestResultObject.getResultDetail().get().getCause().get(), INFO.SILENT_ERROR);
                }
                yield FacesMessage.SEVERITY_INFO;
            }
            case VALID -> FacesMessage.SEVERITY_INFO;
        };

        if (requestResultObject.getResultDetail().isPresent()) {
            showAsGlobalMessage(requestResultObject.getResultDetail().get().getDetail(), severity);
        }
    }

    /**
     * Creates and adds a global faces message using the provided content and severity level.
     * <p>
     * This method resolves the {@link IDisplayNameProvider} to a displayable string
     * and then adds it as a global message with the specified severity.
     * </p>
     *
     * @param content The content provider that supplies the message text, must not be {@code null}
     * @param severity The severity level for the message, must not be {@code null}
     * @throws NullPointerException if content or severity is null
     * @throws IllegalStateException if no suitable converter is found for the content
     */
    public void showAsGlobalMessage(final IDisplayNameProvider<?> content, final FacesMessage.Severity severity) {
        final var message = resolve(requireNonNull(content, "content"));
        messageProducer.addGlobalMessage(message, severity);
    }

    /**
     * Resolves a {@link IDisplayNameProvider} to a displayable string using the JSF converter mechanism.
     * <p>
     * This method attempts to find a suitable converter for the provided content based on its class type.
     * If no converter is found, an {@link IllegalStateException} is thrown.
     * </p>
     *
     * @param content The content provider to be resolved, must not be {@code null}
     * @return The resolved message string
     * @throws NullPointerException if content is null
     * @throws IllegalStateException if no suitable converter is found for the content
     */
    @SuppressWarnings("java:S3740") // Raw type unavoidable: Class<?> from getClass() loses generic info
    public static String resolve(final IDisplayNameProvider<?> content) {
        final Class<?> targetType = content.getClass();
        final var converterAccessor = new ConverterAccessor();
        converterAccessor.setTargetClass(targetType);
        final var converter = converterAccessor.getValue();
        if (null == converter) {
            throw new IllegalStateException(NO_FITTING_CONVERTER_REGISTERED.formatted(targetType.getName()));
        }
        return converter.getAsString(FacesContext.getCurrentInstance(), DUMMY, content);
    }
}
