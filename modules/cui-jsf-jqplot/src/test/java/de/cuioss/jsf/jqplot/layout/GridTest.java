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
package de.cuioss.jsf.jqplot.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Grid class")
class GridTest {

    @Nested
    @DisplayName("Default configuration tests")
    class DefaultConfigurationTests {

        @Test
        @DisplayName("Should have correct default settings")
        void shouldHaveCorrectDefaultSettings() {
            // Arrange
            Grid target = new Grid();
            final var expected = "grid: {drawGridLines:true,gridLineColor:\"#cccccc\",borderWidth:2.000}";

            // Act
            final var actual = target.asJavaScriptObjectNotation();

            // Assert
            assertEquals(expected, actual, "Default grid configuration should match expected output");
        }
    }
}
