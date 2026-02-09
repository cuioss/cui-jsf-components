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
package de.cuioss.jsf.api.ui.model;

import static de.cuioss.test.generator.Generators.integers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import org.junit.jupiter.api.Test;

@VerifyBeanProperty(of = "typedValue")
@VerifyConstructor(of = "typedValue")
@VerifyConstructor(of = {"typedValue", "label"})
@PropertyConfig(name = "typedValue", propertyClass = Integer.class)
@PropertyConfig(name = "label", propertyClass = String.class)
@ObjectTestConfig(equalsAndHashCodeOf = "typedValue")
class TypedSelectItemTest extends ValueObjectTest<IntegerTypedValueItem> {

    @Test
    void shouldSetValue() {
        var item = new IntegerTypedValueItem();
        var integer = integers().next();
        assertNotEquals(integer, item.getTypedValue());
        item.setValue(integer);
        assertEquals(integer, item.getTypedValue());
    }
}
