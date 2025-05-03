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
package de.cuioss.jsf.bootstrap.composite;

import static de.cuioss.jsf.api.components.model.datalist.AddStatus.CREATED;

import de.cuioss.jsf.api.components.base.BaseCuiNamingContainer;
import de.cuioss.jsf.api.components.model.datalist.EditableDataListModel;
import de.cuioss.jsf.api.components.model.datalist.ItemWrapper;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import de.cuioss.jsf.api.composite.accessor.BooleanAttributeAccessor;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Splitter;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;

/**
 * Backing-class for cui-composite/editableDataList.xhtml
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
@FacesComponent(EditableDataListComponent.DATA_LIST_COMPONENT)
public class EditableDataListComponent extends BaseCuiNamingContainer {

    private static final CuiLogger log = new CuiLogger(EditableDataListComponent.class);

    /**
     * The componentId for this component.
     */
    public static final String DATA_LIST_COMPONENT = "de.cuioss.cui.bootstrap.editableDataList";

    private final BooleanAttributeAccessor doubleClickEnabledAccesor = new BooleanAttributeAccessor(
            "enableDoubleClickEdit", false, false);

    private final BooleanAttributeAccessor enableDeleteAccesor = new BooleanAttributeAccessor("enableDelete", true,
            false);

    private final BooleanAttributeAccessor renderAddonButtonsAddMode = new BooleanAttributeAccessor(
            "renderAddonButtonsInAddMode", true, false);

    private final BooleanAttributeAccessor renderAddonButtonsEditMode = new BooleanAttributeAccessor(
            "renderAddonButtonsInEditMode", true, false);

    @SuppressWarnings("rawtypes")
    private final AttributeAccessorImpl<EditableDataListModel> modelAccessor = new AttributeAccessorImpl<>("model",
            EditableDataListModel.class, false);

    private final AttributeAccessorImpl<String> requiredMessageKeyAccesor = new AttributeAccessorImpl<>(
            "requiredMessageKey", String.class, false);

    private final AttributeAccessorImpl<String> requiredMessageValueAccesor = new AttributeAccessorImpl<>(
            "requiredMessageValue", String.class, false);

    private final AttributeAccessorImpl<String> emptyMessageKeyAccesor = new AttributeAccessorImpl<>("emptyMessageKey",
            String.class, false);

    private final AttributeAccessorImpl<String> emptyMessageValueAccesor = new AttributeAccessorImpl<>(
            "emptyMessageValue", String.class, false);

    private final AttributeAccessorImpl<String> validatorsAccessor = new AttributeAccessorImpl<>("modelValidator",
            String.class, false);

    private final BooleanAttributeAccessor requiredAccessor = new BooleanAttributeAccessor("required", false, false);

    /**
     * @return the combined styleClass computed from the constants
     *         {@link CssBootstrap#LIST_GROUP}, and the configured
     *         styleClass-Attribute
     */
    public String getWrapperStyleClass() {
        var styleClassBuilder = CssBootstrap.LIST_GROUP.getStyleClassBuilder().append(super.getStyleClass());
        if (evaluateRequired()) {
            styleClassBuilder.append(CssCuiBootstrap.CUI_REQUIRED);
        }
        return styleClassBuilder.getStyleClass();
    }

    /**
     * @return boolean indicating whether double click to edit is enabled
     */
    public boolean isEditOnDoubleClickDisabled() {
        return !doubleClickEnabledAccesor.value(getAttributes()) || !getModel().isEveryItemSavedOrCanceled();
    }

    /**
     * @param item
     *
     * @return boolean indicating whether the delete button should be rendered for
     *         this item
     */
    public boolean isDeleteButtonVisibleForItem(ItemWrapper<?> item) {
        return enableDeleteAccesor.value(getAttributes()) && !item.isMarkedForDelete();
    }

    /**
     * @param item
     *
     * @return boolean indicating whether the undo button should be rendered for
     *         this item
     */
    public boolean isUndoButtonVisibleForItem(ItemWrapper<?> item) {
        return enableDeleteAccesor.value(getAttributes()) && item.isMarkedForDelete();
    }

    /**
     * @param item
     *
     * @return boolean indicating whether to render the addon-buttons group (add,
     *         cancel) in add-mode or not.
     */
    public boolean isAddonButtonsInAddModeRendered(ItemWrapper<?> item) {
        return CREATED.equals(item.getAddStatus()) && renderAddonButtonsAddMode.value(getAttributes());
    }

    /**
     * @param item
     *
     * @return boolean indicating whether to render the addon-buttons group (save,
     *         cancel) in edit-mode or not.
     */
    public boolean isAddonButtonsInEditModeRendered(ItemWrapper<?> item) {
        return !CREATED.equals(item.getAddStatus()) && renderAddonButtonsEditMode.value(getAttributes());
    }

    private String getErrorMessageKey() {
        return requiredMessageKeyAccesor.value(getAttributes());
    }

    private String getErrorMessageValue() {
        return requiredMessageValueAccesor.value(getAttributes());
    }

    private String getEmptyMessageKey() {
        return emptyMessageKeyAccesor.value(getAttributes());
    }

    private String getEmptyMessageValue() {
        return emptyMessageValueAccesor.value(getAttributes());
    }

    /**
     * @return String representation of the resolved error message
     */
    public String getResolvedErrorMessage() {
        return LabelResolver.builder().withLabelKey(getErrorMessageKey()).withLabelValue(getErrorMessageValue()).build()
                .resolve(getFacesContext());
    }

    private boolean isEmptyMessageAvailable() {
        return null != getEmptyMessageKey() || null != getEmptyMessageValue();
    }

    /**
     * @return Flag indicating whether the empty-message should be rendered
     */
    public boolean isEmptyMessageRendered() {
        final EditableDataListModel<?> model = getModel();
        return model.getDisplayItems().isEmpty() && model.isEveryItemSavedOrCanceled() && isEmptyMessageAvailable();
    }

    /**
     * @return String representation of the resolved empty-elements message
     */
    public String getResolvedEmptyMessage() {
        return LabelResolver.builder().withLabelKey(getEmptyMessageKey()).withLabelValue(getEmptyMessageValue()).build()
                .resolve(getFacesContext());
    }

    private boolean isResultItemsAvailable() {
        final EditableDataListModel<?> model = getModel();
        return null != model && !model.getResultItems().isEmpty();
    }

    /**
     * @param facesContext
     * @param component    helper UIInput component
     * @param value        irrelevant
     */
    public void validate(final FacesContext facesContext, UIComponent component, Object value) {
        validateRequired();
        validateModel(facesContext, component);
    }

    private void validateRequired() {
        if (evaluateRequired() && !isResultItemsAvailable()) {
            final var msg = getResolvedErrorMessage();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }

    /**
     * Go through the given validators and feed them with the model.
     *
     * @param facesContext
     * @param component
     */
    private void validateModel(final FacesContext facesContext, UIComponent component) {
        final var validators = validatorsAccessor.value(getAttributes());
        if (null != validators) {
            final var validatorIds = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(validators);
            final EditableDataListModel<?> model = getModel();
            for (final String validatorId : validatorIds) {
                log.debug("Executing EditableDataList Validator: {}", validatorId);
                facesContext.getApplication().createValidator(validatorId).validate(facesContext, component, model);
            }
        }
    }

    private EditableDataListModel<?> getModel() {
        return modelAccessor.value(getAttributes());
    }

    public boolean evaluateRequired() {
        var result = requiredAccessor.value(getAttributes());
        return null != result && result;
    }
}
