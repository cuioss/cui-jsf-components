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

import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the content
 * string of a component. The implementation relies on the correct use of
 * attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>contentKey</h2>
 * <p>
 * The key for looking up the text to be displayed as the text content. Although
 * this attribute is not required the developer must provide either this or
 * #contentValue if you want a content to be displayed.
 * </p>
 * <h2>contentValue</h2>
 * <p>
 * The Object representing the text to be displayed. This is a replacement for
 * #contentKey. If both are present contentValue takes precedence.
 * </p>
 * <h2>contentConverter</h2>
 * <p>
 * The optional converterId to be used in case of contentValue is set and need
 * conversion.
 * </p>
 * <h2>contentEscape</h2>
 * <p>
 * Indicates whether the content is to be escaped on output or not. Default is
 * <code>true</code>
 * </p>
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
public class ContentProvider {

    /** The key for the {@link StateHelper} */
    private static final String CONTENT_KEY_KEY = "contentKey";

    /** The key for the {@link StateHelper} */
    private static final String CONTENT_VALUE_KEY = "contentValue";

    /** The key for the {@link StateHelper} */
    private static final String CONTENT_CONVERTER_KEY = "contentConverter";

    /** The key for the {@link StateHelper} */
    public static final String CONTENT_ESACPE_KEY = "contentEscape";

    @NonNull
    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param componentBridge
     */
    public ContentProvider(@NonNull ComponentBridge componentBridge) {
        this.componentBridge = componentBridge;
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * @return contentKey
     */
    public String getContentKey() {
        return state.get(CONTENT_KEY_KEY);
    }

    /**
     * @param contentKey the contentKey to set
     */
    public void setContentKey(final String contentKey) {
        state.put(CONTENT_KEY_KEY, contentKey);
    }

    /**
     * @return the contentValue
     */
    public Serializable getContentValue() {
        return state.get(CONTENT_VALUE_KEY);
    }

    /**
     * @param contentValue
     */
    public void setContentValue(final Serializable contentValue) {
        state.put(CONTENT_VALUE_KEY, contentValue);
    }

    /**
     * @return the contentConverter
     */
    public Object getContentConverter() {
        return state.get(CONTENT_CONVERTER_KEY);
    }

    /**
     * @param contentConverter
     */
    public void setContentConverter(final Object contentConverter) {
        state.put(CONTENT_CONVERTER_KEY, contentConverter);
    }

    /**
     * @return contentEscape
     */
    public boolean getContentEscape() {
        return state.getBoolean(CONTENT_ESACPE_KEY, true);
    }

    /**
     * @param contentEscape the escape to set
     */
    public void setContentEscape(final boolean contentEscape) {
        state.put(CONTENT_ESACPE_KEY, contentEscape);
    }

    /**
     * @return the resolved content if available, otherwise it will return null.
     */
    public String resolveContent() {
        final var contentValue = getContentValue();
        final var contentKey = getContentKey();
        if (contentValue == null && MoreStrings.isEmpty(contentKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getContentConverter()).withLabelKey(contentKey)
                .withEscape(getContentEscape()).withLabelValue(contentValue).build()
                .resolve(componentBridge.facesContext());
    }

}
