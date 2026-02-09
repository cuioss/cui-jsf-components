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
package de.cuioss.jsf.jqplot;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;

public class ChartTestSupport {

    public static void assertThatNoPluginsAreUsed(final IPluginConsumer consumer) {
        assertTrue(consumer.usedPlugins().isEmpty());
    }

    public static void assertThatPluginsAreUsed(final IPluginConsumer consumer, final String... pluginNames) {
        assertTrue(consumer.usedPlugins().containsAll(mutableList(pluginNames)));
    }
}
