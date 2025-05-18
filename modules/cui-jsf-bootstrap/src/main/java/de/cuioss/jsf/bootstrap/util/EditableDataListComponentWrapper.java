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
package de.cuioss.jsf.bootstrap.util;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.CuiInterfaceBasedModifier;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link ComponentModifier} wrapper for {@link EditableDataListComponent}
 * that enables it to participate in the component modification framework.
 * Handles validation and required state checking.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see BootstrapComponentModifierResolver
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
class EditableDataListComponentWrapper extends CuiInterfaceBasedModifier {

    private final EditableDataListComponent dataListComponent;

    /**
     * Constructor that initializes the wrapper with the component to be wrapped.
     *
     * @param dataListComponent the {@link EditableDataListComponent} to be wrapped,
     *                          must not be null
     */
    EditableDataListComponentWrapper(EditableDataListComponent dataListComponent) {
        super(dataListComponent);
        this.dataListComponent = dataListComponent;
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@code true} because {@link EditableDataListComponent} acts as an editable value holder
     */
    @Override
    public boolean isEditableValueHolder() {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Determines whether the wrapped component is in a valid state by attempting to
     * validate it. If validation succeeds (no exception is thrown), the component is 
     * considered valid.</p>
     * 
     * @return {@code true} if the component validates successfully, {@code false} otherwise
     */
    @Override
    public boolean isValid() {
        try {
            dataListComponent.validate(FacesContext.getCurrentInstance(), dataListComponent, null);
            return true;
        } catch (ValidatorException e) {
            // We use the exception to check whether the component is valid, but not the
            // exception itself
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @return the result of {@link EditableDataListComponent#evaluateRequired()}
     */
    @Override
    public boolean isRequired() {
        return dataListComponent.evaluateRequired();
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@code false} because {@link EditableDataListComponent} does not support value reset
     */
    @Override
    public boolean isSupportsResetValue() {
        return false;
    }
}
