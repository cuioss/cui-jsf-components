package com.icw.ehf.cui.components.selection;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * Composite of {@linkplain AbstractSelectMenuModel} and converter
 */
public class StringBasedSelectMenuUnit extends AbstractSelectMenuModel<String> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6200177576322860329L;

    /**
     * StringBasedSelectMenuUnit Constructor
     *
     * @param selectableValues {@linkplain List} of {@linkplain SelectItem}
     */
    public StringBasedSelectMenuUnit(List<SelectItem> selectableValues) {
        super(selectableValues);
    }

    @Override
    public String convertToObject(FacesContext context, UIComponent component,
            String value) {
        return value;
    }

    @Override
    public String convertToString(FacesContext context, UIComponent component,
            String value) {
        return value;
    }

}
