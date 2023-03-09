package com.icw.ehf.cui.core.api.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.uimodel.model.RangeCounter;

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
