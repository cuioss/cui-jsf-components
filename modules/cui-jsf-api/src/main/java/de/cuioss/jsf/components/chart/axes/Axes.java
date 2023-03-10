package de.cuioss.jsf.components.chart.axes;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.components.chart.plugin.IPluginConsumer;
import de.cuioss.jsf.components.chart.plugin.PluginSupport;
import de.cuioss.jsf.components.js.support.JsObject;
import lombok.ToString;

/**
 * @author i000576
 */
@ToString(of = { "axisList", "plugins" }, callSuper = false)
public class Axes extends JsObject implements IPluginConsumer {

    /** serial Version UID */
    private static final long serialVersionUID = 8583863109420728880L;

    private static final String JS_OBJECT_NAME = "axes";

    private final PluginSupport plugins = new PluginSupport();

    /**
     * Constructor.
     */
    public Axes() {
        super(JS_OBJECT_NAME);
    }

    private final List<Axis> axisList = new ArrayList<>();

    /**
     * @param axis {@linkplain Axis}
     */
    public void addInNotNull(final Axis axis) {
        if (null != axis) {
            axisList.add(checkAxis(axis));
            plugins.add(axis);
        }
    }

    /**
     * @param axis {@linkplain Axis} value to verify
     * @return {@linkplain Axis} if not already existing in the list
     */
    private Axis checkAxis(final Axis axis) {
        for (final Axis element : axisList) {
            if (element.getType().equals(axis.getType())) {
                throw new IllegalArgumentException(String.format(
                        "Attention you try to add allreay existing axis [%s]", axis));
            }
        }
        return axis;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        for (final Axis element : axisList) {
            this.addProperty(element);
        }
        return this.createAsJSON();
    }

    @Override
    public List<String> usedPlugins() {
        return plugins.usedPlugins();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Axes)) {
            return false;
        }
        var other = (Axes) obj;

        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        final var prime = 31;
        var result = axisList.hashCode();
        result = prime * result + plugins.hashCode();
        return result;
    }
}
