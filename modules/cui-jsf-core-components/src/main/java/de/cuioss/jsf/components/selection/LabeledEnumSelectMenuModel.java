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

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.EnumConverter;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;

import de.cuioss.jsf.api.components.selection.SelectMenuModel;
import de.cuioss.tools.collect.CollectionBuilder;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.nameprovider.LabelKeyProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Variant for simplified usage with enum. The enum is supposed to implement
 * {@link IDisplayNameProvider} in order to compute corresponding labels for the
 * {@link SelectItem}s provided by {@link #getSelectableValues()}
 *
 * @author Oliver Wolff
 * @param <T> enum bounded type. must be an enum <em>AND</em>
 *            {@link IDisplayNameProvider}
 */
@ToString(doNotUseGetters = true, exclude = "selectableValues")
@EqualsAndHashCode(doNotUseGetters = true, exclude = "selectableValues")
public class LabeledEnumSelectMenuModel<T extends Enum<T> & LabelKeyProvider> implements SelectMenuModel<T> {

    private static final long serialVersionUID = -4856620883173044422L;

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

    private final Class<T> targetClass;

    private transient Converter<T> wrappedConverter;

    /**
     * @param enumClass      must not be null
     * @param resourceBundle must not be null. Used to derive the labels
     */
    public LabeledEnumSelectMenuModel(final Class<T> enumClass, final ResourceBundle resourceBundle) {

        targetClass = enumClass;

        // Returns the elements of this enum class
        final List<T> elements = Arrays.asList(enumClass.getEnumConstants());

        final var builder = new CollectionBuilder<SelectItem>();

        for (final T element : elements) {
            builder.add(new SelectItem(element, resourceBundle.getString(element.getLabelKey())));
        }

        selectableValues = builder.toImmutableList();
    }

    @Override
    public T getAsObject(final FacesContext context, final UIComponent component, final String value) {
        return getWrappedConverter().getAsObject(context, component, value);
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final T value) {
        var result = getWrappedConverter().getAsString(context, component, value);
        if (null == result) {
            return "";
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

    private Converter<T> getWrappedConverter() {
        if (null == wrappedConverter) {
            wrappedConverter = new EnumConverter(targetClass);
        }
        return wrappedConverter;
    }

}
