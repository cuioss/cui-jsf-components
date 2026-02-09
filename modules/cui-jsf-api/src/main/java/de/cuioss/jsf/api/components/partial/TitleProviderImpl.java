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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.StateHelper;
import jakarta.faces.render.Renderer;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Standard implementation of the {@link TitleProvider} interface for JSF components.
 * <p>
 * This implementation stores component state using the JSF {@link StateHelper} mechanism
 * via a {@link CuiState} wrapper and resolves titles using the {@link LabelResolver}.
 * It provides full support for both direct title values and resource bundle lookups.
 * </p>
 * <p>
 * Key features of this implementation:
 * </p>
 * <ul>
 *   <li>Efficient state handling using the component's StateHelper</li>
 *   <li>Lazy resolution of titles from resource bundles</li>
 *   <li>Support for value conversion</li>
 *   <li>Cross-JSF implementation compatibility (Mojarra/MyFaces)</li>
 * </ul>
 * <p>
 * The implementation is not thread-safe, which is consistent with the JSF component
 * lifecycle where components are not shared between requests.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * public class MyComponent extends UIComponentBase implements TitleProvider {
 *    
 *    private final TitleProviderImpl titleProvider;
 *    
 *    public MyComponent() {
 *        titleProvider = new TitleProviderImpl(new ComponentBridge(this));
 *    }
 *    
 *    &#64;Override
 *    public void setTitleKey(String titleKey) {
 *        titleProvider.setTitleKey(titleKey);
 *    }
 *    
 *    // Additional delegate methods...
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see TitleProvider
 * @see ComponentBridge
 * @see LabelResolver
 */
public class TitleProviderImpl implements TitleProvider {

    /**
     * The key for the {@link StateHelper} to store the resource bundle key
     */
    private static final String TITLE_KEY_KEY = "titleKey";

    /**
     * The key for the {@link StateHelper} to store the direct title value
     */
    private static final String TITLE_VALUE_KEY = "titleValue";

    /**
     * The key for the {@link StateHelper} to store the converter
     */
    private static final String TITLE_CONVERTER_KEY = "titleConverter";

    /**
     * The key for storing the resolved title in the {@link StateHelper}
     */
    private static final String TITLE_KEY = "title";

    /**
     * Bridge to the owning component, providing access to its FacesContext and StateHelper
     */
    private final ComponentBridge componentBridge;

    /**
     * Wrapper for the component's state management
     */
    private final CuiState state;

    /**
     * Constructor creating a new TitleProviderImpl.
     * <p>
     * The provider needs a bridge to the component it belongs to in order to
     * access the component's state management and FacesContext.
     * </p>
     *
     * @param bridge the component bridge connecting to the owning component, must not be null
     * @throws NullPointerException if bridge is null
     */
    public TitleProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        componentBridge = bridge;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitleKey(final String titleKey) {
        state.put(TITLE_KEY_KEY, titleKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitleKey() {
        return state.get(TITLE_KEY_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitleValue(final Serializable titleValue) {
        state.put(TITLE_VALUE_KEY, titleValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Serializable getTitleValue() {
        return state.get(TITLE_VALUE_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getTitleConverter() {
        return state.get(TITLE_CONVERTER_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitleConverter(final Object titleConverter) {
        state.put(TITLE_CONVERTER_KEY, titleConverter);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The implementation uses {@link LabelResolver} to resolve the title
     * from either the direct value or resource bundle key. This handles
     * all conversion and localization needs.
     * </p>
     */
    @Override
    public String resolveTitle() {
        final var labelValue = getTitleValue();
        final var labelKey = getTitleKey();
        if (!isTitleSet()) {
            return null;
        }
        return LabelResolver.builder()
                .withLabelKey(labelKey)
                .withStrictMode(false)
                .withConverter(getTitleConverter())
                .withLabelValue(labelValue)
                .build()
                .resolve(componentBridge.facesContext());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return state.get(TITLE_KEY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The implementation considers a title to be set if either titleValue is not null
     * or titleKey is not empty.
     * </p>
     */
    @Override
    public boolean isTitleSet() {
        return getTitleValue() != null || !MoreStrings.isEmpty(getTitleKey());
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation always throws an {@link UnsupportedOperationException}
     * to enforce proper usage of the specific title properties.
     * </p>
     * 
     * @throws UnsupportedOperationException always thrown to prevent direct title setting
     */
    @Override
    public void setTitle(final String title) {
        throw new UnsupportedOperationException(componentBridge.getClass().getCanonicalName()
                + " wrong usage detected. Use setTitleKey() or setTitleValue(Serializable) instead.");
    }

    /**
     * {@inheritDoc}
     * <p>
     * The implementation only resolves and stores the title once to avoid
     * redundant processing. This method should be called by the {@link Renderer}
     * before rendering a component.
     * </p>
     */
    @Override
    public void resolveAndStoreTitle() {
        // Titles should usually be set once
        if (isTitleSet() && null == state.get(TITLE_KEY)) {
            state.put(TITLE_KEY, resolveTitle());
        }
    }
}
