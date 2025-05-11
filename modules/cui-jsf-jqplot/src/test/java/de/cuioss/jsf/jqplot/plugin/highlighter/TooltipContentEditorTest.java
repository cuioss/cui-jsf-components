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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for TooltipContentEditor class")
class TooltipContentEditorTest implements ShouldHandleObjectContracts<TooltipContentEditor> {

    @Override
    public TooltipContentEditor getUnderTest() {
        return new TooltipContentEditor();
    }

    @Nested
    @DisplayName("JavaScript function generation tests")
    class JavaScriptFunctionGenerationTests {

        @Test
        @DisplayName("Should generate correct JavaScript function code")
        void shouldGenerateCorrectJavaScriptFunctionCode() {
            // Arrange
            final var target = new TooltipContentEditor();

            // Act & Assert - default function content
            assertEquals("function tooltipContentEditor(str,seriesIndex,pointIndex,plot){return \"\";};",
                    target.getHookFunctionCode(),
                    "Should generate correct default function code");

            // Act - set custom function content
            target.setFunctionContent("{var bla = 10;};");

            // Assert - custom function content
            assertEquals("function tooltipContentEditor(str,seriesIndex,pointIndex,plot){var bla = 10;};",
                    target.getHookFunctionCode(),
                    "Should generate correct function code with custom content");
        }
    }
}
