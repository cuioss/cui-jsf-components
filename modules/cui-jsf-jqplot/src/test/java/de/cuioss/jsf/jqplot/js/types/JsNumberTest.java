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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.Test;

class JsNumberTest extends ValueObjectTest<JsNumber<Number>> {

    @Override
    protected JsNumber<Number> anyValueObject() {
        return JsNumber.create(Generators.integers().next());
    }

    @Test
    void shouldSupportIntegerValue() {
        final JsNumber<Integer> target = JsNumber.create(10);
        assertEquals("10", target.getValueAsString());
        final JsNumber<Integer> targetEmpty = JsNumber.create(null);
        assertNull(targetEmpty.getValueAsString());
    }

    @Test
    void shouldSupportDoubleValue() {
        final JsNumber<Double> target = JsNumber.create(10.1);
        assertEquals("10.100", target.getValueAsString());
        final JsNumber<Double> targetEmpty = JsNumber.create(null);
        assertNull(targetEmpty.getValueAsString());
    }

    @Test
    void shouldReactOnUnsupportedNumberType() {
        assertThrows(UnsupportedOperationException.class, () -> JsNumber.create(Byte.MAX_VALUE));
    }
}
