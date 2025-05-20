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
package de.cuioss.jsf.api.ui.model;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.tools.string.MoreStrings;
import org.junit.jupiter.api.Test;

@PropertyConfig(name = "defaultValue", propertyClass = String.class)
@VerifyConstructor(of = "defaultValue", writeOnly = "defaultValue")
class TracedValueTest extends ValueObjectTest<StringTraceValue> {

    @Test
    void shouldRecognizeIfValueWasChanged() {
        final var defaultValue = MoreStrings.emptyToNull(letterStrings().next());
        final var newValue = "new value";
        final ITracedValue<String> tracedStringValue = anyTracedStringValue(defaultValue);
        verifyDefaultValueActive(tracedStringValue, defaultValue);
        tracedStringValue.setValue(defaultValue);
        verifyDefaultValueActive(tracedStringValue, defaultValue);
        tracedStringValue.setValue(newValue);
        verifyNewValueIsActive(tracedStringValue, newValue);
    }

    @Test
    void shouldProvideRollback() {
        final var defaultValue = letterStrings().next();
        final var newValue = "some new value";
        final ITracedValue<String> tracedStringValue = anyTracedStringValue(defaultValue);
        verifyDefaultValueActive(tracedStringValue, defaultValue);
        verifyDefaultValueActive(tracedStringValue.rollBackToDefault(), defaultValue);
        tracedStringValue.setValue(newValue);
        verifyNewValueIsActive(tracedStringValue, newValue);
        verifyDefaultValueActive(tracedStringValue.rollBackToDefault(), defaultValue);
    }

    private static void verifyDefaultValueActive(final ITracedValue<String> tracedStringValue, final String expected) {
        assertFalse(tracedStringValue.isValueChanged(), "Expected deafult value is active. ");
        verifyValue(tracedStringValue, expected);
    }

    private static void verifyNewValueIsActive(final ITracedValue<String> tracedStringValue, final String expected) {
        assertTrue(tracedStringValue.isValueChanged(), "Expected new value is active. ");
        verifyValue(tracedStringValue, expected);
    }

    private static void verifyValue(final ITracedValue<String> tracedStringValue, final String expected) {
        assertEquals(expected, tracedStringValue.getValue(), "Value is wrong. ");
    }

    private static TracedValue<String> anyTracedStringValue(final String defaultValue) {
        return new TracedValue<>(defaultValue);
    }
}
