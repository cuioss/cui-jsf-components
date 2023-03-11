package de.cuioss.jsf.components.chart.plugin;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Identify entry which needs / expected special plug-in (additional javaScript)
 *
 * @author Eugen Fischer
 */
@ToString(of = "pluginList")
@EqualsAndHashCode(of = "pluginList")
public class PluginSupport implements Serializable, IPluginConsumer {

    /** serial Version UID */
    private static final long serialVersionUID = 4769562352878456691L;

    private final List<String> pluginList = new ArrayList<>();

    /**
     * Add value to plugin list if not null or already available
     *
     * @param value to be added
     * @return {@linkplain PluginSupport} fluent api style
     */
    public PluginSupport add(final String value) {
        if (null != value && !pluginList.contains(value)) {
            pluginList.add(value);
        }
        return this;
    }

    /**
     * Add consumer if not {@code null}
     *
     * @param consumer {@linkplain IPluginConsumer}
     * @return {@linkplain PluginSupport} fluent api style
     */
    public PluginSupport add(final IPluginConsumer consumer) {
        if (null != consumer) {
            for (final String plugin : consumer.usedPlugins()) {
                add(plugin);
            }
        }
        return this;
    }

    @Override
    public List<String> usedPlugins() {
        return immutableList(pluginList);
    }

}
