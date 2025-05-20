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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.RangeCounter;
import de.cuioss.uimodel.model.impl.BaseRangeCounter;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for RangeCounterConverter")
class RangeCounterConverterTest extends AbstractConverterTest<RangeCounterConverter, RangeCounter> {

    @Override
    @DisplayName("Configure test items for RangeCounter conversion")
    public void populate(final TestItems<RangeCounter> testItems) {
        // Test complete RangeCounter (both count and totalCount)
        testItems.addValidObjectWithStringResult(new BaseRangeCounter(1, 2), "1/2");

        // Test RangeCounter with only count
        testItems.addValidObjectWithStringResult(new BaseRangeCounter(1, null), "1");

        // Test RangeCounter with only totalCount
        testItems.addValidObjectWithStringResult(new BaseRangeCounter(null, 5), "5");

        // Test null RangeCounter
        testItems.addValidObjectWithStringResult(null, "");
    }

    @Test
    @DisplayName("Should convert complete RangeCounter to string")
    void shouldConvertCompleteRangeCounter(FacesContext facesContext) {
        // Arrange
        var converter = new RangeCounterConverter();
        var component = getComponent();
        var rangeCounter = new BaseRangeCounter(3, 10);

        // Act
        var result = converter.getAsString(facesContext, component, rangeCounter);

        // Assert
        assertEquals("3/10", result, "Complete RangeCounter should be formatted as 'count/totalCount'");
    }

    @Test
    @DisplayName("Should convert RangeCounter with only count to string")
    void shouldConvertRangeCounterWithOnlyCount(FacesContext facesContext) {
        // Arrange
        var converter = new RangeCounterConverter();
        var component = getComponent();
        var rangeCounter = new BaseRangeCounter(5, null);

        // Act
        var result = converter.getAsString(facesContext, component, rangeCounter);

        // Assert
        assertEquals("5", result, "RangeCounter with only count should be formatted as 'count'");
    }

    @Test
    @DisplayName("Should convert RangeCounter with only totalCount to string")
    void shouldConvertRangeCounterWithOnlyTotalCount(FacesContext facesContext) {
        // Arrange
        var converter = new RangeCounterConverter();
        var component = getComponent();
        var rangeCounter = new BaseRangeCounter(null, 7);

        // Act
        var result = converter.getAsString(facesContext, component, rangeCounter);

        // Assert
        assertEquals("7", result, "RangeCounter with only totalCount should be formatted as 'totalCount'");
    }

    @Test
    @DisplayName("Should handle null value")
    void shouldHandleNullValue(FacesContext facesContext) {
        // Arrange
        var converter = new RangeCounterConverter();
        var component = getComponent();

        // Act
        var result = converter.getAsString(facesContext, component, null);

        // Assert
        assertEquals("", result, "Should return empty string for null value");
    }
}
