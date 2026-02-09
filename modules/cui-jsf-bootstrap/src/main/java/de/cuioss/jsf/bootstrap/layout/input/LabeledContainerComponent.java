/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.api.components.util.ComponentUtility.findNearestNamingContainer;
import static de.cuioss.jsf.bootstrap.layout.input.ContainerFacets.*;

import de.cuioss.jsf.api.components.base.BaseCuiNamingContainer;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.api.components.support.OneTimeCheck;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.api.components.util.styleclass.CombinedComponentModifier;
import de.cuioss.jsf.api.components.util.styleclass.StyleClassModifierFactory;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.common.logging.BootstrapLogMessages;
import de.cuioss.jsf.bootstrap.common.partial.ContentColumnProvider;
import de.cuioss.jsf.bootstrap.common.partial.LabelColumnProvider;
import de.cuioss.jsf.bootstrap.common.partial.LayoutModeProvider;
import de.cuioss.jsf.bootstrap.layout.LayoutMode;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.application.ProjectStage;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.html.HtmlInputHidden;
import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import lombok.experimental.Delegate;

import java.util.*;

/**
 * <p>
 * Renders a labeled form element with integrated validation message support.
 * This component wraps input elements with proper Bootstrap styling, handling label positioning,
 * validation messages, and optional addon elements.
 * </p>
 * <p>
 * The component applies Bootstrap's form styling automatically and supports various layout modes
 * including column-based layouts. It automatically applies "form-control" to child inputs and
 * handles validation states and required indicators.
 * </p>
 * <p>
 * More information and examples can be found in the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/labeledContainerDemo.jsf">Reference
 * Documentation</a>
 * </p>
 * 
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link LabelProvider} - Label content</li>
 * <li>{@link ContentProvider} - Static content</li>
 * <li>{@link ForIdentifierProvider} - Target input identifier(s)</li>
 * <li>{@link PlaceholderProvider} - Input placeholder text</li>
 * <li>{@link LayoutModeProvider} - Layout structure (default: COLUMN)</li>
 * <li>{@link LabelColumnProvider} - Label width (default: 4)</li>
 * <li>{@link ContentColumnProvider} - Content width (default: 8)</li>
 * <li>{@link DisabledComponentProvider} - Disabled state handling</li>
 * <li>errorClass - CSS class for invalid inputs (default: 'has-error')</li>
 * <li>renderMessage - Controls error message visibility (default: true)</li>
 * <li>renderComplexOutput - For handling complex output rather than inputs</li>
 * <li>prependAsButton/appendAsButton - Controls addon styling</li>
 * </ul>
 * 
 * <h2>Facets</h2>
 * <ul>
 * <li>prepend - Content to display before the input</li>
 * <li>append - Content to display after the input</li>
 * <li>label - Custom label content (alternative to label attribute)</li>
 * <li>helpText - Additional help text displayed below the field</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 *
 * <pre>
 * {@code 
 * <boot:labeledContainer label="Username" forIdentifier="inputUsername">
 *   <h:inputText id="inputUsername" />
 * </boot:labeledContainer>
 * 
 * <!-- With addons -->
 * <boot:labeledContainer label="Email">
 *   <f:facet name="prepend">@</f:facet>
 *   <h:inputText id="input" />
 * </boot:labeledContainer>
 * }
 * </pre>
 *
 * @author Matthias Walliczek
 */
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@FacesComponent(BootstrapFamily.LABELED_CONTAINER_COMPONENT)
public class LabeledContainerComponent extends BaseCuiNamingContainer implements TitleProvider {

    private static final CuiLogger LOGGER = new CuiLogger(LabeledContainerComponent.class);

    /**
     * "data-labeled-container".
     */
    public static final String DATA_LABELED_CONTAINER = "data-labeled-container";
    private static final Integer DEFAULT_COLUMN_COUNT_CONTENT = 8;
    private static final Integer DEFAULT_COLUMN_COUNT_LABEL = 4;
    private static final String ERROR_CLASS_KEY = "errorClass";
    private static final String RENDER_MESSAGE_KEY = "renderMessage";
    private static final String RENDER_COMPLEX_OUTPUT_KEY = "renderComplexOutput";
    private static final String RENDER_INPUT_GROUP_KEY = "renderInputGroup";

    private static final String PREPEND_AS_BUTTON_NAME = "prependAsButton";
    private static final String APPEND_AS_BUTTON_NAME = "appendAsButton";

    @Delegate
    private final LabelProvider labelProvider;

    @Delegate
    private final ContentProvider contentProvider;

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final LabelColumnProvider labelColumnProvider;

    @Delegate
    private final ContentColumnProvider contentColumnProvider;

    @Delegate
    private final PlaceholderProvider placeholderProvider;

    @Delegate
    private final ForIdentifierProvider forIdentifierProvider;

    @Delegate
    private final LayoutModeProvider layoutModeProvider;

    @Delegate
    private final DisabledComponentProvider disabledProvider;

    private final OneTimeCheck oneTimeCheck;

    private final CuiState state;

    /**
     *
     */
    public LabeledContainerComponent() {
        super.setRendererType(BootstrapFamily.LABELED_CONTAINER_COMPONENT_RENDERER);
        labelProvider = new LabelProvider(this);
        contentProvider = new ContentProvider(this);
        titleProvider = new TitleProviderImpl(this);
        labelColumnProvider = new LabelColumnProvider(this, DEFAULT_COLUMN_COUNT_LABEL);
        contentColumnProvider = new ContentColumnProvider(this, DEFAULT_COLUMN_COUNT_CONTENT);
        placeholderProvider = new PlaceholderProvider(this);
        forIdentifierProvider = new ForIdentifierProvider(this, ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER);
        layoutModeProvider = new LayoutModeProvider(this, LayoutMode.COLUMN);
        oneTimeCheck = new OneTimeCheck(this);
        disabledProvider = new DisabledComponentProvider(this);
        state = new CuiState(getStateHelper());
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent) {
            resolvePlugins().forEach(p -> p.prerender(this));
            processPreRenderComponentEvent();
        } else if (event instanceof PostAddToViewEvent && !oneTimeCheck.readAndSetChecked()) {
            resolvePlugins().forEach(p -> p.postAddToView(this));
            processPostAddToViewEvent();
        }
        super.processEvent(event);
    }

    private void processPostAddToViewEvent() {
        final var forModifier = findRelatedComponentModifier();
        if (null != forModifier) {
            // Set style class form-control
            if (!containsCheckbox() && forModifier.isSupportsStyleClass()) {
                final var existing = forModifier.getStyleClass();
                // Only add style class if for-component does not provide any
                // defined styleClass.
                if (null == existing) {
                    forModifier.addStyleClass(CssBootstrap.FORM_CONTROL.getStyleClass());
                }
            }
            if (forModifier.isSupportsTitle() && titleProvider.isTitleSet()) {
                forModifier.setTitle(titleProvider.resolveTitle());
            }
            final var cuiMessage = CuiMessageComponent.create(getFacesContext());
            cuiMessage.setId("message");
            getChildren().add(cuiMessage);
            cuiMessage.setParent(this);
            if (forModifier.isCompositeInput()) {
                cuiMessage.setForIdentifier(forModifier.getForIndentifiers());
            }
        }
        getPassThroughAttributes().put(DATA_LABELED_CONTAINER, DATA_LABELED_CONTAINER);
    }

    private void processPreRenderComponentEvent() {
        final var forModifier = findRelatedComponentModifier();
        final var localStyleModifier = getStyleClassBuilder();

        if (checkForComponent(forModifier)) {
            final var errorClass = getErrorClass();
            if (forModifier.isValid()) {
                localStyleModifier.remove(errorClass);
            } else {
                localStyleModifier.append(errorClass);
            }
            handlePluginStates(localStyleModifier);

            if (forModifier.isRequired()) {
                localStyleModifier.append(CssCuiBootstrap.CUI_REQUIRED);
            } else {
                localStyleModifier.remove(CssCuiBootstrap.CUI_REQUIRED);
            }
            if (forModifier.isSupportsDisabled()) {
                forModifier.setDisabled(isDisabled());
            }
            placeholderProvider.setPlaceholder(findRelatedComponent(), getFacesContext(), this);
        } else {
            localStyleModifier.remove(CssCuiBootstrap.CUI_REQUIRED);
            handlePluginStates(localStyleModifier);
        }
        setStyleClass(localStyleModifier.getStyleClass());
    }

    private void handlePluginStates(StyleClassBuilder localStyleModifier) {
        Set<PluginStateInfo> states = new TreeSet<>();
        resolvePlugins().forEach(p -> states.add(p.provideContainerStateInfo()));

        if (states.isEmpty()) {
            // This is the case if there is no plugin defined
            return;
        }
        localStyleModifier.remove(PluginStateInfo.WARNING.getClassBuilder());
        if (states.contains(PluginStateInfo.WARNING)) {
            localStyleModifier.append(PluginStateInfo.WARNING.getClassBuilder());
        }
    }

    private List<ContainerPlugin> resolvePlugins() {
        List<ContainerPlugin> found = new ArrayList<>();
        var iterator = getFacetsAndChildren();
        while (iterator.hasNext()) {
            var next = iterator.next();
            if (next instanceof ContainerPlugin plugin) {
                found.add(plugin);
            }
        }

        return found;
    }

    private boolean isApplicationInProductionStage() {
        return ProjectStage.Production.equals(getFacesContext().getApplication().getProjectStage());
    }

    /**
     * @return the component defined by the for attribute, aka this OutputLabel is
     * related to.
     * It returns null if none could be found.
     */
    UIComponent findRelatedComponent() {
        final var forId = forIdentifierProvider.resolveFirstIdentifier();
        UIComponent found = null;
        if (forId.isPresent()) {
            final var namingContainer = (UIComponent) findNearestNamingContainer(this);
            found = namingContainer.findComponent(forId.get());
            if (LOGGER.isDebugEnabled()) {
                try {
                    if (null == found && null == contentProvider.resolveContent() && isRendered()) {
                        if (getChildren().isEmpty()) {
                            LOGGER.debug("LabeledContainer '%s' does not contain any children and no content.", getClientId());
                        } else if (isApplicationInProductionStage()) {
                            LOGGER.debug("LabeledContainer '%s' does not contain an input component with id '%s'. Please check if you want to render an input element and did not adapt the id of this element. If you want to use it for output text, you can ignore this message", getClientId(), forId.get());
                        } else if (shouldNotRenderComplexOutput()) {
                            LOGGER.info(BootstrapLogMessages.INFO.NO_INPUT_COMPONENT, getClientId(), forId.get());
                        }
                    }
                    // cui-rewrite:disable InvalidExceptionUsageRecipe
                } catch (Exception e) {
                    LOGGER.debug("Exception during logging: ", e);
                }
            }
        } else {
            LOGGER.debug("forId is not present for '%s'", getClientId());
        }
        return found;
    }

    /**
     * @return A CombinedStyleClassComponentModifier of the component defined by the
     * for attribute, aka this OutputLabel is related to.
     * It returns null if none could be found.
     */
    public CombinedComponentModifier findRelatedComponentModifier() {
        final var forComponent = findRelatedComponent();
        if (null != forComponent && !(forComponent instanceof HtmlInputHidden)) {
            return StyleClassModifierFactory.findFittingWrapper(forComponent);
        }
        return null;
    }

    /**
     * @return true if the component contains a radiobutton or checkbox as an input
     * element.
     */
    public boolean containsCheckbox() {
        return findRelatedComponent() instanceof HtmlSelectBooleanCheckbox;
    }

    /**
     * @return true if the component contains an {@link UIInput} as child.
     */
    public boolean containsUIInput() {
        final var forComponent = findRelatedComponentModifier();
        return checkForComponent(forComponent);
    }

    private static boolean checkForComponent(CombinedComponentModifier forComponent) {
        return null != forComponent && forComponent.isEditableValueHolder() && forComponent.isRendered();
    }

    /**
     * @return boolean indicating whether to render complex-output
     */
    public boolean shouldNotRenderComplexOutput() {
        return !state.getBoolean(RENDER_COMPLEX_OUTPUT_KEY, false);
    }

    /**
     * @return the {@link CuiMessageComponent} related to this component
     */
    public CuiMessageComponent getCuiMessage() {
        for (final UIComponent component : getChildren()) {
            if (component instanceof CuiMessageComponent messageComponent) {
                return messageComponent;
            }
        }
        return null;
    }

    /**
     * @param renderMessage Indicating whether to render a message block.
     */
    public void setRenderMessage(final boolean renderMessage) {
        state.put(RENDER_MESSAGE_KEY, renderMessage);
    }

    /**
     * @return boolean Indicating whether to render a complex output.
     */
    public boolean getRenderMessage() {
        return state.getBoolean(RENDER_MESSAGE_KEY, true);
    }

    /**
     * @param renderComplexOutput Indicating whether to render a complex output.
     */
    public void setRenderComplexOutput(final boolean renderComplexOutput) {
        state.put(RENDER_COMPLEX_OUTPUT_KEY, renderComplexOutput);
    }

    /**
     * @return boolean Indicating whether to render an input group style class.
     */
    public boolean getRenderInputGroup() {
        return state.getBoolean(RENDER_INPUT_GROUP_KEY, false);
    }

    /**
     * @param renderInputGroup Indicating whether to render an input group style
     *                         class.
     */
    public void setRenderInputGroup(final boolean renderInputGroup) {
        state.put(RENDER_INPUT_GROUP_KEY, renderInputGroup);
    }

    /**
     * @return boolean Indicating whether to render a message block.
     */
    public boolean getRenderComplexOutput() {
        return state.getBoolean(RENDER_INPUT_GROUP_KEY, false);
    }

    /**
     * @param prependAsButton boolean indicating whether the 'prepend' facet is to
     *                        be treated as a button
     */
    public void setPrependAsButton(final boolean prependAsButton) {
        state.put(PREPEND_AS_BUTTON_NAME, prependAsButton);
    }

    /**
     * @return boolean indicating whether the 'prepend' facet is to be treated as
     * button, defaults to {@code false}
     */
    public boolean getPrependAsButton() {
        return state.getBoolean(PREPEND_AS_BUTTON_NAME, false);
    }

    /**
     * @param appendAsButton boolean indicating whether the 'append' facet is to be
     *                       treated as a button
     */
    public void setAppendAsButton(final boolean appendAsButton) {
        state.put(APPEND_AS_BUTTON_NAME, appendAsButton);
    }

    /**
     * @return boolean indicating whether the 'append' facet is to be treated as
     * button, defaults to {@code false}
     */
    public boolean getAppendAsButton() {
        return state.getBoolean(APPEND_AS_BUTTON_NAME, false);
    }

    /**
     * @param errorClass to be set
     */
    public void setErrorClass(final String errorClass) {
        state.put(ERROR_CLASS_KEY, errorClass);
    }

    /**
     * @return errorClass previously set
     */
    public String getErrorClass() {
        return state.get(ERROR_CLASS_KEY, CssBootstrap.HAS_ERROR.getStyleClass());
    }

    /**
     * @return the prepend-facet if present, otherwise null
     */
    public UIComponent getPrependFacet() {
        return resolveFacetFromPlugins(PREPEND).orElse(getFacet(PREPEND.getName()));
    }

    /**
     * @return an {@link UIComponent} to be displayed as additional help text under
     * the input component and validation messages, if present
     */
    public Optional<UIComponent> getAdditionalHelpText() {
        return resolveFacetFromPlugins(HELP_TEXT);
    }

    private Optional<UIComponent> resolveFacetFromPlugins(ContainerFacets facets) {
        for (ContainerPlugin plugin : resolvePlugins()) {
            var fromPlugin = plugin.provideFacetContent(facets);
            if (fromPlugin.isPresent()) {
                return fromPlugin;
            }
        }
        return Optional.empty();
    }

    /**
     * @return boolean indicating whether there is a 'prepend' facet available
     * <em>and</em> whether this facet is rendered.
     */
    public boolean isPrependFacetRendered() {
        final var facet = getPrependFacet();
        return null != facet && facet.isRendered();
    }

    /**
     * @return the append-facet if present, otherwise null
     */
    public UIComponent getAppendFacet() {
        return resolveFacetFromPlugins(APPEND).orElse(getFacet(APPEND.getName()));
    }

    /**
     * @return boolean indicating whether there is an 'append'-facet available
     * <em>and</em> whether this facet is rendered.
     */
    public boolean isAppendFacetRendered() {
        final var facet = getAppendFacet();
        return null != facet && facet.isRendered();
    }

    /**
     * @return the label facet if present, otherwise null
     */
    public UIComponent getLabelFacet() {
        return getFacet(LABEL.getName());
    }

    /**
     * @return boolean indicating whether there is a 'label' facet available
     * <em>and</em> whether this facet is rendered.
     */
    public boolean isLabelFacetRendered() {
        final var facet = getLabelFacet();
        return null != facet && facet.isRendered();
    }

    /**
     * @return true if an input group should be rendered
     */
    public boolean shouldRenderInputGroup() {
        return getRenderInputGroup() || isAppendFacetRendered() || isPrependFacetRendered();
    }

    /**
     * @return boolean indicating whether to render a 'form-group' element. This is
     * only <em>not</em> the case if it is {@link LayoutMode#PLAIN}
     */
    public boolean shouldWriteFormGroup() {
        return !LayoutMode.PLAIN.equals(resolveLayoutMode());
    }

    /**
     * @return boolean indicating whether to render as a column. This is only the
     * case if it is {@link LayoutMode#COLUMN}
     */
    public boolean shouldRenderAsColumn() {
        return LayoutMode.COLUMN.equals(resolveLayoutMode());
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    @Override
    public String toString() {
        return "LabeledContainerComponent [isRendered()=" + isRendered() + ", getFacetsAndChildren()="
                + getFacetsAndChildren() + ", getId()=" + getId() + ", getParent()=" + getParent() + ", isTransient()="
                + isTransient() + "]";
    }
}
