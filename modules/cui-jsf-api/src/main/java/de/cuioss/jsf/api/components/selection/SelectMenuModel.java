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
package de.cuioss.jsf.api.components.selection;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.convert.Converter;
import jakarta.faces.event.ValueChangeListener;
import jakarta.faces.model.SelectItem;

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
