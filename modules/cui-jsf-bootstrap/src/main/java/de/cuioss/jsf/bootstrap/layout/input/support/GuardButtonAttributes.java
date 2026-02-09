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
package de.cuioss.jsf.bootstrap.layout.input.support;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.layout.input.InputGuardComponent;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

import java.io.Serializable;

/**
 * <p>
 * Component attribute provider that encapsulates guard button configuration for
 * {@link InputGuardComponent}. This class manages the appearance and behavior of the
 * button that unlocks guarded input fields.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Manages icon selection for the unlock button</li>
 *   <li>Provides internationalized title/tooltip text for the button</li>
 *   <li>Supports both resource bundle keys and direct values</li>
 *   <li>Handles conversion and escaping options</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>guardIcon</b> - Icon to display on the unlock button (default: "cui-icon-unlock")</li>
 *   <li><b>guardButtonTitleKey</b> - Resource bundle key for button tooltip (default: "input_guard.unlock.default.title")</li>
 *   <li><b>guardButtonTitleValue</b> - Direct value for button tooltip (takes precedence over titleKey if both are present)</li>
 *   <li><b>guardButtonTitleConverter</b> - Optional converter for title value</li>
 *   <li><b>guardButtonTitleEscape</b> - Whether to escape HTML in title (default: true)</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>
 * This class is used internally by {@link InputGuardComponent} and is not typically
 * instantiated directly by application code. The attributes it manages are exposed
 * as properties on the InputGuardComponent.
 * </p>
 * 
 * @author Oliver Wolff
 * @see InputGuardComponent
 * @see ResetGuardButtonAttributes
 */
public class GuardButtonAttributes {

    /** "cui-icon-unlock". */
    public static final String ICON_DEFAULT = "cui-icon-unlock";
    private static final String KEY_KEY = "guardButtonTitleKey";
    private static final String VALUE_KEY = "guardButtonTitleValue";
    private static final String CONVERTER_KEY = "guardButtonTitleConverter";
    private static final String ESACPE_KEY = "guardButtonTitleEscape";
    private static final String ICON_KEY = "guardIcon";
    private static final String INPUT_GUARD_UNLOCK_DEFAULT_TITLE = "input_guard.unlock.default.title";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param componentBridge must not be null
     */
    public GuardButtonAttributes(@NonNull ComponentBridge componentBridge) {
        this.componentBridge = componentBridge;
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * The icon to be displayed on the button removing the guard and making it
     * editable
     *
     * @return the guardIcon, defaults to 'cui-icon-unlock'
     */
    public String getGuardIcon() {
        return state.get(ICON_KEY, ICON_DEFAULT);
    }

    /**
     * @param guardIcon to be set
     */
    public void setGuardIcon(String guardIcon) {
        state.put(ICON_KEY, guardIcon);
    }

    /**
     * @return the guardButtonTitleKey
     */
    public String getGuardButtonTitleKey() {
        return state.get(KEY_KEY, INPUT_GUARD_UNLOCK_DEFAULT_TITLE);
    }

    /**
     * @param guardButtonTitleKey the guardButtonTitleKey to set
     */
    public void setGuardButtonTitleKey(String guardButtonTitleKey) {
        state.put(KEY_KEY, guardButtonTitleKey);
    }

    /**
     * @return the guardButtonTitleValue
     */
    public Serializable getGuardButtonTitleValue() {
        return state.get(VALUE_KEY);
    }

    /**
     * @param guardButtonTitleValue the guardButtonTitleValue to set
     */
    public void setGuardButtonTitleValue(Serializable guardButtonTitleValue) {
        state.put(VALUE_KEY, guardButtonTitleValue);
    }

    /**
     * @return the guardButtonTitleConverter
     */
    public Object getGuardButtonTitleConverter() {
        return state.get(CONVERTER_KEY);
    }

    /**
     * @param guardButtonTitleConverter the guardButtonTitleConverter to set
     */
    public void setGuardButtonTitleConverter(Object guardButtonTitleConverter) {
        state.put(CONVERTER_KEY, guardButtonTitleConverter);
    }

    /**
     * @return the escape
     */
    public boolean getGuardButtonTitleEscape() {
        return state.getBoolean(ESACPE_KEY, true);
    }

    /**
     * @param guardButtonTitleEscape the escape to set
     */
    public void setGuardButtonTitleEscape(boolean guardButtonTitleEscape) {
        state.put(ESACPE_KEY, guardButtonTitleEscape);
    }

    /**
     * @return the resolved content if available, otherwise it will return null.
     */
    public String resolveGuardButtonTitle() {
        final var titleValue = getGuardButtonTitleValue();
        final var titleKey = getGuardButtonTitleKey();
        if (titleValue == null && MoreStrings.isEmpty(titleKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getGuardButtonTitleConverter()).withLabelKey(titleKey)
                .withLabelValue(titleValue).build().resolve(componentBridge.facesContext());
    }

}
