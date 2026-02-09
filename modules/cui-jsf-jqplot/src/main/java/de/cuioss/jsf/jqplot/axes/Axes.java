/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.axes;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugen Fischer
 */
@ToString(of = {"axisList", "plugins"}, callSuper = false)
public class Axes extends JsObject implements IPluginConsumer {

    /** serial Version UID */
    @Serial
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
                throw new IllegalArgumentException(
                        "Attention you try to add allreay existing axis [%s]".formatted(axis));
            }
        }
        return axis;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        for (final Axis element : axisList) {
            this.addProperty(element);
        }
        return createAsJSON();
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
        if (!(obj instanceof Axes other)) {
            return false;
        }
        return hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        final var prime = 31;
        var result = axisList.hashCode();
        return prime * result + plugins.hashCode();
    }
}
