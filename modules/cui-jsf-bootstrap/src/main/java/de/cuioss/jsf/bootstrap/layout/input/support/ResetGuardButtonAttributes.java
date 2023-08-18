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
package de.cuioss.jsf.bootstrap.layout.input.support;

import java.io.Serializable;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.layout.input.InputGuardComponent;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * This class manages the state and resolving of the information needed for
 * creating the guard- / unguard Button in context of
 * {@link InputGuardComponent}
 * </p>
 * <h2>resetGuardButtonTitleKey</h2>
 * <p>
 * The key for looking up the text to be displayed as the title. Although this
 * attribute is not required the developer must provide either this or
 * #resetGuardButtonTitleValue if you want a title to be displayed. Defaults to
 * 'cc.unlockableField.lock.title', resulting in 'Revert changes'
 * </p>
 * <h2>resetGuardButtonTitleValue</h2>
 * <p>
 * The Object representing the title to be displayed. This is a replacement for
 * #resetGuardButtonTitleKey. If both are present resetGuardButtonTitleValue
 * takes precedence.
 * </p>
 * <h2>resetGuardButtonTitleConverter</h2>
 * <p>
 * The optional converterId to be used in case of resetGuardButtonTitleValue is
 * set and needs conversion.
 * </p>
 * <h2>resetGuardButtonTitleEscape</h2>
 * <p>
 * Indicates whether the title is to be escaped on output or not. Default is
 * <code>true</code>.
 * </p>
 *
 * @author Oliver Wolff
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
     * The icon to be displayed on the button reenabling the guard and
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
