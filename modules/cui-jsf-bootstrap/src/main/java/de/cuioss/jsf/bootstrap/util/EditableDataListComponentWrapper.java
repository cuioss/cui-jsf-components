package de.cuioss.jsf.bootstrap.util;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.CuiInterfaceBasedModifier;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link ComponentModifier} for {@link EditableDataListComponent}
 *
 * @author Oliver Wolff
 *
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
class EditableDataListComponentWrapper extends CuiInterfaceBasedModifier {

    private final EditableDataListComponent dataListComponent;

    /**
     * Constructor
     *
     * @param dataListComponent
     */
    EditableDataListComponentWrapper(EditableDataListComponent dataListComponent) {
        super(dataListComponent);
        this.dataListComponent = dataListComponent;
    }

    @Override
    public boolean isEditableValueHolder() {
        return true;
    }

    @Override
    public boolean isValid() {
        try {
            dataListComponent.validate(FacesContext.getCurrentInstance(), dataListComponent, null);
            return true;
        } catch (ValidatorException e) {
            // We use the exception to check whether the component is valid, but not the
            // exception
            // itself
            return false;
        }
    }

    @Override
    public boolean isRequired() {
        return dataListComponent.evaluateRequired();
    }

    @Override
    public boolean isSupportsResetValue() {
        return false;
    }
}
