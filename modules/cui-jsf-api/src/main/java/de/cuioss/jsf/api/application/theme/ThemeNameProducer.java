package de.cuioss.jsf.api.application.theme;

/**
 * Producer for a concrete theme-name, separating the concerns of accessing and
 * storing a concrete theme.
 *
 * @author Oliver Wolff
 */
public interface ThemeNameProducer {

    /**
     * Access the user persisted theme
     *
     * @return Either the persisted theme or the given default theme. The default
     *         theme must be provided by the implementation.
     */
    String getTheme();

}
