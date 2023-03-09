package com.icw.ehf.cui.components.chart.hook;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashSet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Hook functions provider, manage list of IPlotHookFunctionProvider able to create string
 * representation of all collected.
 *
 * @author i000576
 */
@ToString(of = "hooks")
@EqualsAndHashCode(of = "hooks")
public class HookFunctionsManager implements Serializable {

    private static final long serialVersionUID = 8348653918471855582L;

    private final HashSet<PlotHookFunctionProvider> hooks = new HashSet<>();

    /**
     * Add hook function to list
     *
     * @param hookFunction {@linkplain PlotHookFunctionProvider} should not be {@code null}
     */
    public void addHookFunction(final PlotHookFunctionProvider hookFunction) {

        final var function =
            requireNonNull(hookFunction, "hookFunction");

        if (hooks.contains(function)) {
            throw new IllegalArgumentException(
                    "Hook function [" + function.getIdentifier() + "] already in use.");
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
