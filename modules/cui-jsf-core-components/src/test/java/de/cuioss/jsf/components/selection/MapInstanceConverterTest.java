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
package de.cuioss.jsf.components.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Tests for MapInstanceConverter")
class MapInstanceConverterTest extends AbstractConverterTest<MapInstanceConverter<String, String>, String> {

    @Override
    @DisplayName("Configure converter with test instance map")
    public void configure(final MapInstanceConverter<String, String> toBeConfigured) {
        // Arrange
        Map<String, String> instanceMap = new HashMap<>();
        instanceMap.put("ONE", "eins");
        instanceMap.put("TWO", "zwei");

        // Act
        toBeConfigured.setInstanceMap(instanceMap);
    }

    @Override
    @DisplayName("Populate test items for map instance conversion")
    public void populate(final TestItems<String> testItems) {
        // Add valid strings for testing
        testItems.addValidString("ONE");
        testItems.addValidString("TWO");
    }

    @Test
    @DisplayName("Should convert keys to values and back")
    void shouldConvertKeysToValuesAndBack(FacesContext facesContext) {
        // Arrange
        var converter = new MapInstanceConverter<String, String>();
        Map<String, String> instanceMap = new HashMap<>();
        instanceMap.put("ONE", "eins");
        instanceMap.put("TWO", "zwei");
        converter.setInstanceMap(instanceMap);
        var component = getComponent();

        // Act
        var result = converter.getAsObject(facesContext, component, "ONE");

        // Assert
        assertEquals("eins", result, "Should convert key 'ONE' to value 'eins'");
        assertEquals("ONE", converter.getAsString(facesContext, component, "eins"),
                "Should convert value 'eins' back to key 'ONE'");
    }

}
