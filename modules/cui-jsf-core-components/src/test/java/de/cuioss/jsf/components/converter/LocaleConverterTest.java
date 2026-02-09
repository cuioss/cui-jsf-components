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

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@DisplayName("Tests for LocaleConverter")
class LocaleConverterTest extends AbstractConverterTest<LocaleConverter, Locale> {

    @Override
    @DisplayName("Configure test items for locale conversion")
    public void populate(final TestItems<Locale> testItems) {
        // Test simple language locales
        testItems.addRoundtripValues("en");
        testItems.addRoundtripValues("de");
        testItems.addRoundtripValues("fr");

        // Test language and country locales
        testItems.addRoundtripValues("en-US");
        testItems.addRoundtripValues("de-DE");
        testItems.addRoundtripValues("fr-FR");

        // Test language, country and variant locales
        testItems.addRoundtripValues("en-US-POSIX");

        // Test with null value
        testItems.addValidObjectWithStringResult(null, "");
    }

    @Test
    @DisplayName("Should convert locale to language tag string")
    void shouldConvertLocaleToString(FacesContext facesContext) {
        // Arrange
        var converter = new LocaleConverter();
        var component = getComponent();
        var locale = Locale.GERMANY;

        // Act
        var result = converter.getAsString(facesContext, component, locale);

        // Assert
        assertEquals("de-DE", result, "Should convert Locale.GERMANY to 'de-DE'");
    }

    @Test
    @DisplayName("Should convert language tag string to locale")
    void shouldConvertStringToLocale(FacesContext facesContext) {
        // Arrange
        var converter = new LocaleConverter();
        var component = getComponent();
        var languageTag = "de-DE";

        // Act
        var result = converter.getAsObject(facesContext, component, languageTag);

        // Assert
        assertEquals(Locale.GERMANY, result, "Should convert 'de-DE' to Locale.GERMANY");
    }

    @Test
    @DisplayName("Should handle invalid language tag")
    void shouldHandleInvalidLanguageTag(FacesContext facesContext) {
        // Arrange
        var converter = new LocaleConverter();
        var component = getComponent();

        // Act & Assert
        // Invalid language tags should still be parsed by Locale.forLanguageTag without throwing exceptions
        // but may result in unexpected locales
        var result = converter.getAsObject(facesContext, component, "invalid-tag");
        assertEquals("tag", result.getLanguage(), "Should parse 'tag' as language");
        assertEquals("", result.getCountry(), "Country should be empty");
    }
}
