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

import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"offsetSize", "size"})
class ColumnComponentTest extends AbstractUiComponentTest<ColumnComponent> {

    private static final String COL_SM_OFFSET_PREFIX = " " + ColumnCssResolver.COL_OFFSET_PREFIX;

    private static final String COL_SM_PREFIX = ColumnCssResolver.COL_PREFIX;

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    @Test
    void shouldResolveAndOffsetSize() {
        final var underTest = new ColumnComponent();
        final var size = validNumbers.next();
        final var offsetSize = validNumbers.next();
        underTest.setSize(size);
        underTest.setOffsetSize(offsetSize);
        assertEquals(COL_SM_PREFIX + size + COL_SM_OFFSET_PREFIX + offsetSize,
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldResolveAndOffsetSizeWithStyleClass() {
        final var underTest = new ColumnComponent();
        final var size = validNumbers.next();
        final var offsetSize = validNumbers.next();
        underTest.setSize(size);
        underTest.setOffsetSize(offsetSize);
        assertEquals(COL_SM_PREFIX + size + COL_SM_OFFSET_PREFIX + offsetSize,
                underTest.resolveStyleClass().getStyleClass());
    }
}
