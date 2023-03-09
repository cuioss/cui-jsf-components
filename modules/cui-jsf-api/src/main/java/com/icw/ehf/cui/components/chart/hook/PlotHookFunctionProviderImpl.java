package com.icw.ehf.cui.components.chart.hook;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base implementation of {@linkplain PlotHookFunctionProvider}
 *
 * @author i000576
 */
@ToString(of = "hookIdentifier")
@EqualsAndHashCode(of = "hookIdentifier")
public class PlotHookFunctionProviderImpl implements PlotHookFunctionProvider {

    private static final long serialVersionUID = 8333990834669994111L;

    private final String hookIdentifier;

    private final String hookFunctionContent;

    /**
     * Create a PlotHookFunctionProvider.</br>
     * This implementation do not care about syntax of functionContent. Expected is valid syntax.
     *
     * @param identifier must not be {@code null} or empty
     * @param functionContent must not be {@code null} or empty
     */
    public PlotHookFunctionProviderImpl(final String identifier, final String functionContent) {
        hookIdentifier = requireNotEmpty(identifier, "identifier");
        hookFunctionContent = requireNotEmpty(functionContent, "functionContent");
    }

    @Override
    public String getIdentifier() {
        return hookIdentifier;
    }

    @Override
    public String getHookFunctionCode() {
        return hookFunctionContent;
    }

}
