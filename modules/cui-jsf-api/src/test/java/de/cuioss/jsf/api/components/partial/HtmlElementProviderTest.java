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
package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = HtmlElementProvider.KEY)
@ExplicitParamInjection
@DisplayName("Tests for HtmlElementProvider implementation")
class HtmlElementProviderTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null parameters")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new HtmlElementProvider(null, null),
                "Constructor should reject null parameters");
    }

    @Nested
    @DisplayName("Tests for HTML element resolution")
    class HtmlElementResolutionTests {

        @Test
        @DisplayName("Should use default HTML element when none is set")
        void shouldUseDefaultElement() {
            // Act & Assert
            assertEquals(Node.DIV, anyComponent().resolveHtmlElement(),
                    "Should use default DIV element when none is set");
        }

        @Test
        @DisplayName("Should resolve custom HTML element when set")
        void shouldResolveCustomElement() {
            // Arrange
            var underTest = anyComponent();

            // Act
            underTest.setHtmlElement(Node.NAV.getContent());

            // Assert
            assertEquals(Node.NAV, underTest.resolveHtmlElement(),
                    "Should resolve to NAV element when set to NAV");
        }
    }

    @Nested
    @DisplayName("Tests for invalid HTML element handling")
    class InvalidElementTests {

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid HTML element")
        void shouldRejectInvalidElement() {
            // Arrange
            var underTest = anyComponent();
            underTest.setHtmlElement("boom");

            // Act & Assert
            assertThrows(IllegalArgumentException.class, underTest::resolveHtmlElement,
                    "Should reject invalid HTML element with IllegalArgumentException");
        }
    }
}
