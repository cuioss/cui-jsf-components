package com.icw.ehf.cui.components.selection;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.icw.ehf.cui.core.api.components.selection.SelectMenuModel;
import com.icw.ehf.cui.core.api.converter.AbstractConverter;

import lombok.Getter;
import lombok.Setter;

/**
 * Base implementation for {@link SelectMenuModel} providing some convenient
 * methods.
 *
 * @author Oliver Wolff
 * @param <T> model item type
 */
public abstract class AbstractSelectMenuModel<T extends Serializable> extends AbstractConverter<T>
        implements SelectMenuModel<T> {

    private static final long serialVersionUID = 568340728664691248L;

    @Getter
    private final List<SelectItem> selectableValues;

    @Getter
    @Setter
    private T selectedValue;

    /**
     * flag indication whether the menu model is disabled.
     */
    @Getter
    @Setter
    public boolean disabled = false;

    /**
     * @param values
     *            must not be <code>null</code> but may be empty, what is rather
     *            useless for a dropdown
     */
    public AbstractSelectMenuModel(final List<SelectItem> values) {
        this.selectableValues = requireNonNull(values, "values");
    }

    /**
     * Returns true if value is selected for this unit and false otherwise.
     *
     * @return is value selected status.
     */
    @Override
    public boolean isValueSelected() {
        return null != getSelectedValue();
    }

    @SuppressWarnings("unchecked")
    // Implicitly safe because of typing
    @Override
    public void processValueChange(final ValueChangeEvent event)
        throws AbortProcessingException {
        this.setSelectedValue((T) event.getNewValue());
    }
}
