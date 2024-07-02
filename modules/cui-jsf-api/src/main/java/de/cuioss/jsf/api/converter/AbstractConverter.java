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
package de.cuioss.jsf.api.converter;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;

import java.util.Objects;

/**
 * Base abstract converter class which handles null and empty checks as
 * specified in the JSF converter contract. To support i18n conversion
 * exceptions, we provide derived classes the message producer. The runtime type
 * of the payload is given in the generic argument T.
 *
 * @param <T> bounded type for converter
 * @author Matthias Schmidt
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    /**
     * String to Object conversion as specified in JSF converter, plus null check.
     */
    @Override
    public T getAsObject(final FacesContext context, final UIComponent component, final String value) {

        verifyApiContract(context, component);

        if (value == null) {
            return null;
        }

        return convertToObject(context, component, value);
    }

    /**
     * Object to String serialization as specified in the JSF converter contract,
     * null checks and casting to a generic type.
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final T value) {

        verifyApiContract(context, component);

        if (value == null) {
            return "";
        }

        return convertToString(context, component, value);

    }

    /**
     * Typesafe, boilerplate-free method which gets called by
     * Converter.getAsObject(). If not implemented, the implementation assumes a
     * Formatting only converter throwing the appropriate exception.
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is
     *                  associated
     * @param value     String value to be converted, can't be <code>null</code> but
     *                  could be empty String
     * @return converted Object
     * @throws ConverterException
     * @throws UnsupportedOperationException by default implementation
     */
    protected T convertToObject(final FacesContext context, final UIComponent component, final String value)
        throws ConverterException {
        throw new UnsupportedOperationException("Converter is used for formatting only.");
    }

    /**
     * Typesafe, boilerplate-free method which gets called by
     * Converter.getAsString()
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is
     *                  associated
     * @param value     Generic type value to be converted, can't be
     *                  <code>null</code>
     * @return converted String
     * @throws ConverterException
     */
    protected abstract String convertToString(FacesContext context, UIComponent component, T value)
        throws ConverterException;

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param messageKey key string to be looked up in messages.properties
     * @param parameter  (optional) varargs parameters to be included in the
     *                   resulting Exception string.
     * @throws ConverterException if {@code expression} is false
     */
    protected void checkState(final boolean expression, final String messageKey, final Object... parameter) {
        if (!expression) {
            throwConverterException(messageKey, parameter);
        }
    }

    /**
     * Ensures the string value is not empty
     *
     * @param value      to be verified
     * @param messageKey key string to be looked up in messages.properties
     * @param parameter  (optional) varargs parameters to be included in the
     *                   resulting Exception string.
     * @return verified value
     * @throws ConverterException if {@code expression} is false
     */
    protected String checkNotEmpty(final String value, final String messageKey, final Object... parameter) {

        checkState(!value.isEmpty(), messageKey, parameter);
        return value;
    }

    /**
     * Convenience method to throw a ConversionException with an i18n message and
     * optional varargs parameters.
     *
     * @param messageKey key string to be looked up in messages.properties
     * @param parameter  (optional) varargs parameters to be included in the
     *                   resulting Exception string.
     */
    public static void throwConverterException(final String messageKey, final Object... parameter)
        throws ConverterException {

        throw new ConverterException(
            PortalBeanManager.resolveRequiredBean(MessageProducer.class).getErrorMessageFor(messageKey, parameter));
    }

    /**
     * Throws a {@link NullPointerException} if either context is null or component
     * is null.
     *
     * @param context   must not be null
     * @param component must not be null
     */
    protected void verifyApiContract(final FacesContext context, final UIComponent component) {
        Objects.requireNonNull(context, "According API, FacesContext must not be null");
        Objects.requireNonNull(component, "According API, UIComponent must not be null.");
    }
}
