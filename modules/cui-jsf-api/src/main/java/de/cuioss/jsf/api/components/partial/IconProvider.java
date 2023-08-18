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

import javax.faces.component.StateHelper;

import org.omnifaces.util.State;

import de.cuioss.jsf.api.components.css.IconLibrary;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the icon in form
 * of name regarding to the cui-icon-naming contract. The implementation relies
 * on the correct use of attribute names, saying they must exactly match the
 * accessor methods.
 * </p>
 * <h2>icon</h2>
 * <p>
 * A defined icon class, defined within stylesheet, e.g. cui-icon-warning. In
 * order to prevent improper usage the matching is restricted to the prefixes:
 * "cui-icon, cui-mime-type, ui-icon-". If none of them is matched it will
 * default to "cui-icon-circle_question_mark".
 * </p>
 *
 * @author Oliver Wolff
 */
public class IconProvider {

    /** The default icon if none or an invalid one is set. */
    public static final String FALLBACK_ICON_STRING = "cui-icon cui-icon-circle_question_mark";

    /** The key for the {@link StateHelper} */
    private static final String KEY = "icon";

    private static final String LIBRARY_KEY = "library";

    private final CuiState state;

    private final State alienLibraryState;

    /**
     * @param bridge must not be null
     */
    public IconProvider(@NonNull final ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        alienLibraryState = new State(bridge.stateHelper());
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return state.get(KEY);
    }

    /**
     * @param icon the state to set
     */
    public void setIcon(final String icon) {
        state.put(KEY, icon);
    }

    /**
     * The default fail-safe method to create icon css strings that can be directly
     * consumed as css-class. Sample: If the set icon is "cui-icon-alarm" it will
     * return the css-String "cui-icon cui-icon-alarm"
     *
     * @return the css-string representing an icon. If the configured string is not
     *         valid according the rule or not set it will return
     *         {@value #FALLBACK_ICON_STRING} as fallback.
     */
    public String resolveIconCss() {
        final var configuredIcon = getIcon();
        if (!MoreStrings.isEmpty(configuredIcon) && IconLibrary.isIconUsagePossible(configuredIcon)) {
            return IconLibrary.resolveCssString(configuredIcon);
        }
        if (isExternalLibraryConfigured()) {
            return getLibrary() + ' ' + configuredIcon;
        }
        return FALLBACK_ICON_STRING;
    }

    public String getLibrary() {
        return alienLibraryState.get(LIBRARY_KEY);
    }

    public void setLibrary(final String libraryName) {
        alienLibraryState.put(LIBRARY_KEY, libraryName);
    }

    public boolean isExternalLibraryConfigured() {
        return !MoreStrings.isEmpty(getLibrary());
    }

    /**
     * @return boolean indicating whether an Icon is set / defined
     */
    public boolean isIconSet() {
        return !MoreStrings.isEmpty(getIcon());
    }
}
