package de.cuioss.jsf.bootstrap.layout.input;

import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines a number of states for the plugins
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor
public enum PluginStateInfo {

    /** Defines undefined state. */
    NO_STATE_INFO(null),

    /** The plugin is in warning state. */
    WARNING(CssBootstrap.HAS_WARNING),

    /** The plugin is in error state. */
    ERROR(CssBootstrap.HAS_ERROR);

    @Getter
    private final CssBootstrap classBuilder;

}
