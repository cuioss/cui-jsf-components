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
package de.cuioss.jsf.dev.metadata.composite.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for AttributePropertyWrapper")
class AttributePropertyWrapperTest extends AbstractPropertyWrapperTest<AttributePropertyWrapper> {

    @Override
    public AttributePropertyWrapper getUnderTest() {
        return new AttributePropertyWrapper(featureDescriptorGenerator.next());
    }

    @Nested
    @DisplayName("Property Type Tests")
    class PropertyTypeTests {

        @Test
        @DisplayName("Should return ATTRIBUTE as property type")
        void shouldBeProperType() {
            // Arrange
            var wrapper = getUnderTest();

            // Act
            var propertyType = wrapper.getPropertyType();

            // Assert
            assertEquals(PropertyType.ATTRIBUTE, propertyType);
        }
    }

    @Nested
    @DisplayName("Metadata Tests")
    class MetadataTests {

        @Test
        @DisplayName("Should return non-null advanced metadata")
        void shouldReturnDescriptors() {
            // Arrange
            var wrapper = getUnderTest();

            // Act
            var metadata = wrapper.getAdvancedMetaData();

            // Assert
            assertNotNull(metadata);
        }
    }
}
