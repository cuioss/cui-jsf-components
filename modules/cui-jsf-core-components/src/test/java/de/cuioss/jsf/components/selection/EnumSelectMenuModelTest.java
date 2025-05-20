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
package de.cuioss.jsf.components.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.tools.collect.CollectionBuilder;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Tests for EnumSelectMenuModel")
class EnumSelectMenuModelTest extends AbstractConverterTest<EnumSelectMenuModel<TestEnumeration>, TestEnumeration> {

    @Getter
    private EnumSelectMenuModel<TestEnumeration> converter;

    @Getter(AccessLevel.PROTECTED)
    private TestItems<TestEnumeration> testItems;

    @Override
    @BeforeEach
    @DisplayName("Initialize converter for testing")
    protected void initConverter() {
        // Arrange
        var builder = new CollectionBuilder<SelectItem>();
        for (TestEnumeration enumeration : TestEnumeration.values()) {
            builder.add(new SelectItem(enumeration));
        }
        List<SelectItem> values = builder.toMutableList();

        // Act
        converter = new EnumSelectMenuModel<>(values, TestEnumeration.class);
        testItems = new TestItems<>();
        populate(testItems);
    }

    @Override
    @DisplayName("Populate test items for enum conversion")
    public void populate(final TestItems<TestEnumeration> testItems) {
        // Add valid enum values for roundtrip testing
        testItems.addRoundtripValues(
                TestEnumeration.ONE.toString(),
                TestEnumeration.TWO.toString(),
                TestEnumeration.THREE.toString())
                // Add invalid string to test error handling
                .addInvalidString("notThere");
    }

    @Test
    @DisplayName("Should properly initialize with enum values")
    void shouldInitializeWithEnumValues() {
        // Arrange & Act already done in initConverter

        // Assert
        assertNotNull(converter);
        assertEquals(3, converter.getSelectableValues().size());
    }

    @Test
    @DisplayName("Should convert enum to string and back")
    void shouldConvertEnumToStringAndBack(FacesContext facesContext) {
        // Arrange
        var enumValue = TestEnumeration.TWO;
        var component = new UIInput();

        // Act
        var asString = converter.getAsString(facesContext, component, enumValue);
        var asObject = converter.getAsObject(facesContext, component, asString);

        // Assert
        assertEquals(enumValue.toString(), asString);
        assertEquals(enumValue, asObject);
    }

}

enum TestEnumeration {
    ONE, TWO, THREE
}
