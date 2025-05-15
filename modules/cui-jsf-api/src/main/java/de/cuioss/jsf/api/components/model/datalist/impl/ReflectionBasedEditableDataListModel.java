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

import de.cuioss.jsf.api.components.model.datalist.EditableDataListModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <h2>Reflection-based implementation of EditableDataListModel</h2>
 * <p>
 * This class extends {@link AbstractEditableDataListModel} by providing reflection-based
 * implementations for creating and copying items, making it a more complete and ready-to-use
 * base class for editable data list models.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 * <li>Automatic item creation using the default constructor of the model class</li>
 * <li>Automatic item copying using a copy constructor</li>
 * <li>Simple initialization with an initial set of items</li>
 * </ul>
 * <p>
 * Requirements for the model class:
 * </p>
 * <ul>
 * <li>It must have a public no-argument constructor for creating new items</li>
 * <li>It must have a public copy constructor (constructor accepting its own type) for creating copies</li>
 * <li>It should properly implement equals and hashCode for change detection</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @param <T> identifying the type of items to be managed. Must be at least
 *            {@link Serializable}, implement {@link Object#hashCode()} and
 *            {@link Object#equals(Object)} correctly, and provide both a default
 *            constructor and copy constructor.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReflectionBasedEditableDataListModel<T extends Serializable> extends AbstractEditableDataListModel<T> {

    @Serial
    private static final long serialVersionUID = 6139592941529357681L;

    private final Class<T> modelClass;

    @Getter
    private final List<T> loadedItems;

    /**
     * <p>Creates a new ReflectionBasedEditableDataListModel with the specified model class and initial items.</p>
     * <p>This constructor initializes the model with the provided class type and optional
     * initial items. The model class is used for reflection-based creation of new items
     * and copies.</p>
     *
     * @param modelClass  The class of items to be managed. Must not be null and must provide
     *                    a public no-argument constructor and a public copy constructor
     *                    (constructor accepting its own type).
     * @param intialItems The initial items for populating the data model. May be null,
     *                    in which case an empty list will be used. For lazy loading,
     *                    subclasses can override {@link #getLoadedItems()}.
     * @throws NullPointerException If modelClass is null.
     */
    public ReflectionBasedEditableDataListModel(final Class<T> modelClass, final List<T> intialItems) {
        this.modelClass = requireNonNull(modelClass);
        loadedItems = Objects.requireNonNullElseGet(intialItems, ArrayList::new);
    }

    /**
     * <p>Creates a new empty item using the default constructor of the model class.</p>
     * <p>This implementation uses reflection to instantiate a new object of the model class
     * by invoking its no-argument constructor.</p>
     *
     * @return A new instance of the model class created via its default constructor.
     * @throws IllegalStateException If the model class cannot be instantiated using
     *                               its default constructor, which may occur if the
     *                               constructor is not public, doesn't exist, or throws
     *                               an exception.
     */
    @Override
    public T createEmptyItem() {
        try {
            return modelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | RuntimeException | InvocationTargetException
                | NoSuchMethodException e) {
            throw new IllegalStateException(
                    "Unable to create an Instances using the default constructor, offending class: " + modelClass, e);
        }
    }

    /**
     * <p>Creates a copy of the given item using the copy constructor of the model class.</p>
     * <p>This implementation uses reflection to instantiate a new object of the model class
     * by invoking its copy constructor (a constructor that accepts an instance of its own class).</p>
     *
     * @param item The item to copy. Must not be null.
     * @return A new instance of the model class created via its copy constructor,
     *         containing the same state as the provided item.
     * @throws IllegalStateException If the model class cannot be instantiated using
     *                               its copy constructor, which may occur if the
     *                               constructor is not public, doesn't exist, or throws
     *                               an exception.
     */
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
