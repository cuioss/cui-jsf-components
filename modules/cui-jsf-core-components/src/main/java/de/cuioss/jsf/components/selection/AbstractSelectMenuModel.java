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
package de.cuioss.jsf.components.selection;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.selection.SelectMenuModel;
import de.cuioss.jsf.api.converter.AbstractConverter;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>Base implementation for {@link SelectMenuModel} providing common functionality
 * for JSF select menu components. This abstract class combines model capabilities with
 * converter functionality, simplifying the implementation of selection components.</p>
 * 
 * <p>The model provides:</p>
 * <ul>
 *   <li>Storage and access to available selection options as {@link SelectItem} instances</li>
 *   <li>Storage and access to the currently selected value</li>
 *   <li>Conversion between the object model and string representation</li>
 *   <li>Handling of value change events</li>
 *   <li>Support for the disabled state</li>
 * </ul>
 * 
 * <p>Concrete implementations need to provide the specific logic for converting
 * between the string representation and the model type.</p>
 * 
 * <p>This class is designed to be used with JSF selection components such as
 * h:selectOneMenu, h:selectManyMenu, etc.</p>
 *
 * @author Oliver Wolff
 * @param <T> The type of the model items, must implement {@link Serializable}
 * @since 1.0
 */
public abstract class AbstractSelectMenuModel<T extends Serializable> extends AbstractConverter<T>
        implements SelectMenuModel<T> {

    @Serial
    private static final long serialVersionUID = 568340728664691248L;

    @Getter
    private final List<SelectItem> selectableValues;

    @Getter
    @Setter
    private T selectedValue;

    /**
     * Flag indicating whether the menu model is disabled.
     * When disabled, the corresponding UI component should be rendered in a disabled state.
     */
    @Getter
    @Setter
    public boolean disabled = false;

    /**
     * Constructor that initializes the model with the available selection options.
     * 
     * @param values The list of {@link SelectItem} objects representing the available selection options.
     *               Must not be {@code null} but may be empty (though an empty list would be impractical for a dropdown).
     * @throws NullPointerException if values is {@code null}
     */
    protected AbstractSelectMenuModel(final List<SelectItem> values) {
        selectableValues = requireNonNull(values, "values");
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Determines if a value is currently selected by checking if the selectedValue is non-null.</p>
     * 
     * @return {@code true} if a value is currently selected, {@code false} otherwise
     */
    @Override
    public boolean isValueSelected() {
        return null != getSelectedValue();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Processes a value change event by updating the selectedValue with the new value from the event.</p>
     * 
     * @param event The value change event containing the new selected value
     * @throws AbortProcessingException if processing should be aborted
     */
    @SuppressWarnings("unchecked")
    // Implicitly safe because of typing
    @Override
    public void processValueChange(final ValueChangeEvent event) throws AbortProcessingException {
        this.setSelectedValue((T) event.getNewValue());
    }
}
