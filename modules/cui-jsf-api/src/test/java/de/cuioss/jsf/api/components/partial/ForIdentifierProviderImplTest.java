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
package de.cuioss.jsf.api.components.partial;

import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.string.Joiner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Tests for ForIdentifierProvider implementation")
class ForIdentifierProviderImplTest {

    private final TypedGenerator<Integer> integers = integers(1, 5);

    private final TypedGenerator<String> ids = letterStrings(5, 10);

    private ForIdentifierProvider target;

    @Nested
    @DisplayName("Tests for constructor behavior")
    class ConstructorTests {

        @Test
        @DisplayName("Should throw NullPointerException when constructed with null component")
        void shouldRejectNullComponent() {
            // Act & Assert
            assertThrows(NullPointerException.class,
                    () -> new ForIdentifierProvider(null, ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER),
                    "Constructor should reject null component");
        }
    }

    @Nested
    @DisplayName("Tests for identifier resolution")
    class IdentifierResolutionTests {

        @Test
        @DisplayName("Should handle empty identifier correctly")
        void shouldHandleEmptyIdentifier() {
            // Arrange
            target = new ForIdentifierProvider(new MockPartialComponent(), "");

            // Act & Assert
            assertTrue(isEmpty(target.getForIdentifier()),
                    "ForIdentifier should be empty when initialized with empty string");
            assertFalse(target.resolveFirstIdentifier().isPresent(),
                    "First identifier should not be present for empty string");
            assertTrue(target.resolveIdentifierAsList().isEmpty(),
                    "Identifier list should be empty for empty string");
        }

        @Test
        @DisplayName("Should resolve single identifier correctly")
        void shouldResolveSingleIdentifier() {
            // Arrange
            final var expected_id = ids.next();
            target = createAnyValid();

            // Act
            target.setForIdentifier(expected_id);

            // Assert
            assertEquals(expected_id, target.getForIdentifier(),
                    "ForIdentifier should match the set value");
            assertTrue(target.resolveFirstIdentifier().isPresent(),
                    "First identifier should be present");
            assertEquals(expected_id, target.resolveFirstIdentifier().get(),
                    "First identifier should match the set value");
            assertTrue(target.resolveIdentifierAsList().contains(expected_id),
                    "Identifier list should contain the set value");
        }
    }

    @Nested
    @DisplayName("Tests for multiple identifier handling")
    class MultipleIdentifierTests {

        @Test
        @DisplayName("Should handle multiple space-separated identifiers correctly")
        void shouldHandleMultipleIdentifiers() {
            // Arrange
            final var first_id = ids.next();
            final var separatedIds = anyIds();
            final List<String> allIds = new ArrayList<>();
            allIds.add(first_id);
            allIds.addAll(Arrays.asList(separatedIds));
            final var expected_ids = Joiner.on(" ").join(allIds);
            target = createAnyValid();

            // Act
            target.setForIdentifier(expected_ids);

            // Assert
            assertEquals(expected_ids, target.getForIdentifier(),
                    "ForIdentifier should match the set value with multiple IDs");
            assertTrue(target.resolveFirstIdentifier().isPresent(),
                    "First identifier should be present");
            assertEquals(first_id, target.resolveFirstIdentifier().get(),
                    "First identifier should match the first ID in the space-separated list");
            assertTrue(target.resolveIdentifierAsList().contains(first_id),
                    "Identifier list should contain the first ID");
            assertTrue(target.resolveIdentifierAsList().containsAll(mutableList(separatedIds)),
                    "Identifier list should contain all the additional IDs");
        }
    }

    /**
     * Creates a ForIdentifierProvider with default settings
     * @return a new ForIdentifierProvider instance
     */
    private static ForIdentifierProvider createAnyValid() {
        return new ForIdentifierProvider(new MockPartialComponent(), ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER);
    }

    /**
     * Generates an array of random IDs
     * @return an array of random string IDs
     */
    private String[] anyIds() {
        final var maxCount = integers.next();
        final List<String> tempIds = new ArrayList<>(maxCount);
        for (var i = 0; i < maxCount; i++) {
            tempIds.add(ids.next());
        }
        return tempIds.toArray(new String[0]);
    }
}
