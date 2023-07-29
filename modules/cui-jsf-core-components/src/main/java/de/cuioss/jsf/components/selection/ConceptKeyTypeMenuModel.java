package de.cuioss.jsf.components.selection;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyTypeSelection;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SelectMenuModel for {@link ConceptKeyType}
 *
 * @author Matthias Walliczek
 */
@ToString(doNotUseGetters = true, callSuper = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
public class ConceptKeyTypeMenuModel extends AbstractSelectMenuModelAndConverter<ConceptKeyType>
        implements ConceptKeyTypeSelection {

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
     * {@link AugmentationKeyConstans#DEFAULT_VALUE}).
     */
    public void initToDefault() {
        final var selected = getSelectableValues().stream().map(x -> (ConceptKeyType) x.getValue())
                .filter(x -> !MoreStrings.isEmpty(x.get(AugmentationKeyConstans.DEFAULT_VALUE))).findFirst();
        selected.ifPresent(this::setSelectedValue);
    }

    /**
     * Set the selected value. Special treatment for null: Will be ignored. Special
     * treatment for undefined values (see
     * {@link AugmentationKeyConstans#UNDEFINED_VALUE} and
     * {@link ConceptCategory#createUndefinedConceptKey(String)}: Will be add to the
     * list of selectableValues. Other values not part of
     * {@link #getSelectableValues()}: Will be ignored
     *
     * @param selectedValue the value to set.
     */
    @Override
    public void setSelectedValue(final ConceptKeyType selectedValue) {
        if (null == selectedValue) {
            super.setSelectedValue(selectedValue);
            return;
        }
        if (selectedValue.containsKey(AugmentationKeyConstans.UNDEFINED_VALUE)) {
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
