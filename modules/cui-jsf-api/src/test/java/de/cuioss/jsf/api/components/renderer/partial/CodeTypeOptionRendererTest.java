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
package de.cuioss.jsf.api.components.renderer.partial;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts;
import de.cuioss.uimodel.model.code.CodeTypeImpl;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

@EnableJsfEnvironment
@DisplayName("Tests for CodeTypeOptionRenderer")
class CodeTypeOptionRendererTest {

    private static final String RESOLVED = "resolved";

    private static final String IDENTIFIER = "identifier";

    private static final String OPTION_1_HTML = "<option value=\"identifier\">resolved</option>";

    private static final String OPTION_2_HTML = "<option value=\"resolved\">identifier</option>";

    private StringWriter output;

    private DecoratingResponseWriter<UIComponent> responseWriter;

    @BeforeEach
    void before(FacesContext facesContext) {
        output = new StringWriter();
        UIComponent component = new HtmlInputText();
        component.setId("id");
        facesContext.setResponseWriter(new MockResponseWriter(output));
        responseWriter = new DecoratingResponseWriter<>(facesContext, component);
    }

    @Nested
    @DisplayName("Tests for rendering code type options")
    class RenderingTests {

        @Test
        @DisplayName("Should render a single code type option element")
        void shouldRenderSingleElement() throws IOException {
            // Arrange
            var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
            var renderer = new CodeTypeOptionRenderer(type);

            // Act
            renderer.render(responseWriter, null, false);

            // Assert
            assertWritten(OPTION_1_HTML);
        }

        @Test
        @DisplayName("Should render multiple code type option elements")
        void shouldRenderMultipleElements() throws IOException {
            // Arrange
            var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
            var type2 = new CodeTypeImpl(IDENTIFIER, RESOLVED);
            var renderer = new CodeTypeOptionRenderer(immutableList(type, type2));

            // Act
            renderer.render(responseWriter, null, false);

            // Assert
            assertWritten(OPTION_1_HTML + OPTION_2_HTML);
        }
    }

    private void assertWritten(final String expected) {
        var builderExpected = new HtmlTreeBuilder(expected);
        var builderActual = new HtmlTreeBuilder(output.toString());
        HtmlTreeAsserts.assertHtmlTreeEquals(builderExpected.getDocument(), builderActual.getDocument());
    }
}
