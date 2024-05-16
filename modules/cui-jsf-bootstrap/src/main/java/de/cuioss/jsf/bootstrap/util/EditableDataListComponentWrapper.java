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

import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;

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
