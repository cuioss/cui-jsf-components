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
 * Component attribute provider that encapsulates the reset guard button configuration for
 * {@link InputGuardComponent}. This class manages the appearance and behavior of the
 * button that re-locks previously unlocked input fields.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Manages icon selection for the lock/reset button</li>
 *   <li>Provides internationalized title/tooltip text for the button</li>
 *   <li>Supports both resource bundle keys and direct values</li>
 *   <li>Handles conversion and escaping options</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>resetGuardIcon</b> - Icon to display on the reset/lock button (default: "cui-icon-restart")</li>
 *   <li><b>resetGuardButtonTitleKey</b> - Resource bundle key for button tooltip (default: "input_guard.lock.default.title")</li>
 *   <li><b>resetGuardButtonTitleValue</b> - Direct value for button tooltip (takes precedence over titleKey if both are present)</li>
 *   <li><b>resetGuardButtonTitleConverter</b> - Optional converter for title value</li>
 *   <li><b>resetGuardButtonTitleEscape</b> - Whether to escape HTML in title (default: true)</li>
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
 * @see GuardButtonAttributes
 */
public class ResetGuardButtonAttributes {

    /** . */
    private static final String INPUT_GUARD_LOCK_DEFAULT_TITLE = "input_guard.lock.default.title";
    /** "cui-icon-restart". */
    public static final String ICON_DEFAULT = "cui-icon-restart";
    private static final String KEY_KEY = "resetGuardButtonTitleKey";
    private static final String VALUE_KEY = "resetGuardButtonTitleValue";
    private static final String CONVERTER_KEY = "resetGuardButtonTitleConverter";
    private static final String ESACPE_KEY = "resetGuardButtonTitleEscape";
    private static final String ICON_KEY = "resetGuardIcon";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param componentBridge must not be null
     */
    public ResetGuardButtonAttributes(@NonNull ComponentBridge componentBridge) {
        this.componentBridge = componentBridge;
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * The icon to be displayed on the button re-enabling the guard and
     *
     * @return the guardIcon, defaults to 'cui-icon-unlock'
     */
    public String getResetGuardIcon() {
        return state.get(ICON_KEY, ICON_DEFAULT);
    }

    /**
     * @param resetGuardIcon to be set
     */
    public void setResetGuardIcon(String resetGuardIcon) {
        state.put(ICON_KEY, resetGuardIcon);
    }

    /**
     * @return the resetGuardButtonTitleKey
     */
    public String getResetGuardButtonTitleKey() {
        return state.get(KEY_KEY, INPUT_GUARD_LOCK_DEFAULT_TITLE);
    }

    /**
     * @param resetGuardButtonTitleKey the resetGuardButtonTitleKey to set
     */
    public void setResetGuardButtonTitleKey(String resetGuardButtonTitleKey) {
        state.put(KEY_KEY, resetGuardButtonTitleKey);
    }

    /**
     * @return the resetGuardButtonTitleValue
     */
    public Serializable getResetGuardButtonTitleValue() {
        return state.get(VALUE_KEY);
    }

    /**
     * @param resetGuardButtonTitleValue the resetGuardButtonTitleValue to set
     */
    public void setResetGuardButtonTitleValue(Serializable resetGuardButtonTitleValue) {
        state.put(VALUE_KEY, resetGuardButtonTitleValue);
    }

    /**
     * @return the resetGuardButtonTitleConverter
     */
    public Object getResetGuardButtonTitleConverter() {
        return state.get(CONVERTER_KEY);
    }

    /**
     * @param resetGuardButtonTitleConverter the resetGuardButtonTitleConverter to
     *                                       set
     */
    public void setResetGuardButtonTitleConverter(Object resetGuardButtonTitleConverter) {
        state.put(CONVERTER_KEY, resetGuardButtonTitleConverter);
    }

    /**
     * @return the escape
     */
    public boolean getResetGuardButtonTitleEscape() {
        return state.getBoolean(ESACPE_KEY, true);
    }

    /**
     * @param resetGuardButtonTitleEscape the escape to set
     */
    public void setResetGuardButtonTitleEscape(boolean resetGuardButtonTitleEscape) {
        state.put(ESACPE_KEY, resetGuardButtonTitleEscape);
    }

    /**
     * @return the resolved content if available, otherwise it will return null.
     */
    public String resolveResetGuardButtonTitle() {
        final var titleValue = getResetGuardButtonTitleValue();
        final var titleKey = getResetGuardButtonTitleKey();
        if (titleValue == null && MoreStrings.isEmpty(titleKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getResetGuardButtonTitleConverter()).withLabelKey(titleKey)
                .withLabelValue(titleValue).build().resolve(componentBridge.facesContext());
    }

}
