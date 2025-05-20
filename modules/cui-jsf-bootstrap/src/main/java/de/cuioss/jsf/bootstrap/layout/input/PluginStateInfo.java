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
package de.cuioss.jsf.bootstrap.layout.input;

import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the possible state indicators that plugins can use to communicate their status
 * to container components.
 * <p>
 * These states are used by {@link ContainerPlugin} implementations to indicate
 * conditions like validation warnings or errors, which the parent container
 * can then use to apply appropriate styling.
 * </p>
 * <p>
 * Each state is associated with corresponding Bootstrap CSS classes that will be
 * applied to the container when a plugin reports that state.
 * </p>
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
public enum PluginStateInfo {

    /** Defines undefined state. */
    NO_STATE_INFO(null),

    /** The plugin is in warning state. */
    WARNING(CssBootstrap.HAS_WARNING),

    /** The plugin is in error state. */
    ERROR(CssBootstrap.HAS_ERROR);

    @Getter
    private final CssBootstrap classBuilder;

}
