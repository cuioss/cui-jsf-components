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
package de.cuioss.jsf.api.converter;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;

import java.util.Objects;

/**
 * Base abstract converter class that implements the JSF {@link Converter} interface with
 * type-safety and proper null/empty handling according to the JSF converter contract.
 * <p>
 * This class streamlines converter development by:
 * <ul>
 *   <li>Implementing proper null-checking for the JSF API contract</li>
 *   <li>Providing type-safe methods through generic typing</li>
 *   <li>Integrating with the {@link MessageProducer} for internationalized error messages</li>
 *   <li>Offering utility methods for common converter validation tasks</li>
 * </ul>
 * 
 * <p>
 * Subclasses need to implement at least the {@link #convertToString(FacesContext, UIComponent, Object)}
 * method. By default, converters are assumed to be formatting-only, and the
 * {@link #convertToObject(FacesContext, UIComponent, String)} method throws an
 * {@link UnsupportedOperationException}. Override this method to support bidirectional conversion.
 * 
 * <p>
 * Usage example:
 * 
 * <pre>
 * public class CustomerConverter extends AbstractConverter&lt;Customer&gt; {
 *     
 *     &#64;Override
 *     protected Customer convertToObject(FacesContext context, UIComponent component, String value) {
 *         try {
 *             Long id = Long.valueOf(value);
 *             return customerService.findById(id);
 *         } catch (NumberFormatException e) {
 *             throwConverterException("converter.customer.invalid_id", value);
 *             return null; // Never reached due to exception
 *         }
 *     }
 *     
 *     &#64;Override
 *     protected String convertToString(FacesContext context, UIComponent component, Customer value) {
 *         return value.getId().toString();
 *     }
 * }
 * </pre>
 * 
 * <p>
 * Thread-safety: Instances of this class are expected to be thread-safe for use within JSF applications
 * as they typically don't maintain instance state that affects conversion behavior.
 *
 * @param <T> The target type for conversion (the Java object type being converted to/from String)
 * @author Matthias Schmidt
 * @since 1.0
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    /**
     * Implements the JSF converter contract for String to Object conversion.
     * <p>
     * This implementation properly handles null values by returning null, and
     * delegates non-null values to the {@link #convertToObject(FacesContext, UIComponent, String)}
     * method for type-specific conversion.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is associated
     * @param value     String value to be converted
     * @return the converted value, or null if the input was null
     * @throws ConverterException if conversion fails
     * @throws NullPointerException if context or component is null
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
     * Implements the JSF converter contract for Object to String conversion.
     * <p>
     * This implementation properly handles null values by returning an empty string,
     * and delegates non-null values to the {@link #convertToString(FacesContext, UIComponent, Object)}
     * method for type-specific conversion.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is associated
     * @param value     The value to be converted 
     * @return the converted string value, or an empty string if the input was null
     * @throws ConverterException if conversion fails
     * @throws NullPointerException if context or component is null
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
     * Converts a non-null String value to the target type.
     * <p>
     * This method is called by {@link #getAsObject(FacesContext, UIComponent, String)}
     * after null checking. The default implementation throws {@link UnsupportedOperationException},
     * making subclasses formatting-only converters by default.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is associated
     * @param value     String value to be converted, guaranteed to be non-null
     * @return the converted value
     * @throws ConverterException if conversion fails
     * @throws UnsupportedOperationException by default implementation
     */
    protected T convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        throw new UnsupportedOperationException("Converter is used for formatting only.");
    }

    /**
     * Converts a non-null object of the target type to a String.
     * <p>
     * This method is called by {@link #getAsString(FacesContext, UIComponent, Object)}
     * after null checking. Subclasses must implement this method to provide
     * the actual conversion logic.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object value is associated
     * @param value     The value to be converted, guaranteed to be non-null
     * @return the converted string value
     * @throws ConverterException if conversion fails
     */
    protected abstract String convertToString(FacesContext context, UIComponent component, T value)
            throws ConverterException;

    /**
     * Utility method to verify a condition and throw a localized {@link ConverterException}
     * if the condition is false.
     * <p>
     * This method is useful for validating the state during conversion.
     * </p>
     *
     * @param expression a boolean expression that should be true
     * @param messageKey key string to be looked up using {@link MessageProducer}
     * @param parameter  optional parameters for the message
     * @throws ConverterException if {@code expression} is false
     */
    protected void checkState(final boolean expression, final String messageKey, final Object... parameter) {
        if (!expression) {
            throwConverterException(messageKey, parameter);
        }
    }

    /**
     * Verifies that a string value is not empty, throwing a localized exception if it is.
     * <p>
     * This method is useful for validating that required string values are not empty.
     * </p>
     *
     * @param value      the string value to be verified
     * @param messageKey key string to be looked up using {@link MessageProducer}
     * @param parameter  optional parameters for the message
     * @return the input value if it is not empty
     * @throws ConverterException if the value is an empty string
     */
    protected String checkNotEmpty(final String value, final String messageKey, final Object... parameter) {
        checkState(!value.isEmpty(), messageKey, parameter);
        return value;
    }

    /**
     * Creates and throws a {@link ConverterException} with a localized message.
     * <p>
     * This method resolves the message using the {@link MessageProducer} and
     * creates an appropriate exception.
     * </p>
     *
     * @param messageKey key string to be looked up using {@link MessageProducer}
     * @param parameter  optional parameters for the message
     * @throws ConverterException with the resolved message
     */
    public static void throwConverterException(final String messageKey, final Object... parameter)
            throws ConverterException {
        throw new ConverterException(
                PortalBeanManager.resolveRequiredBean(MessageProducer.class).getErrorMessageFor(messageKey, parameter));
    }

    /**
     * Verifies the JSF API contract by ensuring that context and component are not null.
     * <p>
     * According to the JSF specification, FacesContext and UIComponent parameters must 
     * not be null.
     * </p>
     *
     * @param context   the FacesContext to verify, must not be null
     * @param component the UIComponent to verify, must not be null
     * @throws NullPointerException if context or component is null
     */
    protected void verifyApiContract(final FacesContext context, final UIComponent component) {
        Objects.requireNonNull(context, "According API, FacesContext must not be null");
        Objects.requireNonNull(component, "According API, UIComponent must not be null.");
    }
}
