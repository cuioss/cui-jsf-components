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

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "offsetSize" })
class ControlGroupComponentTest extends AbstractUiComponentTest<ControlGroupComponent> {

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    @Test
    void shouldProvideDefaultSize() {
        final var underTest = new ControlGroupComponent();
        assertEquals(Integer.valueOf(8), underTest.getSize());
    }

    @Test
    void shouldResolveColumnCss() {
        final var underTest = new ControlGroupComponent();
        underTest.setSize(validNumbers.next());
        underTest.setOffsetSize(validNumbers.next());
        assertNotNull(underTest.resolveColumnCss());
    }
}
