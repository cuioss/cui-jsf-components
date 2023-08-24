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

import java.io.Serializable;

import javax.faces.component.FacesComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreRenderComponentEvent;

import de.cuioss.jsf.api.components.base.CuiComponentBase;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.DisabledComponentProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.tools.string.MoreStrings;
import lombok.experimental.Delegate;

/**
 * Component class for creating fieldset-element
 *
 * <h2>Attributes</h2> *
 * <h3>legendKey</h3>
 * <p>
 * The key for looking up the text to be displayed as the text label. Although
 * this attribute is not required you must provide either this or #legendValue
 * if you want a label to be displayed.
 * </p>
 * <h3>legendValue</h3>
 * <p>
 * The Object representing the text to be displayed. This is a replacement for
 * #legendKey. If both are present legendValue takes precedence. The object is
 * usually a string. If not, the developer must ensure that a corresponding
 * converter is either registered for the type or must provide a converter using
 * #legendConverter.
 * </p>
 * <h3>legendConverter</h3>
 * <p>
 * The optional converterId to be used in case of legendValue is set and needs
 * conversion.
 * </p>
 * <h3>legendEscape</h3>
 * <p>
 * Indicates whether the legend is to be escaped on output or not. Default is
 * <code>true</code>
 * </p>
 * <h3>{@link DisabledComponentProvider}</h3>
 *
 * @author Oliver Wolff
 *
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
     *
     */
    public FieldsetComponent() {
        super.setRendererType(CuiFamily.FIELDSET_RENDERER);
        disabledComponentProvider = new DisabledComponentProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
        state = new CuiState(getStateHelper());
    }

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
     * @return the legendKey
     */
    public String getLegendKey() {
        return state.get(LEGEND_KEY_KEY);
    }

    /**
     * @param legendKey
     */
    public void setLegendKey(final String legendKey) {
        state.put(LEGEND_KEY_KEY, legendKey);
    }

    /**
     * @return the legend-value
     */
    public Serializable getLegendValue() {
        return state.get(LEGEND_VALUE_KEY);
    }

    /**
     * @param legendValue
     */
    public void setLegendValue(final Serializable legendValue) {
        state.put(LEGEND_VALUE_KEY, legendValue);
    }

    /**
     * @return the optional converter for the legend
     */
    public Object getLegendConverter() {
        return state.get(LEGEND_CONVERTER_KEY);
    }

    /**
     * @param legendConverter
     */
    public void setLegendConverter(final Object legendConverter) {
        state.put(LEGEND_CONVERTER_KEY, legendConverter);
    }

    /**
     * @return boolean indicating whether to escape the legend, default to true
     */
    public boolean isLegendEscape() {
        return state.getBoolean(LEGEND_ESCAPE_KEY, true);
    }

    /**
     * @param legendEscape
     */
    public void setLegendEscape(final boolean legendEscape) {
        state.put(LEGEND_ESCAPE_KEY, Boolean.valueOf(legendEscape));
    }

    /**
     * @return the resolved label
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
