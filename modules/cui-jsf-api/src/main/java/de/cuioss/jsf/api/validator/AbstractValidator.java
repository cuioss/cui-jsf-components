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
 * Abstract validator implements the interface {@linkplain Validator}.<br>
 * The implementation ensured API conform implementation and provide a type safe
 * value handling.<br>
 *
 * @author Eugen Fischer
 * @param <T> bounded type which should be validated
 */
public abstract class AbstractValidator<T> implements Validator<Object> {

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if value has a wrong type.
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
     * Execute validation business in type safe way
     *
     * @param context   {@linkplain FacesContext} guaranteed never <code>null</code>
     * @param component {@linkplain UIComponent} guaranteed never <code>null</code>
     * @param value     of bounded type could be <code>null</code>
     */
    protected abstract void validateTypeSave(FacesContext context, UIComponent component, T value);

    public static FacesMessage createErrorMessage(final String msgKey, final Object... parameter) {
        return PortalBeanManager.resolveRequiredBean(MessageProducer.class).getErrorMessageFor(msgKey, parameter);
    }

    /**
     * Convenience method to throw a {@link ValidatorException} with a i18n message
     * and optional varargs parameters.
     *
     * @param msgKey    key string to be looked up in messages.properties
     * @param parameter (optional) varargs parameters to be included in the
     *                  resulting Exception string.
     */
    public static void throwValidatorException(final String msgKey, final Object... parameter)
            throws ValidatorException {
        throw new ValidatorException(createErrorMessage(msgKey, parameter));
    }

}
