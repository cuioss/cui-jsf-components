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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.code.CodeTypeImpl;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@DisplayName("Tests for CodeTypeDisplayConverter")
class CodeTypeDisplayConverterTest extends AbstractConverterTest<CodeTypeDisplayConverter, CodeType> {

    @Override
    @DisplayName("Configure test items")
    public void populate(final TestItems<CodeType> testItems) {
        testItems.addValidObjectWithStringResult(new CodeTypeImpl("1"), "1");
        // For CodeTypeImpl, the first parameter is the identifier, the second is the display value
        // The converter should return the resolved value, which is the display value if present
        testItems.addValidObjectWithStringResult(new CodeTypeImpl("display", "display"), "display");
        testItems.addValidObjectWithStringResult(null, "");
    }

    @Test
    @DisplayName("Should handle CodeType with different locales")
    void shouldHandleLocales() {
        // Arrange
        var codeType = new CodeTypeImpl("key") {
            @Override
            public String getResolved(Locale locale) {
                if (Locale.GERMAN.equals(locale)) {
                    return "Deutsch";
                } else if (Locale.ENGLISH.equals(locale)) {
                    return "English";
                }
                return "Default";
            }
        };

        // Act & Assert with German locale
        assertEquals("Deutsch", codeType.getResolved(Locale.GERMAN),
                "Should return German text for German locale");

        // Act & Assert with English locale
        assertEquals("English", codeType.getResolved(Locale.ENGLISH),
                "Should return English text for English locale");
    }

    @Test
    @DisplayName("Should handle getAsObject by throwing UnsupportedOperationException")
    void shouldHandleGetAsObject(FacesContext facesContext) {
        // Arrange
        var converter = new CodeTypeDisplayConverter();

        // Act & Assert
        UIComponent component = getComponent();
        assertThrows(UnsupportedOperationException.class,
                () -> converter.getAsObject(facesContext, component, "test"),
                "getAsObject should throw UnsupportedOperationException as it's a formatting-only converter");
    }
}
