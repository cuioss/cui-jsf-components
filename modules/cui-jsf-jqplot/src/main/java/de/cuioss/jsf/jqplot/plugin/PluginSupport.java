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
package de.cuioss.jsf.jqplot.plugin;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

/**
 * Identify entry which needs / expected special plug-in (additional javaScript)
 *
 * @author Eugen Fischer
 */
@ToString(of = "pluginList")
@EqualsAndHashCode(of = "pluginList")
public class PluginSupport implements Serializable, IPluginConsumer {

    /** serial Version UID */
    @Serial
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
