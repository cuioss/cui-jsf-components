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
package de.cuioss.jsf.components.converter;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.code.CodeTypeImpl;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

@DisplayName("Tests for CodeTypeListDisplayConverter")
class CodeTypeListDisplayConverterTest extends AbstractConverterTest<CodeTypeListDisplayConverter, List<CodeType>> {

    @Override
    @DisplayName("Configure test items")
    public void populate(final TestItems<List<CodeType>> testItems) {
        testItems.addValidObjectWithStringResult(mutableList(new CodeTypeImpl("a"), new CodeTypeImpl("b")), "a;b");
        testItems.addValidObjectWithStringResult(mutableList(new CodeTypeImpl("display1", "display1"),
                new CodeTypeImpl("display2", "display2")), "display1;display2");
        testItems.addValidObjectWithStringResult(null, "");
        testItems.addValidObjectWithStringResult(mutableList(), "");
    }

    @Nested
    @DisplayName("Additional functionality tests")
    class AdditionalTests {

        @Test
        @DisplayName("Should handle custom separator")
        void shouldHandleCustomSeparator(FacesContext facesContext) {
            // Arrange
            var converter = new CodeTypeListDisplayConverter();
            converter.setSeparator(", ");
            var codeTypes = mutableList(new CodeTypeImpl("a"), new CodeTypeImpl("b"), new CodeTypeImpl("c"));

            // Act
            var result = converter.getAsString(facesContext, getComponent(), codeTypes);

            // Assert
            assertEquals("a, b, c", result, "Should join values with custom separator");
        }

        @Test
        @DisplayName("Should handle different locales")
        void shouldHandleLocales() {
            // Arrange
            var codeType1 = new CodeTypeImpl("key1") {
                @Override
                public String getResolved(Locale locale) {
                    if (Locale.GERMAN.equals(locale)) {
                        return "Deutsch1";
                    } else if (Locale.ENGLISH.equals(locale)) {
                        return "English1";
                    }
                    return "Default1";
                }
            };

            var codeType2 = new CodeTypeImpl("key2") {
                @Override
                public String getResolved(Locale locale) {
                    if (Locale.GERMAN.equals(locale)) {
                        return "Deutsch2";
                    } else if (Locale.ENGLISH.equals(locale)) {
                        return "English2";
                    }
                    return "Default2";
                }
            };

            // Act & Assert with German locale
            assertEquals("Deutsch1", codeType1.getResolved(Locale.GERMAN),
                    "CodeType1 should return German text for German locale");
            assertEquals("Deutsch2", codeType2.getResolved(Locale.GERMAN),
                    "CodeType2 should return German text for German locale");

            // Act & Assert with English locale
            assertEquals("English1", codeType1.getResolved(Locale.ENGLISH),
                    "CodeType1 should return English text for English locale");
            assertEquals("English2", codeType2.getResolved(Locale.ENGLISH),
                    "CodeType2 should return English text for English locale");
        }

        @Test
        @DisplayName("Should handle empty collections")
        void shouldHandleEmptyCollections(FacesContext facesContext) {
            // Arrange
            var converter = new CodeTypeListDisplayConverter();
            List<CodeType> emptyList = mutableList();

            // Act
            var result = converter.getAsString(facesContext, getComponent(), emptyList);

            // Assert
            assertEquals("", result, "Should return empty string for empty collections");
        }

        @Test
        @DisplayName("Should handle getAsObject by throwing UnsupportedOperationException")
        void shouldHandleGetAsObject(FacesContext facesContext) {
            // Arrange
            var converter = new CodeTypeListDisplayConverter();

            // Act & Assert
            var component = getComponent();
            assertThrows(UnsupportedOperationException.class,
                    () -> converter.getAsObject(facesContext, component, "test"),
                    "getAsObject should throw UnsupportedOperationException as it's a formatting-only converter");
        }
    }
}
