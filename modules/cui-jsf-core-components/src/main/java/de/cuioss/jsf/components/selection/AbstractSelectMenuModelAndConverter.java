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

import de.cuioss.jsf.api.components.selection.SelectMenuModel;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <p>Abstract class providing both model and converter functionality for JSF selection components.
 * This implementation uses {@link MapInstanceConverter} to handle the conversion between object
 * model and string representation for use with JSF select components.</p>
 * 
 * <p>This class simplifies the creation of dropdown lists by handling:</p>
 * <ul>
 *   <li>Generation of {@link SelectItem} instances from a source collection</li>
 *   <li>Storage and retrieval of the selected value</li>
 *   <li>Conversion between model objects and their string representations</li>
 *   <li>Sorting of select items based on their labels</li>
 *   <li>Support for adding and manipulating items in the selection list</li>
 * </ul>
 * 
 * <p>Implementing classes need to provide two key methods:</p>
 * <ul>
 *   <li>{@link #getLabel(Serializable)} - to determine how each value is displayed</li>
 *   <li>{@link #getIdentifier(Serializable)} - to create a unique string identifier for each value</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 *
 * <pre>
 * &lt;h:selectOneMenu value="#{model.selectedValue}" converter="#{model}"&gt;
 *   &lt;f:selectItems value="#{model.selectableValues}" /&gt;
 * &lt;/h:selectOneMenu&gt;
 * </pre>
 *
 * @param <T> The type of the values in the selection component, must implement {@link Serializable}
 *
 * @author Matthias Walliczek
 * @since 1.0
 */
@ToString(doNotUseGetters = true, exclude = {"converter", "selectableValues"})
@EqualsAndHashCode(doNotUseGetters = true, exclude = {"converter", "selectableValues"})
public abstract class AbstractSelectMenuModelAndConverter<T extends Serializable> implements SelectMenuModel<T> {

    @Serial
    private static final long serialVersionUID = 5538442599313391823L;

    private ArrayList<SelectItem> selectableValues;

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

    private final MapInstanceConverter<String, T> converter = new MapInstanceConverter<>();

    @Getter
    private boolean selectionAvailable;

    private Set<T> sourceData;

    /**
     * Default constructor that initializes the model with the provided source data.
     *
     * @param sourceData The set of values to populate the selection list.
     *                  May be null or empty, which results in an empty selection list.
     */
    protected AbstractSelectMenuModelAndConverter(final Set<T> sourceData) {
        initialize(sourceData);
    }

    /**
     * Initializes or reinitializes the model with a new set of source data.
     * This method can be used to refresh the selection list with updated values.
     *
     * @param newSourceData The set of values to populate the selection list.
     *                     May be null or empty, which results in an empty selection list.
     */
    protected void initialize(final Set<T> newSourceData) {
        if (null == newSourceData) {
            sourceData = null;
            selectionAvailable = false;
            selectableValues = initializeSelectItems(Collections.emptySet());
            converter.setInstanceMap(Collections.emptyMap());
        } else {
            if (newSourceData.isEmpty()) {
                sourceData = Collections.emptySet();
                selectionAvailable = false;
            } else {
                selectionAvailable = true;
                if (newSourceData instanceof SortedSet<T> set) {
                    sourceData = new TreeSet<>(set);
                } else {
                    sourceData = new HashSet<>(newSourceData);
                }
            }
            converter.setInstanceMap(getMapping(newSourceData));
        }
    }

    /**
     * Creates a mapping between string identifiers and their corresponding object values.
     * This mapping is used by the converter for conversion between object and string representations.
     *
     * @param values The set of values to create mappings for
     * @return A map with string identifiers as keys and object values as values
     */
    private Map<String, T> getMapping(final Set<T> values) {
        final Map<String, T> result = new HashMap<>(values.size());
        for (final T value : values) {
            result.put(getIdentifier(value), value);
        }
        return result;
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

    /**
     * Creates a list of {@link SelectItem} instances from the provided set of values.
     * If the input is a {@link SortedSet}, the order is preserved. Otherwise, the items
     * are sorted alphabetically by their labels.
     *
     * @param values The set of values to convert to SelectItems
     * @return An ArrayList of SelectItems representing the values
     */
    private ArrayList<SelectItem> initializeSelectItems(final Set<T> values) {
        if (values instanceof SortedSet) {
            return values.stream().map(value -> new SelectItem(value, getLabel(value)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return values.stream().map(value -> new SelectItem(value, getLabel(value)))
                .sorted(Comparator.comparing(SelectItem::getLabel)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Initializes the selected value to the first element in the model.
     * This is useful for pre-selecting a default value in the UI.
     * Use {@link SortedSet} as source data to control the order of entries.
     */
    public void initToFirstElement() {
        getSelectableValues().stream().findFirst().ifPresent(it -> setSelectedValue((T) it.getValue()));
    }

    /**
     * Inserts a new value at a specific position in the selection list.
     *
     * @param position The position where to insert the new value, e.g., 0 to insert as the first item
     * @param newValue The new value to insert
     * @param itemDisabled Whether the new item should be disabled in the UI
     */
    @SuppressWarnings("unchecked")
    // Implicitly safe because of typing
    public void add(final int position, final T newValue, final boolean itemDisabled) {
        getSelectableValues().add(position, new SelectItem(newValue, getLabel(newValue), null, itemDisabled));
        converter.setInstanceMap(
                getMapping(getSelectableValues().stream().map(x -> (T) x.getValue()).collect(Collectors.toSet())));
        selectionAvailable = true;
    }

    /**
     * Inserts a new value at a specific position in the selection list.
     * The new item will not be disabled.
     *
     * @param position The position where to insert the new value, e.g., 0 to insert as the first item
     * @param newValue The new value to insert
     */
    public void add(final int position, final T newValue) {
        add(position, newValue, false);
    }

    /**
     * Returns the display label for a given value.
     * This label is used in the UI to represent the value to the user.
     *
     * @param value The value to get a label for
     * @return The human-readable label for the value
     */
    protected abstract String getLabel(T value);

    /**
     * Returns a unique identifier for a given value.
     * This identifier is used by the converter for converting between
     * the object model and string representation.
     *
     * @param value The value to get an identifier for
     * @return A unique string identifier for the value
     */
    protected abstract String getIdentifier(T value);

    /**
     * {@inheritDoc}
     * 
     * <p>The list is lazily initialized when first accessed to ensure that
     * implementing classes are fully initialized before calling {@link #getLabel(Serializable)}.</p>
     *
     * @return A list of SelectItems representing the available selection options
     */
    @Override
    public List<SelectItem> getSelectableValues() {
        if (null == selectableValues) {
            selectableValues = initializeSelectItems(sourceData);
        }
        return selectableValues;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Delegates to the internal {@link MapInstanceConverter} to convert the string value
     * back to its object representation.</p>
     *
     * @param context The FacesContext for the current request
     * @param component The UIComponent this converter is being used with
     * @param value The string value to be converted
     * @return The object representation of the string value, or null if conversion fails
     */
    @Override
    public T getAsObject(final FacesContext context, final UIComponent component, final String value) {
        return converter.getAsObject(context, component, value);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Delegates to the internal {@link MapInstanceConverter} to convert the object value
     * to its string representation.</p>
     *
     * @param context The FacesContext for the current request
     * @param component The UIComponent this converter is being used with
     * @param value The object value to be converted
     * @return The string representation of the object value
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final T value) {
        return converter.getAsString(context, component, value);
    }

    /**
     * Sets whether the converter should operate in restricted mode.
     * In restricted mode, the converter will only accept values that are present
     * in its instance map.
     *
     * @param rescrictModeActive True to activate restricted mode, false otherwise
     */
    public void setRescrictModeActive(final boolean rescrictModeActive) {
        converter.setRestrictedModeActive(rescrictModeActive);
    }

    /**
     * Sets the instance map for the internal converter.
     * This allows direct manipulation of the mapping between string identifiers
     * and object values used for conversion.
     *
     * @param instanceMap The map to use for conversion, with string identifiers as keys
     *                   and object values as values
     */
    public void setInstanceMap(final Map<String, T> instanceMap) {
        converter.setInstanceMap(instanceMap);
    }
}
