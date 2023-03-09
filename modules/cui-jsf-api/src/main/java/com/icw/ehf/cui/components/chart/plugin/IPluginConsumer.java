package com.icw.ehf.cui.components.chart.plugin;

import java.util.List;

/**
 * Define that a class may need plugin
 *
 * @author i000576
 */
public interface IPluginConsumer {

    /**
     * @return the used plugins
     */
    List<String> usedPlugins();

}
