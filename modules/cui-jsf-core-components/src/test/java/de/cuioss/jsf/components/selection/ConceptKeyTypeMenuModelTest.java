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

import static de.cuioss.jsf.components.selection.ConceptKeyTypeGenerator.*;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.test.valueobjects.property.util.CollectionType;
import de.cuioss.tools.property.PropertyReadWrite;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.event.ValueChangeEvent;
import org.apache.myfaces.test.mock.MockFacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@PropertyReflectionConfig(skip = true)
@PropertyConfig(name = "sourceData", propertyClass = ConceptKeyType.class, generator = ConceptKeyTypeGenerator.class, collectionType = CollectionType.SET, propertyReadWrite = PropertyReadWrite.WRITE_ONLY)
@PropertyConfig(name = "locale", propertyClass = Locale.class, propertyReadWrite = PropertyReadWrite.WRITE_ONLY)
@VerifyConstructor(of = {"sourceData", "locale"}, required = "locale")
@DisplayName("Tests for ConceptKeyTypeMenuModel")
class ConceptKeyTypeMenuModelTest extends ValueObjectTest<ConceptKeyTypeMenuModel> {

    private static ConceptKeyTypeMenuModel testModel() {
        return new ConceptKeyTypeMenuModel(mutableSet(TEST_CODE, TEST_CODE2), Locale.ENGLISH);
    }

    @Nested
    @DisplayName("Selection tests")
    class SelectionTests {

        @Test
        @DisplayName("Should set selected value")
        void setSelected() {
            // Arrange
            var sut = testModel();
            assertEquals(2, sut.getSelectableValues().size());

            // Act
            sut.setSelectedValue(TEST_CODE2);

            // Assert
            assertEquals(2, sut.getSelectableValues().size());
            assertEquals(TEST_CODE2, sut.getSelectedValue());
        }
    }

    @Nested
    @DisplayName("Value change processing tests")
    class ValueChangeProcessingTests {

        @Test
        @DisplayName("Should process value change and set selected value")
        void shouldProcessValueChangeAndSetSelectedValue() {
            // Arrange
            final var sut = testModel();
            final var selected = TEST_CODE;
            sut.setSelectedValue(selected);
            assertEquals(selected, sut.getSelectedValue());

            // Act & Assert - null value change
            sut.processValueChange(new ValueChangeEvent(new HtmlInputText(), null, null));
            // No assertion as the commented line suggests this behavior is expected
            // Assertions.assertEquals(selected, sut.getSelectedValue());

            // Act & Assert - valid value change
            final var selected2 = TEST_CODE2;
            sut.processValueChange(new ValueChangeEvent(new HtmlInputText(), null, selected2));
            assertEquals(selected2, sut.getSelectedValue());

            // Act & Assert - undefined value
            final var undefined = new BaseConceptCategory().createUndefinedConceptKey("undefined");
            assertEquals(2, sut.getSelectableValues().size());
            sut.setSelectedValue(undefined);
            assertEquals(undefined, sut.getSelectedValue());
            assertEquals(2 + 1, sut.getSelectableValues().size());
        }
    }

    @Nested
    @DisplayName("Default value tests")
    class DefaultValueTests {

        @Test
        @DisplayName("Should initialize to default value")
        void shouldSetDefault() {
            // Arrange
            final var model = new ConceptKeyTypeMenuModel(mutableSet(TEST_CODE, TEST_CODE2, TEST_DEFAULT_CODE),
                    Locale.ENGLISH);

            // Act
            model.initToDefault();

            // Assert
            assertEquals(TEST_DEFAULT_CODE, model.getSelectedValue());
        }
    }

    @Nested
    @DisplayName("Undefined value handling tests")
    class UndefinedValueHandlingTests {

        @Test
        @DisplayName("Should handle undefined concept key")
        void shouldHandleUndefined() {
            // Arrange
            final var sut = testModel();
            var selected = new BaseConceptCategory().createUndefinedConceptKey("bla");
            assertEquals(2, sut.getSelectableValues().size());

            // Act
            sut.setSelectedValue(selected);

            // Assert
            assertEquals(3, sut.getSelectableValues().size());
            assertEquals(selected, sut.getSelectableValues().get(0).getValue());
            assertTrue(sut.getSelectableValues().get(0).isDisabled());
            assertEquals("bla", sut.getAsString(new MockFacesContext(), new DummyComponent(), selected));
        }
    }

    @Nested
    @DisplayName("Conversion tests")
    class ConversionTests {

        @Test
        @DisplayName("Should convert any ConceptKeyType object to string")
        void shouldConvertAnyObject() {
            // Arrange
            final var sut = testModel();
            var conceptKey = ConceptKeyTypeImpl.builder()
                    .identifier("dummy")
                    .labelResolver(new I18nDisplayNameProvider("dummyValue"))
                    .category(new BaseConceptCategory())
                    .build();

            // Act
            var result = sut.getAsString(new MockFacesContext(), new DummyComponent(), conceptKey);

            // Assert
            assertEquals("dummy", result);
        }
    }

}
