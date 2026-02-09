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
package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

@EnableJsfEnvironment
@DisplayName("Tests for ElementReplacingResponseWriter")
class ElementReplacingResponseWriterTest {

    static final String FILTER = "filter";

    static final String REPLACEMENT = "replacement";

    static final String PASSTHROUGH = "passthrough";

    private UIComponent component;

    private StringWriter output;

    @BeforeEach
    void beforeTest(FacesContext facesContext) {
        output = new StringWriter();
        facesContext.setResponseWriter(new MockResponseWriter(output));
        component = new DummyComponent();
    }

    @Nested
    @DisplayName("Tests for element filtering and replacement")
    class ElementFilteringTests {

        @Test
        @DisplayName("Should replace filtered element with replacement element")
        void shouldFilterElement(FacesContext facesContext) throws Exception {
            // Arrange
            var writer = new ElementReplacingResponseWriter(facesContext.getResponseWriter(), FILTER, REPLACEMENT,
                    false);

            // Assert - initial state
            assertTrue(isEmpty(output.toString()),
                    "Output should be empty initially");

            // Act
            writer.startElement(FILTER, component);
            writer.endElement(FILTER);

            // Assert - after filtering
            assertTrue(output.toString().contains(REPLACEMENT),
                    "Output should contain the replacement element");
            assertFalse(output.toString().contains(FILTER),
                    "Output should not contain the filtered element");
            assertFalse(output.toString().contains(PASSTHROUGH),
                    "Output should not contain unrelated elements");
        }

        @Test
        @DisplayName("Should pass through non-filtered elements unchanged")
        void shouldPassthroughElement(FacesContext facesContext) throws Exception {
            // Arrange
            var writer = new ElementReplacingResponseWriter(facesContext.getResponseWriter(), FILTER, REPLACEMENT,
                    false);

            // Assert - initial state
            assertTrue(isEmpty(output.toString()),
                    "Output should be empty initially");

            // Act
            writer.startElement(PASSTHROUGH, component);
            writer.endElement(PASSTHROUGH);

            // Assert - after writing
            assertFalse(output.toString().contains(FILTER),
                    "Output should not contain the filtered element");
            assertTrue(output.toString().contains(PASSTHROUGH),
                    "Output should contain the passthrough element");
        }

        @Test
        @DisplayName("Should only write opening tag when closeElement is true")
        void shouldFilterCloseElement(FacesContext facesContext) throws Exception {
            // Arrange
            var writer = new ElementReplacingResponseWriter(facesContext.getResponseWriter(), FILTER, REPLACEMENT,
                    true);

            // Act
            writer.startElement(FILTER, component);
            writer.endElement(FILTER);

            // Assert
            assertEquals("<" + REPLACEMENT, output.toString(),
                    "Output should only contain the opening tag of the replacement element");
        }
    }
}
