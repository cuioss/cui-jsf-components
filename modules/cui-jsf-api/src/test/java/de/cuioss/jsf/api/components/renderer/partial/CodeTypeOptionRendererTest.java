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

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.renderer.util.HtmlTreeAsserts;
import de.cuioss.uimodel.model.code.CodeTypeImpl;

class CodeTypeOptionRendererTest extends JsfEnabledTestEnvironment {

    private static final String RESOLVED = "resolved";

    private static final String IDENTIFIER = "identifier";

    private static final String OPTION_1_HTML = "<option value=\"identifier\">resolved</option>";

    private static final String OPTION_2_HTML = "<option value=\"resolved\">identifier</option>";

    private StringWriter output;

    private UIComponent component;

    private DecoratingResponseWriter<UIComponent> responseWriter;

    @BeforeEach
    void before() {
        output = new StringWriter();
        component = new HtmlInputText();
        component.setId("id");
        getFacesContext().setResponseWriter(new MockResponseWriter(output));
        responseWriter = new DecoratingResponseWriter<>(getFacesContext(), component);
    }

    @Test
    void shouldRenderSingleElement() throws IOException {
        var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
        var renderer = new CodeTypeOptionRenderer(type);
        renderer.render(responseWriter, null, false);
        assertWritten(OPTION_1_HTML);
    }

    @Test
    void shouldRenderMultipleElements() throws IOException {
        var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);
        var type2 = new CodeTypeImpl(IDENTIFIER, RESOLVED);
        var renderer = new CodeTypeOptionRenderer(immutableList(type, type2));
        renderer.render(responseWriter, null, false);
        assertWritten(OPTION_1_HTML + OPTION_2_HTML);
    }

    private void assertWritten(final String expected) {
        var builderExpected = new HtmlTreeBuilder(expected);
        var builderActutal = new HtmlTreeBuilder(output.toString());
        HtmlTreeAsserts.assertHtmlTreeEquals(builderExpected.getDocument(), builderActutal.getDocument());
    }
}
