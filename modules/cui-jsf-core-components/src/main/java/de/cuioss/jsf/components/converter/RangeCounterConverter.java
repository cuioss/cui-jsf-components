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
package de.cuioss.jsf.components.converter;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.model.RangeCounter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * <p>JSF converter that formats {@link RangeCounter} instances into human-readable string
 * representations. A RangeCounter typically represents a count within a known total (like
 * "5 of 10 items") and this converter produces appropriate string formats based on the
 * state of the counter.</p>
 * 
 * <p>The converter produces different formats depending on the state of the RangeCounter:</p>
 * <ul>
 *   <li>Complete range: "{count}/{totalCount}" (e.g., "5/10")</li>
 *   <li>Single value only with count available: "{count}" (e.g., "5")</li>
 *   <li>Single value only with only total available: "{totalCount}" (e.g., "10")</li>
 *   <li>Invalid or incomplete state: empty string</li>
 * </ul>
 * 
 * <p>The converter is registered for the {@link RangeCounter} class, allowing it to be
 * automatically applied to any RangeCounter value expressions in JSF views without
 * explicitly specifying the converter.</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;h:outputText value="#{bean.rangeCounter}" /&gt;
 * </pre>
 * 
 * <p>Note that this converter only supports converting from object to string (displaying),
 * not from string to object (parsing), since RangeCounter is a complex object that would
 * require additional information for reconstruction from a string.</p>
 * 
 * <p>This converter is thread-safe as it contains no mutable instance state.</p>
 *
 * @author Oliver Wolff
 * @see RangeCounter The model object this converter handles
 * @see AbstractConverter The parent class providing converter infrastructure
 * @since 1.0
 */
@FacesConverter(forClass = RangeCounter.class)
public class RangeCounterConverter extends AbstractConverter<RangeCounter> {

    /**
     * <p>Converts a {@link RangeCounter} to its string representation based on the
     * counter's state.</p>
     * 
     * <p>The conversion logic follows these rules:</p>
     * <ul>
     *   <li>If the counter is complete (both count and total count are available),
     *       returns "{count}/{totalCount}"</li>
     *   <li>If the counter has a single value only:
     *       <ul>
     *         <li>If count is available, returns "{count}"</li>
     *         <li>If count is not available but total count is, returns "{totalCount}"</li>
     *       </ul>
     *   </li>
     *   <li>If none of the above conditions are met, returns an empty string</li>
     * </ul>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The RangeCounter to be converted, not null
     * @return A string representation of the RangeCounter based on its state
     * @throws ConverterException If the conversion fails
     */
    @Override
    protected String convertToString(FacesContext context, UIComponent component, RangeCounter value)
            throws ConverterException {
        var resultBuilder = new StringBuilder();
        if (value.isComplete()) {
            resultBuilder.append(value.getCount()).append("/").append(value.getTotalCount());
        } else if (value.isSingleValueOnly()) {
            if (value.isCountAvailable()) {
                resultBuilder.append(value.getCount());
            } else {
                resultBuilder.append(value.getTotalCount());
            }
        }
        return resultBuilder.toString();
    }

}
