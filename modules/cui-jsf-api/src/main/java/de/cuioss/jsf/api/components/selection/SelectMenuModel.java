package de.cuioss.jsf.api.components.selection;

import java.io.Serializable;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;

import de.cuioss.uimodel.model.TypedSelection;

/**
 * Interfaces for combining functionality used by selection elements.
 * <em>Caution: </em> Because of the technical limitation of JSF selection
 * components (They do not serialize the objects itself) this component extends
 * {@link Converter}
 *
 * @param <T>
 */
public interface SelectMenuModel<T extends Serializable>
        extends Converter<T>, ValueChangeListener, Serializable, TypedSelection<T> {

    /**
     * @return the list of SelectItems
     */
    List<SelectItem> getSelectableValues();

    /**
     * @param selectedValue to be set. may be null
     */
    void setSelectedValue(T selectedValue);

    /**
     * @return the currently selected value.
     */
    @Override
    T getSelectedValue();

    /**
     * @return flag indication whether the boolean is selected.
     */
    boolean isValueSelected();

    /**
     * @return flag indication whether the menu model is disabled.
     */
    boolean isDisabled();

    /**
     * @param modelDisabled
     */
    void setDisabled(boolean modelDisabled);
}
