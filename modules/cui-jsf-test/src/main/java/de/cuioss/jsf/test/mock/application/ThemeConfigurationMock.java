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
package de.cuioss.jsf.test.mock.application;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.List;

import de.cuioss.jsf.api.application.theme.ThemeConfiguration;
import de.cuioss.jsf.api.application.theme.impl.ThemeConfigurationImpl;
import de.cuioss.jsf.api.application.theme.impl.ThemeManager;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Mock Implementation of {@link ThemeConfiguration} with
 * {@value #DEFAULT_THEME} as {@link ThemeConfiguration#getDefaultTheme()} and
 * {@link ThemeConfigurationMock#AVAILABLE_THEMES} as
 * {@link ThemeConfiguration#getAvailableThemes()} It can be easily configured
 * as bean by using {@link JsfTestConfiguration}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ThemeConfigurationMock implements ThemeConfiguration, Serializable, BeanConfigurator {

    /** "High-Contrast" */
    public static final String HIGH_CONTRAST = "High-Contrast";

    /** "Default" */
    public static final String DEFAULT_THEME = "Default";

    /** "Default", "High-Contrast" */
    public static final List<String> AVAILABLE_THEMES = immutableList(DEFAULT_THEME, HIGH_CONTRAST);

    private static final long serialVersionUID = 5242120351578770611L;

    @Getter
    @Setter
    private List<String> availableThemes = AVAILABLE_THEMES;

    @Getter
    @Setter
    private String defaultTheme = DEFAULT_THEME;

    @Getter
    @Setter
    private String cssName = "application.css";

    @Getter
    @Setter
    private String cssLibrary = "de.cuioss.portal.css";

    @Getter
    @Setter
    private boolean useMinVersion = false;

    @Override
    public String getCssForThemeName(final String themeName) {
        return new ThemeManager(this).getCssForThemeName(themeName);
    }

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, ThemeConfigurationImpl.BEAN_NAME);
    }

}
