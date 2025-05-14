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
 * Decorator for {@link MessageProducer} to log and display
 * {@link ResultObject#getResultDetail()}.
 *
 * @author Matthias Walliczek
 */
@RequestScoped
@EqualsAndHashCode(exclude = {"messageProducer"})
@ToString(exclude = {"messageProducer"})
public class DisplayNameMessageProducer implements Serializable {

    private static final CuiLogger LOGGER = new CuiLogger(DisplayNameMessageProducer.class);

    private static final UIComponent DUMMY = new DummyComponent();

    @Serial
    private static final long serialVersionUID = 3367432860164772689L;

    private static final String NO_FITTING_CONVERTER_REGISTERED = "No fitting converter for [%s] class registered.";

    @Inject
    MessageProducer messageProducer;

    /**
     * Create and add a global faces message, use resolved text from detail
     *
     * @param requestResultObject {@linkplain ResultObject } must not be
     *                            {@code null}
     */
    @SuppressWarnings("squid:S3655")
    public void showAsGlobalMessageAndLog(final ResultObject<?> requestResultObject) {
        FacesMessage.Severity severity = switch (requestResultObject.getState()) {
            case ERROR -> {
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    LOGGER.error(requestResultObject.getResultDetail().get().getCause().get(), ERROR.SILENT_ERROR::format);
                }
                yield FacesMessage.SEVERITY_ERROR;
            }
            case WARNING -> {
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    LOGGER.warn(requestResultObject.getResultDetail().get().getCause().get(), WARN.SILENT_ERROR::format);
                }
                yield FacesMessage.SEVERITY_WARN;
            }
            case INFO -> {
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    LOGGER.info(requestResultObject.getResultDetail().get().getCause().get(), INFO.SILENT_ERROR::format);
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
     * Create and add a global faces message, use resolved text from content
     *
     * @param content  {@linkplain IDisplayNameProvider} must not be {@code null}
     * @param severity {@linkplain FacesMessage#getSeverity()} must not be
     *                 {@code null}
     */
    public void showAsGlobalMessage(final IDisplayNameProvider<?> content, final FacesMessage.Severity severity) {

        final var message = resolve(requireNonNull(content, "content"));
        messageProducer.addGlobalMessage(message, severity);
    }

    /**
     * Resolve a {@linkplain IDisplayNameProvider} via matching converter.
     *
     * @param content to be resolved
     * @return the message string
     */
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
