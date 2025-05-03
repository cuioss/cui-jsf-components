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
package de.cuioss.jsf.api.components.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cuioss.jsf.api.components.css.CssCommon;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlInputText;
import org.apache.myfaces.test.el.MockValueExpression;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

class DecoratingResponseWriterTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

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
    void before() {
        sink = new StringWriter();
        component = new HtmlInputText();
        component.setId("id");
        getFacesContext().setResponseWriter(new MockResponseWriter(sink));
        responseWriter = new DecoratingResponseWriter<>(getFacesContext(), component);
    }

    @Test
    void shouldProvideComponent() {
        assertNotNull(responseWriter.getComponent());
    }

    @Test
    void shouldWriteSimpleDiv() throws IOException {
        responseWriter.withStartElement(Node.DIV).withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldWriteNestedDivWithSpan() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN).withEndElement(Node.SPAN)
                .withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV_WITH_NESTED_SPAN);
    }

    @Test
    void shouldWriteNestedDivWithStyleClassProvider() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN).withStyleClass(CssCommon.PULL_LEFT)
                .withEndElement(Node.SPAN).withEndElement(Node.DIV);
        assertWritten(NESTED_WITH_STYLE_CLASS);
    }

    @Test
    void shouldWriteNestedDivWithClientId() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN).withClientId().withEndElement(Node.SPAN)
                .withEndElement(Node.DIV);
        assertWritten(NESTED_WITH_ID);
    }

    @Test
    void shouldWriteNestedDivWithSuffixedClientId() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN).withClientId(ID_SUFFIX)
                .withEndElement(Node.SPAN).withEndElement(Node.DIV);
        assertWritten(NESTED_WITH_SUFFIXED_ID);
    }

    @Test
    void shouldWriteInputText() throws IOException {
        responseWriter.withStartElement(Node.INPUT).withAttribute(AttributeName.TYPE, AttributeValue.INPUT_TEXT)
                .withEndElement(Node.INPUT);
        assertWritten(INPUT_TYPE_TEXT);
    }

    @Test
    void shouldWriteNestedDivWithStyleClassBuilder() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN)
                .withStyleClass(CssCommon.PULL_LEFT.getStyleClassBuilder()).withEndElement(Node.SPAN)
                .withEndElement(Node.DIV);
        assertWritten(NESTED_WITH_STYLE_CLASS);
    }

    @Test
    void shouldWriteNestedDivWithStyleClassString() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN)
                .withStyleClass(CssCommon.PULL_LEFT.getStyleClass()).withEndElement(Node.SPAN).withEndElement(Node.DIV);
        assertWritten(NESTED_WITH_STYLE_CLASS);
    }

    @Test
    void shouldWriteDivWithUnescapedTextChild() throws IOException {
        responseWriter.withStartElement(Node.DIV).withTextContent(TEXT_CONTENT, false).withEndElement(Node.DIV);
        assertEquals(SIMPLE_DIV_WITH_UNESCAPED_TEXT, sink.toString());
    }

    @Test
    void shouldNotWriteTextIfEmpty() throws IOException {
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withTextContent(null, true);
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldWriteDivWithEscapedTextChild() throws IOException {
        responseWriter.withStartElement(Node.DIV).withTextContent(TEXT_CONTENT, true).withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV_WITH_ESCAPED_TEXT);
    }

    @Test
    void shouldWriteNestedDivWithAttribute() throws IOException {
        responseWriter.withStartElement(Node.DIV).withStartElement(Node.SPAN)
                .withAttribute(AttributeName.CLASS, CssCommon.PULL_LEFT.getStyleClass()).withEndElement(Node.SPAN)
                .withEndElement(Node.DIV);
        assertWritten(NESTED_WITH_STYLE_CLASS);
    }

    @Test
    void shouldNotWriteClientIdIfNotSet() throws IOException {
        component.setId(null);
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withClientIdIfNecessary();
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldWriteClientIdIfSet() throws IOException {
        component.setId(COMPONENT_ID);
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withClientIdIfNecessary();
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV_WITH_COMPONENT_ID);
    }

    @Test
    void shouldNotWriteClientIdIfIDIsGenerated() throws IOException {
        component.setId(UIViewRoot.UNIQUE_ID_PREFIX);
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withClientIdIfNecessary();
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldWriteClientIdForClientBehavior() throws IOException {
        final var htmlInputText = new HtmlInputText();
        htmlInputText.addClientBehavior("click", new AjaxBehavior());
        responseWriter = new DecoratingResponseWriter<>(getFacesContext(), htmlInputText);
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withClientIdIfNecessary();
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV_WITH_COMPUTED_COMPONENT_ID);
    }

    @Test
    void shouldNotWritePassThroughAttributesIfNotSet() throws IOException {
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withPassThroughAttributes();
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldWritePassThroughAttributes() throws IOException {
        component.getPassThroughAttributes().put(PASS_THROUGH_NAME, PASS_THROUGH_VALUE);
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withPassThroughAttributes();
        responseWriter.withEndElement(Node.DIV);
        assertWritten("<div passThroughName=\"passThroughValue\"></div>");
    }

    @Test
    void shouldWritePassThroughAttributesWithEl() throws IOException {
        var mockValueExpression = new MockValueExpression("#{test.expression}", Integer.class);
        mockValueExpression.setValue(getFacesContext().getELContext(), 1);
        component.getPassThroughAttributes().put(PASS_THROUGH_NAME, mockValueExpression);
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withPassThroughAttributes();
        responseWriter.withEndElement(Node.DIV);
        assertWritten("<div passThroughName=\"1\"></div>");
    }

    @Test
    void shouldWriteHiddenField() throws IOException {
        responseWriter.withHiddenField(INPUT_HIDDEN_SUFFIX, "value");
        assertWritten(INPUT_HIDDEN_WITH_EXTENSION);
    }

    @Test
    void shouldNotWriteTitleIfNull() throws IOException {
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withAttributeTitle(TITLE);
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV_WITH_TITLE);
    }

    @Test
    void shouldNotWriteTitleIfEmpty() throws IOException {
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withAttributeTitle((String) null);
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldNotWriteStyleIfEmpty() throws IOException {
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withAttributeStyle((String) null);
        responseWriter.withEndElement(Node.DIV);
        assertWritten(SIMPLE_DIV);
    }

    @Test
    void shouldWriteStyleAttribute() throws IOException {
        responseWriter.withStartElement(Node.DIV);
        responseWriter.withAttributeStyle("style");
        responseWriter.withEndElement(Node.DIV);
        assertWritten("<div style=\"style\"></div>");
    }

    @Test
    void shouldEscapeJavaScriptId() {
        var actual = DecoratingResponseWriter.escapeJavaScriptIdentifier("a:b_c");
        var expected = "a\\:b_c";
        assertEquals(expected, actual);
    }

    private void assertWritten(final String expected) {
        var builderExpected = new HtmlTreeBuilder(expected);
        var builderActual = new HtmlTreeBuilder(sink.toString());
        HtmlTreeAsserts.assertHtmlTreeEquals(builderExpected.getDocument(), builderActual.getDocument());
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlInputText();
    }
}
