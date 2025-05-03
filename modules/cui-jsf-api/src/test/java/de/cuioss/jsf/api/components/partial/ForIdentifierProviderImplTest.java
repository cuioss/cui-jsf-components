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
package de.cuioss.jsf.api.components.partial;

import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.letterStrings;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.tools.string.Joiner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ForIdentifierProviderImplTest {

    private final TypedGenerator<Integer> integers = integers(1, 5);

    private final TypedGenerator<String> ids = letterStrings(5, 10);

    private ForIdentifierProvider target;

    @Test
    final void shouldVerifyMandatoryConstructorParameter() {
        assertThrows(NullPointerException.class,
                () -> new ForIdentifierProvider(null, ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER));
    }

    @Test
    void shouldNotRetrieveTargetComponentIdAsLongNoWasDefined() {
        target = new ForIdentifierProvider(new MockPartialComponent(), "");
        assertTrue(isEmpty(target.getForIdentifier()));
        assertFalse(target.resolveFirstIdentifier().isPresent());
        assertTrue(target.resolveIdentifierAsList().isEmpty());
    }

    @Test
    void shouldNotRetrieveTargetComponentId() {
        final var expected_id = ids.next();
        target = createAnyValid();
        target.setForIdentifier(expected_id);
        assertEquals(expected_id, target.getForIdentifier());
        assertTrue(target.resolveFirstIdentifier().isPresent());
        assertEquals(expected_id, target.resolveFirstIdentifier().get());
        assertTrue(target.resolveIdentifierAsList().contains(expected_id));
    }

    @Test
    void shouldSupportMultipleTargetComponentIds() {
        final var first_id = ids.next();
        final var separatedIds = anyIds();
        final List<String> allIds = new ArrayList<>();
        allIds.add(first_id);
        allIds.addAll(Arrays.asList(separatedIds));
        final var expected_ids = Joiner.on(" ").join(allIds);
        target = createAnyValid();
        target.setForIdentifier(expected_ids);
        assertEquals(expected_ids, target.getForIdentifier());
        assertTrue(target.resolveFirstIdentifier().isPresent());
        assertEquals(first_id, target.resolveFirstIdentifier().get());
        assertTrue(target.resolveIdentifierAsList().contains(first_id));
        assertTrue(target.resolveIdentifierAsList().containsAll(mutableList(separatedIds)));
    }

    private static ForIdentifierProvider createAnyValid() {
        return new ForIdentifierProvider(new MockPartialComponent(), ForIdentifierProvider.DEFAULT_FOR_IDENTIFIER);
    }

    private String[] anyIds() {
        final var maxCount = integers.next();
        final List<String> tempIds = new ArrayList<>(maxCount);
        for (var i = 0; i < maxCount; i++) {
            tempIds.add(ids.next());
        }
        return tempIds.toArray(new String[0]);
    }
}
