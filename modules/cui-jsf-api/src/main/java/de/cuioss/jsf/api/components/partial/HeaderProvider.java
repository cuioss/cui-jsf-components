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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import lombok.NonNull;

import java.io.Serializable;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the header
 * string of a component. The implementation relies on the correct use of
 * attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>headerKey</h2>
 * <p>
 * The key for looking up the text to be displayed as the text header. Although
 * this attribute is not required you must provide either this or #headerValue
 * if you want a label to be displayed.
 * </p>
 * <h2>headerValue</h2>
 * <p>
 * The Object representing the text to be displayed. This is a replacement for
 * #headerKey. If both are present headerValue takes precedence. The object is
 * usually a string. If not, the developer must ensure that a corresponding
 * converter is either registered for the type or must provide a converter using
 * #headerConverter.
 * </p>
 * <h2>headerConverter</h2>
 * <p>
 * The optional converterId to be used in case of headerValue is set and needs
 * conversion.
 * </p>
 * <h2>headerEscape</h2>
 * <p>
 * Indicates whether the header is to be escaped on output or not. Default is
 * <code>true</code>.
 * </p>
 * <h2>headerTag</h2>
 * <p>
 * Defines the HTML element to be used within the header. Default is
 * <code>h4</code>.
 * </p>
 *
 * @author Matthias Walliczek
 * @author Sven Haag
 */
public class HeaderProvider {

    /** The key for the {@link StateHelper} */
    private static final String HEADER_KEY_KEY = "headerKey";

    /** The key for the {@link StateHelper} */
    private static final String HEADER_VALUE_KEY = "headerValue";

    /** The key for the {@link StateHelper} */
    private static final String HEADER_CONVERTER_KEY = "headerConverter";

    /** The key for the {@link StateHelper} */
    private static final String HEADER_ESCAPE_KEY = "headerEscape";

    /** The key for the {@link StateHelper} */
    private static final String HEADER_TAG_KEY = "headerTag";

    /** The header facets name */
    private static final String HEADER_FACET_NAME = "header";

    /** Default value for headerTag */
    public static final String HEADERTAG_DEFAULT = Node.H4.getContent();

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public HeaderProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        componentBridge = bridge;
    }

    /**
     * @return the headerKey
     */
    public String getHeaderKey() {
        return state.get(HEADER_KEY_KEY);
    }

    /**
     * @param headerKey the headerKey to set
     */
    public void setHeaderKey(final String headerKey) {
        state.put(HEADER_KEY_KEY, headerKey);
    }

    /**
     * @return the headerValue
     */
    public Serializable getHeaderValue() {
        return state.get(HEADER_VALUE_KEY);
    }

    /**
     * @param headerValue the headerValue to set
     */
    public void setHeaderValue(final Serializable headerValue) {
        state.put(HEADER_VALUE_KEY, headerValue);
    }

    /**
     * @return the headerConverter
     */
    public Object getHeaderConverter() {
        return state.get(HEADER_CONVERTER_KEY);
    }

    /**
     * @param headerConverter the headerConverter to set
     */
    public void setHeaderConverter(final Object headerConverter) {
        state.put(HEADER_CONVERTER_KEY, headerConverter);
    }

    /**
     * @return escape header. Defaults to true.
     */
    public boolean isHeaderEscape() {
        return state.getBoolean(HEADER_ESCAPE_KEY, true);
    }

    /**
     * @param headerEscape the escape to set
     */
    public void setHeaderEscape(final boolean headerEscape) {
        state.put(HEADER_ESCAPE_KEY, headerEscape);
    }

    /**
     * @param headerTag the HTML tag to be used for the header title.
     */
    public void setHeaderTag(final String headerTag) {
        state.put(HEADER_TAG_KEY, headerTag);
    }

    /**
     * @return the HTML tag to be used for the header title.
     */
    public String getHeaderTag() {
        return state.get(HEADER_TAG_KEY, HEADERTAG_DEFAULT);
    }

    /**
     * @return the header facet or null.
     */
    public UIComponent getHeaderFacet() {
        return componentBridge.facet(HEADER_FACET_NAME);
    }

    /**
     * @return the resolved header if available, otherwise it will return null.
     */
    public String resolveHeader() {
        final var headerValue = getHeaderValue();
        final var headerKey = getHeaderKey();
        if (headerValue == null && MoreStrings.isEmpty(headerKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getHeaderConverter()).withLabelKey(headerKey)
                .withEscape(isHeaderEscape()).withLabelValue(headerValue).build()
                .resolve(componentBridge.facesContext());
    }

    /**
     * @return true if a headerValue or headerKey is set.
     */
    public boolean hasHeaderTitleSet() {
        return getHeaderValue() != null || !MoreStrings.isEmpty(getHeaderKey());
    }

    /**
     * @return true if a headerKey, headerValue or header facet is present.
     */
    public boolean shouldRenderHeaderFacet() {
        var facet = getHeaderFacet();
        return null != facet && facet.isRendered();
    }

    /**
     * @return true, if a header facet is present.
     */
    public boolean hasHeaderFacet() {
        return null != getHeaderFacet();
    }

    /**
     * @return boolean indicating whether there is a 'header' facet available
     *         <em>and</em> whether this facet is rendered.
     */
    public boolean shouldRenderHeader() {
        return hasHeaderTitleSet() || shouldRenderHeaderFacet();
    }
}
