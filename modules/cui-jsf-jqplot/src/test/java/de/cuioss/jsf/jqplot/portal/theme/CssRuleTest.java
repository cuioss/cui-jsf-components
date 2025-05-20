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
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@PropertyReflectionConfig(of = "selector")
@DisplayName("Tests for CssRule class")
class CssRuleTest extends ValueObjectTest<CssRule> {

    private final TypedGenerator<String> validRules = fixedValues("name{prop1:value1}", "name{prop2:value2}",
            "name{prop3:value3}");

    private CssRule target;

    @Override
    protected CssRule anyValueObject() {
        return CssRule.createBy(validRules.next());
    }

    @Nested
    @DisplayName("Validation tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw exception when rule content is null")
        void shouldThrowExceptionWhenRuleContentIsNull() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> CssRule.createBy(null),
                    "Should reject null rule content");
        }

        @Test
        @DisplayName("Should throw exception when rule content is empty")
        void shouldThrowExceptionWhenRuleContentIsEmpty() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> target = CssRule.createBy(""),
                    "Should reject empty rule content");
        }
    }

    @Nested
    @DisplayName("Property extraction tests")
    class PropertyExtractionTests {

        @Test
        @DisplayName("Should extract selector and properties from rule content")
        void shouldExtractSelectorAndPropertiesFromRuleContent() {
            // Arrange
            String cssRule = "selector-name{-property-Name:propertyValue}";

            // Act
            target = CssRule.createBy(cssRule);

            // Assert
            assertEquals("selector-name", target.getSelector(),
                    "Should extract correct selector name");
            assertTrue(target.getProperties().contains("-property-name"),
                    "Should contain normalized property name");
            assertEquals("propertyValue", target.getPropertyValue("-property-name"),
                    "Should extract correct property value");
        }
    }
}
