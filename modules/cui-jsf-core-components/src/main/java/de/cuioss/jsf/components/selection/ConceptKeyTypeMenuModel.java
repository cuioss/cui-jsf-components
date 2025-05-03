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

import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstants;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyTypeSelection;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Locale;
import java.util.Set;

/**
 * SelectMenuModel for {@link ConceptKeyType}
 *
 * @author Matthias Walliczek
 */
@ToString(doNotUseGetters = true, callSuper = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
public class ConceptKeyTypeMenuModel extends AbstractSelectMenuModelAndConverter<ConceptKeyType>
        implements ConceptKeyTypeSelection {

    @Serial
    private static final long serialVersionUID = -3462000271711193227L;

    private final Locale locale;

    /**
     * A SelectMenuModel for CodeTypes.
     *
     * @param sourceData {@linkplain Set} of CodeTypes is optional
     * @param locale     {@linkplain Locale} is mandatory {@code null}
     */
    public ConceptKeyTypeMenuModel(final Set<ConceptKeyType> sourceData, final Locale locale) {
        super(sourceData);
        this.locale = requireNonNull(locale);
    }

    /**
     * Initializes to the element in the Model marked as default (
     * {@link AugmentationKeyConstants#DEFAULT_VALUE}).
     */
    public void initToDefault() {
        final var selected = getSelectableValues().stream().map(x -> (ConceptKeyType) x.getValue())
                .filter(x -> !MoreStrings.isEmpty(x.get(AugmentationKeyConstants.DEFAULT_VALUE))).findFirst();
        selected.ifPresent(this::setSelectedValue);
    }

    /**
     * Set the selected value. Special treatment for null: Will be ignored. Special
     * treatment for undefined values (see
     * {@link AugmentationKeyConstants#UNDEFINED_VALUE} and
     * {@link ConceptCategory#createUndefinedConceptKey(String)}: Will be add to the
     * list of selectableValues. Other values not part of
     * {@link #getSelectableValues()}: Will be ignored
     *
     * @param selectedValue the value to set.
     */
    @Override
    public void setSelectedValue(final ConceptKeyType selectedValue) {
        if (null == selectedValue) {
            super.setSelectedValue(null);
            return;
        }
        if (selectedValue.containsKey(AugmentationKeyConstants.UNDEFINED_VALUE)) {
            add(0, selectedValue, true);
        } else if (getSelectableValues().stream()
                .noneMatch(x -> x.getValue() != null && x.getValue().equals(selectedValue))) {
            return;
        }
        super.setSelectedValue(selectedValue);
    }

    @Override
    protected String getLabel(final ConceptKeyType value) {
        return value.getResolved(locale);
    }

    @Override
    protected String getIdentifier(final ConceptKeyType value) {
        return value.getIdentifier();
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final ConceptKeyType value) {
        if (null == value) {
            return "";
        }
        return value.getIdentifier();
    }
}
