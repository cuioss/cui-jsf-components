/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.renderer;

import java.io.Serial;
import java.util.List;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.Data;

/**
 * @author Oliver Wolff
 *
 */
@Data
public class Renderer implements JsValue, IPluginConsumer {

    /** serial Version UID */
    @Serial
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
