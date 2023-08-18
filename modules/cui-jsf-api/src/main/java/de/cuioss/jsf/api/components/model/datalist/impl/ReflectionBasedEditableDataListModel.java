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
package de.cuioss.jsf.api.components.model.datalist.impl;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.cuioss.jsf.api.components.model.datalist.EditableDataListModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Base class for implementing {@link EditableDataListModel}. In addition to
 * {@link AbstractEditableDataListModel} it provides reflection based
 * implementations of {@link EditableDataListModel#createEmptyItem()} and
 * {@link EditableDataListModel#createCopy(Serializable)}
 *
 * @author Oliver Wolff
 * @param <T> identifying the type of items to be created. Must be at least
 *            {@link Serializable} and implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReflectionBasedEditableDataListModel<T extends Serializable> extends AbstractEditableDataListModel<T> {

    private static final long serialVersionUID = 6139592941529357681L;

    private final Class<T> modelClass;

    @Getter
    private final List<T> loadedItems;

    /**
     * @param modelClass  must not be null and provide a default and a
     *                    Copy-Constructor
     * @param intialItems The initial item for populating the dataModel, may be
     *                    null. In case you want to load the items lazily you can
     *                    override {@link #getLoadedItems()}
     */
    public ReflectionBasedEditableDataListModel(final Class<T> modelClass, final List<T> intialItems) {
        this.modelClass = requireNonNull(modelClass);
        loadedItems = Objects.requireNonNullElseGet(intialItems, ArrayList::new);
    }

    @Override
    public T createEmptyItem() {
        try {
            return modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException | RuntimeException e) {
            throw new IllegalStateException(
                    "Unable to create an Instances using the default constructor, offending class: " + modelClass, e);
        }
    }

    @Override
    public T createCopy(final T item) {
        try {
            return modelClass.getConstructor(modelClass).newInstance(item);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new IllegalStateException(
                    "Unable to create an Instances using the copy constructor, offending class: " + modelClass, e);
        }
    }

}
