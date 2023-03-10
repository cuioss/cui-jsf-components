package de.cuioss.jsf.api.application.theme.impl;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import de.cuioss.jsf.api.application.theme.ThemeConfiguration;
import de.cuioss.jsf.api.application.theme.ThemeResourceHandler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Support class for {@link ThemeResourceHandler} utilized to configure it.
 * <ul>
 * <li>It provides a set of default Themes: "Blue", "Sunny", "Green", "High-Contrast",
 * attribute-name is "availableThemes".</li>
 * <li>The default theme is "Blue", attribute-name is "defaultTheme"</li>
 * <li>The default css name is application.css, attribute-name is "cssName"</li>
 * <li>The default library is 'com.icw.portal.css', attribute-name is "cssLibrary"</li>
 * <ul>
 * <p>
 * All this attributes can be altered by overwriting the corresponding resource within the
 * faces-config.xml declaration.
 * </p>
 * <em>Caution: </em> In order to work you need to expose this class as an application scoped bean
 * with the name {@link ThemeConfigurationImpl#BEAN_NAME}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ThemeConfigurationImpl implements ThemeConfiguration, Serializable {

    private static final long serialVersionUID = 8201161314453668030L;

    private static final List<String> DEFAULT_THEMES = immutableList("Blue", "Sunny", "Green",
            "High-Contrast");

    private ThemeManager themeManager;

    /**
     * The names of the available themes.
     */
    @Getter
    @Setter
    private List<String> availableThemes = DEFAULT_THEMES;

    /**
     * Defaults to "blue".
     */
    @Getter
    @Setter
    private String defaultTheme = "Blue";

    /**
     * Defaults to "application.css"
     */
    @Getter
    @Setter
    private String cssName = "application.css";

    /**
     * Defaults to "com.icw.portal.css"
     */
    @Getter
    @Setter
    private String cssLibrary = "com.icw.portal.css";

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "themeConfiguration";

    @Override
    public String getCssForThemeName(String themeName) {
        return themeManager.getCssForThemeName(themeName);
    }

    /**
     * Initializes the bean.
     */
    @PostConstruct
    public void initBean() {
        themeManager = new ThemeManager(this);
    }
}
