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

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;

import de.cuioss.jsf.api.components.selection.SelectMenuModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Abstract class using {@link MapInstanceConverter} to create a drop down list
 * (h:selectOneMenu).
 * <p>
 * Example:
 *
 * <pre>
 * &lt;h:selectOneMenu value="#{model.selectedValue}" converter="#{model}"&gt;
 * &lt;f:selectItems value="#{model.selectableValues}" /&gt;
 * &lt;/h:selectOneMenu&gt;
 * </pre>
 *
 * @param <T> the value of the drop down list.
 *
 * @author Matthias Walliczek
 */
@ToString(doNotUseGetters = true, exclude = { "converter", "selectableValues" })
@EqualsAndHashCode(doNotUseGetters = true, exclude = { "converter", "selectableValues" })
public abstract class AbstractSelectMenuModelAndConverter<T extends Serializable> implements SelectMenuModel<T> {

    private static final long serialVersionUID = 5538442599313391823L;

    private ArrayList<SelectItem> selectableValues;

    @Getter
    @Setter
    private T selectedValue;

    /**
     * flag indication whether the menu model is disabled.
     */
    @Getter
    @Setter
    public boolean disabled = false;

    private final MapInstanceConverter<String, T> converter = new MapInstanceConverter<>();

    @Getter
    private boolean selectionAvailable;

    private Set<T> sourceData;

    /**
     * Default constructor creating the initial list of values.
     *
     * @param sourceData the initial data to create the list with. May be null or
     *                   empty, which results in an empty drop down list.
     */
    protected AbstractSelectMenuModelAndConverter(final Set<T> sourceData) {
        initialize(sourceData);
    }

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

    private Map<String, T> getMapping(final Set<T> values) {
        final Map<String, T> result = new HashMap<>(values.size());
        for (final T value : values) {
            result.put(getIdentifier(value), value);
        }
        return result;
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

    private ArrayList<SelectItem> initializeSelectItems(final Set<T> values) {
        if (values instanceof SortedSet) {
            return values.stream().map(value -> new SelectItem(value, getLabel(value)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return values.stream().map(value -> new SelectItem(value, getLabel(value)))
                .sorted(Comparator.comparing(SelectItem::getLabel)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Initializes to the first element in the Model. Use {@link SortedSet} to
     * control the order of entries.
     */
    public void initToFirstElement() {
        getSelectableValues().stream().findFirst().ifPresent(it -> setSelectedValue((T) it.getValue()));
    }

    /**
     * Insert a new value at a specific position in the drop down list.
     *
     * @param position     the position, e.g. 0 to set as first item.
     * @param newValue     the new value.
     * @param itemDisabled disabled property of the {@link SelectItem}
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
     * Insert a new value at a specific position in the drop down list.
     *
     * @param position the position, e.g. 0 to set as first item.
     * @param newValue the new value.
     */
    public void add(final int position, final T newValue) {
        add(position, newValue, false);
    }

    /**
     * @param value
     *
     * @return the label to display at the drop down list.
     */
    protected abstract String getLabel(T value);

    /**
     * @param value
     *
     * @return the unique identifier to resolve a value with.
     */
    protected abstract String getIdentifier(T value);

    // Lazy initialization because #getLabel() may access properties from
    // implementing class
    @Override
    public List<SelectItem> getSelectableValues() {
        if (null == selectableValues) {
            selectableValues = initializeSelectItems(sourceData);
        }
        return selectableValues;
    }

    @Override
    public T getAsObject(final FacesContext context, final UIComponent component, final String value) {
        return converter.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final T value) {
        return converter.getAsString(context, component, value);
    }

    /**
     * @param rescrictModeActive
     */
    public void setRescrictModeActive(final boolean rescrictModeActive) {
        converter.setRescrictModeActive(rescrictModeActive);
    }

    /**
     * @param instanceMap
     */
    public void setInstanceMap(final Map<String, T> instanceMap) {
        converter.setInstanceMap(instanceMap);
    }
}
