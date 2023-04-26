package de.cuioss.jsf.jqplot;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;

@SuppressWarnings("javadoc")
public class ChartTestSupport {

    public static void assertThatNoPluginsAreUsed(final IPluginConsumer consumer) {
        assertTrue(consumer.usedPlugins().isEmpty());
    }

    public static void assertThatPluginsAreUsed(final IPluginConsumer consumer, final String... pluginNames) {
        assertTrue(consumer.usedPlugins().containsAll(mutableList(pluginNames)));
    }
}
