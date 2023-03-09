package com.icw.ehf.cui.mock.application;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.List;

import com.icw.ehf.cui.core.api.application.theme.ThemeConfiguration;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeConfigurationImpl;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeManager;

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
 * {@link ThemeConfiguration#getAvailableThemes()}
 * It can be easily configured as bean by using {@link JsfTestConfiguration}
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
    public static final List<String> AVAILABLE_THEMES = immutableList(DEFAULT_THEME,
            HIGH_CONTRAST);

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
    private String cssLibrary = "com.icw.portal.css";

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
