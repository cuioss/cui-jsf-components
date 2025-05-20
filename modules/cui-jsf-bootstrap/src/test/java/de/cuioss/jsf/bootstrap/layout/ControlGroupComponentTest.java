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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"offsetSize"})
@DisplayName("Tests for ControlGroupComponent")
class ControlGroupComponentTest extends AbstractUiComponentTest<ControlGroupComponent> {

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    @Nested
    @DisplayName("Tests for component properties")
    class PropertyTests {

        @Test
        @DisplayName("Should provide default size of 8")
        void shouldProvideDefaultSize() {
            // Arrange
            final var underTest = new ControlGroupComponent();

            // Act & Assert
            assertEquals(Integer.valueOf(8), underTest.getSize(),
                    "Default size should be 8");
        }
    }

    @Nested
    @DisplayName("Tests for CSS resolution")
    class CssResolutionTests {

        @Test
        @DisplayName("Should resolve column CSS with size and offset")
        void shouldResolveColumnCss() {
            // Arrange
            final var underTest = new ControlGroupComponent();
            final var size = validNumbers.next();
            final var offsetSize = validNumbers.next();

            // Act
            underTest.setSize(size);
            underTest.setOffsetSize(offsetSize);

            // Assert
            assertNotNull(underTest.resolveColumnCss(),
                    "Should resolve column CSS for size " + size + " and offset " + offsetSize);
        }
    }
}
