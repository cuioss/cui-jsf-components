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
package de.cuioss.jsf.jqplot.js.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;

class JsDoubleTest extends ValueObjectTest<JsDouble> {

    @Override
    protected JsDouble anyValueObject() {
        return new JsDouble(Generators.doubles().next());
    }

    @Test
    void shouldReturnNullOnEmptyValue() {
        final var target = new JsDouble(null);
        assertNull(target.getValueAsString());
    }

    @Test
    void shouldFormatValidValue() {
        final var val1 = new JsDouble(Double.valueOf("3.5"));
        assertEquals("3.500", val1.getValueAsString());
        final var val2 = new JsDouble(5d);
        assertEquals("5.000", val2.getValueAsString());
        final var val3 = new JsDouble(50000d);
        assertEquals("50000.000", val3.getValueAsString());
    }
}
