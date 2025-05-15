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

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * An identity converter for String values that passes through input unchanged.
 * <p>
 * This converter is designed for the specific cases where a String converter is needed
 * but the default {@link HtmlSanitizingConverter} behavior is not desired. It simply
 * returns the input string without any modification in both directions (String to String
 * and String to String).
 * </p>
 * <p>
 * Unlike most standard string handling in JSF, this converter will not apply any
 * sanitization or transformation, making it suitable for cases where the raw string
 * value must be preserved exactly as entered.
 * </p>
 * <p>
 * Usage example in JSF:
 * </p>
 * <pre>
 * &lt;h:inputText value="#{bean.rawValue}" converter="#{de.cuioss.jsf.api.converter.StringIdentConverter}" /&gt;
 * </pre>
 * <p>
 * Or by converter ID:
 * </p>
 * <pre>
 * &lt;h:inputText value="#{bean.rawValue}" converter="de.cuioss.jsf.api.converter.StringIdentConverter" /&gt;
 * </pre>
 * <p>
 * Security note: This converter performs no sanitization, so it should only be used
 * when the unmodified string data is explicitly required and any security implications
 * are handled elsewhere in the application.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesConverter(StringIdentConverter.CONVERTER_ID)
public class StringIdentConverter extends AbstractConverter<String> {

    /** 
     * The converter ID for this converter: "de.cuioss.jsf.api.converter.StringIdentConverter".
     * <p>
     * This ID can be used to reference this converter in JSF views.
     * </p>
     */
    public static final String CONVERTER_ID = "de.cuioss.jsf.api.converter.StringIdentConverter";

    /**
     * Converts a String value to its String representation, which is the value itself.
     * <p>
     * This implementation simply returns the input value unchanged.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be converted
     * @return the input value unchanged
     * @throws ConverterException if conversion fails (never thrown by this implementation)
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return value;
    }

    /**
     * Converts a String value to a String object, which is the value itself.
     * <p>
     * This implementation simply returns the input value unchanged.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The String value to be converted
     * @return the input value unchanged
     * @throws ConverterException if conversion fails (never thrown by this implementation)
     */
    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return value;
    }
}
