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
package de.cuioss.jsf.jqplot.hook;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

/**
 * Base implementation of {@linkplain PlotHookFunctionProvider}
 *
 * @author Eugen Fischer
 */
@ToString(of = "hookIdentifier")
@EqualsAndHashCode(of = "hookIdentifier")
public class PlotHookFunctionProviderImpl implements PlotHookFunctionProvider {

    @Serial
    private static final long serialVersionUID = 8333990834669994111L;

    private final String hookIdentifier;

    private final String hookFunctionContent;

    /**
     * Create a PlotHookFunctionProvider.<br>
     * This implementation do not care about syntax of functionContent. Expected is
     * valid syntax.
     *
     * @param identifier      must not be {@code null} or empty
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
