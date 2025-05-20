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
 * A generic "last resort" converter that converts any object to a String by calling its 
 * {@link Object#toString()} method.
 * <p>
 * This converter is designed as a fallback solution when no specific converter is available
 * for a given object type. It provides a simple way to display object values in JSF views
 * when only the string representation is needed.
 * </p>
 * <p>
 * Note that this is a formatting-only converter that works in one direction (Object to String).
 * It does not support converting from String back to an Object, as that would require 
 * type-specific parsing logic. Attempting to call {@link #getAsObject(FacesContext, UIComponent, String)}
 * will throw an {@link UnsupportedOperationException}.
 * </p>
 * <p>
 * Usage example in JSF:
 * </p>
 * <pre>
 * &lt;h:outputText value="#{bean.anyObject}" converter="#{de.cuioss.jsf.components.converter.ObjectToStringConverter}" /&gt;
 * </pre>
 * <p>
 * Or by converter ID:
 * </p>
 * <pre>
 * &lt;h:outputText value="#{bean.anyObject}" converter="de.cuioss.jsf.components.converter.ObjectToStringConverter" /&gt;
 * </pre>
 * <p>
 * This converter relies on the quality of the object's {@link Object#toString()} implementation.
 * If the object doesn't provide a meaningful string representation, consider creating a 
 * specialized converter for that type.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesConverter(ObjectToStringConverter.CONVERTER_ID)
public class ObjectToStringConverter extends AbstractConverter<Object> {

    /** 
     * The converter ID for this converter: "de.cuioss.jsf.components.converter.ObjectToStringConverter".
     * <p>
     * This ID can be used to reference this converter in JSF views.
     * </p>
     */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.ObjectToStringConverter";

    /**
     * Converts any Object to its String representation by calling the object's 
     * {@link Object#toString()} method.
     * <p>
     * This method relies on the object having a meaningful {@code toString()} implementation.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The Object value to be converted, guaranteed to be non-null
     * @return the result of calling {@code value.toString()}
     * @throws ConverterException if conversion fails (not expected in normal operation)
     * @throws NullPointerException if value is null, which should be prevented by the parent class
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final Object value)
            throws ConverterException {
        return value.toString();
    }
}
