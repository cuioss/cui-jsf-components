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
package de.cuioss.jsf.components.html.fieldset;

import de.cuioss.jsf.api.components.base.CuiComponentBase;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PreRenderComponentEvent;
import lombok.experimental.Delegate;

import java.io.Serializable;

/**
 * <p>JSF component for generating an HTML5 {@code <fieldset>} element with optional
 * legend. The fieldset element is used to group related form controls and provides
 * semantic structure and accessibility benefits.</p>
 * 
 * <p>The component renders a fieldset with configurable styling, state, and legend text.
 * The legend can be specified either via a resource bundle key or a direct value, with
 * optional conversion and escaping control.</p>
 * 
 * <p>Core features include:</p>
 * <ul>
 *   <li>Full HTML5 fieldset support</li>
 *   <li>Internationalization support for the legend text</li>
 *   <li>Ability to disable the fieldset</li>
 *   <li>CSS styling support via styleClass and style attributes</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;cui:fieldset legendValue="Personal Information" styleClass="user-details"&gt;
 *     &lt;!-- Form inputs --&gt;
 *     &lt;h:inputText value="#{bean.firstName}" /&gt;
 *     &lt;h:inputText value="#{bean.lastName}" /&gt;
 * &lt;/cui:fieldset&gt;
 * </pre>
 * 
 * <h2>Attributes</h2>
 * 
 * <h3>legendKey</h3>
 * <p>
 * The key for looking up the text to be displayed as the legend. Although
 * this attribute is not required you must provide either this or #legendValue
 * if you want a legend to be displayed.
 * </p>
 * 
 * <h3>legendValue</h3>
 * <p>
 * The Object representing the text to be displayed as legend. This is a replacement for
 * #legendKey. If both are present legendValue takes precedence. The object is
 * usually a string. If not, the developer must ensure that a corresponding
 * converter is either registered for the type or must provide a converter using
 * #legendConverter.
 * </p>
 * 
 * <h3>legendConverter</h3>
 * <p>
 * The optional converterId to be used in case of legendValue needing conversion
 * to a string representation.
 * </p>
 * 
 * <h3>legendEscape</h3>
 * <p>
 * Indicates whether the legend text should be HTML-escaped on output.
 * Default is <code>true</code>.
 * </p>
 * 
 * <h3>disabled</h3>
 * <p>
 * Boolean attribute indicating whether the fieldset should be rendered in a disabled state.
 * When disabled, the HTML disabled attribute is added to the fieldset element.
 * Default is <code>false</code>.
 * </p>
 * 
 * <h3>styleClass</h3>
 * <p>
 * CSS classes to be applied to the rendered fieldset element.
 * </p>
 * 
 * <h3>style</h3>
 * <p>
 * Inline CSS styles to be applied to the rendered fieldset element.
 * </p>
 * 
 * <p>This component is thread-safe when the underlying JSF implementation properly
 * synchronizes concurrent access to component state.</p>
 *
 * @author Oliver Wolff
 * @see FieldsetRenderer The renderer responsible for the actual HTML output
 * @see DisabledComponentProvider For the disabled state handling
 * @see ComponentStyleClassProvider For the CSS class handling
 * @see StyleAttributeProvider For the style attribute handling
 * @since 1.0
 */
@FacesComponent(CuiFamily.FIELDSET_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
public class FieldsetComponent extends CuiComponentBase implements ComponentStyleClassProvider {

    private static final String LEGEND_KEY_KEY = "legendKey";
    private static final String LEGEND_VALUE_KEY = "legendValue";
    private static final String LEGEND_CONVERTER_KEY = "legendConverter";
    private static final String LEGEND_ESCAPE_KEY = "legendEscape";
    static final String DISABLED_ATTRIBUTE_NAME = "disabled";

    private final CuiState state;

    @Delegate
    private final DisabledComponentProvider disabledComponentProvider;

    @Delegate
    private final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    private final StyleAttributeProvider styleAttributeProvider;

    /**
     * Default constructor. Sets the renderer type and initializes the component with
     * the default providers for disabled state, style classes, and style attributes.
     */
    public FieldsetComponent() {
        super.setRendererType(CuiFamily.FIELDSET_RENDERER);
        disabledComponentProvider = new DisabledComponentProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
        state = new CuiState(getStateHelper());
    }

    /**
     * Process component events. Specifically handles the PreRenderComponentEvent to
     * manage the disabled state of the fieldset by adding or removing the HTML disabled
     * attribute in the pass-through attributes map.
     *
     * @param event The component system event to process
     * @throws AbortProcessingException if event processing should be aborted
     */
    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck") // owolff: defined by API
    public void processEvent(final ComponentSystemEvent event) throws AbortProcessingException {
        if (event instanceof PreRenderComponentEvent) {
            if (disabledComponentProvider.isDisabled()) {
                getPassThroughAttributes(true).put(DISABLED_ATTRIBUTE_NAME, DISABLED_ATTRIBUTE_NAME);
            } else if (null != getPassThroughAttributes(false)) {
                getPassThroughAttributes().remove(DISABLED_ATTRIBUTE_NAME);
            }
        }
        super.processEvent(event);
    }

    /**
     * Returns the resource bundle key used to look up the legend text.
     *
     * @return the legend key or null if not set
     */
    public String getLegendKey() {
        return state.get(LEGEND_KEY_KEY);
    }

    /**
     * Sets the resource bundle key for the legend text.
     *
     * @param legendKey the resource bundle key to be used for looking up the legend text
     */
    public void setLegendKey(final String legendKey) {
        state.put(LEGEND_KEY_KEY, legendKey);
    }

    /**
     * Returns the direct value to be used as the legend text.
     * This takes precedence over legendKey if both are set.
     *
     * @return the legend value or null if not set
     */
    public Serializable getLegendValue() {
        return state.get(LEGEND_VALUE_KEY);
    }

    /**
     * Sets the direct value to be used as the legend text.
     *
     * @param legendValue the value to be used as legend text
     */
    public void setLegendValue(final Serializable legendValue) {
        state.put(LEGEND_VALUE_KEY, legendValue);
    }

    /**
     * Returns the converter to be used for the legend value.
     * This is only used if legendValue is set and is not a String.
     *
     * @return the converter identifier or instance to be used for legend conversion
     */
    public Object getLegendConverter() {
        return state.get(LEGEND_CONVERTER_KEY);
    }

    /**
     * Sets the converter to be used for the legend value.
     *
     * @param legendConverter the converter identifier or instance to be used
     */
    public void setLegendConverter(final Object legendConverter) {
        state.put(LEGEND_CONVERTER_KEY, legendConverter);
    }

    /**
     * Indicates whether the legend should be HTML-escaped when rendered.
     *
     * @return boolean indicating whether to escape the legend, defaults to true
     */
    public boolean isLegendEscape() {
        return state.getBoolean(LEGEND_ESCAPE_KEY, true);
    }

    /**
     * Sets whether the legend should be HTML-escaped when rendered.
     *
     * @param legendEscape true to escape the legend, false to render it as-is
     */
    public void setLegendEscape(final boolean legendEscape) {
        state.put(LEGEND_ESCAPE_KEY, Boolean.valueOf(legendEscape));
    }

    /**
     * Resolves and returns the legend text based on the current configuration.
     * The resolution logic follows these steps:
     * <ol>
     *   <li>If both legendValue and legendKey are not set, returns null</li>
     *   <li>If legendValue is set, it takes precedence over legendKey</li>
     *   <li>Applies the configured converter if necessary</li>
     * </ol>
     *
     * @return the resolved legend text or null if none is configured
     */
    public String resolveLegend() {
        final var labelValue = getLegendValue();
        final var labelKey = getLegendKey();
        if (labelValue == null && MoreStrings.isEmpty(labelKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getLegendConverter()).withLabelKey(labelKey)
                .withLabelValue(labelValue).build().resolve(getFacesContext());
    }

}
