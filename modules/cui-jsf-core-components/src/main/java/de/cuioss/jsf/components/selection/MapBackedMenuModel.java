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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;

import de.cuioss.jsf.api.components.selection.SelectMenuModel;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * Variant of the {@link SelectMenuModel} that is backed by map. Caution: It is
 * assumed that the keys of the map are the labels to be displayed
 *
 * @author Oliver Wolff
 * @param <T> model item type
 */
public class MapBackedMenuModel<T extends Serializable> extends MapInstanceConverter<String, T>
        implements SelectMenuModel<T> {

    private static final long serialVersionUID = -5165456473976568944L;

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
     * @param map to be utilized. Must not be empty
     */
    public MapBackedMenuModel(final Map<String, T> map) {
        super.setInstanceMap(map);
        requireNonNull(map, "Parameter map must not be empty");
        final var itemBuilder = new CollectionBuilder<SelectItem>();
        for (final java.util.Map.Entry<String, T> mapEntry : map.entrySet()) {
            itemBuilder.add(new SelectItem(mapEntry.getValue(), mapEntry.getKey()));
        }
        selectableValues = itemBuilder.toImmutableList();
    }

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
