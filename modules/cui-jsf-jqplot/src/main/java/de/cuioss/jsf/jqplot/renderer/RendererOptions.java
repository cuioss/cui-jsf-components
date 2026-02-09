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
package de.cuioss.jsf.jqplot.renderer;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html">MarkerRenderer</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class RendererOptions extends JsObject implements IPluginConsumer {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = 250675011421695060L;

    private final PluginSupport pSupport = new PluginSupport();

    private JsBoolean show;

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
     * weather or not to show the marker.
     *
     * @param value
     * @return fluent api style
     */
    public RendererOptions setShow(final Boolean value) {
        show = JsBoolean.create(value);
        return this;
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
