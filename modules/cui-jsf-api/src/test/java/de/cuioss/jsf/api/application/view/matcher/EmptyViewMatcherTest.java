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
package de.cuioss.jsf.api.application.view.matcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.jsf.api.common.view.ViewDescriptorImpl;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@DisplayName("Tests for EmptyViewMatcher")
class EmptyViewMatcherTest {

    final TypedGenerator<String> viewGenerator = Generators.letterStrings(2, 10);

    @Nested
    @DisplayName("Tests for match method")
    class MatchTests {

        @Test
        @DisplayName("Should match based on configured behavior")
        void shouldMatchBasedOnConfiguration() {
            // Arrange
            final ViewDescriptor descriptor = ViewDescriptorImpl.builder()
                    .withViewId(viewGenerator.next())
                    .withUrlParameter(Collections.emptyList())
                    .withLogicalViewId(viewGenerator.next())
                    .build();

            // Act & Assert - with matcher configured to not match
            assertFalse(new EmptyViewMatcher(false).match(descriptor),
                    "EmptyViewMatcher(false) should not match any descriptor");

            // Act & Assert - with matcher configured to match
            assertTrue(new EmptyViewMatcher(true).match(descriptor),
                    "EmptyViewMatcher(true) should match any descriptor");
        }
    }
}
