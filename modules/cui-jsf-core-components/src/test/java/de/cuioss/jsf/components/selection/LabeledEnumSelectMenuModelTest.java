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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.valueobjects.util.IdentityResourceBundle;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

@DisplayName("Tests for LabeledEnumSelectMenuModel")
class LabeledEnumSelectMenuModelTest extends AbstractConverterTest<LabeledEnumSelectMenuModel<TestEnum>, TestEnum> {

    @Getter
    private LabeledEnumSelectMenuModel<TestEnum> converter;

    @Getter(AccessLevel.PROTECTED)
    private TestItems<TestEnum> testItems;

    @Override
    @BeforeEach
    @DisplayName("Initialize converter for testing")
    protected void initConverter() {
        // Arrange & Act
        converter = new LabeledEnumSelectMenuModel<>(TestEnum.class, new IdentityResourceBundle());
        testItems = new TestItems<>();
        populate(testItems);
    }

    @Nested
    @DisplayName("Construction tests")
    class ConstructionTests {

        @Test
        @DisplayName("Should construct with correct selectable values")
        void shouldConstructCorrectly() {
            // Arrange & Act already done in initConverter

            // Assert
            assertNotNull(converter.getSelectableValues());
            assertEquals(3, converter.getSelectableValues().size());
        }
    }

    @Nested
    @DisplayName("Translation tests")
    class TranslationTests {

        @Test
        @DisplayName("Should provide correct translations for enum values")
        void shouldProvideCorrectTranslations() {
            // Arrange
            final var expected = new TreeSet<String>();
            for (final TestEnum testEnum : TestEnum.values()) {
                expected.add(testEnum.getLabelKey());
            }

            // Act
            final var actual = new TreeSet<String>();
            for (final SelectItem selectItem : converter.getSelectableValues()) {
                actual.add(selectItem.getLabel());
            }

            // Assert
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("Selection tests")
    class SelectionTests {

        @Test
        @DisplayName("Should handle selection and value changes properly")
        void shouldSelectProperly() {
            // Arrange & Assert initial state
            assertNull(converter.getSelectedValue());
            assertFalse(converter.isValueSelected());

            // Act & Assert - set selected value
            converter.setSelectedValue(TestEnum.DAY);
            assertTrue(converter.isValueSelected());
            assertEquals(TestEnum.DAY, converter.getSelectedValue());

            // Act & Assert - process value change
            converter.processValueChange(new ValueChangeEvent(getComponent(), TestEnum.DAY, TestEnum.MONTH));
            assertEquals(TestEnum.MONTH, converter.getSelectedValue());
        }
    }

    @Override
    @DisplayName("Populate test items for enum conversion")
    public void populate(final TestItems<TestEnum> testItems) {
        // Add valid enum values for roundtrip testing
        testItems.addRoundtripValues(
                TestEnum.DAY.toString(),
                TestEnum.MONTH.toString(),
                TestEnum.YEAR.toString());
    }
}
