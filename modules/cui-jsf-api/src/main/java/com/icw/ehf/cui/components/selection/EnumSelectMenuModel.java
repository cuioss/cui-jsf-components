package com.icw.ehf.cui.components.selection;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.EnumConverter;
import javax.faces.model.SelectItem;

/**
 * Variant for simplified usage with enum.
 *
 * @author Oliver Wolff
 * @param <T> enum bounded type
 */
public class EnumSelectMenuModel<T extends Enum<T>> extends
        AbstractSelectMenuModel<T> {

    private static final long serialVersionUID = -2712127262852018082L;

    private final Class<T> targetClass;
    private transient Converter wrappedConverter;

    /**
     * @param selectableValues
     * @param klass {@linkplain Class} target type
     */
    public EnumSelectMenuModel(final List<SelectItem> selectableValues,
            final Class<T> klass) {
        super(selectableValues);
        this.targetClass = klass;
    }

    @Override
    protected String convertToString(final FacesContext context,
            final UIComponent component, final T value)
        throws ConverterException {
        return getWrappedConverter().getAsString(context, component, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    // Implicitly checked by using
    // com.icw.ehf.cui.components.converter.AbstractConverter<T>
    protected T convertToObject(final FacesContext context, final UIComponent component,
            final String value)
        throws ConverterException {
        return (T) getWrappedConverter().getAsObject(context, component,
                value);
    }

    private Converter getWrappedConverter() {
        if (null == wrappedConverter) {
            wrappedConverter = new EnumConverter(targetClass);
        }
        return wrappedConverter;
    }
}
