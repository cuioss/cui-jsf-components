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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.jsf.api.components.css.CssCommon;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.test.el.MockValueExpression;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

@EnableJsfEnvironment
@DisplayName("Tests for DecoratingResponseWriter")
class DecoratingResponseWriterTest {

    private static final String SIMPLE_DIV = "<div />";

    private static final String TITLE = "Some Title";

    private static final String SIMPLE_DIV_WITH_TITLE = "<div title=\"" + TITLE + "\" />";

    private static final String COMPONENT_ID = "componentId";

    private static final String SIMPLE_DIV_WITH_COMPONENT_ID = "<div id=\"componentId\" name=\"componentId\"></div>";

    private static final String SIMPLE_DIV_WITH_COMPUTED_COMPONENT_ID = "<div id=\"j_id__v_0\" name=\"j_id__v_0\"></div>";

    private static final String SIMPLE_DIV_WITH_NESTED_SPAN = "<div><span></span></div>";

    private static final String NESTED_WITH_STYLE_CLASS = "<div><span class=\"pull-left\"></span></div>";

    private static final String NESTED_WITH_ID = "<div><span id=\"id\" name=\"id\"></span></div>";

    private static final String NESTED_WITH_SUFFIXED_ID = "<div><span id=\"id_suffix\" name=\"id_suffix\"></span></div>";

    private static final String ID_SUFFIX = "suffix";

    private static final String TEXT_CONTENT = "<e@mail.de>";

    private static final String SIMPLE_DIV_WITH_UNESCAPED_TEXT = "<div><e@mail.de></div>";

    private static final String SIMPLE_DIV_WITH_ESCAPED_TEXT = "<div>&lt;e@mail.de&gt;</div>";

    private static final String INPUT_TYPE_TEXT = "<input type=\"text\" />";

    private static final String INPUT_HIDDEN_SUFFIX = "inputHiddenId";

    private static final String INPUT_HIDDEN_CLIENT_ID = "id_" + INPUT_HIDDEN_SUFFIX;

    private static final String INPUT_HIDDEN_WITH_EXTENSION = "<input type=\"hidden\" id=\"" + INPUT_HIDDEN_CLIENT_ID
            + "\" name=\"" + INPUT_HIDDEN_CLIENT_ID + "\" value=\"value\" />";

    public static final String PASS_THROUGH_NAME = "passThroughName";

    public static final String PASS_THROUGH_VALUE = "passThroughValue";

    private StringWriter sink;

    private UIComponent component;

    private DecoratingResponseWriter<UIComponent> responseWriter;

    @BeforeEach
    void before(FacesContext facesContext, ComponentConfigDecorator componentConfig) {
        sink = new StringWriter();
        component = new HtmlInputText();
        component.setId("id");
        facesContext.setResponseWriter(new MockResponseWriter(sink));
        responseWriter = new DecoratingResponseWriter<>(facesContext, component);
        componentConfig.registerMockRendererForHtmlInputText();
    }

    @Test
    @DisplayName("Should provide access to the component")
    void shouldProvideComponent() {
        // Act & Assert
        assertNotNull(responseWriter.getComponent(), "Component should not be null");
    }

    @Nested
    @DisplayName("Tests for basic HTML element rendering")
    class BasicHtmlElementTests {

        @Test
        @DisplayName("Should write a simple div element")
        void shouldWriteSimpleDiv() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV).withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }

        @Test
        @DisplayName("Should write a nested div with span element")
        void shouldWriteNestedDivWithSpan() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withStartElement(Node.SPAN)
                    .withEndElement(Node.SPAN)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV_WITH_NESTED_SPAN);
        }

        @Test
        @DisplayName("Should write an input text element")
        void shouldWriteInputText() throws Exception {
            // Act
            responseWriter.withStartElement(Node.INPUT)
                    .withAttribute(AttributeName.TYPE, AttributeValue.INPUT_TEXT)
                    .withEndElement(Node.INPUT);

            // Assert
            assertWritten(INPUT_TYPE_TEXT);
        }
    }

    @Nested
    @DisplayName("Tests for style class handling")
    class StyleClassTests {

        @Test
        @DisplayName("Should write nested div with style class provider")
        void shouldWriteNestedDivWithStyleClassProvider() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withStartElement(Node.SPAN)
                    .withStyleClass(CssCommon.PULL_LEFT)
                    .withEndElement(Node.SPAN)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(NESTED_WITH_STYLE_CLASS);
        }

        @Test
        @DisplayName("Should write nested div with style class builder")
        void shouldWriteNestedDivWithStyleClassBuilder() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withStartElement(Node.SPAN)
                    .withStyleClass(CssCommon.PULL_LEFT.getStyleClass())
                    .withEndElement(Node.SPAN)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(NESTED_WITH_STYLE_CLASS);
        }

        @Test
        @DisplayName("Should write nested div with class attribute")
        void shouldWriteNestedDivWithAttribute() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withStartElement(Node.SPAN)
                    .withAttribute(AttributeName.CLASS, CssCommon.PULL_LEFT.getStyleClass())
                    .withEndElement(Node.SPAN)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(NESTED_WITH_STYLE_CLASS);
        }
    }

    @Nested
    @DisplayName("Tests for client ID handling")
    class ClientIdTests {

        @Test
        @DisplayName("Should write nested div with client ID")
        void shouldWriteNestedDivWithClientId() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withStartElement(Node.SPAN)
                    .withClientId()
                    .withEndElement(Node.SPAN)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(NESTED_WITH_ID);
        }

        @Test
        @DisplayName("Should write nested div with suffixed client ID")
        void shouldWriteNestedDivWithSuffixedClientId() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withStartElement(Node.SPAN)
                    .withClientId(ID_SUFFIX)
                    .withEndElement(Node.SPAN)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(NESTED_WITH_SUFFIXED_ID);
        }

        @Test
        @DisplayName("Should not write client ID if not set")
        void shouldNotWriteClientIdIfNotSet() throws Exception {
            // Arrange
            component.setId(null);
            responseWriter.withStartElement(Node.DIV);

            // Act
            responseWriter.withClientIdIfNecessary();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }

        @Test
        @DisplayName("Should write client ID if set")
        void shouldWriteClientIdIfSet() throws Exception {
            // Arrange
            component.setId(COMPONENT_ID);
            responseWriter.withStartElement(Node.DIV);

            // Act
            responseWriter.withClientIdIfNecessary();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV_WITH_COMPONENT_ID);
        }

        @Test
        @DisplayName("Should not write client ID if ID is generated")
        void shouldNotWriteClientIdIfIDIsGenerated() throws Exception {
            // Arrange
            component.setId(UIViewRoot.UNIQUE_ID_PREFIX);
            responseWriter.withStartElement(Node.DIV);

            // Act
            responseWriter.withClientIdIfNecessary();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }

        @Test
        @DisplayName("Should write client ID for client behavior")
        void shouldWriteClientIdForClientBehavior(FacesContext facesContext) throws Exception {
            // Arrange
            final var htmlInputText = new HtmlInputText();
            htmlInputText.addClientBehavior("click", new AjaxBehavior());
            responseWriter = new DecoratingResponseWriter<>(facesContext, htmlInputText);
            responseWriter.withStartElement(Node.DIV);

            // Act
            responseWriter.withClientIdIfNecessary();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV_WITH_COMPUTED_COMPONENT_ID);
        }
    }


    @Nested
    @DisplayName("Tests for text content handling")
    class TextContentTests {

        @Test
        @DisplayName("Should write div with unescaped text content")
        void shouldWriteDivWithUnescapedTextChild() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withTextContent(TEXT_CONTENT, false)
                    .withEndElement(Node.DIV);

            // Assert
            assertEquals(SIMPLE_DIV_WITH_UNESCAPED_TEXT, sink.toString(),
                    "Should render div with unescaped text content");
        }

        @Test
        @DisplayName("Should not write text content if empty")
        void shouldNotWriteTextIfEmpty() throws Exception {
            // Arrange
            responseWriter.withStartElement(Node.DIV);

            // Act
            responseWriter.withTextContent(null, true);
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }

        @Test
        @DisplayName("Should write div with escaped text content")
        void shouldWriteDivWithEscapedTextChild() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV)
                    .withTextContent(TEXT_CONTENT, true)
                    .withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV_WITH_ESCAPED_TEXT);
        }
    }

    @Nested
    @DisplayName("Tests for pass-through attributes")
    class PassThroughAttributeTests {

        @Test
        @DisplayName("Should not write pass-through attributes if not set")
        void shouldNotWritePassThroughAttributesIfNotSet() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withPassThroughAttributes();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }

        @Test
        @DisplayName("Should write pass-through attributes")
        void shouldWritePassThroughAttributes() throws Exception {
            // Arrange
            component.getPassThroughAttributes().put(PASS_THROUGH_NAME, PASS_THROUGH_VALUE);

            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withPassThroughAttributes();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten("<div passThroughName=\"passThroughValue\"></div>");
        }

        @Test
        @DisplayName("Should write pass-through attributes with EL expression")
        void shouldWritePassThroughAttributesWithEl(FacesContext facesContext) throws Exception {
            // Arrange
            var mockValueExpression = new MockValueExpression("#{test.expression}", Integer.class);
            mockValueExpression.setValue(facesContext.getELContext(), 1);
            component.getPassThroughAttributes().put(PASS_THROUGH_NAME, mockValueExpression);

            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withPassThroughAttributes();
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten("<div passThroughName=\"1\"></div>");
        }
    }

    @Nested
    @DisplayName("Tests for hidden field handling")
    class HiddenFieldTests {

        @Test
        @DisplayName("Should write hidden field")
        void shouldWriteHiddenField() throws Exception {
            // Act
            responseWriter.withHiddenField(INPUT_HIDDEN_SUFFIX, "value");

            // Assert
            assertWritten(INPUT_HIDDEN_WITH_EXTENSION);
        }
    }

    @Nested
    @DisplayName("Tests for title attribute handling")
    class TitleAttributeTests {

        @Test
        @DisplayName("Should write title attribute")
        void shouldWriteTitleAttribute() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withAttributeTitle(TITLE);
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV_WITH_TITLE);
        }

        @Test
        @DisplayName("Should not write title if null")
        void shouldNotWriteTitleIfNull() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withAttributeTitle((String) null);
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }
    }

    @Nested
    @DisplayName("Tests for style attribute handling")
    class StyleAttributeTests {

        @Test
        @DisplayName("Should not write style if null")
        void shouldNotWriteStyleIfNull() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withAttributeStyle((String) null);
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten(SIMPLE_DIV);
        }

        @Test
        @DisplayName("Should write style attribute")
        void shouldWriteStyleAttribute() throws Exception {
            // Act
            responseWriter.withStartElement(Node.DIV);
            responseWriter.withAttributeStyle("style");
            responseWriter.withEndElement(Node.DIV);

            // Assert
            assertWritten("<div style=\"style\"></div>");
        }
    }

    @Nested
    @DisplayName("Tests for JavaScript handling")
    class JavaScriptTests {

        @Test
        @DisplayName("Should escape JavaScript identifier")
        void shouldEscapeJavaScriptId() {
            // Act
            var actual = DecoratingResponseWriter.escapeJavaScriptIdentifier("a:b_c");
            var expected = "a\\:b_c";

            // Assert
            assertEquals(expected, actual, "Should properly escape colons in JavaScript identifiers");
        }
    }

    private void assertWritten(final String expected) {
        var builderExpected = new HtmlTreeBuilder(expected);
        var builderActual = new HtmlTreeBuilder(sink.toString());
        HtmlTreeAsserts.assertHtmlTreeEquals(builderExpected.getDocument(), builderActual.getDocument());
    }

}
