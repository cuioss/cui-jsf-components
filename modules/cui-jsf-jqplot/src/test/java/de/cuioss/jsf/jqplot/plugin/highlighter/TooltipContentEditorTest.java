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
package de.cuioss.jsf.jqplot.plugin.highlighter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import org.junit.jupiter.api.Test;

class TooltipContentEditorTest implements ShouldHandleObjectContracts<TooltipContentEditor> {

    @Test
    void shouldProvideExtensionPoint() {
        final var target = new TooltipContentEditor();
        assertEquals("function tooltipContentEditor(str,seriesIndex,pointIndex,plot){return \"\";};",
                target.getHookFunctionCode());
        target.setFunctionContent("{var bla = 10;};");
        assertEquals("function tooltipContentEditor(str,seriesIndex,pointIndex,plot){var bla = 10;};",
                target.getHookFunctionCode());
    }

    @Override
    public TooltipContentEditor getUnderTest() {
        return new TooltipContentEditor();
    }
}
