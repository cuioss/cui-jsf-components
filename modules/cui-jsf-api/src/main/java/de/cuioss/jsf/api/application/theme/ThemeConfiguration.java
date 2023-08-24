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
package de.cuioss.jsf.api.application.theme;

import java.util.List;

/**
 * Describes the runtime configuration for theming.
 *
 * @author Oliver Wolff
 */
public interface ThemeConfiguration {

    /**
     * @return the (configured) list of available themes.
     */
    List<String> getAvailableThemes();

    /**
     * @return the (configured default theme). It must be one of
     *         {@link #getAvailableThemes()}
     */
    String getDefaultTheme();

    /**
     * @return The name of the css to be looked for. Caution: it is assumed to end
     *         with ".css". The portal/styling assumes the name to be
     *         'application.css'
     */
    String getCssName();

    /**
     * @return the name of the library where the css files are located in. The
     *         portal styling assumes 'de.cuioss.portal.css'
     */
    String getCssLibrary();

    /**
     * Actual 'business' method for getting a concrete application.css from
     *
     * @param themeName to be looked up. If it is null, empty or not part of
     *                  {@link #getAvailableThemes()} it returns the configured
     *                  {@link #getDefaultTheme()}
     * @return the corresponding css name, e.g. application-blue.css for
     *         themeName=blue
     */
    String getCssForThemeName(String themeName);
}
