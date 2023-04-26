package de.cuioss.jsf.jqplot.plugin;

import java.util.List;

/**
 * Define that a class may need plugin
 *
 * @author Eugen Fischer
 */
public interface IPluginConsumer {

    /**
     * @return the used plugins
     */
    List<String> usedPlugins();

}
