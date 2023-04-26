package de.cuioss.jsf.jqplot.renderer;

import java.util.List;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Eugen Fischer
 * @see <a href="http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html">MarkerRenderer</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class RendererOptions extends JsObject implements IPluginConsumer {

    /** serial Version UID */
    private static final long serialVersionUID = 250675011421695060L;

    private final PluginSupport pSupport = new PluginSupport();

    private JsBoolean show;

    /**
     * wether or not to show the marker.
     *
     * @param value
     * @return fluent api style
     */
    public RendererOptions setShow(final Boolean value) {
        show = JsBoolean.create(value);
        return this;
    }

    /**
     *
     */
    protected RendererOptions() {
        super("rendererOptions");
    }

    /**
     * @param objectName
     */
    protected RendererOptions(final String objectName) {
        super(objectName);
    }

    /**
     * Package private
     *
     * @param name
     * @return fluent api
     */
    RendererOptions addPlugin(final String name) {
        pSupport.add(name);
        return this;
    }

    /**
     * Package private
     *
     * @param consumer
     * @return fluent api
     */
    RendererOptions addPlugin(final IPluginConsumer consumer) {
        pSupport.add(consumer);
        return this;
    }

    @Override
    public List<String> usedPlugins() {
        return pSupport.usedPlugins();
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("show", show);
        addPropertiesForJsonObject();
        return createAsJSON();
    }

    /**
     * add all needed JsValue properties for JSON generator
     */
    protected abstract void addPropertiesForJsonObject();

}
