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
package de.cuioss.jsf.api.common.view;

import static de.cuioss.test.generator.Generators.strings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBuilder;
import de.cuioss.tools.net.ParameterFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

// Class is documented by @DisplayName
@DisplayName("Tests for ViewDescriptorImpl")
@VerifyBuilder(methodPrefix = "with", exclude = {"viewDefined", "shortIdentifier"})
class ViewDescriptorImplTest extends ValueObjectTest<ViewDescriptorImpl> {

    private final TypedGenerator<String> stringGenerator = strings(1, 55);

    @Nested
    @DisplayName("Copy Constructor Tests")
    class CopyConstructorTests {

        @Test
        @DisplayName("Should create equal copy using copy constructor without filter")
        void shouldCreateEqualCopyWithoutFilter() {
            // Arrange
            final var original = ViewDescriptorImpl.builder()
                    .withLogicalViewId(stringGenerator.next())
                    .withUrlParameter(Collections.emptyList())
                    .withViewId(stringGenerator.next())
                    .build();

            // Act
            final var copy = new ViewDescriptorImpl(original, null);

            // Assert
            assertEquals(original, copy, "Copy should be equal to original");
        }

        @Test
        @DisplayName("Should create equal copy using copy constructor with filter")
        void shouldCreateEqualCopyWithFilter() {
            // Arrange
            final var original = ViewDescriptorImpl.builder()
                    .withLogicalViewId(stringGenerator.next())
                    .withUrlParameter(Collections.emptyList())
                    .withViewId(stringGenerator.next())
                    .build();
            final var filter = new ParameterFilter(Collections.emptyList(), true);

            // Act
            final var copy = new ViewDescriptorImpl(original, filter);

            // Assert
            assertEquals(original, copy, "Copy should be equal to original even with filter");
        }
    }

    @Nested
    @DisplayName("View Defined Tests")
    class ViewDefinedTests {

        @Test
        @DisplayName("Should correctly determine if view is defined")
        void shouldCorrectlyDetermineIfViewIsDefined() {
            // Arrange
            final var emptyDescriptor = ViewDescriptorImpl.builder().build();

            // Act & Assert
            assertFalse(emptyDescriptor.isViewDefined(), "Empty descriptor should not have view defined");

            // Arrange
            final var copy = new ViewDescriptorImpl(emptyDescriptor, null);

            // Act & Assert
            assertFalse(copy.isViewDefined(), "Copy of empty descriptor should not have view defined");
        }
    }
}
