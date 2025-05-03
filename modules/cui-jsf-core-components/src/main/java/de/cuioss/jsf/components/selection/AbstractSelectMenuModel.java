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
 * Base implementation for {@link SelectMenuModel} providing some convenient
 * methods.
 *
 * @author Oliver Wolff
 * @param <T> model item type
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
     * flag indication whether the menu model is disabled.
     */
    @Getter
    @Setter
    public boolean disabled = false;

    /**
     * @param values must not be <code>null</code> but may be empty, what is rather
     *               useless for a dropdown
     */
    protected AbstractSelectMenuModel(final List<SelectItem> values) {
        selectableValues = requireNonNull(values, "values");
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
    public void processValueChange(final ValueChangeEvent event) throws AbortProcessingException {
        this.setSelectedValue((T) event.getNewValue());
    }
}
