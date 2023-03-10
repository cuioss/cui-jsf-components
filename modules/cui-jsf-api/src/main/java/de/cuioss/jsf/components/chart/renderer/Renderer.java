package de.cuioss.jsf.components.chart.renderer;

import java.util.List;

import de.cuioss.jsf.components.chart.plugin.IPluginConsumer;
import de.cuioss.jsf.components.chart.plugin.PluginSupport;
import de.cuioss.jsf.components.js.support.JsValue;
import lombok.Data;

/**
 * @author Oliver Wolff
 *
 */
@Data
public class Renderer implements JsValue, IPluginConsumer {

    /** serial Version UID */
    private static final long serialVersionUID = 3803583214943960286L;

    private final String value;

    private final PluginSupport pSupport = new PluginSupport();

    /**
     * Package private
     *
     * @param name
     * @return fluent api
     */
    protected Renderer addPlugin(final String name) {
        pSupport.add(name);
        return this;
    }

    /**
     * Package private
     *
     * @param consumer
     * @return fluent api
     */
    protected Renderer addPlugin(final IPluginConsumer consumer) {
        pSupport.add(consumer);
        return this;
    }

    @Override
    public String getValueAsString() {
        return value;
    }

    @Override
    public List<String> usedPlugins() {
        return pSupport.usedPlugins();
    }

}
