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
package de.cuioss.jsf.components.converter;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.model.RangeCounter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * Converter for {@link RangeCounter} implementations.
 *
 * @author Oliver Wolff
 */
@FacesConverter(forClass = RangeCounter.class)
public class RangeCounterConverter extends AbstractConverter<RangeCounter> {

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
