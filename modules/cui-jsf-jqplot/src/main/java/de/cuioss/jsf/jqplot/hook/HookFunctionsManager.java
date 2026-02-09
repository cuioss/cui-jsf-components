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
package de.cuioss.jsf.jqplot.hook;

import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

/**
 * Hook functions provider, manage list of IPlotHookFunctionProvider able to
 * create string representation of all collected.
 *
 * @author Eugen Fischer
 */
@ToString(of = "hooks")
@EqualsAndHashCode(of = "hooks")
public class HookFunctionsManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 8348653918471855582L;

    private final HashSet<PlotHookFunctionProvider> hooks = new HashSet<>();

    /**
     * Add hook function to list
     *
     * @param hookFunction {@linkplain PlotHookFunctionProvider} should not be
     *                     {@code null}
     */
    public void addHookFunction(final PlotHookFunctionProvider hookFunction) {

        final var function = requireNonNull(hookFunction, "hookFunction");

        if (hooks.contains(function)) {
            throw new IllegalArgumentException("Hook function [" + function.getIdentifier() + "] already in use.");
        }

        hooks.add(function);
    }

    /**
     * @return String representation of all collected hooks
     */
    public String getHooksFunctionCode() {
        if (hooks.isEmpty()) {
            return "";
        }
        final var builder = new StringBuilder();
        for (final PlotHookFunctionProvider hookFunction : hooks) {
            builder.append(hookFunction.getHookFunctionCode());
        }
        return builder.toString();
    }
}
