package com.icw.ehf.cui.core.api.application.theme.impl;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.icw.ehf.cui.core.api.application.theme.ThemeConfiguration;

import de.cuioss.tools.collect.MapBuilder;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Support class for translating theme-names derived from {@link ThemeConfiguration} to actual
 * css-file-names
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ThemeManager implements Serializable {

    private static final long serialVersionUID = 2368337948482686947L;

    private static final CuiLogger log = new CuiLogger(ThemeManager.class);

    private static final String CSS_SUFFIX = ".css";

    private final Map<String, String> themeNameCssMapping;

    private final List<String> availableThemes;

    private final String defaultTheme;

    /**
     * Constructor.
     *
     * @param themeConfiguration must not be null
     */
    public ThemeManager(final ThemeConfiguration themeConfiguration) {
        availableThemes = themeConfiguration.getAvailableThemes();
        defaultTheme = themeConfiguration.getDefaultTheme();
        // Set default and set implementation to immutable.
        checkCssNameContract(themeConfiguration.getCssName());
        checkThemeNameContract(themeConfiguration.getAvailableThemes(),
                themeConfiguration.getDefaultTheme());
        var cssPrefixName = Splitter.on('.').splitToList(themeConfiguration.getCssName()).iterator()
                .next();
        cssPrefixName = cssPrefixName + "-";
        var mapBuilder = new MapBuilder<String, String>();
        for (String themeName : themeConfiguration.getAvailableThemes()) {
            mapBuilder.put(
                    themeName,
                    new StringBuilder(cssPrefixName).append(themeName.toLowerCase())
                            .append(CSS_SUFFIX).toString());
        }
        themeNameCssMapping = mapBuilder.toImmutableMap();
    }

    /**
     * Actual 'business' method for getting a concrete application.css from
     *
     * @param themeName to be looked up. If it is null, empty or not part of
     *            {@link ThemeConfiguration#getAvailableThemes()} it returns the configured
     *            {@link ThemeConfiguration#getDefaultTheme()}
     * @return the corresponding css name.
     */
    public String getCssForThemeName(final String themeName) {
        return themeNameCssMapping.get(verifyThemeName(themeName));
    }

    private String verifyThemeName(final String themeName) {
        var themeLookupName = themeName;
        if (null == themeName || themeName.trim().isEmpty() || !availableThemes.contains(themeName)) {
            themeLookupName = defaultTheme;
            log.debug("No configured theme found for {}, using default theme.", themeLookupName);
        }
        return themeLookupName;
    }

    private static void checkThemeNameContract(final List<String> availableThemes, final String defaultTheme) {
        if (null == availableThemes || availableThemes.isEmpty()) {
            throw new IllegalStateException("no availableThemes provided");
        }
        requireNotEmpty(defaultTheme, "defaultTheme");
        if (!availableThemes.contains(defaultTheme)) {
            throw new IllegalStateException("Default theme: " + defaultTheme
                    + " can not be found within " + availableThemes);
        }

    }

    private static void checkCssNameContract(final String cssName) {
        requireNotEmpty(cssName, "cssName");
        if (!cssName.endsWith(CSS_SUFFIX)) {
            throw new IllegalStateException("cssName must end with .css");
        }
        if (cssName.length() < 5) {
            throw new IllegalStateException("cssName must be at least consist of 5 Characters.");
        }
        if (MoreStrings.countMatches(cssName, ".") > 1) {
            throw new IllegalStateException(
                    "Only 1 '.' as suffix separator is permitted. Caution the implementations expects .min.css variants as optional. "
                            + "The .css variant (without further '.') is mandatory.");
        }
    }
}
