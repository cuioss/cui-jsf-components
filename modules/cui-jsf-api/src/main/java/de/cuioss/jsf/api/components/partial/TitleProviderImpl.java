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

import java.io.Serializable;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the title
 * attribute. The implementation relies on the correct user of the attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>titleKey</h2>
 * <p>
 * The key for looking up the text for the title-attribute. Although this
 * attribute is not required you must provide either this or #titleValue if you
 * want a title to be displayed.
 * </p>
 * <h2>titleValue</h2>
 * <p>
 * The Object displayed for the title-attribute. This is a replacement for
 * #titleKey. If both are present titleValue takes precedence. This object is
 * usually a String. If not, the developer must ensure that a corresponding
 * converter is either registered for the type or must provide a converter using
 * #titleConverter.
 * </p>
 * <h2>titleConverter</h2>
 * <p>
 * The optional converterId to be used in case of titleValue is set and needs
 * conversion.
 * </p>
 *
 * @author Oliver Wolff
 */
public class TitleProviderImpl implements TitleProvider {

    /**
     * The key for the {@link StateHelper}
     */
    private static final String TITLE_KEY_KEY = "titleKey";

    /**
     * The key for the {@link StateHelper}
     */
    private static final String TITLE_VALUE_KEY = "titleValue";

    /**
     * The key for the {@link StateHelper}
     */
    private static final String TITLE_CONVERTER_KEY = "titleConverter";

    /**
     * The key for the resolved title.
     */
    private static final String TITLE_KEY = "title";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public TitleProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        componentBridge = bridge;
    }

    @Override
    public void setTitleKey(final String titleKey) {
        state.put(TITLE_KEY_KEY, titleKey);
    }

    @Override
    public String getTitleKey() {
        return state.get(TITLE_KEY_KEY);
    }

    /**
     * @param titleValue to be set.
     */
    @Override
    public void setTitleValue(final Serializable titleValue) {
        state.put(TITLE_VALUE_KEY, titleValue);
    }

    @Override
    public Serializable getTitleValue() {
        return state.get(TITLE_VALUE_KEY);
    }

    @Override
    public Object getTitleConverter() {
        return state.get(TITLE_CONVERTER_KEY);
    }

    @Override
    public void setTitleConverter(final Object titleConverter) {
        state.put(TITLE_CONVERTER_KEY, titleConverter);
    }

    @Override
    public String resolveTitle() {
        final var labelValue = getTitleValue();
        final var labelKey = getTitleKey();
        if (!isTitleSet()) {
            return null;
        }
        return LabelResolver.builder().withLabelKey(labelKey).withStrictMode(false).withConverter(getTitleConverter())
                .withLabelValue(labelValue).build().resolve(componentBridge.facesContext());
    }

    @Override
    public String getTitle() {
        return state.get(TITLE_KEY);
    }

    @Override
    public boolean isTitleSet() {
        return getTitleValue() != null || !MoreStrings.isEmpty(getTitleKey());
    }

    @Override
    public void setTitle(final String title) {
        throw new UnsupportedOperationException(componentBridge.getClass().getCanonicalName()
                + " wrong usage detected. Use setTitleKey() or setTitleValue(Serializable) instead.");
    }

    @Override
    public void resolveAndStoreTitle() {
        // Titles should usually be set once
        if (isTitleSet() && null == state.get(TITLE_KEY)) {
            state.put(TITLE_KEY, resolveTitle());
        }
    }
}
