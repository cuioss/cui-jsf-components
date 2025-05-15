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
package de.cuioss.jsf.api.validator;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.application.message.MessageProducer;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
     * <p>Abstract base class for JSF validators that implements the {@link Validator} interface
     * with type-safe value handling. This class provides a robust foundation for creating custom
     * validators in JSF applications.</p>
     * 
     * <p>Key features:</p>
     * <ul>
     *   <li>Type-safe validation through generic parameter</li>
     *   <li>Proper null handling</li>
     *   <li>JSF API conformance with appropriate null checks</li>
     *   <li>Helper methods for error message creation and exception handling</li>
     * </ul>
     * 
     * <p>To use this class, extend it with a concrete implementation specifying the type
     * of value to be validated, and implement the {@link #validateTypeSave(FacesContext, UIComponent, Object)}
     * method with your validation logic:</p>
     * 
     * <pre>
     * public class EmailValidator extends AbstractValidator&lt;String&gt; {
     *     
     *     &#64;Override
     *     protected void validateTypeSave(FacesContext context, UIComponent component, String value) {
     *         if (value != null &amp;&amp; !value.contains("@")) {
     *             throwValidatorException("invalid_email", value);
     *         }
     *     }
     * }
     * </pre>
     *
     * @author Eugen Fischer
     * @param <T> the type of value to be validated, providing type safety in the validation method
     */
public abstract class AbstractValidator<T> implements Validator<Object> {

    /**
     * Validates the provided value according to the JSF validation lifecycle.
     * <p>
     * This implementation handles the JSF API contract by ensuring proper null checks
     * and type conversion. It delegates the actual validation logic to the
     * {@link #validateTypeSave(FacesContext, UIComponent, Object)} method after
     * performing type conversion.
     * </p>
     *
     * @param context the JSF context for the request being processed
     * @param component the UI component that is being validated
     * @param value the value to be validated, may be null
     * @throws ValidatorException if validation fails
     * @throws NullPointerException if context or component is null (as per JSF API contract)
     * @throws IllegalArgumentException if value cannot be cast to the expected type T
     */
    @Override
    public void validate(final FacesContext context, final UIComponent component, final Object value)
            throws ValidatorException {

        // API contract
        final var checkedContext = requireNonNull(context, "FacesContext must not be null.");

        // API contract
        final var checkedComponent = requireNonNull(component, "UIComponent must not be null");

        if (null == value) {
            // no need to cast null at all
            validateTypeSave(checkedContext, checkedComponent, null);
        } else {
            try {
                @SuppressWarnings("unchecked")
                var checked = (T) value;
                validateTypeSave(checkedContext, checkedComponent, checked);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * Executes validation logic in a type-safe way. This method should contain the
     * actual validation logic for concrete validator implementations.
     * <p>
     * The method is called from {@link #validate(FacesContext, UIComponent, Object)}
     * after ensuring the JSF API contract and performing type conversion.
     * Implementations should throw a {@link ValidatorException} when validation fails,
     * preferably using the {@link #throwValidatorException(String, Object...)} helper method.
     * </p>
     *
     * @param context   the JSF context, guaranteed never {@code null}
     * @param component the UI component being validated, guaranteed never {@code null}
     * @param value     the value to validate, cast to the bounded type T; may be {@code null}
     * @throws ValidatorException if the provided value is not valid according to
     *                           implementation-specific rules
     */
    protected abstract void validateTypeSave(FacesContext context, UIComponent component, T value);

    /**
     * Creates a {@link FacesMessage} with error severity for the given message key and parameters.
     * <p>
     * This utility method resolves the message text using the application's
     * {@link MessageProducer} and the provided message key, which should be defined
     * in the application's message resources.
     * </p>
     *
     * @param msgKey    the resource key for the message text to be looked up
     * @param parameter optional parameters to be interpolated into the message text
     * @return a FacesMessage with error severity containing the resolved text
     */
    public static FacesMessage createErrorMessage(final String msgKey, final Object... parameter) {
        return PortalBeanManager.resolveRequiredBean(MessageProducer.class).getErrorMessageFor(msgKey, parameter);
    }

    /**
     * Convenience method to throw a {@link ValidatorException} with an internationalized error message.
     * <p>
     * This method creates a {@link FacesMessage} using the provided message key and parameters,
     * and then wraps it in a {@link ValidatorException}. It simplifies the common pattern
     * of creating and throwing validation error messages in validator implementations.
     * </p>
     *
     * @param msgKey    key string to be looked up in the application's message resources
     * @param parameter optional parameters to be interpolated into the message text
     * @throws ValidatorException always, containing the resolved error message
     */
    public static void throwValidatorException(final String msgKey, final Object... parameter)
            throws ValidatorException {
        throw new ValidatorException(createErrorMessage(msgKey, parameter));
    }
}
