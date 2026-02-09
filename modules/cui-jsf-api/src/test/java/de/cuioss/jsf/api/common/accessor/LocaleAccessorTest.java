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
package de.cuioss.jsf.api.common.accessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;

// Class is documented by @DisplayName
@DisplayName("Tests for LocaleAccessor")
@EnableJsfEnvironment
@JsfTestConfiguration(BasicApplicationConfiguration.class)
@PropertyReflectionConfig(skip = true)
class LocaleAccessorTest {

    @Nested
    @DisplayName("Value Retrieval Tests")
    class ValueRetrievalTests {

        @Test
        @DisplayName("Should provide the correct locale from JSF context")
        void shouldProvideCorrectLocaleFromContext(FacesContext facesContext) {
            // Arrange
            var localeAccessor = new LocaleAccessor();

            // Act
            var result = localeAccessor.getValue();

            // Assert - using the actual locale from the test environment
            Locale expectedLocale = facesContext.getViewRoot().getLocale();
            assertEquals(expectedLocale, result, "Should return the locale from JSF context");

            // Act again - testing caching
            var cachedResult = localeAccessor.getValue();

            // Assert cached value
            assertEquals(expectedLocale, cachedResult, "Should return the same locale from cache");
        }
    }
}
