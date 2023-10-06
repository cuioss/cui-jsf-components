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
package de.cuioss.jsf.bootstrap.common.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import lombok.Getter;

class ColumnProviderImplTest extends AbstractComponentTest<MockPartialComponent> {

    public static final String COL_MD_PREFIX = ColumnCssResolver.COL_PREFIX;

    public static final String COL_MD_OFFSET_PREFIX = ColumnCssResolver.COL_OFFSET_PREFIX;

    @Getter
    private MockPartialComponent underTest;

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    private final TypedGenerator<Integer> invalidNumbers = Generators.integers(13, 17);

    @BeforeEach
    void before() {
        underTest = new MockPartialComponent();
    }

    @Test
    void shouldHandleValidNumbers() {
        var valid = validNumbers.next();
        underTest.setSize(valid);
        assertEquals(valid, underTest.getSize());
        valid = validNumbers.next();
        underTest.setOffsetSize(valid);
        assertEquals(valid, underTest.getOffsetSize());
    }

    @Test
    void shouldResolveSize() {
        final var valid = validNumbers.next();
        underTest.setSize(valid);
        assertEquals(COL_MD_PREFIX + valid, underTest.resolveColumnCss().getStyleClass());
    }

    @Test
    void shouldResolveAndOffsetSize() {
        final var size = validNumbers.next();
        final var offsetSize = validNumbers.next();
        underTest.setSize(size);
        underTest.setOffsetSize(offsetSize);
        assertEquals(COL_MD_PREFIX + size + " " + COL_MD_OFFSET_PREFIX + offsetSize,
                underTest.resolveColumnCss().getStyleClass());
    }

    @Test
    void shouldFailOnResolveWithNoSize() {
        assertThrows(NullPointerException.class, () -> underTest.resolveColumnCss());
    }

    @Test
    void shouldFailOnInvalidSize() {
        underTest.setSize(invalidNumbers.next());
        assertThrows(IllegalArgumentException.class, () -> underTest.resolveColumnCss());
    }

    @Test
    void shouldFailOnInvalidOffsetSize() {
        underTest.setOffsetSize(invalidNumbers.next());
        assertThrows(NullPointerException.class, () -> underTest.resolveColumnCss());
    }

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new ColumnProvider(null));
    }
}
