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
package de.cuioss.jsf.jqplot.portal.theme;

import static de.cuioss.test.generator.Generators.fixedValues;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(of = "selector")
class CssRuleTest extends ValueObjectTest<CssRule> {

    private final TypedGenerator<String> validRules = fixedValues("name{prop1:value1}", "name{prop2:value2}",
            "name{prop3:value3}");

    private CssRule target;

    @Test
    final void shouldFailOnMissingRequiredParameter() {
        assertThrows(IllegalArgumentException.class, () -> CssRule.createBy(null));
    }

    @Test
    final void shouldFailOnMissingContent() {
        assertThrows(IllegalArgumentException.class, () -> target = CssRule.createBy(""));
    }

    @Test
    final void shouldProvideAvailablePropertyValue() {
        target = CssRule.createBy("selector-name{-property-Name:propertyValue}");
        assertEquals("selector-name", target.getSelector());
        assertTrue(target.getProperties().contains("-property-name"));
        assertEquals("propertyValue", target.getPropertyValue("-property-name"));
    }

    @Override
    protected CssRule anyValueObject() {
        return CssRule.createBy(validRules.next());
    }
}
